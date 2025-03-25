package com.gtlugo.ampere.std.datagen;

import com.gtlugo.ampere.std.AmpereStd;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = AmpereStd.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput pack_output = generator.getPackOutput();
    ExistingFileHelper existing_file_helper = event.getExistingFileHelper();
    CompletableFuture<HolderLookup.Provider> lookup_provider = event.getLookupProvider();

    generator.addProvider(
      event.includeServer(),
      new LootTableProvider(
        pack_output,
        Collections.emptySet(),
        List.of(
          new LootTableProvider.SubProviderEntry(
            ModBlockLootTableProvider::new,
            LootContextParamSets.BLOCK
          )
        ),
        lookup_provider
      )
    );
    generator.addProvider(
      event.includeServer(),
      new ModBlockTagProvider(
        pack_output,
        lookup_provider,
        existing_file_helper
      )
    );

    generator.addProvider(
      event.includeClient(),
      new ModBlockStateProvider(pack_output, existing_file_helper)
    );
    generator.addProvider(
      event.includeClient(),
      new ModItemModelProvider(pack_output, existing_file_helper)
    );
  }
}
