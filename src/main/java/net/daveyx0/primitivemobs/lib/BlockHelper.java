package net.daveyx0.primitivemobs.lib;

import cpw.mods.fml.common.registry.GameData;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class BlockHelper {
  public static ItemStack getSaplingByLocalName(String localName) {
    Set blockSet = GameData.getBlockRegistry().getKeys();
    Object[] allBlocks = blockSet.toArray();
    for (int i = 0; i < allBlocks.length; i++) {
      if (((Block)GameData.getBlockRegistry().getObject(allBlocks[i])).getMaterial() == Material.plants) {
        Block plant = (Block)GameData.getBlockRegistry().getObject(allBlocks[i]);
        for (int j = 0; j < 16; j++) {
          ItemStack checkPlant = new ItemStack(plant, 1, j);
          if (checkPlant != null && checkPlant.getItem() instanceof net.minecraft.item.ItemBlock) {
            String localPlant = StatCollector.translateToLocal(checkPlant.getUnlocalizedName() + ".name");
            if (localPlant.equals(localName) || localPlant == localName)
              return checkPlant; 
          } 
        } 
      } 
    } 
    return null;
  }
  
  public static ItemStack getSaplingByUnlocalName(String localName) {
    Set blockSet = GameData.getBlockRegistry().getKeys();
    Object[] allBlocks = blockSet.toArray();
    for (int i = 0; i < allBlocks.length; i++) {
      if (((Block)GameData.getBlockRegistry().getObject(allBlocks[i])).getMaterial() == Material.plants) {
        Block plant = (Block)GameData.getBlockRegistry().getObject(allBlocks[i]);
        for (int j = 0; j < 16; j++) {
          ItemStack checkPlant = new ItemStack(plant, 1, j);
          if (checkPlant != null && checkPlant.getItem() instanceof net.minecraft.item.ItemBlock) {
            String localPlant = checkPlant.getUnlocalizedName();
            if (localPlant.equals(localName) || localPlant == localName)
              return checkPlant; 
          } 
        } 
      } 
    } 
    return null;
  }
}
