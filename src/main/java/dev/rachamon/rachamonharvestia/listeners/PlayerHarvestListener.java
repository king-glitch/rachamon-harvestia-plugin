package dev.rachamon.rachamonharvestia.listeners;

import com.flowpowered.math.vector.Vector3d;
import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import dev.rachamon.rachamonharvestia.config.PlantsConfig;
import dev.rachamon.rachamonharvestia.config.PlayerSettingsConfig;
import dev.rachamon.rachamonharvestia.structure.EntityData;
import dev.rachamon.rachamonharvestia.structure.PlantData;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
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

/**
 * The type Player harvest listener.
 */
public class PlayerHarvestListener {
    private final Map<UUID, EntityData> track = new HashMap<>();
    private final RachamonHarvestia plugin = RachamonHarvestia.getInstance();

    /**
     * On player harvest plant.
     *
     * @param event  the event
     * @param player the player
     */
    @Listener(order = Order.POST)
    public void onPlayerHarvestPlant(ChangeBlockEvent.Break event, @Root Player player) {

        List<Transaction<BlockSnapshot>> transactions = event.getTransactions();

        for (Transaction<BlockSnapshot> transaction : transactions) {
            BlockSnapshot targetBlock = transaction.getOriginal();

            PlantsConfig.PlantDataConfig plantDataConfig = RachamonHarvestia
                    .getInstance()
                    .getAllPlants()
                    .get(targetBlock.getState().getType().getId().toLowerCase());

            this.plugin.getLogger().debug("harvest block:" + targetBlock.getState().getType().getId().toLowerCase());

            if (plantDataConfig == null) {
                return;
            }

            PlantData plantData = new PlantData(plantDataConfig.getBlock(), plantDataConfig.getFuel(), plantDataConfig.getStage());

            Optional<MutableBoundedValue<Integer>> targetStage = targetBlock.getState().getValue(Keys.GROWTH_STAGE);

            if (plantData.getStage() != 0 && !targetStage.isPresent()) {
                return;
            }


            plantData.setFullyGrown(!targetStage.isPresent() || plantData.getStage() == targetStage.get().get());

            if (player.get(Keys.IS_SNEAKING).orElse(false)) {
                return;
            }

            Optional<Location<World>> location = targetBlock.getLocation();

            if (!location.isPresent()) {
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
                    .getPlayerSettingOrCreate(player.getUniqueId());

            if (playerSetting == null) {
                return;
            }


            boolean isAutoReplant = playerSetting.isAutoReplant() && this.isPlayerHasAutoReplantPermission(player, plantData.getBlock()) && !RachamonHarvestia
                    .getInstance()
                    .getConfig()
                    .getMainCategorySetting()
                    .getReplantBlockBlacklists()
                    .contains(targetBlock.getState().getType().getId().toLowerCase());

            boolean isNeedDirt = RachamonHarvestia
                    .getInstance()
                    .getConfig()
                    .getMainCategorySetting()
                    .getNeedDirtCollection()
                    .contains(targetBlock.getState().getType().getId().toLowerCase());

            boolean isDirtInCollection = RachamonHarvestia
                    .getInstance()
                    .getConfig()
                    .getMainCategorySetting()
                    .getDirtCollection()
                    .contains(location
                            .get()
                            .setPosition(new Vector3d(location.get().getBlockX(), location
                                    .get()
                                    .getBlockY() - 1, location.get().getBlockZ()))
                            .getBlock()
                            .getType()
                            .getId()
                            .toLowerCase());

            Task.builder().execute(() -> {

                if (!isAutoReplant) {
                    return;
                }

                if (isNeedDirt && !isDirtInCollection) {
                    return;
                }

                BlockState oldState = targetBlock.getState();
                BlockState newState = oldState.with(Keys.GROWTH_STAGE, 0).orElse(oldState);
                location.get().setBlock(newState);

            }).delay(40, TimeUnit.MILLISECONDS).submit(this.plugin);

            // process track.
            this.processTrack(new EntityData(player.getUniqueId(), player, plantData, isAutoReplant && (!isNeedDirt || isDirtInCollection)));
        }

    }

    private boolean isPlayerHasAutoReplantPermission(Player player, String plant) {
        boolean isPlayerHasPermission = player.hasPermission(RachamonHarvestia
                .getInstance()
                .getConfig()
                .getPermissionCategorySetting()
                .getAutoReplantPermission());
        boolean isSeparatePermission = RachamonHarvestia
                .getInstance()
                .getConfig()
                .getPermissionCategorySetting()
                .isPlantSeparatePermission();
        boolean isPlayerHasSeparatePermission = player.hasPermission(RachamonHarvestia
                .getInstance()
                .getConfig()
                .getPermissionCategorySetting()
                .getAutoBaseReplantPermission() + "." + plant);
        return isPlayerHasPermission && (!isSeparatePermission || isPlayerHasSeparatePermission);
    }

    private void processTrack(EntityData data) {
        this.track.put(data.getLiving(), data);
        Task.builder().delayTicks(40).execute(() -> this.track.remove(data.getLiving())).submit(this.plugin);
    }

    /**
     * On plant drop.
     *
     * @param event  the event
     * @param living the living
     */
    @Listener(order = Order.EARLY)
    public void onPlantDrop(SpawnEntityEvent event, @First Living living) {

        EntityData data = this.track.get(living.getUniqueId());

        if (data == null) {
            return;
        }

        PlayerSettingsConfig.PlayerSetting playerSetting = RachamonHarvestia
                .getInstance()
                .getHarvestiaManager()
                .getPlayerSettingOrCreate(data.getPlayer().getUniqueId());

        if (playerSetting == null) {
            return;
        }

        List<Item> items = event
                .getEntities()
                .stream()
                .filter(entity -> entity instanceof Item)
                .map(entity -> (Item) entity)
                .collect(Collectors.toList());

        if (data.isPlantSuccessfully() && !data.getPlantData().isFullyGrown()) {
            Task.builder().execute(() -> {
                items.forEach(Entity::remove);
            }).delay(20, TimeUnit.MILLISECONDS).submit(this.plugin);
            return;
        }

        if (!data
                .getPlayer()
                .hasPermission(RachamonHarvestia
                        .getInstance()
                        .getConfig()
                        .getPermissionCategorySetting()
                        .getAutoItemPickupPermission())) {
            return;
        }

        if (!RachamonHarvestia.getInstance().getConfig().getMainCategorySetting().isAutoItemPickup()) {
            return;
        }

        if (!playerSetting.isAutoPickupItem()) {
            return;
        }

        if (items.isEmpty()) {
            return;
        }

        if (data.getPlayer() == null) {
            return;
        }

        Task.builder().execute(() -> {
            boolean isCollected = false;
            for (Item item : items) {

                if (data.isPlantSuccessfully() && playerSetting.isAutoPickupItem() && !data
                        .getPlantData()
                        .isFullyGrown()) {
                    item.remove();
                    continue;
                }

                ItemStack stack = item.item().get().createStack();

                if (data.isPlantSuccessfully() && !isCollected && data.getPlantData().getFuel() != null && data
                        .getPlantData()
                        .getFuel()
                        .equalsIgnoreCase(stack.getType().getId().toLowerCase())) {
                    stack.setQuantity(stack.getQuantity() - 1);
                    isCollected = true;
                }

                InventoryTransactionResult result = data
                        .getPlayer()
                        .getInventory()
                        .query(QueryOperationTypes.INVENTORY_TYPE.of(Hotbar.class))
                        .union(data
                                .getPlayer()
                                .getInventory()
                                .query(QueryOperationTypes.INVENTORY_TYPE.of(MainPlayerInventory.class)))
                        .offer(stack);

                if (stack.getType() == ItemTypes.AIR || result.getType() == InventoryTransactionResult.Type.SUCCESS) {
                    item.remove();
                }
            }
        }).delay(100, TimeUnit.MILLISECONDS).submit(this.plugin);
    }

    /**
     * On experience orb drop.
     *
     * @param event  the event
     * @param living the living
     */
    @Listener(order = Order.EARLY)
    public void onExperienceOrbDrop(SpawnEntityEvent event, @First Living living) {

        if (!RachamonHarvestia.getInstance().getConfig().getMainCategorySetting().isAutoExpPickup()) {
            return;
        }

        EntityData data = this.track.get(living.getUniqueId());

        if (data == null) {
            return;
        }

        if (!data
                .getPlayer()
                .hasPermission(RachamonHarvestia
                        .getInstance()
                        .getConfig()
                        .getPermissionCategorySetting()
                        .getAutoExpPickupPermission())) {
            return;
        }

        PlayerSettingsConfig.PlayerSetting playerSetting = RachamonHarvestia
                .getInstance()
                .getHarvestiaManager()
                .getPlayerSettingOrCreate(data.getPlayer().getUniqueId());

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

        if (data.getPlayer() == null) {
            return;
        }

        Task.builder().execute(() -> {
            int exp = 0;
            for (ExperienceOrb orb : orbs)
                exp += orb.experience().get();

            int finalExp = exp;
            if (data.getPlayer().transform(Keys.TOTAL_EXPERIENCE, t -> t + finalExp).isSuccessful()) {
                orbs.clear();
            }

            this.plugin.getLogger().debug("success give exp");

        }).delay(100, TimeUnit.MILLISECONDS).submit(this.plugin);


    }

}
