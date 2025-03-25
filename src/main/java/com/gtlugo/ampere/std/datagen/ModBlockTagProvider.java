package com.gtlugo.ampere.std.datagen;

import com.gtlugo.ampere.std.AmpereStd;
import com.gtlugo.ampere.std.common.Tags;
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
    super(output, lookupProvider, AmpereStd.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.@NotNull Provider provider) {
    tag(Tags.Blocks.WIRE_CONNECTABLE).add(
      AmpereStd.Blocks.POWER_PLANT.get(),
      AmpereStd.Blocks.FURNACE.get()
    );
  }
}
