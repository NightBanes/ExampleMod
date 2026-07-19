package github.nightbanes.examplemod.datagen;

import github.nightbanes.examplemod.Constants;
import github.nightbanes.examplemod.init.ModBlocks;
import github.nightbanes.examplemod.init.ModCreativeTabs;
import github.nightbanes.examplemod.init.ModItems;
import github.nightbanes.examplemod.services.util.BlockItemRegistryHandle;
import github.nightbanes.examplemod.services.util.RegistryHandle;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;

public class ModEnglishLanguageProvider extends LanguageProvider {
    public ModEnglishLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Add translations
        add(ModCreativeTabs.EXAMPLE_TAB.get().getDisplayName(), "Example Mod");

        // Auto adds translation based on item/block name
        addItemTranslations();
        addBlockTranslations();

        /* Manually override translations
        add(ModItems.EXAMPLE_ITEM.get(), "Example Item");

        //add(ModBlocks.EXAMPLE_BLOCK.block().get(), "Example Block");
        //add(ModBlocks.EXAMPLE_BLOCK.item().get(), "Example Block");
        addBlockAndItem(ModBlocks.EXAMPLE_BLOCK, "Example Block");
        //*/
    }

    private void add(Component component, String value) {
        if(component.getContents() instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(), value);
        }
    }

    private void addBlockAndItem(BlockItemRegistryHandle<? extends Block> blockItem, String name) {
        add(blockItem.block().get(), name);
        add(blockItem.item().get(), name);
    }

    private void addItemTranslations() {
        for(Field field : ModItems.class.getDeclaredFields()) {
            if(!isStaticFieldOfType(field, RegistryHandle.class)) {
                continue;
            }

            try {
                RegistryHandle<?> item = (RegistryHandle<?>) field.get(null);
                if(item.get() instanceof Item itemValue) {
                    add(itemValue, createTranslation(item.id().getPath()));
                }
            } catch(IllegalAccessException exception) {
                throw new IllegalStateException("Failed to read item field " + field.getName(), exception);
            }
        }
    }

    private void addBlockTranslations() {
        for(Field field : ModBlocks.class.getDeclaredFields()) {
            if(!isStaticFieldOfType(field, BlockItemRegistryHandle.class)) {
                continue;
            }

            try {
                BlockItemRegistryHandle<?> blockItem = (BlockItemRegistryHandle<?>) field.get(null);
                addBlockAndItem(blockItem, createTranslation(blockItem.block().id().getPath()));
            } catch(IllegalAccessException exception) {
                throw new IllegalStateException("Failed to read block field " + field.getName(), exception);
            }
        }
    }

    private boolean isStaticFieldOfType(Field field, Class<?> type) {
        return Modifier.isStatic(field.getModifiers()) && type.isAssignableFrom(field.getType());
    }

    private String createTranslation(String name) {
        String path = name.contains(":") ? name.substring(name.indexOf(':') + 1) : name;
        String[] words = path.split("_");
        StringBuilder translation = new StringBuilder();

        for(String word : words) {
            if(word.isEmpty()) {
                continue;
            }

            if(!translation.isEmpty()) {
                translation.append(' ');
            }

            translation.append(word.substring(0, 1).toUpperCase(Locale.ROOT));
            translation.append(word.substring(1).toLowerCase(Locale.ROOT));
        }

        return translation.toString();
    }

}
