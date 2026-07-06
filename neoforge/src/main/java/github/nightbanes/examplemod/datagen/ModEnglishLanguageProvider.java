package github.nightbanes.examplemod.datagen;

import github.nightbanes.examplemod.Constants;
import github.nightbanes.examplemod.init.ModBlocks;
import github.nightbanes.examplemod.init.ModCreativeTabs;
import github.nightbanes.examplemod.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnglishLanguageProvider extends LanguageProvider {
    public ModEnglishLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Add translations
        add(ModCreativeTabs.EXAMPLE_TAB.get().getDisplayName(), "Example Mod");

        add(ModItems.EXAMPLE_ITEM.get(), "Example Item");

        add(ModBlocks.EXAMPLE_BLOCK.block().get(), "Example Block");
        add(ModBlocks.EXAMPLE_BLOCK.item().get(), "Example Block");
    }

    private void add(Component component, String value) {
        if(component.getContents() instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(), value);
        }
    }

}
