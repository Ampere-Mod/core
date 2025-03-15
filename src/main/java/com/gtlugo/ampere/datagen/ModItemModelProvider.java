package com.gtlugo.ampere.datagen;

import com.gtlugo.ampere.Ampere;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
  public ModItemModelProvider(
    PackOutput output,
    ExistingFileHelper existingFileHelper
  ) {
    super(output, Ampere.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    handheldItem(Ampere.Items.SCREWDRIVER.get());
    handheldItem(Ampere.Items.PROBE.get());

    simpleBlockItem(Ampere.Blocks.FURNACE.get());
    simpleBlockItem(Ampere.Blocks.POWER_PLANT.get());
  }
}
