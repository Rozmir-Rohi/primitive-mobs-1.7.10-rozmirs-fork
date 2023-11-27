package net.daveyx0.primitivemobs.config;

import java.io.File;
import java.util.logging.Level;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraftforge.common.config.Configuration;

public class PrimitiveMobsConfigurationSpawns {
  public static Configuration config;
  
  public static float rocketCreeperFreq;
  
  public static float festiveCreeperFreq;
  
  public static float supportCreeperFreq;
  
  public static float chameleonFreq;
  
  public static float treasureSlimeFreq;
  
  public static float hauntedToolFreq;
  
  public static float mimicChestFreq;
  
  public static float bewitchedTomeFreq;
  
  public static float travelingMerchantFreq;
  
  public static float lostMinerFreq;
  
  public static float brainSlimeFreq;
  
  public static float skeletonWarriorFreq;
  
  public static float motherSpiderFreq;
  
  public static float blazingJuggernautFreq;
  
  public static float summonerFreq;
  
  public static float sheepManFreq;
  
  public static float sheepManSmithFreq;
  
  public static float lilyLurkerFreq;
  
  public static float groveSpriteFreq;
  
  public static boolean isMultiplier;
  
  public static void init(File configFile) {
    config = new Configuration(configFile);
    try {
      config.load();
      config.addCustomCategoryComment("Frequency Multiplier Boolean", "The following boolean determines how spawn frequencies should be determined. \n Please, refer to the forum page to learn more about what this does.");
      isMultiplier = config.get("Frequency Multiplier Boolean", "Only put true or false. When put to false, please change the spawn frequencies below to prevent overpopulation.", true).getBoolean();
      config.addCustomCategoryComment("Mob Frequencies", "Change mob spawn frequencies here. 0 = no Spawns.\n Spawns as Mob means: Spawns around the player at all times usually at night.\nSpawns as Creature means: Spawns only once in an area, no matter how many times revisited.\nSpawns as WaterCreature means: Spawns like squids.\nSpawns as Ambient means: Spawns like bats.");
      rocketCreeperFreq = config.get("Mob Frequencies", "Rocket Creeper Spawn Frequency. Spawns as Mob", 22).getInt();
      festiveCreeperFreq = config.get("Mob Frequencies", "Festive Creeper Spawn Frequency. Spawns as Mob", 15).getInt();
      supportCreeperFreq = config.get("Mob Frequencies", "Support Creeper Spawn Frequency. Spawns as Mob", 15).getInt();
      treasureSlimeFreq = config.get("Mob Frequencies", "Treasure Slime Spawn Frequency. Spawns as Mob (Only underground)", 50).getInt();
      hauntedToolFreq = config.get("Mob Frequencies", "Haunted Tool Spawn Frequency. Spawns as Mob", 25).getInt();
      mimicChestFreq = config.get("Mob Frequencies", "Mimic Chest Spawn Frequency. Spawns as Mob (Only underground)", 30).getInt();
      bewitchedTomeFreq = config.get("Mob Frequencies", "Bewitched Tome Spawn Frequency. Spawns as Mob (Only underground)", 40).getInt();
      lostMinerFreq = config.get("Mob Frequencies", "Lost Miner Spawn Frequency. Spawns as Mob (Only underground)", 40).getInt();
      skeletonWarriorFreq = config.get("Mob Frequencies", "Skeleton Warrior Spawn Frequency. Spawns as Mob", 20).getInt();
      motherSpiderFreq = config.get("Mob Frequencies", "Mother Spider Spawn Frequency. Spawns as Mob", 15).getInt();
      summonerFreq = config.get("Mob Frequencies", "Summoner Spawn Frequency. Spawns as Mob", 15).getInt();
      blazingJuggernautFreq = config.get("Mob Frequencies", "Blazing Juggernaut Spawn Frequency. Spawns as Mob (Only Nether)", 25).getInt();
      sheepManFreq = config.get("Mob Frequencies", "Sheepman Spawn Frequency. Spawns as Mob (Only Nether)", 15).getInt();
      sheepManSmithFreq = config.get("Mob Frequencies", "Sheepman Smith Spawn Frequency. Spawns as Mob (Only Nether)", 10).getInt();
      chameleonFreq = config.get("Mob Frequencies", "Chameleon Spawn Frequency. Spawns as Creature", 80).getInt();
      travelingMerchantFreq = config.get("Mob Frequencies", "Traveling Merchant Spawn Frequency. Spawns as Creature", 60).getInt();
      brainSlimeFreq = config.get("Mob Frequencies", "Brain Slime Spawn Frequency. Spawns as Mob (Only on beaches)", 150).getInt();
      lilyLurkerFreq = config.get("Mob Frequencies", "Lily Lurker Spawn Frequency. Spawns as WaterCreature (In swamps in water)", 70).getInt();
      PrimitiveMobs.PMlogger.info("Loaded Spawnrate configuration file");
    } catch (Throwable e) {
      PrimitiveMobs.PMlogger.log(Level.SEVERE, "PrimitiveMobs cannot load the configuration file: SpawnRates");
    } finally {
      if (config.hasChanged())
        config.save(); 
    } 
  }
  
  public static boolean getIsMultiplier() {
    return isMultiplier;
  }
  
  public static float getRocketCreeperFreq() {
    return rocketCreeperFreq;
  }
  
  public static float getFestiveCreeperFreq() {
    return festiveCreeperFreq;
  }
  
  public static float getSupportCreeperFreq() {
    return supportCreeperFreq;
  }
  
  public static float getChameleonFreq() {
    return chameleonFreq;
  }
  
  public static float getTreasureSlimeFreq() {
    return treasureSlimeFreq;
  }
  
  public static float getHauntedToolFreq() {
    return hauntedToolFreq;
  }
  
  public static float getMimicChestFreq() {
    return mimicChestFreq;
  }
  
  public static float getBewitchedTomeFreq() {
    return bewitchedTomeFreq;
  }
  
  public static float getTravelingMerchantFreq() {
    return travelingMerchantFreq;
  }
  
  public static float getLostMinerFreq() {
    return lostMinerFreq;
  }
  
  public static float getBrainSlimeFreq() {
    return brainSlimeFreq;
  }
  
  public static float getSkeletonWarriorFreq() {
    return skeletonWarriorFreq;
  }
  
  public static float getMotherSpiderFreq() {
    return motherSpiderFreq;
  }
  
  public static float getBlazingJuggernautFreq() {
    return blazingJuggernautFreq;
  }
  
  public static float getSheepmanFreq() {
    return sheepManFreq;
  }
  
  public static float getSummonerFreq() {
    return summonerFreq;
  }
  
  public static float getSheepmanSmithFreq() {
    return sheepManSmithFreq;
  }
  
  public static float getLilyLurkerFreq() {
    return lilyLurkerFreq;
  }
  
  public static float getGroveSpriteFreq() {
    return groveSpriteFreq;
  }
}
