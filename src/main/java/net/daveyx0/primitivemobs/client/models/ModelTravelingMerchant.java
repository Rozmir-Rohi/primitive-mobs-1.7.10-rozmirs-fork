package net.daveyx0.primitivemobs.client.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelTravelingMerchant extends ModelBase {
  ModelRenderer Head;
  
  ModelRenderer Body;
  
  ModelRenderer LegLeft;
  
  ModelRenderer LegRight;
  
  ModelRenderer ArmLeftShoulder;
  
  ModelRenderer ArmLeftHand;
  
  ModelRenderer Burden1;
  
  ModelRenderer Burden2;
  
  ModelRenderer ArmRightShoulder;
  
  ModelRenderer ArmRightHand;
  
  ModelRenderer Nose;
  
  public ModelTravelingMerchant() {
    this.textureWidth = 128;
    this.textureHeight = 64;
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.addBox(-4.0F, -9.5F, -5.1F, 8, 10, 8);
    this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Head.setTextureSize(128, 64);
    this.Head.mirror = true;
    setRotation(this.Head, 0.0F, 0.0F, 0.0F);
    this.Body = new ModelRenderer(this, 0, 39);
    this.Body.addBox(-5.0F, 0.0F, -4.0F, 8, 17, 6);
    this.Body.setRotationPoint(1.0F, 0.5F, 0.0F);
    this.Body.setTextureSize(128, 64);
    this.Body.mirror = true;
    setRotation(this.Body, 0.0F, 0.0F, 0.0F);
    this.LegLeft = new ModelRenderer(this, 0, 22);
    this.LegLeft.addBox(-2.1F, 0.0F, -2.0F, 4, 12, 4);
    this.LegLeft.setRotationPoint(2.0F, 12.0F, -1.0F);
    this.LegLeft.setTextureSize(128, 64);
    this.LegLeft.mirror = true;
    setRotation(this.LegLeft, 0.0F, 0.0F, 0.0F);
    this.LegRight = new ModelRenderer(this, 0, 22);
    this.LegRight.addBox(-1.9F, 0.0F, -2.0F, 4, 12, 4);
    this.LegRight.setRotationPoint(-2.0F, 12.0F, -1.0F);
    this.LegRight.setTextureSize(128, 64);
    this.LegRight.mirror = false;
    setRotation(this.LegRight, 0.0F, 0.0F, 0.0F);
    this.ArmLeftShoulder = new ModelRenderer(this, 40, 38);
    this.ArmLeftShoulder.addBox(0.0F, -2.0F, -2.0F, 4, 4, 4);
    this.ArmLeftShoulder.setRotationPoint(2.0F, 3.0F, 1.0F);
    this.ArmLeftShoulder.setTextureSize(128, 64);
    this.ArmLeftShoulder.mirror = true;
    setRotation(this.ArmLeftShoulder, 0.6981317F, 0.0F, 0.0F);
    this.ArmLeftHand = new ModelRenderer(this, 44, 22);
    this.ArmLeftHand.addBox(0.0F, -6.0F, -6.0F, 4, 8, 4);
    this.ArmLeftHand.setRotationPoint(2.0F, 3.0F, 1.0F);
    this.ArmLeftHand.setTextureSize(128, 64);
    this.ArmLeftHand.mirror = true;
    setRotation(this.ArmLeftHand, 0.6981317F, 0.0F, 0.0F);
    this.Burden1 = new ModelRenderer(this, 66, 26);
    this.Burden1.addBox(-8.0F, 0.0F, 0.0F, 16, 6, 6);
    this.Burden1.setRotationPoint(0.0F, -8.0F, 3.0F);
    this.Burden1.setTextureSize(128, 64);
    this.Burden1.mirror = true;
    setRotation(this.Burden1, 0.0F, 0.0F, 0.0F);
    this.Burden2 = new ModelRenderer(this, 66, 38);
    this.Burden2.addBox(-7.0F, 0.0F, 0.0F, 14, 18, 8);
    this.Burden2.setRotationPoint(0.0F, -2.0F, 3.0F);
    this.Burden2.setTextureSize(128, 64);
    this.Burden2.mirror = true;
    setRotation(this.Burden2, 0.0F, 0.0F, 0.0F);
    this.ArmRightShoulder = new ModelRenderer(this, 40, 38);
    this.ArmRightShoulder.addBox(-4.0F, -2.0F, -2.0F, 4, 4, 4);
    this.ArmRightShoulder.setRotationPoint(-2.0F, 3.0F, 1.0F);
    this.ArmRightShoulder.setTextureSize(128, 64);
    this.ArmRightShoulder.mirror = false;
    setRotation(this.ArmRightShoulder, 0.6981317F, 0.0F, 0.0F);
    this.ArmRightHand = new ModelRenderer(this, 44, 22);
    this.ArmRightHand.addBox(-4.0F, -6.0F, -6.0F, 4, 8, 4);
    this.ArmRightHand.setRotationPoint(-2.0F, 3.0F, 1.0F);
    this.ArmRightHand.setTextureSize(128, 64);
    this.ArmRightHand.mirror = false;
    setRotation(this.ArmRightHand, 0.6981317F, 0.0F, 0.0F);
    this.Nose = new ModelRenderer(this, 24, 0);
    this.Nose.addBox(-1.0F, -2.5F, -7.0F, 2, 4, 2);
    this.Nose.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Nose.setTextureSize(128, 64);
    this.Nose.mirror = true;
    setRotation(this.Nose, 0.0F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Head.render(f5);
    this.Body.render(f5);
    this.LegLeft.render(f5);
    this.LegRight.render(f5);
    this.ArmLeftShoulder.render(f5);
    this.ArmLeftHand.render(f5);
    this.Burden1.render(f5);
    this.Burden2.render(f5);
    this.ArmRightShoulder.render(f5);
    this.ArmRightHand.render(f5);
    this.Nose.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Head.rotateAngleY = f4 / 57.295776F;
    this.Head.rotateAngleX = f5 / 57.295776F;
    this.Nose.rotateAngleY = f4 / 57.295776F;
    this.Nose.rotateAngleX = f5 / 57.295776F;
    this.LegRight.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.6F * f1;
    this.LegLeft.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.6F * f1;
  }
}
