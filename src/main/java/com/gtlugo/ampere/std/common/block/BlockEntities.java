package com.gtlugo.ampere.std.common.block;

import com.gtlugo.ampere.std.AmpereStd;
import com.gtlugo.ampere.std.common.block.wire.WireBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
    Registries.BLOCK_ENTITY_TYPE,
    AmpereStd.MOD_ID
  );

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WireBlockEntity>> WIRE_BE = BLOCK_ENTITIES.register(
    "wire_entity",
    () -> BlockEntityType.Builder.of(WireBlockEntity::new, AmpereStd.Blocks.WIRE.get()).build(null)
  );

  public static void register(IEventBus eventBus) {
    BLOCK_ENTITIES.register(eventBus);
  }
}