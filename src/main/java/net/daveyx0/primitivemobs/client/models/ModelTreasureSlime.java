package net.daveyx0.primitivemobs.client.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelTreasureSlime extends ModelBase {
  ModelRenderer slimeBodies;
  
  ModelRenderer slimeRightEye;
  
  ModelRenderer slimeLeftEye;
  
  ModelRenderer slimeMouth;
  
  ModelRenderer slimeItem;
  
  public ModelTreasureSlime(int par1) {
    this.slimeBodies = new ModelRenderer(this, 0, par1);
    this.slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);
    if (par1 > 0) {
      this.slimeBodies = null;
      this.slimeRightEye = new ModelRenderer(this, 32, 0);
      this.slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
      this.slimeLeftEye = new ModelRenderer(this, 32, 4);
      this.slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2);
      this.slimeMouth = new ModelRenderer(this, 32, 8);
      this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);
      this.slimeItem = new ModelRenderer(this, 32, 16);
      this.slimeItem.addBox(-8.0F, 68.0F, 2.0F, 16, 16, 0);
    } 
  }
  
  public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
    float f6 = 4.0F;
    GL11.glPushMatrix();
    if (this.slimeBodies != null)
      this.slimeBodies.render(par7); 
    if (this.slimeRightEye != null) {
      this.slimeRightEye.render(par7);
      this.slimeLeftEye.render(par7);
      this.slimeMouth.render(par7);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
      GL11.glTranslatef(0.05F, 0.0F * par5, -0.1F);
      this.slimeItem.render(par7);
    } 
    GL11.glPopMatrix();
  }
}
