package github.nightbanes.wallcraft.init;

import github.nightbanes.wallcraft.services.Services;
import github.nightbanes.wallcraft.services.util.RegistryHandle;
import net.minecraft.world.item.Item;

public final class ModItems {
    private ModItems() {}

    public static void load() {}

    public static final RegistryHandle<Item> EXAMPLE_ITEM = Services.REGISTRY.registerItem("example_item",
            properties -> new Item(properties));
}
