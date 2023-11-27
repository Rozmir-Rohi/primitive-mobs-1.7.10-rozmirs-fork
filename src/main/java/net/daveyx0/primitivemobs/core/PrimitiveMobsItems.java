package net.daveyx0.primitivemobs.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.item.ItemCamouflageDye;
import net.daveyx0.primitivemobs.item.ItemCustomEgg;
import net.daveyx0.primitivemobs.item.ItemPinkSlimeBall;
import net.daveyx0.primitivemobs.item.ItemPreciousPickaxe;
import net.daveyx0.primitivemobs.item.ItemSkullMinionSpawner;
import net.daveyx0.primitivemobs.item.ItemSpiderEggShell;
import net.daveyx0.primitivemobs.item.ItemTarBall;
import net.daveyx0.primitivemobs.item.ItemUsefulSpawner;
import net.minecraft.item.Item;

public class PrimitiveMobsItems {
  public static Item customEgg;
  
  public static Item spiderEggShell;
  
  public static Item camouflageDye;
  
  public static Item skullMinionOrb;
  
  public static Item camouflageHelmet;
  
  public static Item camouflageChest;
  
  public static Item camouflageLegs;
  
  public static Item camouflageBoots;
  
  public static Item preciousPickaxe;
  
  public static Item tarBall;
  
  public static Item pinkSlimeBall;
  
  public static Item usefulSpawner;
  
  public static void init() {
    initializeItems();
    registerItems();
  }
  
  private static void initializeItems() {
    customEgg = (Item)new ItemCustomEgg();
    spiderEggShell = (Item)new ItemSpiderEggShell();
    camouflageDye = (Item)new ItemCamouflageDye();
    skullMinionOrb = (Item)new ItemSkullMinionSpawner();
    camouflageHelmet = (Item)new ItemCamouflageArmor(0);
    camouflageChest = (Item)new ItemCamouflageArmor(1);
    camouflageLegs = (Item)new ItemCamouflageArmor(2);
    camouflageBoots = (Item)new ItemCamouflageArmor(3);
    preciousPickaxe = (Item)new ItemPreciousPickaxe();
    tarBall = (Item)new ItemTarBall();
    pinkSlimeBall = (Item)new ItemPinkSlimeBall();
    usefulSpawner = (Item)new ItemUsefulSpawner();
  }
  
  private static void registerItems() {
    registerItem(customEgg);
    registerItem(spiderEggShell);
    registerItem(camouflageDye);
    registerItem(skullMinionOrb);
    registerItem(camouflageHelmet);
    registerItem(camouflageChest);
    registerItem(camouflageLegs);
    registerItem(camouflageBoots);
    registerItem(preciousPickaxe);
    registerItem(tarBall);
    registerItem(pinkSlimeBall);
    registerItem(usefulSpawner);
  }
  
  public static void registerItem(Item item) {
    GameRegistry.registerItem(item, "primitivemobs_" + item.getUnlocalizedName().substring(5));
  }
}
