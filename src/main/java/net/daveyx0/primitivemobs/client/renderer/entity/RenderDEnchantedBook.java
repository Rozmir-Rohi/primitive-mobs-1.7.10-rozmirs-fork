package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntityDEnchantedBook;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDEnchantedBook extends RenderLiving {
  public RenderDEnchantedBook(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
    super(par1ModelBase, par3);
    setRenderPassModel(par2ModelBase);
  }
  
  public void renderLivingDEnchantedBook(EntityDEnchantedBook par1EntityDEnchantedBook, double par2, double par4, double par6, float par8, float par9) {
    doRender((EntityLiving)par1EntityDEnchantedBook, par2, par4, par6, par8, par9);
  }
  
  public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
    renderLivingDEnchantedBook((EntityDEnchantedBook)par1EntityLiving, par2, par4, par6, par8, par9);
  }
  
  protected float handleRotationFloat(EntityLivingBase entityliving, float f) {
    EntityDEnchantedBook entityDEnchantedBook = (EntityDEnchantedBook)entityliving;
    float f1 = entityDEnchantedBook.floatinge + (entityDEnchantedBook.floatingb - entityDEnchantedBook.floatinge) * f;
    float f2 = entityDEnchantedBook.floatingd + (entityDEnchantedBook.floatingc - entityDEnchantedBook.floatingd) * f;
    return (MathHelper.sin(f1) + 0.2F) * f2;
  }
  
  protected void preRenderCallback(EntityLivingBase entityliving, float f) {
    EntityDEnchantedBook entitybook = (EntityDEnchantedBook)entityliving;
    float f1 = entitybook.floatinge + (entitybook.floatingb - entitybook.floatinge) * f;
    float f2 = entitybook.floatingd + (entitybook.floatingc - entitybook.floatingd) * f;
    float f3 = (MathHelper.sin(f1) + 0.5F) * f2 * 1.5F;
    float var6 = f3 - 0.8F;
    GL11.glTranslatef(0.0F, var6, 0.0F);
    GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
  }
  
  private static final ResourceLocation texture = new ResourceLocation("primitivemobs:textures/entity/enchantedbook/book.png");
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return texture;
  }
}
