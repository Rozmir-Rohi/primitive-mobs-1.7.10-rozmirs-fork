package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationGeneral;
import net.daveyx0.primitivemobs.entity.monster.EntityLily;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLily extends RenderLiving {
  ModelBase lurker;
  
  private static final ResourceLocation texture = new ResourceLocation("textures/blocks/waterlily.png");
  
  private static final ResourceLocation lilyTexture = new ResourceLocation("primitivemobs:textures/entity/lilylurker/waterlily.png");
  
  public boolean onSpawn;
  
  public RenderLily(ModelBase par1ModelBase, float par2) {
    super(par1ModelBase, par2);
    this.lurker = par1ModelBase;
    setRenderPassModel(this.lurker);
    this.onSpawn = false;
  }
  
  protected ResourceLocation getEntityTexture(Entity entity) {
    if (PrimitiveMobsConfigurationGeneral.getWaterLilyAdaptive())
      return texture; 
    return lilyTexture;
  }
  
  protected void scaleLily(EntityLily par1EntityLily, float par2) {
    int Blue = 48;
    int Green = 128;
    int Red = 32;
    GL11.glColor4f(Red / 255.0F, Green / 255.0F, Blue / 255.0F, 1.0F);
    if (PrimitiveMobsConfigurationGeneral.getWaterLilyAdaptive()) {
      GL11.glScalef(0.25F, 0.25F, 0.25F);
      GL11.glTranslatef(0.1F, 0.0F, 0.0F);
    } 
    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
  }
  
  protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
    scaleLily((EntityLily)par1EntityLiving, par2);
  }
}
