package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.entity.monster.EntityDMimic;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDMimic extends ModelBase {
  ModelRenderer Top;
  
  ModelRenderer Lock;
  
  ModelRenderer Bottom;
  
  ModelRenderer Teeth;
  
  public ModelDMimic() {
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.Top = new ModelRenderer(this, 0, 0);
    this.Top.addBox(-7.0F, -5.0F, -14.0F, 14, 5, 14);
    this.Top.setRotationPoint(0.0F, 15.0F, 7.0F);
    this.Top.setTextureSize(64, 64);
    this.Top.mirror = true;
    setRotation(this.Top, 0.0F, 0.0F, 0.0F);
    this.Lock = new ModelRenderer(this, 0, 0);
    this.Lock.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1);
    this.Lock.setRotationPoint(0.0F, 15.0F, 7.0F);
    this.Lock.setTextureSize(64, 64);
    this.Lock.mirror = true;
    setRotation(this.Lock, 0.0F, 0.0F, 0.0F);
    this.Bottom = new ModelRenderer(this, 0, 19);
    this.Bottom.addBox(-7.0F, 0.0F, -7.0F, 14, 10, 14);
    this.Bottom.setRotationPoint(0.0F, 14.0F, 0.0F);
    this.Bottom.setTextureSize(64, 64);
    this.Bottom.mirror = true;
    setRotation(this.Bottom, 0.0F, 0.0F, 0.0F);
    this.Teeth = new ModelRenderer(this, 0, 43);
    this.Teeth.addBox(-6.5F, -1.0F, -6.5F, 13, 1, 13);
    this.Teeth.setRotationPoint(0.0F, 14.0F, 0.0F);
    this.Teeth.setTextureSize(64, 64);
    this.Teeth.mirror = true;
    setRotation(this.Teeth, 0.0F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    EntityDMimic mimic = (EntityDMimic)entity;
    this.Top.render(f5);
    this.Lock.render(f5);
    this.Bottom.render(f5);
    this.Teeth.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Top.rotateAngleX = f2;
    this.Lock.rotateAngleX = f2;
  }
}
