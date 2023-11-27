package net.daveyx0.primitivemobs.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentPrimitiveSticky extends Enchantment {
  public EnchantmentPrimitiveSticky(int effectID, int weight, EnumEnchantmentType type) {
    super(effectID, weight, type);
    setName("primitivemobs.stickyFeet");
  }
  
  public int getMinEnchantability(int par1) {
    return 20;
  }
  
  public int getMaxEnchantability(int par1) {
    return 50;
  }
  
  public int getMaxLevel() {
    return 1;
  }
}
