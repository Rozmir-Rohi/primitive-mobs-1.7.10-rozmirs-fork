package net.daveyx0.primitivemobs.client.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelCustomCreeper extends ModelCreeper {
  protected float field_78145_g = 8.0F;
  
  protected float field_78151_h = 4.0F;
  
  public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
    EntitySupportCreeper yellowcreeper = (EntitySupportCreeper)par1Entity;
    if (yellowcreeper.isChild()) {
      float var8 = 2.0F;
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, this.field_78145_g * par7 + 0.25F, this.field_78151_h * par7 - 0.25F);
      this.head.render(par7);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
      GL11.glTranslatef(0.0F, 28.0F * par7, 0.0F);
      this.body.render(par7);
      this.leg1.render(par7);
      this.leg2.render(par7);
      this.leg3.render(par7);
      this.leg4.render(par7);
      GL11.glPopMatrix();
    } else {
      this.head.render(par7);
      this.body.render(par7);
      this.leg1.render(par7);
      this.leg2.render(par7);
      this.leg3.render(par7);
      this.leg4.render(par7);
    } 
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
    this.head.rotateAngleY = par4 / 57.295776F;
    this.head.rotateAngleX = par5 / 57.295776F;
    this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
    this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 1.4F * par2;
    this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 1.4F * par2;
    this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
  }
}
