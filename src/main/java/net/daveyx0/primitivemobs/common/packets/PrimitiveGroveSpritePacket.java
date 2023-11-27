package net.daveyx0.primitivemobs.common.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.entity.Entity;

public class PrimitiveGroveSpritePacket implements IMessage {
  private String text;
  
  public PrimitiveGroveSpritePacket() {}
  
  public PrimitiveGroveSpritePacket(String text) {
    this.text = text;
  }
  
  public void fromBytes(ByteBuf buf) {
    this.text = ByteBufUtils.readUTF8String(buf);
  }
  
  public void toBytes(ByteBuf buf) {
    ByteBufUtils.writeUTF8String(buf, this.text);
  }
  
  public static class Handler implements IMessageHandler<PrimitiveGroveSpritePacket, IMessage> {
    public IMessage onMessage(PrimitiveGroveSpritePacket message, MessageContext ctx) {
      String[] inputs = message.text.split(":");
      int id = Integer.parseInt(inputs[0]);
      String color = inputs[1];
      int index = Integer.parseInt(inputs[2]);
      Entity sprite = (ctx.getServerHandler()).playerEntity.worldObj.getEntityByID(id);
      if (sprite != null && sprite instanceof EntityGroveSprite) {
        EntityGroveSprite groveSprite = (EntityGroveSprite)sprite;
        if (index == 0) {
          groveSprite.setBaseColor(color);
        } else if (index == 1) {
          groveSprite.setInnerColor(color);
        } else if (index == 2) {
          groveSprite.setLeavesColor(color);
        } else if (index == 3) {
          groveSprite.setHeldItemName(color);
        } else if (index == 4) {
          groveSprite.setLeafKind(Integer.parseInt(color));
        } 
      } 
      return null;
    }
  }
}
