package net.daveyx0.primitivemobs.client.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelSheepman extends ModelBase {
  ModelRenderer Head;
  
  ModelRenderer HeadCover;
  
  ModelRenderer Body;
  
  ModelRenderer LegLeft;
  
  ModelRenderer LegRight;
  
  ModelRenderer ArmLeftShoulder;
  
  ModelRenderer ArmRightShoulder;
  
  ModelRenderer ArmFolderHands;
  
  public ModelSheepman() {
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.addBox(-3.0F, -6.0F, -6.0F, 6, 6, 8);
    this.Head.setRotationPoint(0.0F, 2.0F, 0.0F);
    this.Head.setTextureSize(64, 64);
    this.Head.mirror = true;
    setRotation(this.Head, 0.0F, 0.0F, 0.0F);
    this.HeadCover = new ModelRenderer(this, 0, 14);
    this.HeadCover.addBox(-3.5F, -6.5F, -5.1F, 7, 7, 8);
    this.HeadCover.setRotationPoint(0.0F, 2.0F, 0.0F);
    this.HeadCover.setTextureSize(64, 64);
    this.HeadCover.mirror = true;
    setRotation(this.HeadCover, 0.0F, 0.0F, 0.0F);
    this.Body = new ModelRenderer(this, 28, 40);
    this.Body.addBox(-5.0F, 0.0F, -4.0F, 10, 16, 8);
    this.Body.setRotationPoint(0.0F, 2.0F, 0.0F);
    this.Body.setTextureSize(64, 64);
    this.Body.mirror = true;
    setRotation(this.Body, 0.0F, 0.0F, 0.0F);
    this.LegLeft = new ModelRenderer(this, 0, 32);
    this.LegLeft.addBox(-1.9F, 0.0F, -2.0F, 4, 12, 4);
    this.LegLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
    this.LegLeft.setTextureSize(64, 64);
    this.LegLeft.mirror = true;
    setRotation(this.LegLeft, 0.0F, 0.0F, 0.0F);
    this.LegRight = new ModelRenderer(this, 0, 32);
    this.LegRight.addBox(-2.1F, 0.0F, -2.0F, 4, 12, 4);
    this.LegRight.setRotationPoint(-2.0F, 12.0F, 0.0F);
    this.LegRight.setTextureSize(64, 64);
    this.LegRight.mirror = false;
    setRotation(this.LegRight, 0.0F, 0.0F, 0.0F);
    this.ArmLeftShoulder = new ModelRenderer(this, 34, 0);
    this.ArmLeftShoulder.addBox(1.0F, 0.0F, -4.0F, 4, 8, 4);
    this.ArmLeftShoulder.setRotationPoint(2.0F, 5.0F, 1.0F);
    this.ArmLeftShoulder.setTextureSize(64, 64);
    this.ArmLeftShoulder.mirror = true;
    setRotation(this.ArmLeftShoulder, -0.7853982F, 0.0F, 0.0F);
    this.ArmRightShoulder = new ModelRenderer(this, 34, 0);
    this.ArmRightShoulder.addBox(-5.0F, 0.0F, -4.0F, 4, 8, 4);
    this.ArmRightShoulder.setRotationPoint(-2.0F, 5.0F, 1.0F);
    this.ArmRightShoulder.setTextureSize(64, 64);
    this.ArmRightShoulder.mirror = false;
    setRotation(this.ArmRightShoulder, -0.7853982F, 0.0F, 0.0F);
    this.ArmFolderHands = new ModelRenderer(this, 30, 16);
    this.ArmFolderHands.addBox(-2.0F, -8.05F, -0.1F, 8, 4, 4);
    this.ArmFolderHands.setRotationPoint(-2.0F, 5.0F, 1.0F);
    this.ArmFolderHands.setTextureSize(64, 64);
    this.ArmFolderHands.mirror = false;
    setRotation(this.ArmFolderHands, 2.356194F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Head.render(f5);
    this.HeadCover.render(f5);
    this.Body.render(f5);
    this.LegLeft.render(f5);
    this.LegRight.render(f5);
    this.ArmLeftShoulder.render(f5);
    this.ArmRightShoulder.render(f5);
    this.ArmFolderHands.render(f5);
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
    this.HeadCover.rotateAngleY = f4 / 57.295776F;
    this.HeadCover.rotateAngleX = f5 / 57.295776F;
    this.LegRight.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.6F * f1;
    this.LegLeft.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.6F * f1;
  }
}
