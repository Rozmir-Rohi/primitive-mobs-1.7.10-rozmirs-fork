package net.daveyx0.primitivemobs.world.gen.features.trees;

import java.util.Random;
import net.daveyx0.primitivemobs.core.PrimitiveMobsBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenPrimitiveDefaultTree extends WorldGenAbstractTree {
  private final int minTreeHeight;
  
  private final int randomTreeHeight;
  
  private final Block wood;
  
  private final Block leaves;
  
  private final boolean vinesGrow;
  
  private final int metaWood;
  
  private final int metaLeaves;
  
  private static final String __OBFID = "CL_00000438";
  
  public WorldGenPrimitiveDefaultTree(Block parWood, Block parLeaves, int metaDataWood, int metaDataLeaves) {
    this(parWood, parLeaves, metaDataWood, metaDataLeaves, true, 3, 5, false);
  }
  
  public WorldGenPrimitiveDefaultTree(Block parWood, Block parLeaves, int metaDataWood, int metaDataLeaves, boolean doBlockNotify, int par1, int par2, boolean vines) {
    super(doBlockNotify);
    this.minTreeHeight = par1;
    this.wood = parWood;
    this.leaves = parLeaves;
    this.metaWood = metaDataWood;
    this.metaLeaves = metaDataLeaves;
    this.randomTreeHeight = par2;
    this.vinesGrow = vines;
  }
  
  public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
    int l = p_76484_2_.nextInt(3) + this.minTreeHeight;
    boolean flag = true;
    if (p_76484_4_ >= 1 && p_76484_4_ + l + 1 <= 256) {
      for (int i1 = p_76484_4_; i1 <= p_76484_4_ + 1 + l; i1++) {
        byte b0 = 1;
        if (i1 == p_76484_4_)
          b0 = 0; 
        if (i1 >= p_76484_4_ + 1 + l - 2)
          b0 = 2; 
        for (int j1 = p_76484_3_ - b0; j1 <= p_76484_3_ + b0 && flag; j1++) {
          for (int k1 = p_76484_5_ - b0; k1 <= p_76484_5_ + b0 && flag; k1++) {
            if (i1 >= 0 && i1 < 256) {
              Block block = p_76484_1_.getBlock(j1, i1, k1);
              if (!isReplaceable(p_76484_1_, j1, i1, k1))
                flag = false; 
            } else {
              flag = false;
            } 
          } 
        } 
      } 
      if (!flag)
        return false; 
      Block block2 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_);
      boolean isSoil = block2.canSustainPlant((IBlockAccess)p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, ForgeDirection.UP, (IPlantable)PrimitiveMobsBlocks.blockSapling);
      if (isSoil && p_76484_4_ < 256 - l - 1) {
        block2.onPlantGrow(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, p_76484_3_, p_76484_4_, p_76484_5_);
        byte b0 = 3;
        byte b1 = 0;
        int k1;
        for (k1 = p_76484_4_ - b0 + l; k1 <= p_76484_4_ + l; k1++) {
          int i3 = k1 - p_76484_4_ + l;
          int l1 = b1 + 1 - i3 / 2;
          for (int i2 = p_76484_3_ - l1; i2 <= p_76484_3_ + l1; i2++) {
            int j2 = i2 - p_76484_3_;
            for (int k2 = p_76484_5_ - l1; k2 <= p_76484_5_ + l1; k2++) {
              int l2 = k2 - p_76484_5_;
              if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || (p_76484_2_.nextInt(2) != 0 && i3 != 0)) {
                Block block1 = p_76484_1_.getBlock(i2, k1, k2);
                if (block1.isAir((IBlockAccess)p_76484_1_, i2, k1, k2) || block1.isLeaves((IBlockAccess)p_76484_1_, i2, k1, k2))
                  setBlockAndNotifyAdequately(p_76484_1_, i2, k1, k2, PrimitiveMobsBlocks.blockLeaf, this.metaLeaves); 
              } 
            } 
          } 
        } 
        for (k1 = 0; k1 < l; k1++) {
          Block block = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + k1, p_76484_5_);
          if (block.isAir((IBlockAccess)p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_) || block.isLeaves((IBlockAccess)p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_)) {
            setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_, PrimitiveMobsBlocks.blockLog, this.metaWood);
            if (this.vinesGrow && k1 > 0) {
              if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ - 1, p_76484_4_ + k1, p_76484_5_))
                setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_ - 1, p_76484_4_ + k1, p_76484_5_, Blocks.vine, 8); 
              if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ + 1, p_76484_4_ + k1, p_76484_5_))
                setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + k1, p_76484_5_, Blocks.vine, 2); 
              if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ + k1, p_76484_5_ - 1))
                setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_ - 1, Blocks.vine, 1); 
              if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ + k1, p_76484_5_ + 1))
                setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_, p_76484_4_ + k1, p_76484_5_ + 1, Blocks.vine, 4); 
            } 
          } 
        } 
        if (this.vinesGrow) {
          for (k1 = p_76484_4_ - 3 + l; k1 <= p_76484_4_ + l; k1++) {
            int i3 = k1 - p_76484_4_ + l;
            int l1 = 2 - i3 / 2;
            for (int i2 = p_76484_3_ - l1; i2 <= p_76484_3_ + l1; i2++) {
              for (int j2 = p_76484_5_ - l1; j2 <= p_76484_5_ + l1; j2++) {
                if (p_76484_1_.getBlock(i2, k1, j2).isLeaves((IBlockAccess)p_76484_1_, i2, k1, j2)) {
                  if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(i2 - 1, k1, j2).isAir((IBlockAccess)p_76484_1_, i2 - 1, k1, j2))
                    growVines(p_76484_1_, i2 - 1, k1, j2, 8); 
                  if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(i2 + 1, k1, j2).isAir((IBlockAccess)p_76484_1_, i2 + 1, k1, j2))
                    growVines(p_76484_1_, i2 + 1, k1, j2, 2); 
                  if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(i2, k1, j2 - 1).isAir((IBlockAccess)p_76484_1_, i2, k1, j2 - 1))
                    growVines(p_76484_1_, i2, k1, j2 - 1, 1); 
                  if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(i2, k1, j2 + 1).isAir((IBlockAccess)p_76484_1_, i2, k1, j2 + 1))
                    growVines(p_76484_1_, i2, k1, j2 + 1, 4); 
                } 
              } 
            } 
          } 
          if (p_76484_2_.nextInt(5) == 0 && l > 5)
            for (k1 = 0; k1 < 2; k1++) {
              for (int i3 = 0; i3 < 4; i3++) {
                if (p_76484_2_.nextInt(4 - k1) == 0) {
                  int l1 = p_76484_2_.nextInt(3);
                  setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_ + Direction.offsetX[Direction.rotateOpposite[i3]], p_76484_4_ + l - 5 + k1, p_76484_5_ + Direction.offsetZ[Direction.rotateOpposite[i3]], Blocks.cocoa, l1 << 2 | i3);
                } 
              } 
            }  
        } 
        return true;
      } 
      return false;
    } 
    return false;
  }
  
  private void growVines(World p_76529_1_, int p_76529_2_, int p_76529_3_, int p_76529_4_, int p_76529_5_) {
    setBlockAndNotifyAdequately(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, Blocks.vine, p_76529_5_);
    int i1 = 4;
    while (true) {
      p_76529_3_--;
      if (!p_76529_1_.getBlock(p_76529_2_, p_76529_3_, p_76529_4_).isAir((IBlockAccess)p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_) || i1 <= 0)
        return; 
      setBlockAndNotifyAdequately(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, Blocks.vine, p_76529_5_);
      i1--;
    } 
  }
}
