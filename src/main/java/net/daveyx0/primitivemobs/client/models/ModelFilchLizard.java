package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFilchLizard extends ModelBase {
  ModelRenderer Body;
  
  ModelRenderer Head;
  
  ModelRenderer Tail;
  
  ModelRenderer Leg1;
  
  ModelRenderer Leg2;
  
  ModelRenderer Leg3;
  
  ModelRenderer Leg4;
  
  ModelRenderer Filch1;
  
  ModelRenderer Filch2;
  
  ModelRenderer Filch3;
  
  ModelRenderer Filch7;
  
  ModelRenderer Filch5;
  
  ModelRenderer Filch4;
  
  ModelRenderer Filch6;
  
  ModelRenderer Filch8;
  
  public ModelFilchLizard() {
    this.textureWidth = 64;
    this.textureHeight = 32;
    this.Body = new ModelRenderer(this, 0, 6);
    this.Body.addBox(-2.0F, -1.5F, -6.0F, 4, 3, 12);
    this.Body.setRotationPoint(0.0F, 21.0F, 0.0F);
    this.Body.setTextureSize(64, 32);
    this.Body.mirror = true;
    setRotation(this.Body, 0.0F, 0.0F, 0.0F);
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.addBox(-2.0F, -0.5F, -4.0F, 4, 2, 4);
    this.Head.setRotationPoint(0.0F, 21.0F, -6.0F);
    this.Head.setTextureSize(64, 32);
    this.Head.mirror = true;
    setRotation(this.Head, 0.0F, 0.0F, 0.0F);
    this.Tail = new ModelRenderer(this, 32, 9);
    this.Tail.addBox(-1.0F, -0.5F, 0.0F, 2, 2, 10);
    this.Tail.setRotationPoint(0.0F, 21.0F, 6.0F);
    this.Tail.setTextureSize(64, 32);
    this.Tail.mirror = true;
    setRotation(this.Tail, 0.0F, 0.0F, 0.0F);
    this.Leg1 = new ModelRenderer(this, 16, 0);
    this.Leg1.addBox(0.0F, -0.5F, -0.5F, 4, 1, 1);
    this.Leg1.setRotationPoint(2.0F, 22.0F, -4.0F);
    this.Leg1.setTextureSize(64, 32);
    this.Leg1.mirror = true;
    setRotation(this.Leg1, 0.0F, 0.0F, 0.3839724F);
    this.Leg2 = new ModelRenderer(this, 16, 3);
    this.Leg2.addBox(-4.0F, -0.5F, -0.5F, 4, 1, 1);
    this.Leg2.setRotationPoint(-2.0F, 22.0F, -4.0F);
    this.Leg2.setTextureSize(64, 32);
    this.Leg2.mirror = true;
    setRotation(this.Leg2, 0.0F, 0.0F, -0.3839724F);
    this.Leg3 = new ModelRenderer(this, 16, 0);
    this.Leg3.addBox(0.0F, -0.5F, -0.5F, 4, 1, 1);
    this.Leg3.setRotationPoint(2.0F, 22.0F, 5.0F);
    this.Leg3.setTextureSize(64, 32);
    this.Leg3.mirror = true;
    setRotation(this.Leg3, 0.0F, 0.0F, 0.3839724F);
    this.Leg4 = new ModelRenderer(this, 16, 3);
    this.Leg4.addBox(-4.0F, -0.5F, -0.5F, 4, 1, 1);
    this.Leg4.setRotationPoint(-2.0F, 22.0F, 5.0F);
    this.Leg4.setTextureSize(64, 32);
    this.Leg4.mirror = true;
    setRotation(this.Leg4, 0.0F, 0.0F, -0.3839724F);
    this.Filch1 = new ModelRenderer(this, 0, 22);
    this.Filch1.addBox(1.0F, -1.5F, 0.0F, 1, 3, 6);
    this.Filch1.setRotationPoint(0.0F, 21.0F, -6.0F);
    this.Filch1.setTextureSize(64, 32);
    this.Filch1.mirror = true;
    setRotation(this.Filch1, 0.0F, 0.0349066F, 0.0F);
    this.Filch2 = new ModelRenderer(this, 14, 22);
    this.Filch2.addBox(-2.0F, -1.5F, 0.0F, 1, 3, 6);
    this.Filch2.setRotationPoint(0.0F, 21.0F, -6.0F);
    this.Filch2.setTextureSize(64, 32);
    this.Filch2.mirror = true;
    setRotation(this.Filch2, 0.0F, -0.0349066F, 0.0F);
    this.Filch3 = new ModelRenderer(this, 0, 22);
    this.Filch3.addBox(0.0F, -2.5F, 2.0F, 1, 3, 6);
    this.Filch3.setRotationPoint(0.0F, 12.0F, -1.0F);
    this.Filch3.setTextureSize(64, 32);
    this.Filch3.mirror = true;
    setRotation(this.Filch3, -0.1745329F, 1.500983F, -1.047198F);
    this.Filch7 = new ModelRenderer(this, 0, 22);
    this.Filch7.addBox(0.0F, -4.5F, 0.0F, 1, 3, 6);
    this.Filch7.setRotationPoint(0.0F, 12.0F, -1.0F);
    this.Filch7.setTextureSize(64, 32);
    this.Filch7.mirror = true;
    setRotation(this.Filch7, -1.308997F, 1.308997F, -0.8726646F);
    this.Filch5 = new ModelRenderer(this, 0, 22);
    this.Filch5.addBox(0.5F, -3.5F, 1.0F, 1, 3, 6);
    this.Filch5.setRotationPoint(0.0F, 12.0F, -1.0F);
    this.Filch5.setTextureSize(64, 32);
    this.Filch5.mirror = true;
    setRotation(this.Filch5, -1.134464F, 0.9599311F, -1.047198F);
    this.Filch4 = new ModelRenderer(this, 14, 22);
    this.Filch4.addBox(-1.0F, -2.5F, 2.0F, 1, 3, 6);
    this.Filch4.setRotationPoint(0.0F, 12.0F, -1.0F);
    this.Filch4.setTextureSize(64, 32);
    this.Filch4.mirror = true;
    setRotation(this.Filch4, -0.1745329F, -1.48353F, 1.047198F);
    this.Filch6 = new ModelRenderer(this, 14, 22);
    this.Filch6.addBox(-1.5F, -3.5F, 1.0F, 1, 3, 6);
    this.Filch6.setRotationPoint(0.0F, 12.0F, -1.0F);
    this.Filch6.setTextureSize(64, 32);
    this.Filch6.mirror = true;
    setRotation(this.Filch6, -1.134464F, -0.9599311F, 1.047198F);
    this.Filch8 = new ModelRenderer(this, 14, 22);
    this.Filch8.addBox(-1.0F, -4.5F, 0.0F, 1, 3, 6);
    this.Filch8.setRotationPoint(0.0F, 12.0F, -1.0F);
    this.Filch8.setTextureSize(64, 32);
    this.Filch8.mirror = true;
    setRotation(this.Filch8, -1.308997F, -1.308997F, 0.8726646F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Body.render(f5);
    this.Head.render(f5);
    this.Tail.render(f5);
    this.Leg1.render(f5);
    this.Leg2.render(f5);
    this.Leg3.render(f5);
    this.Leg4.render(f5);
    this.Filch1.render(f5);
    this.Filch2.render(f5);
    this.Filch3.render(f5);
    this.Filch7.render(f5);
    this.Filch5.render(f5);
    this.Filch4.render(f5);
    this.Filch6.render(f5);
    this.Filch8.render(f5);
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
