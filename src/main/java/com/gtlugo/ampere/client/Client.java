package com.gtlugo.ampere.client;

import com.gtlugo.ampere.Ampere;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Ampere.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Client {
  private static final Logger LOGGER = LogUtils.getLogger();

  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent event) {
    // Some client setup code
    LOGGER.info("Ampere Client Setup");
  }
}