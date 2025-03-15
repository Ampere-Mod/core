package com.gtlugo.ampere.common.block;

import com.gtlugo.ampere.Ampere;
import com.gtlugo.ampere.common.block.wire.WireBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
    Registries.BLOCK_ENTITY_TYPE,
    Ampere.MOD_ID
  );

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WireBlockEntity>> WIRE_BE = BLOCK_ENTITIES.register(
    "wire_entity",
    () -> BlockEntityType.Builder.of(WireBlockEntity::new, Ampere.Blocks.WIRE.get()).build(null)
  );

  public static void register(IEventBus eventBus) {
    BLOCK_ENTITIES.register(eventBus);
  }
}