package net.daveyx0.primitivemobs.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.core.PrimitiveMobsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPrimitiveLeaves extends BlockLeaves {
  public static IIcon[][] textures;
  
  public static final String[] leaves = new String[] { "cinder" };
  
  public BlockPrimitiveLeaves() {
    setBlockName("primitivemobs.Leaf");
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
  }
  
  public IIcon getIcon(int side, int meta) {
    int type = getTypeFromMeta(meta);
    return textures[isOpaqueCube() ? 1 : 0][(type < leaves.length) ? type : 0];
  }
  
  @SideOnly(Side.CLIENT)
  public int getBlockColor() {
    return 16777215;
  }
  
  @SideOnly(Side.CLIENT)
  public int getRenderColor(int p_149741_1_) {
    return 16777215;
  }
  
  @SideOnly(Side.CLIENT)
  public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
    return 16777215;
  }
  
  public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
    return 60;
  }
  
  public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
    return true;
  }
  
  public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
    return 30;
  }
  
  public String[] func_150125_e() {
    return leaves;
  }
  
  private static int getTypeFromMeta(int meta) {
    meta &= 0x3;
    if (meta < 0 || meta >= leaves.length)
      meta = 0; 
    return meta;
  }
  
  public boolean renderAsNormalBlock() {
    return false;
  }
  
  public boolean shouldSideBeRendered(IBlockAccess blockacces, int x, int y, int z, int side) {
    return true;
  }
  
  public boolean isOpaqueCube() {
    return Blocks.leaves.isOpaqueCube();
  }
  
  @SideOnly(Side.CLIENT)
  public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
     for(int x = 0; x < leaves.length; ++x) {
        list.add(new ItemStack(this, 1, x));
     }  
  }
  
  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister registry) {
    textures = new IIcon[2][leaves.length];
    for (int i = 0; i < leaves.length; i++) {
      textures[0][i] = registry.registerIcon("primitivemobs:leaves_" + leaves[i] + "_fancy");
      textures[1][i] = registry.registerIcon("primitivemobs:leaves_" + leaves[i] + "_fast");
    } 
  }
  
  public int damageDropped(int i) {
    return super.damageDropped(i);
  }
  
  public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
    return Item.getItemFromBlock(PrimitiveMobsBlocks.blockSapling);
  }
  
  public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
    return p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
  }
  
  protected void func_150124_c(World world, int x, int y, int z, int meta, int chance) {
    if (world.getBlock(x, y, z) instanceof BlockPrimitiveLeaves && meta == 0 && world.rand.nextInt(2) == 0) {
      dropBlockAsItem(world, x, y, z, new ItemStack(Items.flint, 1, 0));
    } else if ((meta & 0x3) == 1 && world.rand.nextInt(chance) == 0) {
      dropBlockAsItem(world, x, y, z, new ItemStack(Items.apple, 1, 0));
    } 
  }
}
