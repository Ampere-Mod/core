package com.gtlugo.ampere.datagen;

import com.gtlugo.ampere.Ampere;
import com.gtlugo.ampere.common.block.wire.WireBlock;
import com.gtlugo.ampere.common.block.wire.WireSide;
import com.mojang.logging.LogUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

public class ModBlockStateProvider extends BlockStateProvider {
  private static final Logger LOGGER = LogUtils.getLogger();

  public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, Ampere.MOD_ID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    machine(Ampere.Blocks.POWER_PLANT);
    machine(Ampere.Blocks.FURNACE);
    wire();
  }

  private void simple(Ampere.Blocks block) {
    simpleBlockWithItem(block.get(), cubeAll(block.get()));
  }

  // different from cube_all because the parent is still cube
  private ModelFile cube(String name, ResourceLocation all) {
    return models().cube(name, all, all, all, all, all, all);
  }

  // different from cube_all because the parent is still cube
  private ModelFile with_facing(String name, ResourceLocation all) {
    return models().orientableWithBottom(name, all, all, all, all);
  }

  private void machine(Ampere.Blocks block) {
    String name = block.deferred().getId().getPath();
    var variant_builder = getVariantBuilder(block.get());
    variant_builder.forAllStates(state -> ConfiguredModel
      .builder()
      .modelFile(models()
                   .withExistingParent(name, modLoc("block/machine"))
                   .texture("all", blockTexture(block.get())))
      .rotationY((int) state
        .getValue(BlockStateProperties.HORIZONTAL_FACING)
        .getOpposite()
        .toYRot())
      .build());
  }

  private void wire() {
    var wire = models().getExistingFile(modLoc("wire"));
    wire.assertExistence();
    var wire_core = models().getExistingFile(modLoc("wire_core"));
    wire_core.assertExistence();
    var wire_side = models().getExistingFile(modLoc("wire_side"));
    wire_side.assertExistence();
    simpleBlockItem(Ampere.Blocks.WIRE.get(), wire);
    getMultipartBuilder(Ampere.Blocks.WIRE.get()) // Get multipart builder
      .part() // Create part
      .modelFile(wire_core) // Can show 'redstoneDot'
      .addModel() // 'redstoneDot' is displayed
      .end() // Finish part
      .part() // Create part
      .modelFile(wire_side) // Can show 'redstoneSide0'
      .addModel() // 'redstoneSide0' is displayed when...
      .condition(WireBlock.NORTH_WIRE, WireSide.WIRE, WireSide.MACHINE) // NORTH_REDSTONE is SIDE or UP
      .end() // Finish part
      .part() // Create part
      .modelFile(wire_side) // Can show 'redstoneSideAlt0'
      .rotationY(180) // Rotates 'redstoneSideAlt1' 270 degrees on the Y axis
      .addModel() // 'redstoneSideAlt0' is displayed when...
      .condition(WireBlock.SOUTH_WIRE, WireSide.WIRE, WireSide.MACHINE) // SOUTH_REDSTONE is SIDE or UP
      .end() // Finish part
      .part() // Create part
      .modelFile(wire_side) // Can show 'redstoneSideAlt1'
      .rotationY(90) // Rotates 'redstoneSide1' 270 degrees on the Y axis
      .addModel() // 'redstoneSideAlt1' is displayed when...
      .condition(WireBlock.EAST_WIRE, WireSide.WIRE, WireSide.MACHINE) // EAST_REDSTONE is SIDE or UP
      .end() // Finish part
      .part() // Create part
      .modelFile(wire_side) // Can show 'redstoneSide1'
      .rotationY(270) // Rotates 'redstoneSideAlt1' 270 degrees on the Y axis
      .addModel() // 'redstoneSide1' is displayed when...
      .condition(WireBlock.WEST_WIRE, WireSide.WIRE, WireSide.MACHINE) // WEST_REDSTONE is SIDE or UP
      .end().part() // Create part
      .modelFile(wire_side) // Can show 'redstoneSideAlt1'
      .rotationX(-90) // Rotates 'redstoneSideAlt1' 270 degrees on the Y axis
      .addModel() // 'redstoneSideAlt1' is displayed when...
      .condition(WireBlock.UP_WIRE, WireSide.WIRE, WireSide.MACHINE) // EAST_REDSTONE is SIDE or UP
      .end() // Finish part
      .part() // Create part
      .modelFile(wire_side) // Can show 'redstoneSide1'
      .rotationX(90) // Rotates 'redstoneSide1' 270 degrees on the Y axis
      .addModel() // 'redstoneSide1' is displayed when...
      .condition(WireBlock.DOWN_WIRE, WireSide.WIRE, WireSide.MACHINE) // WEST_REDSTONE is SIDE or UP
      .end(); // Finish part
  }
}
