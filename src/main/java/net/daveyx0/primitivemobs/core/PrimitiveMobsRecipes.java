package net.daveyx0.primitivemobs.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PrimitiveMobsRecipes {
  public static void init() {
    GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveMobsItems.camouflageHelmet, 1), new Object[] { new ItemStack((Item)Items.leather_helmet), new ItemStack(PrimitiveMobsItems.camouflageDye, 1) });
    GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveMobsItems.camouflageChest, 1), new Object[] { new ItemStack((Item)Items.leather_chestplate), new ItemStack(PrimitiveMobsItems.camouflageDye, 1) });
    GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveMobsItems.camouflageLegs, 1), new Object[] { new ItemStack((Item)Items.leather_leggings), new ItemStack(PrimitiveMobsItems.camouflageDye, 1) });
    GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveMobsItems.camouflageBoots, 1), new Object[] { new ItemStack((Item)Items.leather_boots), new ItemStack(PrimitiveMobsItems.camouflageDye, 1) });
    GameRegistry.addShapelessRecipe(new ItemStack(Items.magma_cream, 1), new Object[] { Items.blaze_powder, PrimitiveMobsItems.pinkSlimeBall });
    GameRegistry.addRecipe(new ItemStack((Block)Blocks.sticky_piston, 1), new Object[] { "X", "C", Character.valueOf('X'), PrimitiveMobsItems.pinkSlimeBall, Character.valueOf('C'), new ItemStack((Block)Blocks.piston, 1) });
    GameRegistry.addRecipe(new ItemStack(Items.lead, 1), new Object[] { "XX#", "XC#", "##X", Character.valueOf('X'), Items.string, Character.valueOf('C'), PrimitiveMobsItems.pinkSlimeBall });
  }
}
