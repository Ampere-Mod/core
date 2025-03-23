package com.gtlugo.ampere.common.block.wire;

import com.gtlugo.ampere.Ampere;
import com.gtlugo.ampere.common.Tags;
import com.gtlugo.ampere.common.block.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WireBlock extends ModBlock implements EntityBlock {
  public static final VoxelShape CORE_SHAPE = Block.box(5, 5, 5, 11, 11, 11);
  public static final VoxelShape NORTH_SHAPE = Block.box(5, 5, 0, 11, 11, 5);
  public static final VoxelShape SOUTH_SHAPE = Block.box(5, 5, 11, 11, 11, 16);
  public static final VoxelShape WEST_SHAPE = Block.box(0, 5, 5, 5, 11, 11);
  public static final VoxelShape EAST_SHAPE = Block.box(11, 5, 5, 16, 11, 11);
  public static final VoxelShape DOWN_SHAPE = Block.box(5, 0, 5, 11, 5, 11);
  public static final VoxelShape UP_SHAPE = Block.box(5, 11, 5, 11, 16, 11);

  public static final EnumProperty<WireSide> EAST_WIRE = EnumProperty.create(
    "east",
    WireSide.class
  );
  public static final EnumProperty<WireSide> NORTH_WIRE = EnumProperty.create(
    "north",
    WireSide.class
  );
  public static final EnumProperty<WireSide> SOUTH_WIRE = EnumProperty.create(
    "south",
    WireSide.class
  );
  public static final EnumProperty<WireSide> WEST_WIRE = EnumProperty.create(
    "west",
    WireSide.class
  );
  public static final EnumProperty<WireSide> UP_WIRE = EnumProperty.create("up", WireSide.class);
  public static final EnumProperty<WireSide> DOWN_WIRE = EnumProperty.create(
    "down",
    WireSide.class
  );

  public WireBlock() {
    super(Properties.of().noCollission().noOcclusion());
  }

  @Override @NotNull
  public VoxelShape getShape(
    @NotNull BlockState pState,
    @NotNull BlockGetter pLevel,
    @NotNull BlockPos pPos,
    @NotNull CollisionContext pContext
  ) {
    VoxelShape shape = CORE_SHAPE;

    if (pState.getValue(NORTH_WIRE) != WireSide.NONE) {
      shape = Shapes.or(shape, NORTH_SHAPE);
    }

    if (pState.getValue(SOUTH_WIRE) != WireSide.NONE) {
      shape = Shapes.or(shape, SOUTH_SHAPE);
    }

    if (pState.getValue(WEST_WIRE) != WireSide.NONE) {
      shape = Shapes.or(shape, WEST_SHAPE);
    }

    if (pState.getValue(EAST_WIRE) != WireSide.NONE) {
      shape = Shapes.or(shape, EAST_SHAPE);
    }

    if (pState.getValue(UP_WIRE) != WireSide.NONE) {
      shape = Shapes.or(shape, UP_SHAPE);
    }

    if (pState.getValue(DOWN_WIRE) != WireSide.NONE) {
      shape = Shapes.or(shape, DOWN_SHAPE);
    }

    return shape;
  }

  @Override
  public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
    return RenderShape.MODEL;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    return new WireBlockEntity(blockPos, blockState);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder
      .add(EAST_WIRE)
      .add(NORTH_WIRE)
      .add(SOUTH_WIRE)
      .add(WEST_WIRE)
      .add(UP_WIRE)
      .add(DOWN_WIRE);
  }

  @Override
  public void destroy(LevelAccessor pLevel, BlockPos pPos, @NotNull BlockState pState) {
    var east_block = pLevel.getBlockState(pPos.east());
    if (east_block.is(Ampere.Blocks.WIRE.get())) {
      east_block = east_block.setValue(WEST_WIRE, WireSide.NONE);
      pLevel.setBlock(pPos.east(), east_block, 3);
    }
    var north_block = pLevel.getBlockState(pPos.north());
    if (north_block.is(Ampere.Blocks.WIRE.get())) {
      north_block = north_block.setValue(SOUTH_WIRE, WireSide.NONE);
      pLevel.setBlock(pPos.north(), north_block, 3);
    }
    var south_block = pLevel.getBlockState(pPos.south());
    if (south_block.is(Ampere.Blocks.WIRE.get())) {
      south_block = south_block.setValue(NORTH_WIRE, WireSide.NONE);
      pLevel.setBlock(pPos.south(), south_block, 3);
    }
    var west_block = pLevel.getBlockState(pPos.west());
    if (west_block.is(Ampere.Blocks.WIRE.get())) {
      west_block = west_block.setValue(EAST_WIRE, WireSide.NONE);
      pLevel.setBlock(pPos.west(), west_block, 3);
    }
    var up_block = pLevel.getBlockState(pPos.above());
    if (up_block.is(Ampere.Blocks.WIRE.get())) {
      up_block = up_block.setValue(DOWN_WIRE, WireSide.NONE);
      pLevel.setBlock(pPos.above(), up_block, 3);
    }
    var down_block = pLevel.getBlockState(pPos.below());
    if (down_block.is(Ampere.Blocks.WIRE.get())) {
      down_block = down_block.setValue(UP_WIRE, WireSide.NONE);
      pLevel.setBlock(pPos.below(), down_block, 3);
    }

    super.destroy(pLevel, pPos, pState);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    var east = WireSide.NONE;
    var east_block = pContext.getLevel().getBlockState(pContext.getClickedPos().east());
    if (east_block.is(Ampere.Blocks.WIRE.get())) {
      east = WireSide.WIRE;
      east_block = east_block.setValue(WEST_WIRE, WireSide.WIRE);
      pContext.getLevel().setBlockAndUpdate(pContext.getClickedPos().east(), east_block);
    }

    if (east_block.is(Tags.Blocks.WIRE_CONNECTABLE)) {
      east = WireSide.MACHINE;
    }

    var north = WireSide.NONE;
    var north_block = pContext.getLevel().getBlockState(pContext.getClickedPos().north());
    if (north_block.is(Ampere.Blocks.WIRE.get())) {
      north = WireSide.WIRE;
      north_block = north_block.setValue(SOUTH_WIRE, WireSide.WIRE);
      pContext.getLevel().setBlockAndUpdate(pContext.getClickedPos().north(), north_block);
    }

    if (north_block.is(Tags.Blocks.WIRE_CONNECTABLE)) {
      north = WireSide.MACHINE;
    }

    var south = WireSide.NONE;
    var south_block = pContext.getLevel().getBlockState(pContext.getClickedPos().south());
    if (south_block.is(Ampere.Blocks.WIRE.get())) {
      south = WireSide.WIRE;
      south_block = south_block.setValue(NORTH_WIRE, WireSide.WIRE);
      pContext.getLevel().setBlockAndUpdate(pContext.getClickedPos().south(), south_block);
    }

    if (south_block.is(Tags.Blocks.WIRE_CONNECTABLE)) {
      south = WireSide.MACHINE;
    }

    var west = WireSide.NONE;
    var west_block = pContext.getLevel().getBlockState(pContext.getClickedPos().west());
    if (west_block.is(Ampere.Blocks.WIRE.get())) {
      west = WireSide.WIRE;
      west_block = west_block.setValue(EAST_WIRE, WireSide.WIRE);
      pContext.getLevel().setBlockAndUpdate(pContext.getClickedPos().west(), west_block);
    }

    if (west_block.is(Tags.Blocks.WIRE_CONNECTABLE)) {
      west = WireSide.MACHINE;
    }

    var up = WireSide.NONE;
    var up_block = pContext.getLevel().getBlockState(pContext.getClickedPos().above());
    if (up_block.is(Ampere.Blocks.WIRE.get())) {
      up = WireSide.WIRE;
      up_block = up_block.setValue(DOWN_WIRE, WireSide.WIRE);
      pContext.getLevel().setBlockAndUpdate(pContext.getClickedPos().above(), up_block);
    }

    if (up_block.is(Tags.Blocks.WIRE_CONNECTABLE)) {
      up = WireSide.MACHINE;
    }

    var down = WireSide.NONE;
    var down_block = pContext.getLevel().getBlockState(pContext.getClickedPos().below());
    if (down_block.is(Ampere.Blocks.WIRE.get())) {
      down = WireSide.WIRE;
      down_block = down_block.setValue(UP_WIRE, WireSide.WIRE);
      pContext.getLevel().setBlockAndUpdate(pContext.getClickedPos().below(), down_block);
    }

    if (down_block.is(Tags.Blocks.WIRE_CONNECTABLE)) {
      down = WireSide.MACHINE;
    }

    return stateDefinition
      .any()
      .setValue(EAST_WIRE, east)
      .setValue(NORTH_WIRE, north)
      .setValue(SOUTH_WIRE, south)
      .setValue(WEST_WIRE, west)
      .setValue(UP_WIRE, up)
      .setValue(DOWN_WIRE, down);
  }
}
