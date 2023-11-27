package net.daveyx0.primitivemobs.client.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelDEnchantedBook extends ModelBase {
  ModelRenderer Book1;
  
  ModelRenderer Book2;
  
  ModelRenderer Pages1;
  
  ModelRenderer Pages2;
  
  ModelRenderer Page1;
  
  ModelRenderer Page2;
  
  public ModelDEnchantedBook() {
    this.textureWidth = 64;
    this.textureHeight = 32;
    this.Book1 = new ModelRenderer(this, 0, 0);
    this.Book1.addBox(-6.0F, -5.0F, 0.0F, 6, 10, 0);
    this.Book1.setRotationPoint(0.0F, 20.0F, 0.0F);
    this.Book1.setTextureSize(64, 32);
    this.Book1.mirror = true;
    setRotation(this.Book1, 1.570796F, 0.0F, 0.0F);
    this.Book2 = new ModelRenderer(this, 16, 0);
    this.Book2.addBox(0.0F, -5.0F, 0.0F, 6, 10, 0);
    this.Book2.setRotationPoint(0.0F, 20.0F, 0.0F);
    this.Book2.setTextureSize(64, 32);
    this.Book2.mirror = true;
    setRotation(this.Book2, 1.570796F, 0.0F, 0.0F);
    this.Pages1 = new ModelRenderer(this, 0, 10);
    this.Pages1.addBox(-5.0F, -4.0F, -1.1F, 5, 8, 1);
    this.Pages1.setRotationPoint(0.0F, 20.0F, 0.0F);
    this.Pages1.setTextureSize(64, 32);
    this.Pages1.mirror = true;
    setRotation(this.Pages1, 1.570796F, 0.0F, 0.0F);
    this.Pages2 = new ModelRenderer(this, 12, 10);
    this.Pages2.addBox(0.0F, -4.0F, -1.1F, 5, 8, 1);
    this.Pages2.setRotationPoint(0.0F, 20.0F, 0.0F);
    this.Pages2.setTextureSize(64, 32);
    this.Pages2.mirror = true;
    setRotation(this.Pages2, 1.570796F, 0.0F, 0.0F);
    this.Page1 = new ModelRenderer(this, 24, 10);
    this.Page1.addBox(0.0F, -4.0F, -1.1F, 5, 8, 0);
    this.Page1.setRotationPoint(0.0F, 20.0F, 0.0F);
    this.Page1.setTextureSize(64, 32);
    this.Page1.mirror = true;
    setRotation(this.Page1, 1.570796F, 0.0F, 0.5235988F);
    this.Page2 = new ModelRenderer(this, 24, 10);
    this.Page2.addBox(-5.0F, -4.0F, -1.1F, 5, 8, 0);
    this.Page2.setRotationPoint(0.0F, 20.0F, 0.0F);
    this.Page2.setTextureSize(64, 32);
    this.Page2.mirror = true;
    setRotation(this.Page2, 1.570796F, 0.0F, -0.5235988F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Book1.render(f5);
    this.Book2.render(f5);
    this.Pages1.render(f5);
    this.Pages2.render(f5);
    this.Page1.render(f5);
    this.Page2.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Book1.rotateAngleZ = f2;
    this.Book2.rotateAngleZ = -f2;
    this.Pages1.rotateAngleZ = f2;
    this.Pages2.rotateAngleZ = -f2;
    this.Page1.rotateAngleZ = f2 + 0.5235988F;
    this.Page2.rotateAngleZ = -f2 - 0.5235988F;
  }
}
