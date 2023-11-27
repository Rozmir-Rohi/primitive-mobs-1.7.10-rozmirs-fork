package net.daveyx0.primitivemobs.core;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationGeneral;
import net.daveyx0.primitivemobs.enchantment.EnchantmentPrimitiveSticky;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class PrimitiveMobsEnchantments {
  public static Enchantment stickyFeet;
  
  public static void init() {
    stickyFeet = (Enchantment)new EnchantmentPrimitiveSticky(PrimitiveMobsConfigurationGeneral.getStickyFeetID(), 2, EnumEnchantmentType.armor_feet);
  }
}
