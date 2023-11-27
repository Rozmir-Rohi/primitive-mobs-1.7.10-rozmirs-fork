package net.daveyx0.primitivemobs.common.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class PrimitiveColorPacket implements IMessage {
  private String text;
  
  public PrimitiveColorPacket() {}
  
  public PrimitiveColorPacket(String text) {
    this.text = text;
  }
  
  public void fromBytes(ByteBuf buf) {
    this.text = ByteBufUtils.readUTF8String(buf);
  }
  
  public void toBytes(ByteBuf buf) {
    ByteBufUtils.writeUTF8String(buf, this.text);
  }
  
  public static class Handler implements IMessageHandler<PrimitiveColorPacket, IMessage> {
    public IMessage onMessage(PrimitiveColorPacket message, MessageContext ctx) {
      String string1 = message.text.substring(1);
      String string2 = message.text.replace(string1, "");
      int newColor = Integer.parseInt(string1);
      int armorType = Integer.parseInt(string2);
      ItemStack armor = (ctx.getServerHandler()).playerEntity.getCurrentArmor(armorType);
      if (armor != null && (armor.getItem() == PrimitiveMobsItems.camouflageBoots || armor.getItem() == PrimitiveMobsItems.camouflageHelmet || armor
        .getItem() == PrimitiveMobsItems.camouflageChest || armor.getItem() == PrimitiveMobsItems.camouflageLegs))
        if (newColor == 5) {
          NBTTagCompound nbttagcompound = armor.getTagCompound();
          if (nbttagcompound == null) {
            nbttagcompound = new NBTTagCompound();
            armor.setTagCompound(nbttagcompound);
          } 
          NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
          if (!nbttagcompound.hasKey("display", 10))
            nbttagcompound.setTag("display", (NBTBase)nbttagcompound1); 
          byte newByte = 0;
          if (nbttagcompound1.hasKey("camo", 1) && nbttagcompound1.getByte("camo") == 0)
            newByte = 1; 
          nbttagcompound1.setByte("camo", newByte);
        } else {
          NBTTagCompound nbttagcompound = armor.getTagCompound();
          if (nbttagcompound == null) {
            nbttagcompound = new NBTTagCompound();
            armor.setTagCompound(nbttagcompound);
          } 
          NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
          if (!nbttagcompound.hasKey("display", 10))
            nbttagcompound.setTag("display", (NBTBase)nbttagcompound1); 
          nbttagcompound1.setInteger("color", newColor);
        }  
      return null;
    }
  }
}
