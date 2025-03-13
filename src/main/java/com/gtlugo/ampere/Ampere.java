package com.gtlugo.ampere;

import com.gtlugo.ampere.common.block.FurnaceBlock;
import com.gtlugo.ampere.common.block.ModBlock;
import com.gtlugo.ampere.common.block.PowerPlantBlock;
import com.gtlugo.ampere.common.item.ModItem;
import com.gtlugo.ampere.common.item.ProbeItem;
import com.gtlugo.ampere.common.item.ScrewdriverItem;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

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

import java.util.function.Supplier;

/*
  Ideas:
  -----------------------------------------------------------------------------------
  Conversion:
    Transformer (ingest FE, provide Watts, or vice versa) <- Not sure about
    Electric generator (Create SU/Rpm -> Watts)
    Electric motor (Watts -> Create SU/Rpm)

  Processing:
    Electric crusher
    Electric washer
    Electric centrifuge

  Furnaces:
    Electric furnace (blast furnace)
    Electric smoker (smoker)

  Misc:
    Electric pump
  ------------------------------------------------------------------------------------
  Perhaps should split the machines into a separate mod. That way the main API and energy system are more standalone.
  This would be relevant in the case where users are not interested in using machines provided by this mod.
 */

@Mod(Ampere.MOD_ID)
public class Ampere {
  public static final String MOD_ID = "ampere";
  private static final Logger LOGGER = LogUtils.getLogger();

  public static final DeferredRegister.Items ITEM_REGISTRY = DeferredRegister.createItems(Ampere.MOD_ID);

  public enum Items {
    SCREWDRIVER ("screwdriver", ScrewdriverItem::new),
    PROBE ("probe", ProbeItem::new);

    private final DeferredItem<ModItem> item;

    Items(String name, Supplier<ModItem> item) {
      this.item = Ampere.ITEM_REGISTRY.register(name, item);
    }

    public ModItem get() {
      return this.item.get();
    }

    public static void register(IEventBus event_bus) {
      Ampere.ITEM_REGISTRY.register(event_bus);
    }
  }

  public static final DeferredRegister.Blocks BLOCK_REGISTRY = DeferredRegister.createBlocks(Ampere.MOD_ID);

  public enum Blocks {
    FURNACE ("furnace", FurnaceBlock::new),
    POWER_PLANT ("power_plant", PowerPlantBlock::new);

    private final DeferredBlock<ModBlock> block;

    Blocks(String name, Supplier<ModBlock> block) {
      this.block = registerBlock(name, block);
    }

    public ModBlock get() {
      return this.block.get();
    }

    public static void register(IEventBus event_bus) {
      Ampere.BLOCK_REGISTRY.register(event_bus);
    }

    static <T extends ModBlock> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
      DeferredBlock<T> b = Ampere.BLOCK_REGISTRY.register(name, block);
      Ampere.ITEM_REGISTRY.registerSimpleBlockItem(b);
      return b;
    }
  }

  // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "ampere" namespace
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

  // Creates a creative tab with the id "ampere:example_tab" for the example item, that is placed after the combat tab
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TOOLS_TAB = CREATIVE_MODE_TABS.register(MOD_ID + "_tools", () -> CreativeModeTab.builder()
      .title(Component.translatable("itemGroup.ampere.tools")) //The language key for the title of your CreativeModeTab
      .withTabsBefore(CreativeModeTabs.COMBAT)
      .icon(() -> Items.SCREWDRIVER.get().getDefaultInstance())
      .displayItems((parameters, output) -> {
        output.accept(Items.SCREWDRIVER.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
      }).build());

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKS_TAB = CREATIVE_MODE_TABS.register(MOD_ID + "_blocks", () -> CreativeModeTab.builder()
      .title(Component.translatable("itemGroup.ampere.blocks")) //The language key for the title of your CreativeModeTab
      .withTabsBefore(TOOLS_TAB.getId())
      .icon(() -> Blocks.FURNACE.get().asItem().getDefaultInstance())
      .displayItems((parameters, output) -> {
        output.accept(Blocks.FURNACE.get());
        output.accept(Blocks.POWER_PLANT.get());
      }).build());

  // The constructor for the mod class is the first code that is run when your mod is loaded.
  // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
  public Ampere(IEventBus modEventBus, ModContainer modContainer) {
    NeoForge.EVENT_BUS.register(this);
    modEventBus.addListener(this::commonSetup);

    Items.register(modEventBus);
    Blocks.register(modEventBus);
    CREATIVE_MODE_TABS.register(modEventBus);

    modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    // Some common setup code
    LOGGER.info("Ampere Common Setup");
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    // Do something when the server starts
    LOGGER.info("Ampere Server Startup");
  }
}
