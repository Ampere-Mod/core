package com.gtlugo.ampere.std.common.block.wire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.gtlugo.ampere.std.common.block.BlockEntities.WIRE_BE;

public class WireBlockEntity extends BlockEntity {
  public WireBlockEntity(
    BlockPos pPos,
    BlockState pBlockState
  ) {
    super(WIRE_BE.get(), pPos, pBlockState);
  }
}
