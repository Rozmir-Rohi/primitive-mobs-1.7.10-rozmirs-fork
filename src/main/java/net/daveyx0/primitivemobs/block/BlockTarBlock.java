package net.daveyx0.primitivemobs.block;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockTarBlock extends Block {
  public BlockTarBlock() {
    super(Material.sand);
    setBlockName("primitivemobs.Tar");
    setBlockTextureName("primitivemobs:tar");
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
    setHardness(0.5F);
    setStepSound(soundTypeSand);
  }
  
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
    float f = 0.125F;
    return AxisAlignedBB.getBoundingBox(p_149668_2_, p_149668_3_, p_149668_4_, p_149668_2_, p_149668_3_, p_149668_4_);
  }
  
  public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
    p_149670_5_.motionX *= 0.1D;
    p_149670_5_.motionZ *= 0.1D;
    p_149670_5_.motionY *= 0.1D;
  }
}
