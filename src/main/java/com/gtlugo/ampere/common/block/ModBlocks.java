package com.gtlugo.ampere.common.block;

import com.gtlugo.ampere.Ampere;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

public enum ModBlocks {
  FURNACE ("furnace", FurnaceBlock::new) {},
  POWER_PLANT ("power_plant", PowerPlantBlock::new) {};

  private final DeferredBlock<ModBlock> block;

  ModBlocks(String name, Supplier<ModBlock> block) {
    this.block = registerBlock(name, block);
  }

  public ModBlock get() {
    return this.block.get();
  }

  public static void register(IEventBus event_bus) {
    Ampere.BLOCK_REGISTRY.register(event_bus);
  }

  static <T extends ModBlock> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
    DeferredBlock<T> b = Ampere.BLOCK_REGISTRY.register(name, block);
    Ampere.ITEM_REGISTRY.registerSimpleBlockItem(b);
    return b;
  }
}