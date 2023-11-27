package net.daveyx0.primitivemobs.common.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PrimitiveJumpPacket implements IMessage {
  private String text;
  
  public PrimitiveJumpPacket() {}
  
  public PrimitiveJumpPacket(String text) {
    this.text = text;
  }
  
  public void fromBytes(ByteBuf buf) {
    this.text = ByteBufUtils.readUTF8String(buf);
  }
  
  public void toBytes(ByteBuf buf) {
    ByteBufUtils.writeUTF8String(buf, this.text);
  }
  
  public static class Handler implements IMessageHandler<PrimitiveJumpPacket, IMessage> {
    public IMessage onMessage(PrimitiveJumpPacket message, MessageContext ctx) {
      EntityPlayerMP entityPlayerMP = (ctx.getServerHandler()).playerEntity;
      if (((EntityPlayer)entityPlayerMP).ridingEntity != null && ((EntityPlayer)entityPlayerMP).ridingEntity instanceof EntityBabySpider)
        ((EntityBabySpider)((EntityPlayer)entityPlayerMP).ridingEntity).setIsJumping(true); 
      return null;
    }
  }
}
