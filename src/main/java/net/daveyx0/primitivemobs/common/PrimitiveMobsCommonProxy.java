package net.daveyx0.primitivemobs.common;

import cpw.mods.fml.common.network.IGuiHandler;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PrimitiveMobsCommonProxy implements IGuiHandler {
  private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();
  
  public void registerRenderInformation() {}
  
  public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
    return null;
  }
  
  public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
    return null;
  }
  
  public void registerTiles() {}
  
  public void registerBlocks() {}
  
  public void registerItems() {}
  
  public void registerRenderers() {}
  
  public static void storeEntityData(String name, NBTTagCompound compound) {
    extendedEntityData.put(name, compound);
  }
  
  public static NBTTagCompound getEntityData(String name) {
    return extendedEntityData.remove(name);
  }
}
