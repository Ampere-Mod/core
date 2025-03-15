package com.gtlugo.ampere.datagen;

import com.gtlugo.ampere.Ampere;
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
  }

  private void simple(Ampere.Blocks block) {
    simpleBlockWithItem(block.get(), cubeAll(block.get()));
  }

  private void machine(Ampere.Blocks block) {
    String name = block.deferred().getId().getPath();
    var variant_builder = getVariantBuilder(block.get());
    variant_builder.forAllStates(state -> ConfiguredModel
      .builder()
      .modelFile(models().withExistingParent(name, modLoc("block/machine")).texture("all", blockTexture(block.get())))
      .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite().toYRot())
      .build());
  }

  // different from cube_all because the parent is still cube
  public ModelFile cube(String name, ResourceLocation all) {
    return models().cube(name, all, all, all, all, all, all);
  }

  // different from cube_all because the parent is still cube
  public ModelFile with_facing(String name, ResourceLocation all) {
    return models().orientableWithBottom(name, all, all, all, all);
  }
}
