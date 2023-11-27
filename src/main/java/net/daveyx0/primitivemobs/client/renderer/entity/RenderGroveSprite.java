package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelGroveSprite;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

public class RenderGroveSprite extends RenderLiving {
  ModelGroveSprite sprite;
  
  private static final ResourceLocation texture = new ResourceLocation("primitivemobs:textures/entity/grovesprite/grovesprite.png");
  
  private static final ResourceLocation texture1 = new ResourceLocation("primitivemobs:textures/entity/grovesprite/grovebase.png");
  
  private static final ResourceLocation texture2 = new ResourceLocation("primitivemobs:textures/entity/grovesprite/grovestump.png");
  
  private static final ResourceLocation texture3 = new ResourceLocation("primitivemobs:textures/entity/grovesprite/groveleaf.png");
  
  private static final ResourceLocation texture4 = new ResourceLocation("primitivemobs:textures/entity/grovesprite/spruceleaf.png");
  
  private static final ResourceLocation texture5 = new ResourceLocation("primitivemobs:textures/entity/grovesprite/birchbase.png");
  
  private static final ResourceLocation texture6 = new ResourceLocation("primitivemobs:textures/entity/grovesprite/grovecinder.png");
  
  private static final ResourceLocation texture7 = new ResourceLocation("primitivemobs:textures/entity/grovesprite/cinderleaf.png");
  
  private static final ResourceLocation texture8 = new ResourceLocation("primitivemobs:textures/entity/grovesprite/deadleaf.png");
  
  private ModelBase grove;
  
  public RenderGroveSprite(ModelBase par1ModelBase, ModelBase par2ModelBase, float par2) {
    super(par1ModelBase, par2);
    this.sprite = (ModelGroveSprite)par2ModelBase;
  }
  
  protected int shouldRenderPass(EntityGroveSprite p_77032_1_, int p_77032_2_, float p_77032_3_) {
    if (p_77032_2_ == 0) {
      setRenderPassModel((ModelBase)this.sprite);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (p_77032_1_.getInnerColor() != null && !p_77032_1_.getInnerColor().equals("")) {
        int Blue = Integer.parseInt(p_77032_1_.getInnerColor()) & 0xFF;
        int Green = Integer.parseInt(p_77032_1_.getInnerColor()) >> 8 & 0xFF;
        int Red = Integer.parseInt(p_77032_1_.getInnerColor()) >> 16 & 0xFF;
        GL11.glColor4f(Red / 255.0F, Green / 255.0F, Blue / 255.0F, 1.0F);
      } 
      if (p_77032_1_.getLeafKind() == 22)
        return 0; 
      bindTexture(texture2);
      return 1;
    } 
    if (p_77032_2_ == 1) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (p_77032_1_.getLeavesColor() != null && !p_77032_1_.getLeavesColor().equals("")) {
        int Blue = Integer.parseInt(p_77032_1_.getLeavesColor()) & 0xFF;
        int Green = Integer.parseInt(p_77032_1_.getLeavesColor()) >> 8 & 0xFF;
        int Red = Integer.parseInt(p_77032_1_.getLeavesColor()) >> 16 & 0xFF;
        GL11.glColor4f(Red / 255.0F, Green / 255.0F, Blue / 255.0F, 1.0F);
      } 
      if (p_77032_1_.getLeafKind() == 20) {
        bindTexture(texture4);
      } else if (p_77032_1_.getLeafKind() == 22) {
        bindTexture(texture7);
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
      } else if (p_77032_1_.getLeafKind() == 23) {
        bindTexture(texture8);
      } else {
        bindTexture(texture3);
      } 
      return 1;
    } 
    return 0;
  }
  
  protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
    return shouldRenderPass((EntityGroveSprite)p_77032_1_, p_77032_2_, p_77032_3_);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity) {
    if (((EntityGroveSprite)entity).getLeafKind() == 21)
      return texture5; 
    if (((EntityGroveSprite)entity).getLeafKind() == 22)
      return texture6; 
    return texture1;
  }
  
  protected void scaleGroveSprite(EntityGroveSprite p_77032_1_, float par2) {
    GL11.glTranslatef(0.0F, 0.78F, 0.0F);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    if (p_77032_1_.getBaseColor() != null && !p_77032_1_.getBaseColor().equals("")) {
      int Blue = Integer.parseInt(p_77032_1_.getBaseColor()) & 0xFF;
      int Green = Integer.parseInt(p_77032_1_.getBaseColor()) >> 8 & 0xFF;
      int Red = Integer.parseInt(p_77032_1_.getBaseColor()) >> 16 & 0xFF;
      GL11.glColor4f(Red / 255.0F, Green / 255.0F, Blue / 255.0F, 1.0F);
    } 
  }
  
  protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
    scaleGroveSprite((EntityGroveSprite)par1EntityLiving, par2);
  }
  
  protected void renderEquippedItems(EntityLivingBase par1EntityLiving, float par2) {
    EntityGroveSprite groveSprite = (EntityGroveSprite)par1EntityLiving;
    if (groveSprite.getSaplings() > 0) {
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
        this.sprite.bipedRightArm.postRender(0.0625F);
        GL11.glTranslatef(-0.0625F, 0.0F, 0.0625F);
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
  }
}
