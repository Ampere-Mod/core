package com.gtlugo.ampere.std.datagen;

import com.gtlugo.ampere.std.AmpereStd;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
  public ModItemModelProvider(
    PackOutput output,
    ExistingFileHelper existingFileHelper
  ) {
    super(output, AmpereStd.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    handheldItem(AmpereStd.Items.SCREWDRIVER.get());
    handheldItem(AmpereStd.Items.PROBE.get());

    simpleBlockItem(AmpereStd.Blocks.FURNACE.get());
    simpleBlockItem(AmpereStd.Blocks.POWER_PLANT.get());
  }
}
