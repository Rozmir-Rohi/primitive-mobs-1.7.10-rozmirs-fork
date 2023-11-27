package net.daveyx0.primitivemobs.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.daveyx0.primitivemobs.world.gen.features.trees.WorldGenPrimitiveCinderTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BlockPrimitiveSapling extends BlockSapling {
  public static final String[] saplings = new String[] { "cinder", "grovesprite" };
  
  private static IIcon[] textures = new IIcon[saplings.length];
  
  public BlockPrimitiveSapling() {
    setBlockName("primitivemobs.Sapling");
    float f = 0.4F;
    setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
    setStepSound(soundTypeGrass);
  }
  
  public void updateTick(World world, int posX, int posY, int posZ, Random random_) {
    if (!world.isRemote) {
      super.updateTick(world, posX, posY, posZ, random_);
      if (world.getBlockLightValue(posX, posY + 1, posZ) >= 9 && random_.nextInt(7) == 0)
        func_149879_c(world, posX, posY, posZ, random_); 
    } 
  }
  
  public void func_149879_c(World world, int posX, int posY, int posZ, Random random) {
    int l = world.getBlockMetadata(posX, posY, posZ);
    if ((l & 0x8) == 0) {
      world.setBlockMetadataWithNotify(posX, posY, posZ, l | 0x8, 4);
    } else {
      func_149878_d(world, posX, posY, posZ, random);
    } 
  }
  
  public void func_149878_d(World world, int posX, int posY, int posZ, Random random) {
    if (!TerrainGen.saplingGrowTree(world, random, posX, posY, posZ))
      return; 
    int l = world.getBlockMetadata(posX, posY, posZ) & 0x7;
    Object object = (random.nextInt(10) == 0) ? new WorldGenBigTree(true) : new WorldGenTrees(true);
    int i1 = 0;
    int j1 = 0;
    boolean flag = false;
    switch (l) {
      case 0:
        object = new WorldGenPrimitiveCinderTree();
        break;
      case 1:
        onPlantGrow(world, posX, posY - 1, posZ, posX, posY, posZ);
        if (!world.isRemote) {
          EntityGroveSprite entity = new EntityGroveSprite(world);
          entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
          world.spawnEntityInWorld((Entity)entity);
        } 
        world.setBlock(posX, posY, posZ, Blocks.air, 0, 4);
        object = null;
        break;
    } 
    Block block = Blocks.air;
    if (flag) {
      world.setBlock(posX + i1, posY, posZ + j1, block, 0, 4);
      world.setBlock(posX + i1 + 1, posY, posZ + j1, block, 0, 4);
      world.setBlock(posX + i1, posY, posZ + j1 + 1, block, 0, 4);
      world.setBlock(posX + i1 + 1, posY, posZ + j1 + 1, block, 0, 4);
    } else {
      world.setBlock(posX, posY, posZ, block, 0, 4);
    } 
    if (object != null)
      if (!((WorldGenerator)object).generate(world, random, posX + i1, posY, posZ + j1))
        if (flag) {
          world.setBlock(posX + i1, posY, posZ + j1, (Block)this, l, 4);
          world.setBlock(posX + i1 + 1, posY, posZ + j1, (Block)this, l, 4);
          world.setBlock(posX + i1, posY, posZ + j1 + 1, (Block)this, l, 4);
          world.setBlock(posX + i1 + 1, posY, posZ + j1 + 1, (Block)this, l, 4);
        } else {
          world.setBlock(posX, posY, posZ, (Block)this, l, 4);
        }   
  }
  
  public boolean func_149880_a(World p_149880_1_, int p_149880_2_, int p_149880_3_, int p_149880_4_, int p_149880_5_) {
    return (p_149880_1_.getBlock(p_149880_2_, p_149880_3_, p_149880_4_) == this && (p_149880_1_.getBlockMetadata(p_149880_2_, p_149880_3_, p_149880_4_) & 0x7) == p_149880_5_);
  }
  
  public int damageDropped(int p_149692_1_) {
    return MathHelper.clamp_int(p_149692_1_ & 0x7, 0, 5);
  }
  
  @SideOnly(Side.CLIENT)
  public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
      for(int x = 0; x < saplings.length; ++x) {
         list.add(new ItemStack(this, 1, x));
      }
   }
  
  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister registry) {
    for (int i = 0; i < saplings.length; i++)
      textures[i] = registry.registerIcon("primitivemobs:sapling_" + saplings[i]); 
  }
  
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
    p_149691_2_ &= 0x7;
    return textures[MathHelper.clamp_int(p_149691_2_, 0, 5)];
  }
  
  public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_) {
    return true;
  }
  
  public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_) {
    return (p_149852_1_.rand.nextFloat() < 0.45D);
  }
  
  public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_) {
    func_149879_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
  }
}
