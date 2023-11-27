package net.daveyx0.primitivemobs.client.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelBlazingJuggernaut extends ModelBase {
  ModelRenderer Head;
  
  ModelRenderer RightArm;
  
  ModelRenderer Body;
  
  ModelRenderer LeftArm;
  
  ModelRenderer RightShoulder;
  
  ModelRenderer LeftShoulder;
  
  ModelRenderer[] Sticks = new ModelRenderer[12];
  
  public ModelBlazingJuggernaut() {
    this.textureWidth = 128;
    this.textureHeight = 64;
    this.Head = new ModelRenderer(this, 8, 17);
    this.Head.addBox(-3.0F, -5.0F, -2.5F, 6, 5, 5);
    this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.Head.setTextureSize(128, 64);
    this.Head.mirror = true;
    setRotation(this.Head, 0.0F, 0.0F, 0.0F);
    this.RightArm = new ModelRenderer(this, 0, 27);
    this.RightArm.addBox(5.0F, -4.5F, -2.5F, 3, 9, 4);
    this.RightArm.setRotationPoint(0.0F, 4.0F, 0.0F);
    this.RightArm.setTextureSize(128, 64);
    this.RightArm.mirror = true;
    setRotation(this.RightArm, 0.0F, -0.0698132F, -0.0349066F);
    this.Body = new ModelRenderer(this, 0, 0);
    this.Body.addBox(-6.0F, -4.5F, -4.0F, 12, 9, 8);
    this.Body.setRotationPoint(0.0F, 4.0F, 0.0F);
    this.Body.setTextureSize(128, 64);
    this.Body.mirror = true;
    setRotation(this.Body, 0.0F, 0.0F, 0.0F);
    this.LeftArm = new ModelRenderer(this, 0, 27);
    this.LeftArm.addBox(-8.0F, -4.5F, -2.5F, 3, 9, 4);
    this.LeftArm.setRotationPoint(0.0F, 4.0F, 0.0F);
    this.LeftArm.setTextureSize(128, 64);
    this.LeftArm.mirror = true;
    setRotation(this.LeftArm, 0.0F, 0.0698132F, 0.0349066F);
    this.RightShoulder = new ModelRenderer(this, 30, 17);
    this.RightShoulder.addBox(3.5F, -7.5F, -4.5F, 5, 5, 7);
    this.RightShoulder.setRotationPoint(0.0F, 4.0F, 0.0F);
    this.RightShoulder.setTextureSize(128, 64);
    this.RightShoulder.mirror = true;
    setRotation(this.RightShoulder, 0.0F, -0.1396263F, 0.0F);
    this.LeftShoulder = new ModelRenderer(this, 30, 17);
    this.LeftShoulder.addBox(-8.5F, -7.5F, -4.5F, 5, 5, 7);
    this.LeftShoulder.setRotationPoint(0.0F, 4.0F, 0.0F);
    this.LeftShoulder.setTextureSize(128, 64);
    this.LeftShoulder.mirror = true;
    setRotation(this.LeftShoulder, 0.0F, 0.1396263F, 0.0F);
    for (int i = 0; i < this.Sticks.length; i++) {
      this.Sticks[i] = new ModelRenderer(this, 0, 18);
      this.Sticks[i].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
    } 
  }
  
  public int func_78104_a() {
    return 8;
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Head.render(f5);
    this.RightArm.render(f5);
    this.Body.render(f5);
    this.LeftArm.render(f5);
    this.RightShoulder.render(f5);
    this.LeftShoulder.render(f5);
    for (int i = 0; i < this.Sticks.length; i++)
      this.Sticks[i].render(f5); 
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
    float f6 = par3 * 3.1415927F * -0.1F;
    int i;
    for (i = 0; i < 4; i++) {
      (this.Sticks[i]).rotationPointY = 6.0F + MathHelper.cos(((i * 2) + par3) * 0.25F);
      (this.Sticks[i]).rotationPointX = MathHelper.cos(f6) * 9.0F;
      (this.Sticks[i]).rotationPointZ = MathHelper.sin(f6) * 9.0F;
      f6++;
    } 
    f6 = 0.7853982F + par3 * 3.1415927F * 0.03F;
    for (i = 4; i < 8; i++) {
      (this.Sticks[i]).rotationPointY = 5.0F + MathHelper.cos(((i * 2) + par3) * 0.25F);
      (this.Sticks[i]).rotationPointX = MathHelper.cos(f6) * 11.0F;
      (this.Sticks[i]).rotationPointZ = MathHelper.sin(f6) * 11.0F;
      f6++;
    } 
    f6 = 0.47123894F + par3 * 3.1415927F * -0.05F;
    for (i = 8; i < 12; i++) {
      (this.Sticks[i]).rotationPointY = 4.0F + MathHelper.cos((i * 1.5F + par3) * 0.5F);
      (this.Sticks[i]).rotationPointX = MathHelper.cos(f6) * 13.0F;
      (this.Sticks[i]).rotationPointZ = MathHelper.sin(f6) * 13.0F;
      f6++;
    } 
    this.Head.rotateAngleY = par4 / 57.295776F;
    this.Head.rotateAngleX = par5 / 57.295776F;
  }
}
