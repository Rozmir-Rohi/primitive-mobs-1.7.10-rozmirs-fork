package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntityBrainSlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBrainSlime extends RenderLiving {
  private ModelBase scaleAmount;
  
  private static final String __OBFID = "CL_00001024";
  
  private ModelBase mainModel;
  
  public RenderBrainSlime(ModelBase p_i1267_1_, ModelBase p_i1267_2_, float p_i1267_3_) {
    super(p_i1267_1_, p_i1267_3_);
    this.scaleAmount = p_i1267_2_;
  }
  
  protected int shouldRenderPass(EntityBrainSlime p_77032_1_, int p_77032_2_, float p_77032_3_) {
    if (p_77032_1_.isInvisible())
      return 0; 
    if (p_77032_2_ == 0) {
      setRenderPassModel(this.scaleAmount);
      GL11.glEnable(2977);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      return 1;
    } 
    if (p_77032_2_ == 1) {
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    } 
    return -1;
  }
  
  protected void scaleBrainSlime(EntityBrainSlime entityBrainSlime, float f) {
    float f3 = entityBrainSlime.getSlimeSize();
    float f1 = (entityBrainSlime.prevSquishFactor + (entityBrainSlime.squishFactor - entityBrainSlime.prevSquishFactor) * f) / (f3 * 0.5F + 1.0F);
    float f2 = 1.0F / (f1 + 1.0F);
    float f4 = entityBrainSlime.suckinge + (entityBrainSlime.suckingb - entityBrainSlime.suckinge) * f;
    float f5 = entityBrainSlime.suckingd + (entityBrainSlime.suckingc - entityBrainSlime.suckingd) * f;
    float f6 = (MathHelper.sin(f4) + 0.5F) * f5 * 1.5F;
    GL11.glScalef(f2 * f3, 1.0F / f2 * f3 + f6, f2 * f3);
  }
  
  protected void preRenderCallback(EntityLivingBase entityliving, float f) {
    scaleBrainSlime((EntityBrainSlime)entityliving, f);
  }
  
  protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f) {
    return shouldRenderPass((EntityBrainSlime)entityliving, i, f);
  }
  
  private static final ResourceLocation texture = new ResourceLocation("primitivemobs:textures/entity/brainslime/brainslime.png");
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return texture;
  }
}
