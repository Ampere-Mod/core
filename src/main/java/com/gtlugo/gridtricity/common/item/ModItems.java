package com.gtlugo.gridtricity.common.item;

import com.gtlugo.gridtricity.Gridtricity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItems {
  public static final DeferredItem<Item> SCREWDRIVER = Gridtricity.ITEM_REGISTRY.register("screwdriver", () -> new Item(new Item.Properties().stacksTo(1)));

  public static void register(IEventBus event_bus) {
    Gridtricity.ITEM_REGISTRY.register(event_bus);
  }
}
