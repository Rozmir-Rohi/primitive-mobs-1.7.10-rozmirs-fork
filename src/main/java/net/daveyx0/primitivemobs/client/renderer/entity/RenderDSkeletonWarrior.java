package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntityDSkeletonWarrior;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDSkeletonWarrior extends RenderBiped {
  public RenderDSkeletonWarrior() {
    super((ModelBiped)new ModelSkeleton(), 0.5F);
  }
  
  protected void func_82438_a(EntityDSkeletonWarrior par1EntitySkeleton, float par2) {
    if (par1EntitySkeleton.getSkeletonType() == 1)
      GL11.glScalef(1.2F, 1.2F, 1.2F); 
  }
  
  protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2) {
    EntityDSkeletonWarrior entityDSkeletonWarrior = (EntityDSkeletonWarrior)par1EntityLiving;
    float var3 = 1.0F;
    GL11.glColor3f(var3, var3, var3);
    super.renderEquippedItems(par1EntityLiving, par2);
    ItemStack back = entityDSkeletonWarrior.getBackItem();
    if (back != null) {
      GL11.glTranslatef(0.1F, 0.0F, 0.2F);
      GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-45.0F, 0.0F, 1.0F, 0.0F);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.renderManager.itemRenderer.renderItem((EntityLivingBase)par1EntityLiving, back, 0);
      if (back.getItem().requiresMultipleRenderPasses())
        for (int x = 1; x < back.getItem().getRenderPasses(back.getItemDamage()); x++)
          this.renderManager.itemRenderer.renderItem((EntityLivingBase)par1EntityLiving, back, x);  
    } 
  }
  
  protected void func_82422_c() {
    GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
  }
  
  protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
    func_82438_a((EntityDSkeletonWarrior)par1EntityLiving, par2);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return texture;
  }
  
  private static final ResourceLocation texture = new ResourceLocation("primitivemobs:textures/entity/skeletonwarrior/skeletonwarrior.png");
}
