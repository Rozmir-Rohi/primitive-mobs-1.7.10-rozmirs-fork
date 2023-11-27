package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntityDMinion;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDMinion extends RenderLiving {
  public RenderDMinion(ModelBase par1ModelBase, float par3) {
    super(par1ModelBase, par3);
  }
  
  public void renderLivingDMinion(EntityDMinion par1EntityDMinion, double par2, double par4, double par6, float par8, float par9) {
    doRender((EntityLiving)par1EntityDMinion, par2, par4, par6, par8, par9);
  }
  
  public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
    renderLivingDMinion((EntityDMinion)par1EntityLiving, par2, par4, par6, par8, par9);
  }
  
  protected void preRenderCallback(EntityLivingBase entityliving, float f) {
    EntityDMinion entityminion = (EntityDMinion)entityliving;
    if (entityminion.getFlames() == 1) {
      GL11.glColor4f(0.9490196F, 0.75686276F, 0.76862746F, 0.5F);
    } else if (entityminion.getFlames() == 2) {
      GL11.glColor4f(0.63529414F, 0.6745098F, 0.84705883F, 0.5F);
    } else if (entityminion.getFlames() == 3) {
      GL11.glColor4f(0.60784316F, 0.76862746F, 0.67058825F, 0.5F);
    } else if (entityminion.getFlames() == 4) {
      GL11.glColor4f(1.0F, 1.0F, 0.67058825F, 0.5F);
    } 
  }
  
  private static final ResourceLocation texture = new ResourceLocation("textures/entity/skeleton/skeleton.png");
  
  private static final ResourceLocation textureWither = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    EntityDMinion entityminion = (EntityDMinion)p_110775_1_;
    if (entityminion.getFlames() == 6)
      return textureWither; 
    return texture;
  }
}
