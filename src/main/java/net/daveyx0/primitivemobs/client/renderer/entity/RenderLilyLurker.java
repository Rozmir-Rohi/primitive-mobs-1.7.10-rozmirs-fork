package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.entity.monster.EntityLilyLurker;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLilyLurker extends RenderLiving {
  ModelBase lurker;
  
  private static final ResourceLocation texture = new ResourceLocation("primitivemobs:textures/entity/lilylurker/lilylurker.png");
  
  public RenderLilyLurker(ModelBase par1ModelBase, float par2) {
    super(par1ModelBase, par2);
    this.lurker = par1ModelBase;
    setRenderPassModel(this.lurker);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity) {
    return texture;
  }
  
  protected void scaleLilyLurker(EntityLilyLurker par1EntityLilyLurker, float par2) {
    if (!par1EntityLilyLurker.getIsAggro()) {
      GL11.glTranslatef(0.0F, -0.24F, 0.0F);
    } else {
      GL11.glTranslatef(0.0F, 0.0F, 0.0F);
    } 
  }
  
  protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
    scaleLilyLurker((EntityLilyLurker)par1EntityLiving, par2);
  }
}
