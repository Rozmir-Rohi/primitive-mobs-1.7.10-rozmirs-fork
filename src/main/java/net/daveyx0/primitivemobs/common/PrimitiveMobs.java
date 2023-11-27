package net.daveyx0.primitivemobs.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import java.util.logging.Logger;
import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.client.TabPrimitiveMobs;
import net.daveyx0.primitivemobs.common.handlers.PrimitiveMobsEventHandler;
import net.daveyx0.primitivemobs.common.handlers.PrimitiveMobsFMLEventHandler;
import net.daveyx0.primitivemobs.common.packets.PrimitiveColorPacket;
import net.daveyx0.primitivemobs.common.packets.PrimitiveGroveSpritePacket;
import net.daveyx0.primitivemobs.common.packets.PrimitiveJumpPacket;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfiguration;
import net.daveyx0.primitivemobs.core.PrimitiveMobsEntities;
import net.daveyx0.primitivemobs.core.PrimitiveMobsEntitySpawning;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.core.PrimitiveMobsRecipes;
import net.daveyx0.primitivemobs.core.PrimitiveMobsTileEntities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "primitivemobs", name = "Primitive Mobs", version = "1.0")
public class PrimitiveMobs {
  @Instance("primitivemobs")
  public static PrimitiveMobs instance = new PrimitiveMobs();
  
  public static String configPath;
  
  public static Logger PMlogger;
  
  public static TabPrimitiveMobs tabPrimitiveMobs;
  
  public static SimpleNetworkWrapper network;
  
  @SidedProxy(clientSide = "net.daveyx0.primitivemobs.client.PrimitiveMobsClientProxy", serverSide = "net.daveyx0.primitivemobs.common.PrimitiveMobsCommonProxy")
  public static PrimitiveMobsCommonProxy proxy;

  private static boolean isThaumcraftLoaded;
  
  @EventHandler
  public void PreInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new PrimitiveMobs());
    configPath = event.getModConfigurationDirectory() + "/primitivemobs/";
    PMlogger = Logger.getLogger("PrimitiveMobs");
    PMlogger.info("Config file path: " + configPath);
    PrimitiveMobsConfiguration.init(configPath);
    tabPrimitiveMobs = new TabPrimitiveMobs(CreativeTabs.getNextID(), "tabPrimitiveMobs");
    PrimitiveMobsEntities.init();
    PrimitiveMobsTileEntities.init();
    PrimitiveMobsItems.init();
    MinecraftForge.EVENT_BUS.register(new PrimitiveMobsEventHandler());
    FMLCommonHandler.instance().bus().register(new PrimitiveMobsFMLEventHandler());
    network = NetworkRegistry.INSTANCE.newSimpleChannel("primitivemobs");
    network.registerMessage(PrimitiveJumpPacket.Handler.class, PrimitiveJumpPacket.class, 0, Side.SERVER);
    network.registerMessage(PrimitiveColorPacket.Handler.class, PrimitiveColorPacket.class, 1, Side.SERVER);
    network.registerMessage(PrimitiveGroveSpritePacket.Handler.class, PrimitiveGroveSpritePacket.class, 2, Side.SERVER);
  }
  
  @EventHandler
  public void Init(FMLInitializationEvent event) {
    proxy.registerRenderers();
    PrimitiveMobsRecipes.init();
    AchievementPage.registerAchievementPage((AchievementPage)new PrimitiveMobsAchievementPage());
    
    this.isThaumcraftLoaded = Loader.isModLoaded("Thaumcraft");
    
    if (isThaumcraftLoaded)
     {PrimitiveMobsThaumcraftAspects.addThaumcraftAspects();};
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    BiomeDictionary.registerAllBiomesAndGenerateEvents();
    PrimitiveMobsEntitySpawning.postInit();
  }
}
