package com.gtlugo.gridtricity;

import com.gtlugo.gridtricity.common.block.ModBlocks;
import com.gtlugo.gridtricity.common.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/*
  Ideas:
  -----------------------------------------------------------------------------------
  Conversion:
    Transformer (ingest FE, provide Watts) <- Not sure about
    Electric generator (Create SU/Rpm -> Watts)
    Electric motor (Watts -> Create SU/Rpm)

  Processing:
    Electric crusher
    Electric washer
    Electric centrifuge

  Furnaces:
    Electric kiln (blast furnace)
    Electric smoker (smoker)

  Misc:
    Electric pump
  ------------------------------------------------------------------------------------
  Perhaps should split the machines into a separate mod. That way the main API and energy system are more standalone.
  This would be relevant in the case where users are not interested in using machines provided by this mod.
 */

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Gridtricity.MOD_ID)
public class Gridtricity {
  // Define mod id in a common place for everything to reference
  public static final String MOD_ID = "gridtricity";
  // Directly reference a slf4j logger
  private static final Logger LOGGER = LogUtils.getLogger();

  public static final DeferredRegister.Items ITEM_REGISTRY = DeferredRegister.createItems(Gridtricity.MOD_ID);
  public static final DeferredRegister.Blocks BLOCK_REGISTRY = DeferredRegister.createBlocks(Gridtricity.MOD_ID);

  // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "gridtricity" namespace
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

  // Creates a creative tab with the id "gridtricity:example_tab" for the example item, that is placed after the combat tab
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TOOLS_TAB = CREATIVE_MODE_TABS.register(MOD_ID + "_tools", () -> CreativeModeTab.builder()
      .title(Component.translatable("itemGroup.gridtricity.tools")) //The language key for the title of your CreativeModeTab
      .withTabsBefore(CreativeModeTabs.COMBAT)
      .icon(() -> ModItems.SCREWDRIVER.get().getDefaultInstance())
      .displayItems((parameters, output) -> {
        output.accept(ModItems.SCREWDRIVER.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
      }).build());

  // Creates a creative tab with the id "gridtricity:example_tab" for the example item, that is placed after the combat tab
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKS_TAB = CREATIVE_MODE_TABS.register(MOD_ID + "_blocks", () -> CreativeModeTab.builder()
      .title(Component.translatable("itemGroup.gridtricity.blocks")) //The language key for the title of your CreativeModeTab
      .withTabsBefore(TOOLS_TAB.getId())
      .icon(() -> ModBlocks.KILN.block().get().asItem().getDefaultInstance())
      .displayItems((parameters, output) -> {
        output.accept(ModBlocks.KILN.block().get());
        output.accept(ModBlocks.POWER_PLANT.block().get());
      }).build());

  // The constructor for the mod class is the first code that is run when your mod is loaded.
  // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
  public Gridtricity(IEventBus modEventBus, ModContainer modContainer) {
    NeoForge.EVENT_BUS.register(this);
    modEventBus.addListener(this::commonSetup);

    ModItems.register(modEventBus);
    ModBlocks.register(modEventBus);
    CREATIVE_MODE_TABS.register(modEventBus);

    modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    // Some common setup code
    LOGGER.info("HELLO FROM COMMON SETUP");
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    // Do something when the server starts
    LOGGER.info("HELLO from server starting");
  }

  // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
  @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
      // Some client setup code
      LOGGER.info("HELLO FROM CLIENT SETUP");
      LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
  }
}
