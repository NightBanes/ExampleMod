package github.nightbanes.examplemod.datagen;

import github.nightbanes.examplemod.init.ModBlocks;
import github.nightbanes.examplemod.init.ModItems;
import github.nightbanes.examplemod.services.NeoForgeRegistryHelper;
import github.nightbanes.examplemod.services.util.BlockItemRegistryHandle;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output, Set.of(), List.of(
                        new SubProviderEntry(ModBlockLootSubProvider::new, LootContextParamSets.BLOCK)
                        //, new SubProviderEntry(...)
                ), registries);
    }

    private static final class ModBlockLootSubProvider extends BlockLootSubProvider {
        ModBlockLootSubProvider(HolderLookup.Provider registries) {
            super(Set.of(/*.add(explosion resistant items)*/), FeatureFlags.DEFAULT_FLAGS, registries);
        }

        @Override
        protected void generate() {
            // Add blocks that do not drop self
            Set<Block> customLootBlocks = Set.of(
                    ModBlocks.EXAMPLE_BLOCK.block().get()
            );

            // Add custom drops
            dropOther(ModBlocks.EXAMPLE_BLOCK.block().get(), ModItems.EXAMPLE_ITEM.get());

            // Automatically sets every block to dropself unless added to customLootBlocks
            for(Block block : modBlocks()) {
                if(!customLootBlocks.contains(block)) {
                    dropSelf(block);
                }
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return NeoForgeRegistryHelper.BLOCKS.getEntries()
                    .stream()
                    .map(entry -> (Block) entry.value())
                    .toList();
        }

        private Iterable<Block> modBlocks() {
            return List.of(ModBlocks.class.getDeclaredFields())
                    .stream()
                    .filter(field -> Modifier.isStatic(field.getModifiers()))
                    .filter(field -> BlockItemRegistryHandle.class.isAssignableFrom(field.getType()))
                    .map(ModBlockLootSubProvider::getBlock)
                    .toList();
        }

        private static Block getBlock(Field field) {
            try {
                BlockItemRegistryHandle<?> blockItem = (BlockItemRegistryHandle<?>) field.get(null);
                return blockItem.block().get();
            } catch(IllegalAccessException exception) {
                throw new IllegalStateException("Failed to read block field " + field.getName(), exception);
            }
        }
    }


}
