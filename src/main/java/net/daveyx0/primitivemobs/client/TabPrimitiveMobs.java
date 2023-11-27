package net.daveyx0.primitivemobs.client;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabPrimitiveMobs extends CreativeTabs {
  public TabPrimitiveMobs(int position, String tabID) {
    super(position, tabID);
  }
  
  public String getTranslatedTabLabel() {
    return "PrimitiveMobs";
  }
  
  public Item getTabIconItem() {
    return (new ItemStack(PrimitiveMobsItems.camouflageDye, 1, 1)).getItem();
  }
}
