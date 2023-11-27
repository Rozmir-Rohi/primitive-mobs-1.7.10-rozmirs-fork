package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderRocketCreeper extends RenderCreeper {
  private static final ResourceLocation texture = new ResourceLocation("primitivemobs:textures/entity/rocketcreeper/rocketcreeper.png");
  
  private static final String __OBFID = "CL_00000985";
  
  protected void preRenderCallback(EntityRocketCreeper p_77041_1_, float p_77041_2_) {
    float f1 = p_77041_1_.getCreeperFlashIntensity(p_77041_2_);
    float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;
    if (f1 < 0.0F)
      f1 = 0.0F; 
    if (f1 > 1.0F)
      f1 = 1.0F; 
    f1 *= f1;
    f1 *= f1;
    float f3 = (1.0F + f1 * 0.4F) * f2;
    float f4 = (1.0F + f1 * 0.1F) / f2;
    GL11.glScalef(f3, f4, f3);
  }
  
  protected int getColorMultiplier(EntityRocketCreeper p_77030_1_, float p_77030_2_, float p_77030_3_) {
    float f2 = p_77030_1_.getCreeperFlashIntensity(p_77030_3_);
    if ((int)(f2 * 10.0F) % 2 == 0)
      return 0; 
    int i = (int)(f2 * 0.2F * 255.0F);
    if (i < 0)
      i = 0; 
    if (i > 255)
      i = 255; 
    short short1 = 255;
    short short2 = 255;
    short short3 = 255;
    return i << 24 | short1 << 16 | short2 << 8 | short3;
  }
  
  protected int inheritRenderPass(EntityRocketCreeper p_77035_1_, int p_77035_2_, float p_77035_3_) {
    return -1;
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) {
    isInAir((EntityRocketCreeper)p_77041_1_);
    preRenderCallback((EntityRocketCreeper)p_77041_1_, p_77041_2_);
  }
  
  protected int func_77030_a(EntityLivingBase p_77030_1_, float p_77030_2_, float p_77030_3_) {
    return getColorMultiplier((EntityRocketCreeper)p_77030_1_, p_77030_2_, p_77030_3_);
  }
  
  protected int func_77035_b(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_) {
    return inheritRenderPass((EntityRocketCreeper)p_77035_1_, p_77035_2_, p_77035_3_);
  }
  
  protected void isInAir(EntityRocketCreeper kodasblueBlueCreeper) {
    if (!kodasblueBlueCreeper.onGround)
      if (kodasblueBlueCreeper.motionY > 0.1D && kodasblueBlueCreeper.motionY < 0.5D) {
        GL11.glRotatef(25.0F, -1.0F, 0.0F, 0.0F);
      } else {
        GL11.glRotatef((float)(kodasblueBlueCreeper.motionY * 175.0D), -1.0F, 0.0F, 0.0F);
      }  
  }
  
  protected ResourceLocation getResource(EntityRocketCreeper par1Entity) {
    return texture;
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return getResource((EntityRocketCreeper)p_110775_1_);
  }
}