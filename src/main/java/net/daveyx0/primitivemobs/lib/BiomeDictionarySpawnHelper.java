package net.daveyx0.primitivemobs.lib;

import cpw.mods.fml.common.registry.EntityRegistry;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeDictionarySpawnHelper {
  static BiomeGenBase[] biomes;
  
  public static boolean spawn;
  
  static float averageWeight = 0.0F;
  
  public static void listAllBiomesForAllTypes() {
    String totaldoc = null;
    BiomeDictionary.Type[] typeList = BiomeDictionary.Type.values();
    for (int i = 0; i < typeList.length; i++) {
      biomes = BiomeDictionary.getBiomesForType(typeList[i]);
      String biomeList = "Biomes for Type " + typeList[i] + " are ";
      for (int j = 0; j < biomes.length; j++)
        biomeList = biomeList + (biomes[j]).biomeName + ", "; 
      totaldoc = totaldoc + "\n" + biomeList;
    } 
    try {
      File newTextFile = new File(PrimitiveMobs.configPath + "biomes.txt");
      FileWriter fw = new FileWriter(newTextFile);
      fw.write(totaldoc);
      fw.close();
    } catch (IOException iox) {
      iox.printStackTrace();
    } 
  }
  
  public static void addToAllBiomes(Class entityClass, float weightedProb, int min, int max, EnumCreatureType typeOfCreature, boolean isMultiplier) {
    if (weightedProb <= 0.0F) {
      spawn = false;
    } else {
      spawn = true;
    } 
    if (spawn) {
      BiomeDictionary.Type[] typeList = BiomeDictionary.Type.values();
      for (int i = 0; i < typeList.length; i++) {
        biomes = BiomeDictionary.getBiomesForType(typeList[i]);
        for (int j = 0; j < biomes.length; j++) {
          int weight;
          if (isMultiplier) {
            averageWeight = SpawnListWeightHelper.checkAverageWeight(typeOfCreature, biomes[j]);
            weight = (int)(weightedProb * averageWeight);
          } else {
            weight = (int)weightedProb;
          } 
          if (weight != 0)
            addEntitySpawning(entityClass, weight, min, max, typeOfCreature, new BiomeGenBase[] { biomes[j] }); 
        } 
      } 
    } 
  }
  
  public static void addToAllSurfaceBiomes(Class entityClass, float weightedProb, int min, int max, EnumCreatureType typeOfCreature, boolean isMultiplier) {
    if (weightedProb <= 0.0F) {
      spawn = false;
    } else {
      spawn = true;
    } 
    if (spawn) {
      BiomeDictionary.Type[] typeList = BiomeDictionary.Type.values();
      for (int i = 0; i < typeList.length; i++) {
        biomes = BiomeDictionary.getBiomesForType(typeList[i]);
        if (typeList[i] != BiomeDictionary.Type.NETHER && typeList[i] != BiomeDictionary.Type.END)
          for (int j = 0; j < biomes.length; j++) {
            int weight;
            if (isMultiplier) {
              averageWeight = SpawnListWeightHelper.checkAverageWeight(typeOfCreature, biomes[j]);
              weight = (int)(weightedProb * averageWeight);
            } else {
              weight = (int)weightedProb;
            } 
            if (weight != 0)
              addEntitySpawning(entityClass, weight, min, max, typeOfCreature, new BiomeGenBase[] { biomes[j] }); 
          }  
      } 
      BiomeGenBase[] netherBiomes = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.NETHER);
      for (int k = 0; k < netherBiomes.length; k++) {
        EntityRegistry.removeSpawn(entityClass, typeOfCreature, new BiomeGenBase[] { netherBiomes[k] });
      } 
      BiomeGenBase[] endBiomes = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.END);
      for (int l = 0; l < endBiomes.length; l++) {
        EntityRegistry.removeSpawn(entityClass, typeOfCreature, new BiomeGenBase[] { endBiomes[l] });
      } 
    } 
  }
  
  public static void addToAllBiomesWithType(Class entityClass, float weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeDictionary.Type type, boolean isMultiplier) {
    if (weightedProb <= 0.0F) {
      spawn = false;
    } else {
      spawn = true;
    } 
    if (spawn) {
      biomes = BiomeDictionary.getBiomesForType(type);
      for (int j = 0; j < biomes.length; j++) {
        int weight;
        if (isMultiplier) {
          averageWeight = SpawnListWeightHelper.checkAverageWeight(typeOfCreature, biomes[j]);
          weight = (int)(weightedProb * averageWeight);
        } else {
          weight = (int)weightedProb;
        } 
        if (weight != 0)
          addEntitySpawning(entityClass, weight, min, max, typeOfCreature, new BiomeGenBase[] { biomes[j] }); 
      } 
    } 
  }
  
  public static void addToSingleTypeBiome(Class entityClass, float weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeDictionary.Type type, boolean isMultiplier) {
    if (weightedProb <= 0.0F) {
      spawn = false;
    } else {
      spawn = true;
    } 
    if (spawn) {
      biomes = BiomeDictionary.getBiomesForType(type);
      for (int i = 0; i < biomes.length; i++) {
        BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(biomes[i]);
        for (int j = 0; j < types.length; j++) {
          if (types[j] == type) {
            int weight;
            if (isMultiplier) {
              averageWeight = SpawnListWeightHelper.checkAverageWeight(typeOfCreature, biomes[i]);
              weight = (int)(weightedProb * averageWeight);
            } else {
              weight = (int)weightedProb;
            } 
            if (weight != 0)
              addEntitySpawning(entityClass, weight, min, max, typeOfCreature, new BiomeGenBase[] { biomes[i] }); 
          } 
        } 
      } 
    } 
  }
  
  public static void addToDoubleTypeBiome(Class entityClass, float weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeDictionary.Type type1, BiomeDictionary.Type type2, boolean isMultiplier) {
    if (weightedProb <= 0.0F) {
      spawn = false;
    } else {
      spawn = true;
    } 
    if (spawn) {
      biomes = BiomeDictionary.getBiomesForType(type1);
      for (int i = 0; i < biomes.length; i++) {
        if (BiomeDictionary.isBiomeOfType(biomes[i], type2)) {
          int weight;
          if (isMultiplier) {
            averageWeight = SpawnListWeightHelper.checkAverageWeight(typeOfCreature, biomes[i]);
            weight = (int)(weightedProb * averageWeight);
          } else {
            weight = (int)weightedProb;
          } 
          if (weight != 0)
            addEntitySpawning(entityClass, weight, min, max, typeOfCreature, new BiomeGenBase[] { biomes[i] }); 
        } 
      } 
    } 
  }
  
  public static void addToTripleTypeBiome(Class entityClass, float weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeDictionary.Type type1, BiomeDictionary.Type type2, BiomeDictionary.Type type3, boolean isMultiplier) {
    if (weightedProb <= 0.0F) {
      spawn = false;
    } else {
      spawn = true;
    } 
    if (spawn) {
      biomes = BiomeDictionary.getBiomesForType(type1);
      for (int i = 0; i < biomes.length; i++) {
        if (BiomeDictionary.isBiomeOfType(biomes[i], type2) && BiomeDictionary.isBiomeOfType(biomes[i], type3)) {
          int weight;
          if (isMultiplier) {
            averageWeight = SpawnListWeightHelper.checkAverageWeight(typeOfCreature, biomes[i]);
            weight = (int)(weightedProb * averageWeight);
          } else {
            weight = (int)weightedProb;
          } 
          if (weight != 0)
            addEntitySpawning(entityClass, weight, min, max, typeOfCreature, new BiomeGenBase[] { biomes[i] }); 
        } 
      } 
    } 
  }
  
  public static void addEntitySpawning(Class entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes) {
    int adjmin = settingsLimiter(min);
    int adjmax = settingsLimiter(max);
    EntityRegistry.addSpawn(entityClass, weightedProb, adjmin, adjmax, typeOfCreature, biomes);
  }
  
  public static int settingsLimiter(int par1) {
    if (par1 > 8)
      return par1 = 8; 
    if (par1 < 0)
      return par1 = 0; 
    return par1;
  }
}
