package net.daveyx0.primitivemobs.lib;

import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class SpawnListWeightHelper {
  public static float checkAverageWeight(EnumCreatureType type, BiomeGenBase biome) {
    float totalWeight = 0.0F;
    List<BiomeGenBase.SpawnListEntry> spawns = biome.getSpawnableList(type);
    for (BiomeGenBase.SpawnListEntry entry : spawns)
      totalWeight += entry.itemWeight; 
    float averageWeight = totalWeight / spawns.size();
    return averageWeight;
  }
}
