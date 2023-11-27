package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.entity.passive.EntitySheepmanSmith;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSheepmanSmith extends RenderLiving {
  ModelBase model;
  
  private static final ResourceLocation texture = new ResourceLocation("primitivemobs:textures/entity/villager/sheepmansmith.png");
  
  public RenderSheepmanSmith(ModelBase par1ModelBase, float par2) {
    super(par1ModelBase, par2);
    this.model = par1ModelBase;
    setRenderPassModel(this.model);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity) {
    return texture;
  }
  
  protected int shouldSheepmanSmithRenderPass(EntitySheepmanSmith par1EntitySheepmanSmith, int par2, float par3) {
    return -1;
  }
  
  public void renderSheepmanSmith(EntitySheepmanSmith par1EntitySheepmanSmith, double par2, double par4, double par6, float par8, float par9) {
    doRender((EntityLiving)par1EntitySheepmanSmith, par2, par4, par6, par8, par9);
  }
  
  protected void preRenderSheepmanSmith(EntitySheepmanSmith par1EntitySheepmanSmith, float par2) {
    float var3 = 0.9375F;
    if (par1EntitySheepmanSmith.getGrowingAge() < 0) {
      var3 = (float)(var3 * 0.5D);
      this.shadowSize = 0.25F;
    } else {
      this.shadowSize = 0.5F;
    } 
    GL11.glScalef(var3, var3, var3);
  }
  
  protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {
    preRenderSheepmanSmith((EntitySheepmanSmith)par1EntityLiving, par2);
  }
  
  protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3) {
    return shouldSheepmanSmithRenderPass((EntitySheepmanSmith)par1EntityLiving, par2, par3);
  }
  
  protected void renderEquippedItems(EntityLivingBase par1EntityLiving, float par2) {
    super.renderEquippedItems(par1EntityLiving, par2);
  }
  
  public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
    renderSheepmanSmith((EntitySheepmanSmith)par1EntityLiving, par2, par4, par6, par8, par9);
  }
  
  public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
    renderSheepmanSmith((EntitySheepmanSmith)par1Entity, par2, par4, par6, par8, par9);
  }
  
  protected void passSheepmanSmithSpecialRender(EntitySheepmanSmith par1EntitySheepmanSmith, double par2, double par4, double par6) {}
  
  protected void passSpecialRender(EntityLivingBase par1EntityLiving, double par2, double par4, double par6) {
    EntityLiving thisEntity = (EntityLiving)par1EntityLiving;
    if (thisEntity.getCustomNameTag().equals("Sheepman Smith")) {
      passSheepmanSmithSpecialRender((EntitySheepmanSmith)par1EntityLiving, par2, par4, par6);
    } else {
      super.passSpecialRender(par1EntityLiving, par2, par4, par6);
    } 
  }
}
