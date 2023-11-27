package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationGeneral;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLily extends ModelBase {
  ModelRenderer LilyUpper;
  
  ModelRenderer LilyBottom;
  
  public ModelLily() {
    if (PrimitiveMobsConfigurationGeneral.getWaterLilyAdaptive()) {
      this.LilyUpper = new ModelRenderer(this, -4, -4);
      this.LilyUpper.addBox(-32.0F, -32.0F, 0.0F, 64, 64, 4);
      this.LilyUpper.setRotationPoint(0.0F, 23.9F, 0.0F);
      this.LilyUpper.setTextureSize(64, 32);
      this.LilyUpper.mirror = true;
      setRotation(this.LilyUpper, 1.570796F, 0.0F, 0.0F);
    } else {
      this.LilyUpper = new ModelRenderer(this, 0, 0);
      this.LilyUpper.addBox(-8.0F, -8.0F, 0.0F, 16, 16, 1);
      this.LilyUpper.setRotationPoint(0.0F, 23.9F, 0.0F);
      this.LilyUpper.setTextureSize(64, 32);
      this.LilyUpper.mirror = true;
      setRotation(this.LilyUpper, 1.570796F, 0.0F, 0.0F);
    } 
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.LilyUpper.render(f5);
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
