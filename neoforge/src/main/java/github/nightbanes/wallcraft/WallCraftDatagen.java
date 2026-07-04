package github.nightbanes.wallcraft;

import github.nightbanes.wallcraft.datagen.WallCraftBlockTagProvider;
import github.nightbanes.wallcraft.datagen.WallCraftEnglishLanguageProvider;
import github.nightbanes.wallcraft.datagen.WallCraftLootTableProvider;
import github.nightbanes.wallcraft.datagen.WallCraftModelProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class WallCraftDatagen {
    private WallCraftDatagen() {}

    public static void onGatherClientData(GatherDataEvent.Client event) {
        event.createProvider(WallCraftModelProvider::new);
        event.createProvider(WallCraftEnglishLanguageProvider::new);
        event.createProvider(WallCraftBlockTagProvider::new);
        event.createProvider(WallCraftLootTableProvider::new);
    }

}
