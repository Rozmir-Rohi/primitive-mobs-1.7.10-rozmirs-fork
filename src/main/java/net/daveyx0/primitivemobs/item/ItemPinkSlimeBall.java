package net.daveyx0.primitivemobs.item;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPinkSlimeBall extends Item {
  public ItemPinkSlimeBall() {
    this.maxStackSize = 64;
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
    setUnlocalizedName("PinkSlimeBall");
  }
  
  public void registerIcons(IIconRegister iconRegister) {
    this.itemIcon = iconRegister.registerIcon("primitivemobs:pinkslimeball");
  }
  
  public String getUnlocalizedName(ItemStack par1ItemStack) {
    String localName = getUnlocalizedName().replace("item.", "");
    return "item.primitivemobs." + localName;
  }
}
