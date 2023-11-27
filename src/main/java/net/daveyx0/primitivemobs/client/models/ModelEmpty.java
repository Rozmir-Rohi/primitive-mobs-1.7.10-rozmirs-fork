package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEmpty extends ModelBase {
  ModelRenderer Empty;
  
  float f3;
  
  public ModelEmpty() {
    this.textureWidth = 64;
    this.textureHeight = 32;
    this.Empty = new ModelRenderer(this, 0, 0);
    this.Empty.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0);
    this.Empty.setRotationPoint(0.0F, 10.0F, 0.0F);
    this.Empty.setTextureSize(64, 32);
    this.Empty.mirror = true;
    setRotation(this.Empty, 0.0F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Empty.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
}
