package net.daveyx0.primitivemobs.item.itemblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockSapling extends ItemBlock {
  public static final String[] names = new String[] { "cinder", "grovesprite" };
  
  public static final String[] namesLang = new String[] { "Cinder", "GroveSprite" };
  
  public ItemBlockSapling(Block block) {
    super(block);
    setHasSubtypes(true);
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
  }
  
  public String getUnlocalizedName(ItemStack par1ItemStack) {
    int i = par1ItemStack.getItemDamage();
    if (i < 0 || i >= namesLang.length)
      i = 0; 
    return getUnlocalizedName() + "." + namesLang[i];
  }
  
  public int func_77647_b(int meta) {
    return meta & 0xF;
  }
  
  @SideOnly(Side.CLIENT)
  public IIcon func_77617_a(int p_77617_1_) {
    return this.field_150939_a.getIcon(1, p_77617_1_);
  }
}
