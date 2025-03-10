package com.gtlugo.gridtricity.common.item;

import com.gtlugo.gridtricity.Gridtricity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Gridtricity.MOD_ID);

  public static final DeferredItem<Item> SCREWDRIVER = ITEMS.register("screwdriver", () -> new Item(new Item.Properties().stacksTo(1)));

  public static void register(IEventBus event_bus) {
    ITEMS.register(event_bus);
  }
}
