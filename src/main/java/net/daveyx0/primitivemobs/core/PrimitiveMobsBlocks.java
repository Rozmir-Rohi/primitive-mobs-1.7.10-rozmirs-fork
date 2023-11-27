package net.daveyx0.primitivemobs.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.daveyx0.primitivemobs.block.BlockPrimitiveLeaves;
import net.daveyx0.primitivemobs.block.BlockPrimitiveLog;
import net.daveyx0.primitivemobs.block.BlockPrimitiveSapling;
import net.daveyx0.primitivemobs.block.BlockTarBlock;
import net.daveyx0.primitivemobs.item.itemblock.ItemBlockLeaves;
import net.daveyx0.primitivemobs.item.itemblock.ItemBlockLog;
import net.daveyx0.primitivemobs.item.itemblock.ItemBlockSapling;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class PrimitiveMobsBlocks {
  public static Block tarBlock;
  
  public static Block blockLog;
  
  public static Block blockLeaf;
  
  public static Block blockSapling;
  
  public static void init() {
    initializeBlocks();
    registerBlocks();
  }
  
  private static void initializeBlocks() {
    tarBlock = (Block)new BlockTarBlock();
    blockLog = (Block)new BlockPrimitiveLog();
    blockLeaf = (Block)new BlockPrimitiveLeaves();
    blockSapling = (Block)new BlockPrimitiveSapling();
  }
  
  private static void registerBlocks() {
    registerBlock(blockLog, (Class)ItemBlockLog.class);
    registerBlock(blockLeaf, (Class)ItemBlockLeaves.class);
    registerBlock(blockSapling, (Class)ItemBlockSapling.class);
  }
  
  public static void registerBlock(Block block) {
    GameRegistry.registerBlock(block, "primitivemobs_" + block.getUnlocalizedName().substring(5));
  }
  
  public static void registerBlock(Block block, Class<? extends ItemBlock> itemBlockClass) {
    GameRegistry.registerBlock(block, itemBlockClass, block.getUnlocalizedName().substring(5));
  }
}
