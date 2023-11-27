package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.client.models.ModelLostMiner;
import net.daveyx0.primitivemobs.entity.passive.EntityLostMiner;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderLostMiner extends RenderLiving {
  protected ModelLostMiner LostMinerModel;
  
  public RenderLostMiner() {
    super((ModelBase)new ModelLostMiner(), 0.5F);
    this.LostMinerModel = (ModelLostMiner)this.mainModel;
  }
  
  protected int shouldDLostMinerRenderPass(EntityLostMiner par1EntityDLostMiner, int par2, float par3) {
    return -1;
  }
  
  public void renderDLostMiner(EntityLostMiner par1EntityDLostMiner, double par2, double par4, double par6, float par8, float par9) {
    doRender((EntityLiving)par1EntityDLostMiner, par2, par4, par6, par8, par9);
  }
  
  protected void renderDLostMinerEquipedItems(EntityLostMiner par1EntityDLostMiner, float par2) {
    super.renderEquippedItems((EntityLivingBase)par1EntityDLostMiner, par2);
  }
  
  protected void preRenderDLostMiner(EntityLostMiner par1EntityDLostMiner, float par2) {
    float var3 = 0.9375F;
    if (par1EntityDLostMiner.getGrowingAge() < 0) {
      var3 = (float)(var3 * 0.5D);
      this.shadowSize = 0.25F;
    } else {
      this.shadowSize = 0.5F;
    } 
    GL11.glScalef(1.0F, 1.0F, 1.0F);
  }
  
  protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {
    preRenderDLostMiner((EntityLostMiner)par1EntityLiving, par2);
  }
  
  protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3) {
    return shouldDLostMinerRenderPass((EntityLostMiner)par1EntityLiving, par2, par3);
  }
  
  protected void renderEquippedItems(EntityLivingBase par1EntityLiving, float par2) {
    float var3 = 1.0F;
    GL11.glColor3f(var3, var3, var3);
    super.renderEquippedItems(par1EntityLiving, par2);
    ItemStack itemstack = par1EntityLiving.getHeldItem();
    if (itemstack != null && itemstack.getItem() != null) {
      Item item = itemstack.getItem();
      GL11.glPushMatrix();
      if (this.mainModel.isChild) {
        float f1 = 0.5F;
        GL11.glTranslatef(0.0F, 0.625F, 0.0F);
        GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
        GL11.glScalef(f1, f1, f1);
      } 
      this.LostMinerModel.ArmRightHand.postRender(0.0625F);
      GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
      IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED);
      boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D));
      if (item instanceof net.minecraft.item.ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType()))) {
        float f1 = 0.5F;
        GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
        f1 *= 0.75F;
        GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(-f1, -f1, f1);
      } else if (item == Items.bow) {
        float f1 = 0.625F;
        GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
        GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(f1, -f1, f1);
        GL11.glRotatef(40.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
      } else if (item.isFull3D()) {
        float f1 = 0.625F;
        if (item.shouldRotateAroundWhenRendering()) {
          GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
          GL11.glTranslatef(0.0F, -0.125F, 0.0F);
        } 
        GL11.glScalef(f1, -f1, f1);
        GL11.glTranslatef(-0.3F, 1.4F, -0.8F);
        GL11.glRotatef(40.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
      } else {
        float f1 = 0.375F;
        GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
        GL11.glScalef(f1, f1, f1);
        GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
      } 
      if (itemstack.getItem().requiresMultipleRenderPasses()) {
        for (int i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); i++) {
          int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
          float f5 = (j >> 16 & 0xFF) / 255.0F;
          float f2 = (j >> 8 & 0xFF) / 255.0F;
          float f3 = (j & 0xFF) / 255.0F;
          GL11.glColor4f(f5, f2, f3, 1.0F);
          this.renderManager.itemRenderer.renderItem(par1EntityLiving, itemstack, i);
        } 
      } else {
        int i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
        float f4 = (i >> 16 & 0xFF) / 255.0F;
        float f5 = (i >> 8 & 0xFF) / 255.0F;
        float f2 = (i & 0xFF) / 255.0F;
        GL11.glColor4f(f4, f5, f2, 1.0F);
        this.renderManager.itemRenderer.renderItem(par1EntityLiving, itemstack, 0);
      } 
      GL11.glPopMatrix();
    } 
  }
  
  public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
    renderDLostMiner((EntityLostMiner)par1EntityLiving, par2, par4, par6, par8, par9);
  }
  
  public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
    renderDLostMiner((EntityLostMiner)par1Entity, par2, par4, par6, par8, par9);
  }
  
  private static final ResourceLocation texture = new ResourceLocation("primitivemobs:textures/entity/villager/lostminer.png");
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return texture;
  }
}
