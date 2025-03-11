package com.gtlugo.gridtricity.common.block;

import com.gtlugo.gridtricity.Gridtricity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

public enum ModBlocks {
  KILN ("kiln", KilnBlock::new) {},
  POWER_PLANT ("power_plant", PowerPlantBlock::new) {};

  private final DeferredBlock<ModBlock> block;

  ModBlocks(String name, Supplier<ModBlock> block) {
    this.block = registerBlock(name, block);
  }

  public DeferredBlock<ModBlock> block() {
    return this.block;
  }

  public static void register(IEventBus event_bus) {
    Gridtricity.BLOCK_REGISTRY.register(event_bus);
  }

  static <T extends ModBlock> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
    DeferredBlock<T> b = Gridtricity.BLOCK_REGISTRY.register(name, block);
    Gridtricity.ITEM_REGISTRY.registerSimpleBlockItem(b);
    return b;
  }
}