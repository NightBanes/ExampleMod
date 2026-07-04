package github.nightbanes.wallcraft.datagen;

import github.nightbanes.wallcraft.init.ModBlocks;
import github.nightbanes.wallcraft.services.NeoForgeRegistryHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WallCraftLootTableProvider extends LootTableProvider {
    public WallCraftLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output,
                Set.of(),
                List.of(
                        new SubProviderEntry(WallCraftBlockLootSubProvider::new, LootContextParamSets.BLOCK)
                        //, new SubProviderEntry(...)
                ),
                registries);
    }

    private static final class WallCraftBlockLootSubProvider extends BlockLootSubProvider {
        WallCraftBlockLootSubProvider(HolderLookup.Provider registries) {
            super(Set.of(/*.add(explosion resistant items)*/), FeatureFlags.DEFAULT_FLAGS, registries);
        }

        @Override
        protected void generate() {
            // Add item drops
            dropSelf(ModBlocks.EXAMPLE_BLOCK.block().get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return NeoForgeRegistryHelper.BLOCKS.getEntries()
                    .stream()
                    .map(entry -> (Block) entry.value())
                    .toList();
        }
    }


}
