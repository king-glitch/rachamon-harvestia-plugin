package dev.rachamon.rachamonharvestia.listeners;

import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import dev.rachamon.rachamonharvestia.config.PlayerSettingsConfig;
import dev.rachamon.rachamonharvestia.structure.EntityData;
import dev.rachamon.rachamonharvestia.structure.PlantData;
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
        put("minecraft:wheat", new PlantData(BlockTypes.WHEAT, ItemTypes.WHEAT, ItemTypes.WHEAT_SEEDS, 7));
        put("minecraft:carrots", new PlantData(BlockTypes.CARROTS, ItemTypes.CARROT, ItemTypes.CARROT, 7));
        put("minecraft:potatoes", new PlantData(BlockTypes.POTATOES, ItemTypes.POTATO, ItemTypes.POTATO, 7));
        put("minecraft:beetroots", new PlantData(BlockTypes.BEETROOTS, ItemTypes.BEETROOT, ItemTypes.BEETROOT_SEEDS, 3));
        put("minecraft:cocoa", new PlantData(BlockTypes.COCOA, ItemTypes.DYE, ItemTypes.DYE, 2));
        put("minecraft:nether_wart", new PlantData(BlockTypes.NETHER_WART, ItemTypes.NETHER_WART, ItemTypes.NETHER_WART, 3));
    }};

    @Listener(order = Order.POST)
    public void onPlayerHarvestPlant(InteractBlockEvent.Primary.MainHand event, @Root Player player) {

        // check if block are plant

        BlockSnapshot targetBlock = event.getTargetBlock();
        PlantData plantData = PLANTS.get(targetBlock.getState().getType().getId().toLowerCase());

        if (plantData == null) {
            return;
        }

        Optional<MutableBoundedValue<Integer>> targetStage = targetBlock.getState().getValue(Keys.GROWTH_STAGE);

        if (!targetStage.isPresent()) {
            return;
        }


        if (player.get(Keys.IS_SNEAKING).orElse(false)) {

            this.plugin.getLogger().debug("player try to break block : " + targetStage.get().get());
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

        PlayerSettingsConfig.PlayerSetting playerSetting = RachamonHarvestia
                .getInstance()
                .getHarvestiaManager()
                .createPlayerSetting(player.getUniqueId());

        if (playerSetting == null) {
            return;
        }

        if (!playerSetting.isAutoReplant()) {
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
        this.processTrack(new EntityData(player.getUniqueId(), player, plantData));


    }

    private void processTrack(EntityData data) {
        this.track.put(data.living, data);
        Task.builder().delayTicks(20).execute(() -> this.track.remove(data.living)).submit(this.plugin);
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

        PlayerSettingsConfig.PlayerSetting playerSetting = RachamonHarvestia
                .getInstance()
                .getHarvestiaManager()
                .createPlayerSetting(data.player.getUniqueId());

        if (playerSetting == null) {
            return;
        }

        if (!playerSetting.isAutoPickupItem()) {
            return;
        }

        List<Item> items = event
                .getEntities()
                .stream()
                .filter(entity -> entity instanceof Item)
                .map(entity -> (Item) entity)
                .collect(Collectors.toList());

        if (items.isEmpty()) {
            return;
        }

        if (data.player == null) {
            return;
        }

        Task.builder().execute(() -> {
            boolean isCollected = false;
            for (Item item : items) {
                ItemStack stack = item.item().get().createStack();
                if (!isCollected && data.plantData.fuel == stack.getType()) {
                    stack.setQuantity(stack.getQuantity() - 1);
                    isCollected = true;
                }

                InventoryTransactionResult result = data.player
                        .getInventory()
                        .query(QueryOperationTypes.INVENTORY_TYPE.of(Hotbar.class))
                        .union(data.player
                                .getInventory()
                                .query(QueryOperationTypes.INVENTORY_TYPE.of(MainPlayerInventory.class)))
                        .offer(stack);

                if (stack.getType() == ItemTypes.AIR) {
                    item.remove();
                    continue;
                }

                if (result.getType() == InventoryTransactionResult.Type.SUCCESS) {
                    item.remove();
                    continue;
                }

                this.plugin.getLogger().debug("unsuccessful inventory : " + result.getType() + " : " + stack);
            }
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

        PlayerSettingsConfig.PlayerSetting playerSetting = RachamonHarvestia
                .getInstance()
                .getHarvestiaManager()
                .createPlayerSetting(data.player.getUniqueId());

        if (playerSetting == null) {
            return;
        }

        if (!playerSetting.isAutoPickupExp()) {
            return;
        }

        List<ExperienceOrb> orbs = event
                .getEntities()
                .stream()
                .filter(entity -> entity instanceof ExperienceOrb)
                .map(entity -> (ExperienceOrb) entity)
                .collect(Collectors.toList());

        if (orbs.isEmpty()) {
            return;
        }

        if (data.player == null) {
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
