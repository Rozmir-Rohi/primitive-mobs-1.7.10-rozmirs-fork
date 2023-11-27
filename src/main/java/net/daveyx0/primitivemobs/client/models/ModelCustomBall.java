package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCustomBall extends ModelBase {
  ModelRenderer Ball;
  
  public ModelCustomBall() {
    this.textureWidth = 64;
    this.textureHeight = 32;
    this.Ball = new ModelRenderer(this, 0, 0);
    this.Ball.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
    this.Ball.setRotationPoint(0.0F, 22.5F, 0.0F);
    this.Ball.setTextureSize(64, 32);
    this.Ball.mirror = true;
    setRotation(this.Ball, 0.0F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Ball.render(f5);
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
