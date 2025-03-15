package com.gtlugo.ampere.datagen;

import com.gtlugo.ampere.Ampere;
import com.gtlugo.ampere.common.Tags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
  public ModBlockTagProvider(
    PackOutput output,
    CompletableFuture<HolderLookup.Provider> lookupProvider,
    @Nullable ExistingFileHelper existingFileHelper
  ) {
    super(output, lookupProvider, Ampere.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.@NotNull Provider provider) {
    tag(Tags.Blocks.WIRE_CONNECTABLE).add(
      Ampere.Blocks.POWER_PLANT.get(),
      Ampere.Blocks.FURNACE.get()
    );
  }
}
