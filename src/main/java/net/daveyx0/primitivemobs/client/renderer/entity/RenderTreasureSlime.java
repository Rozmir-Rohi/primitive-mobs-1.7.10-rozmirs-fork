package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTreasureSlime extends RenderLiving {
  private ModelBase scaleAmount;
  
  private static final String __OBFID = "CL_00001024";
  
  private static ResourceLocation itemtexture;
  
  public RenderTreasureSlime(ModelBase p_i1267_1_, ModelBase p_i1267_2_, float p_i1267_3_) {
    super(p_i1267_1_, p_i1267_3_);
    this.scaleAmount = p_i1267_2_;
  }
  
  protected int shouldRenderPass(EntityTreasureSlime p_77032_1_, int p_77032_2_, float p_77032_3_) {
    if (p_77032_1_.isInvisible())
      return 0; 
    if (p_77032_2_ == 0) {
      setRenderPassModel(this.scaleAmount);
      GL11.glEnable(2977);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      if (p_77032_1_.getPlayerOwned())
        GL11.glColor4f(p_77032_1_.getR() / 127.5F, p_77032_1_.getG() / 127.5F, p_77032_1_.getB() / 127.5F, 1.0F); 
      return 1;
    } 
    if (p_77032_2_ == 1) {
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    } 
    return -1;
  }
  
  protected void preRenderCallback(EntityTreasureSlime p_77041_1_, float p_77041_2_) {
    float f1 = p_77041_1_.getSlimeSize();
    float f2 = (p_77041_1_.prevSquishFactor + (p_77041_1_.squishFactor - p_77041_1_.prevSquishFactor) * p_77041_2_) / (f1 * 0.5F + 1.0F);
    float f3 = 1.0F / (f2 + 1.0F);
    GL11.glScalef(f3 * f1, 1.0F / f3 * f1, f3 * f1);
  }
  
  protected ResourceLocation getResource(EntityTreasureSlime par1Entity) {
	String texture;
    switch (par1Entity.getItem()) {
      case 0:
        texture = "primitivemobs:textures/entity/treasureslime/slime_default.png";
        break;
      case 1:
        texture = "primitivemobs:textures/entity/treasureslime/slime_apple.png";
        break;
      case 2:
        texture = "primitivemobs:textures/entity/treasureslime/slime_cake.png";
        break;
      case 3:
        texture = "primitivemobs:textures/entity/treasureslime/slime_cookie.png";
        break;
      case 4:
        texture = "primitivemobs:textures/entity/treasureslime/slime_diamond.png";
        break;
      case 5:
        texture = "primitivemobs:textures/entity/treasureslime/slime_disc.png";
        break;
      case 6:
        texture = "primitivemobs:textures/entity/treasureslime/slime_emerald.png";
        break;
      case 7:
        texture = "primitivemobs:textures/entity/treasureslime/slime_enderpearl.png";
        break;
      case 8:
        texture = "primitivemobs:textures/entity/treasureslime/slime_glowstone.png";
        break;
      case 9:
        texture = "primitivemobs:textures/entity/treasureslime/slime_gold.png";
        break;
      case 10:
        texture = "primitivemobs:textures/entity/treasureslime/slime_gunpowder.png";
        break;
      case 11:
        texture = "primitivemobs:textures/entity/treasureslime/slime_iron.png";
        itemtexture = new ResourceLocation(texture);
        return itemtexture;
      case 12:
        texture = "primitivemobs:textures/entity/treasureslime/slime_redstone.png";
        break;
      case 13:
        texture = "primitivemobs:textures/entity/treasureslime/slime_saddle.png";
        break;
      case 14:
        texture = "primitivemobs:textures/entity/treasureslime/slime_magma.png";
        break;
      case 15:
        texture = "primitivemobs:textures/entity/treasureslime/slime_lapis.png";
        break;
      case 16:
        texture = "primitivemobs:textures/entity/treasureslime/slime_charge.png";
        break;
      case 17:
        texture = "primitivemobs:textures/entity/treasureslime/slime_snow.png";
        break;
      case 18:
        texture = "primitivemobs:textures/entity/treasureslime/slime_eye.png";
        break;
      case 19:
        texture = "primitivemobs:textures/entity/treasureslime/slime_blaze.png";
        break;
      case 20:
        texture = "primitivemobs:textures/entity/treasureslime/slime_tameable.png";
        break;
      case 21:
        texture = "primitivemobs:textures/entity/treasureslime/slime_default.png";
        break;
      default:
    	texture = "primitivemobs:textures/entity/treasureslime/slime_default.png";
    } 
    itemtexture = new ResourceLocation(texture);
    return itemtexture;
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) {
    preRenderCallback((EntityTreasureSlime)p_77041_1_, p_77041_2_);
  }
  
  protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
    return shouldRenderPass((EntityTreasureSlime)p_77032_1_, p_77032_2_, p_77032_3_);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return getResource((EntityTreasureSlime)p_110775_1_);
  }
}
