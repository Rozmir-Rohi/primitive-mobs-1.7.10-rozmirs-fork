package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.entity.monster.EntityHangingSpider;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderHangingSpider extends RenderLiving {
  private static final ResourceLocation spiderEyesTextures = new ResourceLocation("textures/entity/spider_eyes.png");
  
  private static final ResourceLocation spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");
  
  public RenderHangingSpider() {
    super((ModelBase)new ModelSpider(), 0.3F);
    setRenderPassModel((ModelBase)new ModelSpider());
  }
  
  protected float getDeathMaxRotation(EntityHangingSpider p_77037_1_) {
    return 180.0F;
  }
  
  protected int shouldRenderPass(EntityHangingSpider p_77032_1_, int p_77032_2_, float p_77032_3_) {
    if (p_77032_2_ != 0)
      return -1; 
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
  
  protected float getDeathMaxRotation(EntityLivingBase p_77037_1_) {
    return getDeathMaxRotation((EntityHangingSpider)p_77037_1_);
  }
  
  protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
    return setAlterations((EntityHangingSpider)p_77032_1_, p_77032_2_, p_77032_3_);
  }
  
  protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
    rotateSpider((EntityHangingSpider)par1EntityLiving, par2);
  }
  
  protected void rotateSpider(EntityHangingSpider hangingspider, float par2) {
    if (hangingspider.getIsHanging()) {
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslatef(0.0F, 0.5F, 0.0F);
    } 
  }
  
  protected int setAlterations(EntityHangingSpider hangingspider, int par2, float par3) {
    Tessellator tess = Tessellator.instance;
    GL11.glPushMatrix();
    GL11.glDisable(3553);
    GL11.glDisable(2896);
    GL11.glColor3d(1.0D, 1.0D, 1.0D);
    GL11.glLineWidth(3.0F);
    tess.startDrawing(2);
    tess.addVertex(0.0D, 1.0D, 0.0D);
    tess.addVertex(0.0D, 1.0D, hangingspider.getStringY());
    tess.draw();
    GL11.glEnable(3553);
    GL11.glEnable(2896);
    GL11.glPopMatrix();
    return 1;
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return spiderTextures;
  }
}
