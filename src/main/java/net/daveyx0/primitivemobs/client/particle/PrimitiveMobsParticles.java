package net.daveyx0.primitivemobs.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

public class PrimitiveMobsParticles {
  public static EntityFX spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
    if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
      int i = mc.gameSettings.particleSetting;
      if (i == 1 && theWorld.rand.nextInt(3) == 0)
        i = 2; 
      double d6 = mc.renderViewEntity.posX - par2;
      double d7 = mc.renderViewEntity.posY - par4;
      double d8 = mc.renderViewEntity.posZ - par6;
      EntityFX entityfx = null;
      double d9 = 16.0D;
      if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9)
        return null; 
      if (i > 1)
        return null; 
      if (par1Str.equals("bluefire"))
        entityfx = new EntityCustomFireFX(theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12, 3, 0, 0.0F, 0.0F, 255.0F); 
      if (par1Str.equals("greenfire"))
        entityfx = new EntityCustomFireFX(theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12, 3, 0, 0.0F, 255.0F, 0.0F); 
      if (par1Str.equals("yellowfire"))
        entityfx = new EntityCustomFireFX(theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12, 3, 0, 255.0F, 255.0F, 0.0F); 
      if (par1Str.equals("purplefire"))
        entityfx = new EntityCustomFireFX(theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12, 3, 0, 127.0F, 0.0F, 127.0F); 
      if (par1Str.equals("blackfire"))
        entityfx = new EntityCustomFireFX(theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12, 3, 0, 0.0F, 0.0F, 0.0F); 
      if (par1Str.equals("tar"))
        entityfx = new EntityCustomFireFX(theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12, 6, 1, 0.0F, 0.0F, 0.0F); 
      if (entityfx != null)
        mc.effectRenderer.addEffect(entityfx); 
      return entityfx;
    } 
    return null;
  }
  
  private static Minecraft mc = Minecraft.getMinecraft();
  
  private static World theWorld = (World)mc.theWorld;
  
  private static TextureManager renderEngine = mc.renderEngine;
}
