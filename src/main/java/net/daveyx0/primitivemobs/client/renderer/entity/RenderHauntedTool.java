package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.client.models.ModelEmpty;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHauntedTool extends RenderLiving {
  private ModelEmpty modelThis;
  
  public RenderHauntedTool(ModelEmpty modelbase, float f) {
    super((ModelBase)modelbase, f);
    this.modelThis = modelbase;
  }
  
  protected float setDeathMaxRotation(EntityHauntedTool entityHauntedPickaxe) {
    return 90.0F;
  }
  
  protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2) {
    EntityHauntedTool entityHauntedPickaxe = (EntityHauntedTool)par1EntityLiving;
    float varx = 1.0F;
    GL11.glColor3f(varx, varx, varx);
    super.renderEquippedItems((EntityLivingBase)par1EntityLiving, par2);
    ItemStack itemstack = par1EntityLiving.getHeldItem();
    float f1 = entityHauntedPickaxe.floatinge + (entityHauntedPickaxe.floatingb - entityHauntedPickaxe.floatinge) * par2;
    float f2 = entityHauntedPickaxe.floatingd + (entityHauntedPickaxe.floatingc - entityHauntedPickaxe.floatingd) * par2;
    float f3 = (MathHelper.sin(f1) + 0.5F) * f2 * 1.5F;
    float var6 = f3 + 0.5F;
    ItemStack itemstack1 = par1EntityLiving.func_130225_q(3);
    if (itemstack != null && itemstack.getItem() != null) {
      Item item = itemstack.getItem();
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, var6, 0.0F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED);
      boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D));
      if (item == Items.bow) {
        float var1 = 0.625F;
        GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
        GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(var1, -var1, var1);
        GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
      } 
      if (itemstack.getItem().requiresMultipleRenderPasses()) {
        for (int i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); i++) {
          int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
          float var3 = (j >> 16 & 0xFF) / 255.0F;
          float var2 = (j >> 8 & 0xFF) / 255.0F;
          float var4 = (j & 0xFF) / 255.0F;
          GL11.glColor4f(var3, var2, var4, 1.0F);
          this.renderManager.itemRenderer.renderItem((EntityLivingBase)par1EntityLiving, itemstack, i);
        } 
      } else {
        int i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
        float f4 = (i >> 16 & 0xFF) / 255.0F;
        float var3 = (i >> 8 & 0xFF) / 255.0F;
        float var2 = (i & 0xFF) / 255.0F;
        GL11.glColor4f(f4, var3, var2, 1.0F);
        this.renderManager.itemRenderer.renderItem((EntityLivingBase)par1EntityLiving, itemstack, 0);
      } 
      GL11.glPopMatrix();
    } 
  }
  
  protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_) {
    renderEquippedItems((EntityLiving)p_77029_1_, p_77029_2_);
  }
  
  protected float getDeathMaxRotation(EntityLiving entityliving) {
    return setDeathMaxRotation((EntityHauntedTool)entityliving);
  }
  
  protected void rotateAnimal(EntityLiving entityliving) {}
  
  public void renderHauntedPickaxe(EntityHauntedTool entityHauntedPickaxe, double d, double d1, double d2, float f, float f1) {
    doRender((EntityLiving)entityHauntedPickaxe, d, d1, d2, f, f1);
  }
  
  public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
    renderHauntedPickaxe((EntityHauntedTool)entityliving, d, d1, d2, f, f1);
  }
  
  protected void preRenderCallback(EntityLiving entityliving, float f) {
    EntityHauntedTool entityHauntedPickaxe = (EntityHauntedTool)entityliving;
    if (entityHauntedPickaxe.climbing())
      rotateAnimal(entityliving); 
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return new ResourceLocation("primitivemobs:textures/entity/festivecreeper/festivecreeper.png");
  }
}
