package dev.rachamon.rachamonharvestia.listeners;

import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import dev.rachamon.rachamonharvestia.structure.EntityData;
import dev.rachamon.rachamonharvestia.structure.PlantData;
import dev.rachamon.rachamonharvestia.structure.PlayerSetting;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.entity.MainPlayerInventory;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PlayerHarvestListener {
    private final Map<UUID, EntityData> track = new HashMap<>();
    private final RachamonHarvestia plugin = RachamonHarvestia.getInstance();
    private final HashMap<String, PlantData> PLANTS = new HashMap<String, PlantData>() {{
        put(String.valueOf(BlockTypes.WHEAT), new PlantData(BlockTypes.WHEAT, ItemTypes.WHEAT, ItemTypes.WHEAT_SEEDS, 1, 3, 7));
    }};

    @Listener(order = Order.POST)
    public void onPlayerHarvestPlant(InteractBlockEvent.Primary.MainHand event, @Root Player player) {

        // check if block are plant

        BlockSnapshot targetBlock = event.getTargetBlock();
        PlantData plantData = PLANTS.get(targetBlock.getState().getType().toString());

        if (plantData == null) {
            this.plugin.getLogger().debug("Not Plant Block");
            return;
        }

        Optional<MutableBoundedValue<Integer>> targetStage = targetBlock.getState().getValue(Keys.GROWTH_STAGE);

        if (!targetStage.isPresent()) {
            this.plugin.getLogger().debug("Stage key not found on this block.");
            return;
        }


        if (plantData.stage != targetStage.get().get()) {
            this.plugin.getLogger().debug("this plant doesn't on last stage");
            return;
        }

        Optional<Location<World>> location = targetBlock.getLocation();

        if (!location.isPresent()) {
            this.plugin.getLogger().debug("location not found; might error ?");
            return;
        }

        if (!RachamonHarvestia.getInstance().getConfig().getMainCategorySetting().isAutoReplant()) {
            return;
        }

        if (!player.hasPermission(RachamonHarvestia
                .getInstance()
                .getConfig()
                .getPermissionCategorySetting()
                .getAutoReplantPermission())) {
            return;
        }

        PlayerSetting playerSetting = RachamonHarvestia
                .getInstance()
                .getPlayerSettings()
                .getSettings()
                .get(player.getUniqueId().toString());

        if (playerSetting != null && !playerSetting.isAutoReplant()) {
            return;
        }

        if (!RachamonHarvestia
                .getInstance()
                .getConfig()
                .getPermissionCategorySetting()
                .isPlantSeparatePermission() && !player.hasPermission(player.hasPermission(RachamonHarvestia
                .getInstance()
                .getConfig()
                .getPermissionCategorySetting()
                .getAutoReplantPermission()) + "." + plantData.block.getName().toLowerCase())) {
            return;
        }

        Task.builder().execute(() -> {
            BlockState oldState = targetBlock.getState();
            BlockState newState = oldState.with(Keys.GROWTH_STAGE, 0).orElse(oldState);
            location.get().setBlock(newState);
        }).delay(100, TimeUnit.MILLISECONDS).submit(this.plugin);

        // process track.
        this.processTrack(new EntityData(player.getUniqueId(), player));


    }

    private void processTrack(EntityData data) {
        this.track.put(data.living, data);
        Task.builder().delayTicks(100).execute(() -> this.track.remove(data.living)).submit(this.plugin);
    }

    @Listener(order = Order.EARLY)
    public void onPlantDrop(SpawnEntityEvent event, @First Living living) {

        if (!RachamonHarvestia.getInstance().getConfig().getMainCategorySetting().isAutoItemPickup()) {
            return;
        }

        EntityData data = this.track.get(living.getUniqueId());

        if (data == null) {
            RachamonHarvestia.getInstance().getLogger().debug("Data not found");
            return;
        }

        if (!data.player.hasPermission(RachamonHarvestia
                .getInstance()
                .getConfig()
                .getPermissionCategorySetting()
                .getAutoItemPickupPermission())) {
            return;
        }

        PlayerSetting playerSetting = RachamonHarvestia
                .getInstance()
                .getPlayerSettings()
                .getSettings()
                .get(data.player.getUniqueId().toString());
        if (playerSetting != null && !playerSetting.isAutoPickupItem()) {
            return;
        }

        List<Item> items = event
                .getEntities()
                .stream()
                .filter(entity -> entity instanceof Item)
                .map(entity -> (Item) entity)
                .collect(Collectors.toList());

        if (items.isEmpty()) {
            RachamonHarvestia.getInstance().getLogger().debug("item are empty");
            return;
        }

        if (data.player == null) {
            RachamonHarvestia.getInstance().getLogger().debug("Player not found");
            return;
        }

        Task.builder().execute(() -> {
            items.forEach(item -> {
                ItemStack stack = item.item().get().createStack();
                InventoryTransactionResult result = data.player
                        .getInventory()
                        .query(QueryOperationTypes.INVENTORY_TYPE.of(Hotbar.class))
                        .union(data.player
                                .getInventory()
                                .query(QueryOperationTypes.INVENTORY_TYPE.of(MainPlayerInventory.class)))
                        .offer(stack);
                if (result.getType() == InventoryTransactionResult.Type.SUCCESS) {
                    item.remove();
                    return;
                }

                this.plugin
                        .getLogger()
                        .debug("unsuccessful inventory : " + result.getType() + " : " + stack.toString());
            });

            this.plugin.getLogger().debug("success inventory");

        }).delay(100, TimeUnit.MILLISECONDS).submit(this.plugin);
    }

    @Listener(order = Order.EARLY)
    public void onExperienceOrbDrop(SpawnEntityEvent event, @First Living living) {

        if (!RachamonHarvestia.getInstance().getConfig().getMainCategorySetting().isAutoExpPickup()) {
            return;
        }

        EntityData data = this.track.get(living.getUniqueId());

        if (data == null) {
            RachamonHarvestia.getInstance().getLogger().debug("Data not found");
            return;
        }

        if (!data.player.hasPermission(RachamonHarvestia
                .getInstance()
                .getConfig()
                .getPermissionCategorySetting()
                .getAutoExpPickupPermission())) {
            return;
        }

        PlayerSetting playerSetting = RachamonHarvestia
                .getInstance()
                .getPlayerSettings()
                .getSettings()
                .get(data.player.getUniqueId().toString());
        if (playerSetting != null && !playerSetting.isAutoPickupExp()) {
            return;
        }

        List<ExperienceOrb> orbs = event
                .getEntities()
                .stream()
                .filter(entity -> entity instanceof ExperienceOrb)
                .map(entity -> (ExperienceOrb) entity)
                .collect(Collectors.toList());

        if (orbs.isEmpty()) {
            RachamonHarvestia.getInstance().getLogger().debug("Exp are empty");
            return;
        }

        if (data.player == null) {

            RachamonHarvestia.getInstance().getLogger().debug("Player not found");

            return;
        }

        Task.builder().execute(() -> {
            int exp = 0;
            for (ExperienceOrb orb : orbs)
                exp += orb.experience().get();

            int finalExp = exp;
            if (data.player.transform(Keys.TOTAL_EXPERIENCE, t -> t + finalExp).isSuccessful()) {
                orbs.clear();
            }

            this.plugin.getLogger().debug("success give exp");

        }).delay(100, TimeUnit.MILLISECONDS).submit(this.plugin);


    }

}
