package net.daveyx0.primitivemobs.world.gen.features.trees;

import java.util.Random;
import net.daveyx0.primitivemobs.core.PrimitiveMobsBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenPrimitiveCinderTree extends WorldGenAbstractTree {
  public WorldGenPrimitiveCinderTree() {
    super(true);
  }
  
  public boolean generate(World world, Random random, int x, int y, int z) {
    while (world.isAirBlock(x, y, z) && y > 2)
      y--; 
    Block block = world.getBlock(x, y, z);
    if (!(block instanceof net.minecraft.block.BlockGrass) && !(block instanceof net.minecraft.block.BlockDirt))
      return false; 
    for (int i = -2; i <= 2; i++) {
      for (int j = -2; j <= 2; j++) {
        if (world.isAirBlock(x + i, y - 1, z + j) && world.isAirBlock(x + i, y - 2, z + j) && world.getBlock(x + i, y, z + j).isOpaqueCube() && 
          !world.isAirBlock(x + i, y, z + j))
          return false; 
      } 
    } 
    int length = 6 + random.nextInt(8);
    int branches = 2 + random.nextInt(4);
    int currentHeight = 1;
    block.onPlantGrow(world, x, y - 1, z, x, y, z);
    int randomDirection = random.nextInt(4) + 1;
    buildBlock(world, x, y + currentHeight, z, PrimitiveMobsBlocks.blockLog, 0, null);
    currentHeight++;
    generateRotatingBranch(world, random, x, y + currentHeight, z, length, randomDirection);
    buildBlock(world, x, y + currentHeight + length, z, PrimitiveMobsBlocks.blockLog, 0, null);
    generateTop(world, x, y + currentHeight, z, length);
    return true;
  }
  
  public void generateRotatingBranch(World world, Random random, int x, int y, int z, int l, int d) {
    ForgeDirection rotation = null;
    int branchChance = 1;
    int spriteChance = 1;
    for (int i = 0; i < l; i++) {
      if (d > 4)
        d = 1; 
      switch (d) {
        case 1:
          rotation = ForgeDirection.NORTH;
          buildBlock(world, x, y + i, z + 1, PrimitiveMobsBlocks.blockLog, 0, null);
          buildBlock(world, x + 1, y + i, z + 1, PrimitiveMobsBlocks.blockLog, 0, rotation);
          if (i == l - 1)
            buildBlock(world, x + 1, y + i + 1, z, PrimitiveMobsBlocks.blockLog, 0, null); 
          if (random.nextInt(10) == branchChance && i > 2)
            generateBranch(world, random, x, y + i, z, 0, 1, ForgeDirection.WEST); 
          break;
        case 2:
          rotation = ForgeDirection.WEST;
          buildBlock(world, x + 1, y + i, z, PrimitiveMobsBlocks.blockLog, 0, null);
          buildBlock(world, x + 1, y + i, z - 1, PrimitiveMobsBlocks.blockLog, 0, rotation);
          if (i == l - 1)
            buildBlock(world, x, y + i + 1, z - 1, PrimitiveMobsBlocks.blockLog, 0, null); 
          if (random.nextInt(10) == branchChance && i > 2)
            generateBranch(world, random, x, y + i, z, 1, 0, ForgeDirection.SOUTH); 
          break;
        case 3:
          rotation = ForgeDirection.SOUTH;
          buildBlock(world, x, y + i, z - 1, PrimitiveMobsBlocks.blockLog, 0, null);
          buildBlock(world, x - 1, y + i, z - 1, PrimitiveMobsBlocks.blockLog, 0, rotation);
          if (i == l - 1)
            buildBlock(world, x - 1, y + i + 1, z, PrimitiveMobsBlocks.blockLog, 0, null); 
          if (random.nextInt(10) == branchChance && i > 2)
            generateBranch(world, random, x, y + i, z, 0, -1, ForgeDirection.EAST); 
          break;
        case 4:
          rotation = ForgeDirection.EAST;
          buildBlock(world, x - 1, y + i, z, PrimitiveMobsBlocks.blockLog, 0, null);
          buildBlock(world, x - 1, y + i, z + 1, PrimitiveMobsBlocks.blockLog, 0, rotation);
          if (i == l - 1)
            buildBlock(world, x, y + i + 1, z + 1, PrimitiveMobsBlocks.blockLog, 0, null); 
          if (random.nextInt(10) == branchChance && i > 2)
            generateBranch(world, random, x, y + i, z, -1, 0, ForgeDirection.NORTH); 
          break;
      } 
      d++;
    } 
  }
  
  public void generateTop(World world, int x, int y, int z, int l) {
    int i;
    for (i = -2; i < 3; i++) {
      for (int j = -2; j < 3; j++) {
        if (i != 0 || j != 0)
          buildBlock(world, x + i, y + l, z + j, PrimitiveMobsBlocks.blockLeaf, 0, null); 
      } 
    } 
    buildBlock(world, x, y + l + 2, z, PrimitiveMobsBlocks.blockLeaf, 0, null);
    for (i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (i != 0 || j != 0) {
          buildBlock(world, x + i, y + l + 1, z + j, PrimitiveMobsBlocks.blockLeaf, 0, null);
          buildBlock(world, x + i, y + l + 2, z + j, (Block)Blocks.fire, 0, null);
        } 
      } 
    } 
  }
  
  public void generateBranch(World world, Random random, int x, int y, int z, int offsetX, int offsetZ, ForgeDirection rotation) {
    buildBlock(world, x + offsetX * 2, y, z + offsetZ * 2, PrimitiveMobsBlocks.blockLog, 0, rotation);
    buildBlock(world, x + offsetX * 3, y, z + offsetZ * 3, PrimitiveMobsBlocks.blockLog, 0, rotation);
    buildBlock(world, x + offsetX * 3 + 1, y, z + offsetZ * 3, PrimitiveMobsBlocks.blockLeaf, 0, null);
    buildBlock(world, x + offsetX * 3 - 1, y, z + offsetZ * 3, PrimitiveMobsBlocks.blockLeaf, 0, null);
    buildBlock(world, x + offsetX * 3, y, z + offsetZ * 3 + 1, PrimitiveMobsBlocks.blockLeaf, 0, null);
    buildBlock(world, x + offsetX * 3, y, z + offsetZ * 3 - 1, PrimitiveMobsBlocks.blockLeaf, 0, null);
    buildBlock(world, x + offsetX * 3, y + 1, z + offsetZ * 3, PrimitiveMobsBlocks.blockLeaf, 0, null);
    buildBlock(world, x + offsetX * 3 + 1, y + 1, z + offsetZ * 3, (Block)Blocks.fire, 0, null);
    buildBlock(world, x + offsetX * 3 - 1, y + 1, z + offsetZ * 3, (Block)Blocks.fire, 0, null);
    buildBlock(world, x + offsetX * 3, y + 1, z + offsetZ * 3 + 1, (Block)Blocks.fire, 0, null);
    buildBlock(world, x + offsetX * 3, y + 1, z + offsetZ * 3 - 1, (Block)Blocks.fire, 0, null);
    buildBlock(world, x + offsetX * 3, y + 2, z + offsetZ * 3, (Block)Blocks.fire, 0, null);
  }
  
  protected void setBlockAndMetadata(World world, int x, int y, int z, Block block, int meta) {
    setBlockAndNotifyAdequately(world, x, y, z, block, meta);
  }
  
  public void buildBlock(World world, int x, int y, int z, Block block, int meta, ForgeDirection rotation) {
    if (world.isAirBlock(x, y, z) || world.getBlock(x, y, z).isLeaves((IBlockAccess)world, x, y, z)) {
      setBlockAndMetadata(world, x, y, z, block, meta);
      if (rotation != null)
        world.getBlock(x, y, z).rotateBlock(world, x, y, z, rotation); 
    } 
  }
}
