package net.daveyx0.primitivemobs.item;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSpiderEggShell extends Item {
  public ItemSpiderEggShell() {
    this.maxStackSize = 1;
    setMaxDamage(64);
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
    setUnlocalizedName("SpiderEggShell");
  }
  
  public void registerIcons(IIconRegister iconRegister) {
    this.itemIcon = iconRegister.registerIcon("primitivemobs:spidereggshell");
  }
  
  public String getUnlocalizedName(ItemStack par1ItemStack) {
    String localName = getUnlocalizedName().replace("item.", "");
    return "item.primitivemobs." + localName;
  }
}
