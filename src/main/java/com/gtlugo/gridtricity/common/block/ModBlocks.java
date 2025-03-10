package com.gtlugo.gridtricity.common.block;

import com.gtlugo.gridtricity.Gridtricity;
import com.gtlugo.gridtricity.common.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Gridtricity.MOD_ID);

  public static final DeferredBlock<ModBlock> KILN = registerBlock("kiln", KilnBlock::new);
  public static final DeferredBlock<ModBlock> POWER_PLANT = registerBlock("power_plant", PowerPlantBlock::new);

  public static void register(IEventBus event_bus) {
    BLOCKS.register(event_bus);
  }

  static <T extends ModBlock> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
    DeferredBlock<T> b = ModBlocks.BLOCKS.register(name, block);
    ModItems.ITEMS.registerSimpleBlockItem(b);
    return b;
  }
}
