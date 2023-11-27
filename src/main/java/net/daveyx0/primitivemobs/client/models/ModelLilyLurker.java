package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelLilyLurker extends ModelBase {
  ModelRenderer Body1;
  
  ModelRenderer Fin1;
  
  ModelRenderer Fin2;
  
  ModelRenderer Body2;
  
  ModelRenderer Fin3;
  
  ModelRenderer Fin4;
  
  ModelRenderer Root1;
  
  ModelRenderer Root2;
  
  public ModelLilyLurker() {
    this.textureWidth = 64;
    this.textureHeight = 32;
    this.Body1 = new ModelRenderer(this, 28, 0);
    this.Body1.addBox(-4.0F, 0.0F, -5.0F, 8, 4, 10);
    this.Body1.setRotationPoint(0.0F, 20.0F, 0.0F);
    this.Body1.setTextureSize(64, 32);
    this.Body1.mirror = true;
    setRotation(this.Body1, 0.0F, 0.0F, 0.0F);
    this.Fin1 = new ModelRenderer(this, 0, 0);
    this.Fin1.addBox(0.0F, 0.0F, -1.5F, 6, 1, 3);
    this.Fin1.setRotationPoint(3.0F, 22.0F, -1.0F);
    this.Fin1.setTextureSize(64, 32);
    this.Fin1.mirror = true;
    setRotation(this.Fin1, 0.0F, -0.6806784F, 0.0F);
    this.Fin2 = new ModelRenderer(this, 0, 0);
    this.Fin2.addBox(0.0F, 0.0F, -1.5F, 6, 1, 3);
    this.Fin2.setRotationPoint(-3.0F, 22.0F, -1.0F);
    this.Fin2.setTextureSize(64, 32);
    this.Fin2.mirror = true;
    setRotation(this.Fin2, 0.0F, -2.460914F, 0.0F);
    this.Body2 = new ModelRenderer(this, 0, 10);
    this.Body2.addBox(-3.0F, -1.0F, 0.0F, 6, 3, 2);
    this.Body2.setRotationPoint(0.0F, 22.0F, 5.0F);
    this.Body2.setTextureSize(64, 32);
    this.Body2.mirror = true;
    setRotation(this.Body2, 0.0F, 0.0F, 0.0F);
    this.Fin3 = new ModelRenderer(this, 0, 4);
    this.Fin3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 5);
    this.Fin3.setRotationPoint(0.0F, 22.0F, 7.0F);
    this.Fin3.setTextureSize(64, 32);
    this.Fin3.mirror = true;
    setRotation(this.Fin3, 0.0F, 0.4712389F, 0.0F);
    this.Fin4 = new ModelRenderer(this, 0, 4);
    this.Fin4.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 5);
    this.Fin4.setRotationPoint(0.0F, 22.0F, 7.0F);
    this.Fin4.setTextureSize(64, 32);
    this.Fin4.mirror = true;
    setRotation(this.Fin4, 0.0F, -0.4712389F, 0.0F);
    this.Root1 = new ModelRenderer(this, 18, 0);
    this.Root1.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1);
    this.Root1.setRotationPoint(0.0F, 21.0F, -2.0F);
    this.Root1.setTextureSize(64, 32);
    this.Root1.mirror = true;
    setRotation(this.Root1, -0.6981317F, 0.0F, 0.0F);
    this.Root2 = new ModelRenderer(this, 18, 0);
    this.Root2.addBox(-0.5F, -4.0F, -0.5F, 1, 5, 1);
    this.Root2.setRotationPoint(0.0F, 16.5F, 1.0F);
    this.Root2.setTextureSize(64, 32);
    this.Root2.mirror = true;
    setRotation(this.Root2, 0.3316126F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Body1.render(f5);
    this.Fin1.render(f5);
    this.Fin2.render(f5);
    this.Body2.render(f5);
    this.Fin3.render(f5);
    this.Fin4.render(f5);
    this.Root1.render(f5);
    this.Root2.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Fin1.rotateAngleY = MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1 - 0.6806784F;
    this.Fin2.rotateAngleY = -MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1 - 2.460914F;
    this.Fin3.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1;
    this.Fin4.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1;
  }
}
