package net.daveyx0.primitivemobs.item.itemblock;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemBlockLog extends ItemBlock {
  public static final String[] names = new String[] { "cinder" };
  
  public static final String[] namesLang = new String[] { "Cinder" };
  
  public ItemBlockLog(Block block) {
    super(block);
    setHasSubtypes(true);
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
  }
  
  public String getUnlocalizedName(ItemStack par1ItemStack) {
    int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
    if (i < 0 || i >= names.length)
      i = 0; 
    return getUnlocalizedName() + "." + namesLang[i];
  }
}
