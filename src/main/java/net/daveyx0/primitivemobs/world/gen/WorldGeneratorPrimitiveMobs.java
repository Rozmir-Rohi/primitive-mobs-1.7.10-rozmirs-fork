package net.daveyx0.primitivemobs.world.gen;

import cpw.mods.fml.common.IWorldGenerator;
import java.util.Random;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationGeneral;
import net.daveyx0.primitivemobs.world.gen.features.WorldGenPrimitiveSpiderCave;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;

public class WorldGeneratorPrimitiveMobs implements IWorldGenerator {
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
    switch (world.provider.dimensionId) {
      case 0:
        generateSurface(world, random, chunkX * 16, chunkZ * 16);
      case 1:
        generateEnd(world, random, chunkX * 16, chunkZ * 16);
      case -1:
        generateNether(world, random, chunkX * 16, chunkZ * 16);
        break;
    } 
  }
  
  private void generateSurface(World world, Random random, int x, int z) {
    BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(x, z);
    BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(biome);
    for (int i = 0; i < types.length; i++) {
      if (types[i] != null && types[i] == BiomeDictionary.Type.PLAINS)
        if (PrimitiveMobsConfigurationGeneral.getSpiderCaveSpawnRate() != 0 && random.nextInt(PrimitiveMobsConfigurationGeneral.getSpiderCaveSpawnRate()) == 0) {
          int y = 32 + random.nextInt(12);
          PrimitiveMobs.PMlogger.info("Spider Cave @" + x + " " + y + " " + z);
          WorldGenPrimitiveSpiderCave spiderCave = new WorldGenPrimitiveSpiderCave();
          spiderCave.generate(world, random, x, y, z);
        }  
    } 
  }
  
  private void generateNether(World world, Random random, int x, int z) {}
  
  private void generateEnd(World world, Random random, int x, int z) {}
}
