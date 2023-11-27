package net.daveyx0.primitivemobs.config;

import java.io.File;
import java.util.logging.Level;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraftforge.common.config.Configuration;

public class PrimitiveMobsConfigurationGeneral {
  public static Configuration config;
  
  public static boolean villagerSounds;
  
  public static boolean blueMinionSteal;
  
  public static boolean lilyLurkerAdaptive;
  
  public static int spiderCaveSpawnRate;
  
  public static int stickyFeetID;
  
  public static void init(File configFile) {
    config = new Configuration(configFile);
    try {
      config.load();
      config.addCustomCategoryComment("General Settings", "Some general settings regarding this mod.");
      villagerSounds = config.get("General Settings", "Only put true or false. Disable or enable villager sounds used by this mod", true).getBoolean();
      blueMinionSteal = config.get("General Settings", "Only put true or false. Disable or enable the weapon stealing from mobs ability of a blue Skull Minion", true).getBoolean();
      config.addCustomCategoryComment("Texture Settings", "Some settings related to textures for the mod.");
      lilyLurkerAdaptive = config.get("Texture Settings", "Only put true or false. Disable or enable waterlily texture adaptive Lily Lurker textures. Put false to use the waterlily.png in my mod. ", true).getBoolean();
      config.addCustomCategoryComment("Structure Settings", "Some settings related the generating structures of the mod.");
      spiderCaveSpawnRate = config.get("Structure Settings", "Rarity of the Spider Caves. Higher = rarer. It's a 1/input chance. Put 0 to prevent it from generating.", 50).getInt();
      config.addCustomCategoryComment("Enchantment Settings", "Some settings related to enchantment IDs.");
      stickyFeetID = config.get("Enchantment Settings", "Enchantment ID for the Sticky Feet enchantment. Max = 255.", 120).getInt();
      PrimitiveMobs.PMlogger.info("Loaded General configuration file");
    } catch (Throwable e) {
      PrimitiveMobs.PMlogger.log(Level.SEVERE, "PrimitiveMobs cannot load the configuration file: General");
    } finally {
      if (config.hasChanged())
        config.save(); 
    } 
  }
  
  public static boolean getVillagerSounds() {
    return villagerSounds;
  }
  
  public static boolean getCanBlueMinionSteal() {
    return blueMinionSteal;
  }
  
  public static boolean getWaterLilyAdaptive() {
    return lilyLurkerAdaptive;
  }
  
  public static int getSpiderCaveSpawnRate() {
    return spiderCaveSpawnRate;
  }
  
  public static int getStickyFeetID() {
    return stickyFeetID;
  }
}
