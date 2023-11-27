package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderMotherSpider extends RenderLiving {
  private static final ResourceLocation spiderEyesTextures = new ResourceLocation("textures/entity/spider_eyes.png");
  
  private static final ResourceLocation spiderTextures = new ResourceLocation("primitivemobs:textures/entity/spiderfamily/motherspider.png");
  
  private static final String __OBFID = "CL_00001027";
  
  public RenderMotherSpider() {
    super((ModelBase)new ModelSpider(), 1.0F);
    setRenderPassModel((ModelBase)new ModelSpider());
  }
  
  protected float getDeathMaxRotation(EntityMotherSpider p_77037_1_) {
    return 180.0F;
  }
  
  protected int shouldRenderPass(EntityMotherSpider p_77032_1_, int p_77032_2_, float p_77032_3_) {
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
    return getDeathMaxRotation((EntityMotherSpider)p_77037_1_);
  }
  
  protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
    return shouldRenderPass((EntityMotherSpider)p_77032_1_, p_77032_2_, p_77032_3_);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return spiderTextures;
  }
}
