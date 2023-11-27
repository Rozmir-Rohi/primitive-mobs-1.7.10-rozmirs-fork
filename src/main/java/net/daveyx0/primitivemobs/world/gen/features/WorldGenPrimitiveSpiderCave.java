package net.daveyx0.primitivemobs.world.gen.features;

import java.util.Random;
import net.daveyx0.primitivemobs.entity.spawnhandler.EntityCaveSpiderSpawner;
import net.daveyx0.primitivemobs.lib.CustomChestGenHooks;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenPrimitiveSpiderCave extends WorldGenerator {
  int xMax = 0;
  
  int yMax = 0;
  
  int zMax = 0;
  
  public boolean generate(World world, Random random, int x, int y, int z) {
    int radius = 3;
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        for (int k = 0; k < 2; k++)
          createRandomSphere(world, random, x + 2 * i, y - 2 * k, z + 2 * j, radius); 
      } 
    } 
    decorateWithWebs(world, random, x + 2, y - 2, z + 2, 6, 4);
    for (int l = 0; l < 32; l++) {
      if (!world.isAirBlock(x, y - l - 1, z)) {
        world.setBlock(x, y - l, z, (Block)Blocks.chest, 0, 0);
        TileEntityChest var12 = (TileEntityChest)world.getTileEntity(x, y - l, z);
        CustomChestGenHooks info = CustomChestGenHooks.getInfo("chestSpiderCave");
        if (var12 != null)
          WeightedRandomChestContent.generateChestContents(random, info.getItems(random), (IInventory)var12, info.getCount(random)); 
        if (!world.isRemote) {
          EntityCaveSpiderSpawner spawner = new EntityCaveSpiderSpawner(world);
          spawner.setLocationAndAngles(x, (y - l + 1), z, 0.0F, 0.0F);
          world.spawnEntityInWorld((Entity)spawner);
        } 
        break;
      } 
    } 
    return true;
  }
  
  public void generateNest(World world, Random random, int x, int y, int z) {}
  
  public void buildBlock(World world, int x, int y, int z, Block block, int meta, ForgeDirection rotation) {
    if (world.isAirBlock(x, y, z)) {
      world.setBlock(x, y, z, block, meta, 2);
      if (rotation != null)
        world.getBlock(x, y, z).rotateBlock(world, x, y, z, rotation); 
    } 
  }
  
  public void createCleanSphere(World world, Random random, int i, int j, int k, int radius) {
    int minimum = -radius;
    for (int i1 = -radius; i1 < radius; i1++) {
      for (int k1 = -radius; k1 < radius; k1++) {
        for (int j1 = radius; j1 > minimum; j1--) {
          if (Math.pow(i1, 2.0D) + Math.pow(j1, 2.0D) + Math.pow(k1, 2.0D) < Math.pow(radius, 2.0D))
            world.setBlock(i + i1, j + j1, k + k1, Blocks.air, 0, 3); 
        } 
      } 
    } 
  }
  
  public void decorateWithWebs(World world, Random random, int i, int j, int k, int radius, int webChance) {
    int zRadius = this.zMax * 2;
    int xRadius = this.xMax * 2;
    int yRadius = this.yMax * 2;
    int minimum = -yRadius;
    for (int i1 = -xRadius; i1 < xRadius; i1++) {
      for (int k1 = -zRadius; k1 < zRadius; k1++) {
        for (int j1 = yRadius; j1 > minimum; j1--) {
          if (Math.pow(i1, 2.0D) + Math.pow(j1, 2.0D) + Math.pow(k1, 2.0D) < Math.pow((radius * 2), 2.0D))
            if (canEdit(world, i + i1, j + j1, k + k1))
              if (world.isAirBlock(i + i1, j + j1, k + k1) && random.nextInt(webChance) == 0 && (!world.isAirBlock(i + i1 + 1, j + j1, k + k1) || !world.isAirBlock(i + i1 - 1, j + j1, k + k1) || 
                !world.isAirBlock(i + i1, j + j1 + 1, k + k1) || !world.isAirBlock(i + i1, j + j1 - 1, k + k1) || !world.isAirBlock(i + i1, j + j1, k + k1 + 1) || 
                !world.isAirBlock(i + i1, j + j1, k + k1 - 1)))
                world.setBlock(i + i1, j + j1, k + k1, Blocks.web, 0, 3);   
        } 
      } 
    } 
  }
  
  public void createRandomSphere(World world, Random random, int i, int j, int k, int radius) {
    int zRadius = random.nextInt(radius) + 3;
    int xRadius = random.nextInt(radius) + 3;
    int yRadius = random.nextInt(radius) + 3;
    if (zRadius > this.zMax)
      this.zMax = zRadius; 
    if (xRadius > this.xMax)
      this.xMax = xRadius; 
    if (yRadius > this.yMax)
      this.yMax = yRadius; 
    int minimum = -yRadius;
    for (int i1 = -xRadius; i1 < xRadius; i1++) {
      for (int k1 = -zRadius; k1 < zRadius; k1++) {
        for (int j1 = yRadius; j1 > minimum; j1--) {
          if (Math.pow(i1, 2.0D) + Math.pow(j1, 2.0D) + Math.pow(k1, 2.0D) < Math.pow((radius + 3), 2.0D))
            world.setBlock(i + i1, j + j1, k + k1, Blocks.air, 0, 3); 
        } 
      } 
    } 
  }
  
  public boolean canEdit(World world, int i, int j, int k) {
    return (world.getBlock(i, j, k) != Blocks.web);
  }
}
