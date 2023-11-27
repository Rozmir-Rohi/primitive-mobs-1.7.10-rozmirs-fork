package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBabySpider extends RenderLiving {
  private static final ResourceLocation spiderEyesTextures = new ResourceLocation("textures/entity/spider_eyes.png");
  
  private static final ResourceLocation spiderTextures = new ResourceLocation("primitivemobs:textures/entity/spiderfamily/babyspider.png");
  
  private static final ResourceLocation friendlySpiderEyesTextures = new ResourceLocation("primitivemobs:textures/entity/spiderfamily/spider_eyes.png");
  
  private static final ResourceLocation largeSpiderTextures = new ResourceLocation("primitivemobs:textures/entity/spiderfamily/spider.png");
  
  private static final String __OBFID = "CL_00001027";
  
  public RenderBabySpider() {
    super((ModelBase)new ModelSpider(), 0.3F);
    setRenderPassModel((ModelBase)new ModelSpider());
  }
  
  protected float getDeathMaxRotation(EntityBabySpider p_77037_1_) {
    return 180.0F;
  }
  
  protected int shouldRenderPass(EntityBabySpider p_77032_1_, int p_77032_2_, float p_77032_3_) {
    if (p_77032_2_ != 0)
      return -1; 
    if (p_77032_1_.getAge() == 5) {
      bindTexture(friendlySpiderEyesTextures);
      GL11.glEnable(3042);
      GL11.glDisable(3008);
      GL11.glBlendFunc(1, 1);
      if (p_77032_1_.isInvisible()) {
        GL11.glDepthMask(false);
      } else {
        GL11.glDepthMask(true);
      } 
      char c = '\uf0f0';
      int i = c % 65536;
      int m = c / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, m / 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      return 1;
    } 
    bindTexture(spiderEyesTextures);
    GL11.glEnable(3042);
    GL11.glDisable(3008);
    GL11.glBlendFunc(1, 1);
    if (p_77032_1_.isInvisible()) {
      GL11.glDepthMask(false);
    } else {
      GL11.glDepthMask(true);
    } 
    char c0 = '\uf0f0';
    int j = c0 % 65536;
    int k = c0 / 65536;
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    return 1;
  }
  
  protected void scaleSpider(EntityBabySpider par1EntityBabySpider, float par2) {
    if (par1EntityBabySpider.getAge() <= 5) {
      float d = par1EntityBabySpider.getAge() / 10.0F;
      GL11.glScalef(0.5F + d, 0.5F + d, 0.5F + d);
    } else {
      GL11.glScalef(1.0F, 1.0F, 1.0F);
    } 
  }
  
  protected ResourceLocation changeTextureByAge(EntityBabySpider par1EntityBabySpider) {
    if (par1EntityBabySpider.getAge() < 5)
      return spiderTextures; 
    return largeSpiderTextures;
  }
  
  protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
    scaleSpider((EntityBabySpider)par1EntityLiving, par2);
  }
  
  protected float getDeathMaxRotation(EntityLivingBase p_77037_1_) {
    return getDeathMaxRotation((EntityBabySpider)p_77037_1_);
  }
  
  protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
    return shouldRenderPass((EntityBabySpider)p_77032_1_, p_77032_2_, p_77032_3_);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return changeTextureByAge((EntityBabySpider)p_110775_1_);
  }
}
