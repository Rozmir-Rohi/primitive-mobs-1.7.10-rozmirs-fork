package net.daveyx0.primitivemobs.common.handlers;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.common.packets.PrimitiveColorPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class PrimitiveMobsKeyHandler {
  public static final int NO_CAMO = 0;
  
  private static final String[] desc = new String[] { "key.primitivemobs.desc" };
  
  private static final int[] keyValues = new int[] { 38 };
  
  private final KeyBinding[] keys = new KeyBinding[desc.length];
  
  public PrimitiveMobsKeyHandler() {
    for (int i = 0; i < desc.length; i++) {
      this.keys[i] = new KeyBinding(desc[i], keyValues[i], "key.primitivemobs.category");
      ClientRegistry.registerKeyBinding(this.keys[i]);
      (Minecraft.getMinecraft()).gameSettings.loadOptions();
    } 
  }
  
  @SubscribeEvent
  public void onKeyInput(InputEvent.KeyInputEvent event) {
    if (this.keys[0].isPressed()) {
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveColorPacket("05"));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveColorPacket("15"));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveColorPacket("25"));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveColorPacket("35"));
    } 
  }
}
