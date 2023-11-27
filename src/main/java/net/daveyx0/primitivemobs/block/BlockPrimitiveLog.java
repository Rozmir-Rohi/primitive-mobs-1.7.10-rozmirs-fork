package net.daveyx0.primitivemobs.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPrimitiveLog extends BlockLog {
  public static final String[] names = new String[] { "cinder" };
  
  public static final String[] namesLang = new String[] { "Cinder" };
  
  public BlockPrimitiveLog() {
    setBlockName("primitivemobs.Log");
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
  }
  
  @SideOnly(Side.CLIENT)
  public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
      for(int x = 0; x < names.length; ++x) {
         list.add(new ItemStack(this, 1, x));
      }
  }
  
  public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
    if (world.getBlock(x, y, z) instanceof BlockPrimitiveLog && world.getBlockMetadata(x, y, z) == 0)
      return 0; 
    return Blocks.fire.getFlammability((Block)this);
  }
  
  public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
    if (world.getBlock(x, y, z) instanceof BlockPrimitiveLog && world.getBlockMetadata(x, y, z) == 0)
      return false; 
    return (getFlammability(world, x, y, z, face) > 0);
  }
  
  public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
    if (world.getBlock(x, y, z) instanceof BlockPrimitiveLog && world.getBlockMetadata(x, y, z) == 0)
      return 0; 
    return Blocks.fire.getEncouragement((Block)this);
  }
  
  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister registry) {
    this.field_150167_a = new net.minecraft.util.IIcon[names.length];
    this.field_150166_b = new net.minecraft.util.IIcon[names.length];
    for (int i = 0; i < this.field_150167_a.length; i++) {
      this.field_150167_a[i] = registry.registerIcon("primitivemobs:log_" + names[i] + "_side");
      this.field_150166_b[i] = registry.registerIcon("primitivemobs:log_" + names[i] + "_top");
    } 
  }
}
