package net.daveyx0.primitivemobs.core;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationSpawns;
import net.daveyx0.primitivemobs.entity.monster.EntityBlazingJuggernaut;
import net.daveyx0.primitivemobs.entity.monster.EntityBrainSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityDEnchantedBook;
import net.daveyx0.primitivemobs.entity.monster.EntityDMimic;
import net.daveyx0.primitivemobs.entity.monster.EntityDSkeletonWarrior;
import net.daveyx0.primitivemobs.entity.monster.EntityDSummoner;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityLilyLurker;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.daveyx0.primitivemobs.entity.passive.EntityLostMiner;
import net.daveyx0.primitivemobs.entity.passive.EntitySheepman;
import net.daveyx0.primitivemobs.entity.passive.EntitySheepmanSmith;
import net.daveyx0.primitivemobs.entity.passive.EntityTravelingMerchant;
import net.daveyx0.primitivemobs.lib.BiomeDictionarySpawnHelper;
import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.BiomeDictionary;

public class PrimitiveMobsEntitySpawning {
  static BiomeDictionary.Type[] chameleonSpawns = new BiomeDictionary.Type[] { BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.JUNGLE };
  
  static BiomeDictionary.Type[] merchantSpawns = new BiomeDictionary.Type[] { BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.SANDY };
  
  static BiomeDictionary.Type[] brainSlimeSpawns = new BiomeDictionary.Type[] { BiomeDictionary.Type.BEACH, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.RIVER };
  
  static BiomeDictionary.Type[] lilyLurkerSpawns = new BiomeDictionary.Type[] { BiomeDictionary.Type.SWAMP };
  
  static BiomeDictionary.Type[] groveSpriteSpawns = new BiomeDictionary.Type[] { BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.SAVANNA };
  
  public static void postInit() {
    addRegularMobSpawns(EntityRocketCreeper.class, PrimitiveMobsConfigurationSpawns.getRocketCreeperFreq(), 1, 3);
    addRegularMobSpawns(EntityFestiveCreeper.class, PrimitiveMobsConfigurationSpawns.getFestiveCreeperFreq(), 1, 2);
    addRegularMobSpawns(EntitySupportCreeper.class, PrimitiveMobsConfigurationSpawns.getSupportCreeperFreq(), 1, 3);
    addRegularMobSpawns(EntityTreasureSlime.class, PrimitiveMobsConfigurationSpawns.getTreasureSlimeFreq(), 1, 3);
    addRegularMobSpawns(EntityHauntedTool.class, PrimitiveMobsConfigurationSpawns.getHauntedToolFreq(), 1, 3);
    addRegularMobSpawns(EntityDMimic.class, PrimitiveMobsConfigurationSpawns.getMimicChestFreq(), 1, 1);
    addRegularMobSpawns(EntityDEnchantedBook.class, PrimitiveMobsConfigurationSpawns.getBewitchedTomeFreq(), 1, 3);
    addRegularMobSpawns(EntityLostMiner.class, PrimitiveMobsConfigurationSpawns.getLostMinerFreq(), 1, 2);
    addRegularMobSpawns(EntityDSkeletonWarrior.class, PrimitiveMobsConfigurationSpawns.getSkeletonWarriorFreq(), 1, 2);
    addRegularMobSpawns(EntityMotherSpider.class, PrimitiveMobsConfigurationSpawns.getMotherSpiderFreq(), 1, 1);
    addRegularMobSpawns(EntityDSummoner.class, PrimitiveMobsConfigurationSpawns.getSummonerFreq(), 1, 1);
    addNetherMobSpawns(EntityBlazingJuggernaut.class, PrimitiveMobsConfigurationSpawns.getBlazingJuggernautFreq(), 1, 2);
    addNetherMobSpawns(EntitySheepman.class, PrimitiveMobsConfigurationSpawns.getSheepmanFreq(), 1, 2);
    addNetherMobSpawns(EntitySheepmanSmith.class, PrimitiveMobsConfigurationSpawns.getSheepmanSmithFreq(), 1, 1);
    addSpecificCreatureSpawns(EntityChameleon.class, PrimitiveMobsConfigurationSpawns.getChameleonFreq(), 1, 3, chameleonSpawns);
    addSpecificCreatureSpawns(EntityTravelingMerchant.class, PrimitiveMobsConfigurationSpawns.getTravelingMerchantFreq(), 1, 1, merchantSpawns);
    addSpecificMobSpawns(EntityBrainSlime.class, PrimitiveMobsConfigurationSpawns.getBrainSlimeFreq(), 1, 2, brainSlimeSpawns);
    addSpecificMobSpawns(EntityGroveSprite.class, PrimitiveMobsConfigurationSpawns.getGroveSpriteFreq(), 1, 3, groveSpriteSpawns);
    addSpecificWaterCreatureSpawns(EntityLilyLurker.class, PrimitiveMobsConfigurationSpawns.getLilyLurkerFreq(), 1, 2, lilyLurkerSpawns);
  }
  
  public static void addRegularMobSpawns(Class entityClass, float weightedProb, int min, int max) {
    EnumCreatureType typeOfCreature = EnumCreatureType.monster;
    if (PrimitiveMobsConfigurationSpawns.getIsMultiplier())
      weightedProb /= 100.0F; 
    BiomeDictionarySpawnHelper.addToAllSurfaceBiomes(entityClass, weightedProb, min, max, typeOfCreature, PrimitiveMobsConfigurationSpawns.getIsMultiplier());
  }
  
  public static void addNetherMobSpawns(Class entityClass, float weightedProb, int min, int max) {
    EnumCreatureType typeOfCreature = EnumCreatureType.monster;
    if (PrimitiveMobsConfigurationSpawns.getIsMultiplier())
      weightedProb /= 100.0F; 
    BiomeDictionarySpawnHelper.addToAllBiomesWithType(entityClass, weightedProb, min, max, typeOfCreature, BiomeDictionary.Type.NETHER, PrimitiveMobsConfigurationSpawns.getIsMultiplier());
  }
  
  public static void addSpecificMobSpawns(Class entityClass, float weightedProb, int min, int max, BiomeDictionary.Type[] biomeTypes) {
    EnumCreatureType typeOfCreature = EnumCreatureType.monster;
    if (PrimitiveMobsConfigurationSpawns.getIsMultiplier())
      weightedProb /= 100.0F; 
    for (int i = 0; i < biomeTypes.length; i++)
      BiomeDictionarySpawnHelper.addToAllBiomesWithType(entityClass, weightedProb, min, max, typeOfCreature, biomeTypes[i], PrimitiveMobsConfigurationSpawns.getIsMultiplier()); 
  }
  
  public static void addSpecificCreatureSpawns(Class entityClass, float weightedProb, int min, int max, BiomeDictionary.Type[] biomeTypes) {
    EnumCreatureType typeOfCreature = EnumCreatureType.creature;
    if (PrimitiveMobsConfigurationSpawns.getIsMultiplier())
      weightedProb /= 100.0F; 
    for (int i = 0; i < biomeTypes.length; i++)
      BiomeDictionarySpawnHelper.addToAllBiomesWithType(entityClass, weightedProb, min, max, typeOfCreature, biomeTypes[i], PrimitiveMobsConfigurationSpawns.getIsMultiplier()); 
  }
  
  public static void addSpecificWaterCreatureSpawns(Class entityClass, float weightedProb, int min, int max, BiomeDictionary.Type[] biomeTypes) {
    EnumCreatureType typeOfCreature = EnumCreatureType.waterCreature;
    if (PrimitiveMobsConfigurationSpawns.getIsMultiplier())
      weightedProb /= 100.0F; 
    for (int i = 0; i < biomeTypes.length; i++)
      BiomeDictionarySpawnHelper.addToAllBiomesWithType(entityClass, weightedProb, min, max, typeOfCreature, biomeTypes[i], PrimitiveMobsConfigurationSpawns.getIsMultiplier()); 
  }
}
