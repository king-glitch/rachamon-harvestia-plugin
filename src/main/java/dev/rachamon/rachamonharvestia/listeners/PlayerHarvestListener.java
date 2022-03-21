package dev.rachamon.rachamonharvestia.listeners;

import dev.rachamon.rachamonharvestia.RachamonHarvestia;
import dev.rachamon.rachamonharvestia.interfaces.IPlant;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.block.ImmutableGrowthData;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.Optional;

public class PlayerHarvestListener {

    private final RachamonHarvestia plugin = RachamonHarvestia.getInstance();
    private final HashMap<String, IPlant> plants = new HashMap<String, IPlant>() {{
        put(String.valueOf(BlockTypes.WHEAT), new IPlant(BlockTypes.WHEAT, ItemTypes.WHEAT_SEEDS, 7));
    }};

    @Listener(order = Order.LATE)
    public void OnPlayerHarvestPlant(ChangeBlockEvent.Break event, @Root Player player) {

        // check if block are plant

        for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {

            IPlant plant = plants.get(transaction.getOriginal().getState().getType().toString());

            if (plant == null) {
                this.plugin.getLogger().debug("Not Plant Block");
                return;
            }

            Optional<MutableBoundedValue<Integer>> targetStage = transaction.getOriginal().getState().getValue(Keys.GROWTH_STAGE);

            if (!targetStage.isPresent()) {
                this.plugin.getLogger().debug("Stage key not found on this block.");
                return;
            }


            if (plant.stage != targetStage.get().get()) {
                this.plugin.getLogger().debug("this plant doesn't on last stage");
                return;
            }

            Optional<Location<World>> location = transaction.getOriginal().getLocation();

            if (!location.isPresent()) {
                this.plugin.getLogger().debug("location not found; might error ?");
                return;
            }
            BlockState state = transaction.getOriginal().getState();
            Optional<ImmutableGrowthData> glowData = state.get(ImmutableGrowthData.class);

            if (!glowData.isPresent()) {
                this.plugin.getLogger().debug("No Glow data");
                return;
            }

            BlockState oldState = transaction.getOriginal().getState();
            BlockState newState = oldState.with(Keys.GROWTH_STAGE, 0).orElse(oldState);
            location.get().setBlock(newState);
        }

        // check if config where enable to auto replant

        // check if user has permission to replant

        // check if user has enabled the config

    }

}
