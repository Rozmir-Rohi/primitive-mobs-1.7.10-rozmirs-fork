package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntityDMimic;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDMimic extends RenderLiving {
  private static final ResourceLocation mouthTextures = new ResourceLocation("primitivemobs:textures/entity/mimic/chest.png");
  
  private static final ResourceLocation chestTextures = new ResourceLocation("textures/entity/chest/normal.png");
  
  private static final ResourceLocation enderChestTextures = new ResourceLocation("textures/entity/chest/ender.png");
  
  public RenderDMimic(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
    super(par1ModelBase, par3);
    setRenderPassModel(par2ModelBase);
  }
  
  protected int shouldRenderPass(EntityDMimic p_77032_1_, int p_77032_2_, float p_77032_3_) {
    if (p_77032_1_.getMimic() == 1 || p_77032_1_.getMimic() == 7) {
      bindTexture(mouthTextures);
      return 1;
    } 
    return 0;
  }
  
  protected void isInAir(EntityDMimic entitymimic) {
    if ((!entitymimic.onGround && entitymimic.getMimic() == 1) || (!entitymimic.onGround && entitymimic.getMimic() == 7))
      if (entitymimic.motionY > 0.1D && entitymimic.motionY < 0.5D) {
        GL11.glRotatef(25.0F, -1.0F, 0.0F, 0.0F);
      } else {
        GL11.glRotatef((float)(entitymimic.motionY * 70.0D), -1.0F, 0.0F, 0.0F);
      }  
  }
  
  protected float handleRotationFloat(EntityLivingBase entityliving, float f) {
    EntityDMimic entityDMimic = (EntityDMimic)entityliving;
    float f1 = entityDMimic.nomminge + (entityDMimic.nommingb - entityDMimic.nomminge) * f;
    float f2 = entityDMimic.nommingd + (entityDMimic.nommingc - entityDMimic.nommingd) * f;
    return (MathHelper.sin(f1) + 0.2F) * f2 + entityDMimic.rotation;
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) {
    EntityDMimic mimic = (EntityDMimic)p_77041_1_;
    isInAir(mimic);
    if (mimic.ridingEntity != null) {
      GL11.glScalef(0.8F, 0.8F, 0.8F);
      GL11.glTranslatef(0.0F, -0.2F, 0.0F);
    } 
  }
  
  protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
    return shouldRenderPass((EntityDMimic)p_77032_1_, p_77032_2_, p_77032_3_);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    if (((EntityDMimic)p_110775_1_).getMimic() == 7)
      return enderChestTextures; 
    return chestTextures;
  }
}
