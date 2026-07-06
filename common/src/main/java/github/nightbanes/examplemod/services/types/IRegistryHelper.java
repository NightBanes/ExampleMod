package github.nightbanes.examplemod.services.types;

import github.nightbanes.examplemod.Constants;
import github.nightbanes.examplemod.services.util.BlockItemRegistryHandle;
import github.nightbanes.examplemod.services.util.RegistryHandle;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IRegistryHelper {
    <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item);
    <T extends Block> RegistryHandle<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block);
    <T extends BlockItem> RegistryHandle<T> registerBlockItem(String name, RegistryHandle<? extends Block> block, BiFunction<Block, Item.Properties, T> item);

    default <T extends Block> BlockItemRegistryHandle<T> registerBlockItem(String name, Function<BlockBehaviour.Properties, T> block){
        return registerBlockItem(name, block, BlockItem::new);
    }
    default <T extends Block> BlockItemRegistryHandle<T> registerBlockItem(String name, Function<BlockBehaviour.Properties, T> block, BiFunction<Block, Item.Properties, BlockItem> item) {
        RegistryHandle<T> blockHandle = registerBlock(name, block);
        RegistryHandle<BlockItem> itemHandle = registerBlockItem(name, blockHandle, item);
        return new BlockItemRegistryHandle<>(blockHandle, itemHandle);
    }

    static ResourceKey<Item> itemkey(String name) { return ResourceKey.create(Registries.ITEM, Constants.id(name)); }
    static ResourceKey<Block> blockkey(String name) { return ResourceKey.create(Registries.BLOCK, Constants.id(name)); }

    RegistryHandle<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> icon, Consumer<CreativeTabOutput> entries);

    @FunctionalInterface
    interface CreativeTabOutput {
        void accept(ItemLike itemLike);
    }

}
