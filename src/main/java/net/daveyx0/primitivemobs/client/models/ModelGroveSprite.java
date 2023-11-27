package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelGroveSprite extends ModelBase {
  public ModelRenderer bipedHead;
  
  public ModelRenderer bipedHeadwear;
  
  public ModelRenderer bipedBody;
  
  public ModelRenderer bipedRightArm;
  
  public ModelRenderer bipedLeftArm;
  
  public ModelRenderer bipedRightLeg;
  
  public ModelRenderer bipedLeftLeg;
  
  public ModelRenderer stem;
  
  public ModelRenderer[] leaf;
  
  public static final int leaves = 3;
  
  public boolean raiseHands;
  
  public boolean field_1279_h;
  
  public boolean field_1278_i;
  
  public boolean isSneak;
  
  public ModelGroveSprite() {
    this(0.0F);
  }
  
  public ModelGroveSprite(float f) {
    this(f, 0.0F);
  }
  
  public ModelGroveSprite(float f, float f1) {
    this.field_1279_h = false;
    this.field_1278_i = false;
    this.isSneak = false;
    this.textureWidth = 64;
    this.textureHeight = 32;
    this.bipedHead = new ModelRenderer(this, 0, 0);
    this.bipedHead.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, f);
    this.bipedHead.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
    this.bipedHead.setTextureSize(64, 32);
    this.bipedHeadwear = new ModelRenderer(this, 24, 0);
    this.bipedHeadwear.addBox(-3.0F, -6.0F, 2.0F, 6, 6, 2, f);
    this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
    this.bipedHeadwear.setTextureSize(64, 32);
    this.bipedBody = new ModelRenderer(this, 24, 12);
    this.bipedBody.addBox(-2.5F, 0.0F, -2.0F, 5, 6, 4, f);
    this.bipedBody.setRotationPoint(0.0F, 0.0625F + f1, 0.0F);
    this.bipedBody.setTextureSize(64, 32);
    this.bipedRightArm = new ModelRenderer(this, 12, 12);
    this.bipedRightArm.addBox(-2.0F, -1.0F, -1.5F, 3, 5, 3, f);
    this.bipedRightArm.setRotationPoint(-3.5F, 1.0F + f1, 0.0F);
    this.bipedRightArm.setTextureSize(64, 32);
    this.bipedLeftArm = new ModelRenderer(this, 12, 12);
    this.bipedLeftArm.mirror = true;
    this.bipedLeftArm.addBox(-1.0F, -1.0F, -1.5F, 3, 5, 3, f);
    this.bipedLeftArm.setRotationPoint(3.5F, 1.0F + f1, 0.0F);
    this.bipedLeftArm.setTextureSize(64, 32);
    this.bipedRightLeg = new ModelRenderer(this, 0, 12);
    this.bipedRightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, f);
    this.bipedRightLeg.setRotationPoint(-1.5F, 6.0F + f1, 0.0F);
    this.bipedRightLeg.setTextureSize(64, 32);
    this.bipedLeftLeg = new ModelRenderer(this, 0, 12);
    this.bipedLeftLeg.mirror = true;
    this.bipedLeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, f);
    this.bipedLeftLeg.setRotationPoint(1.5F, 6.0F + f1, 0.0F);
    this.bipedLeftLeg.setTextureSize(64, 32);
    this.stem = new ModelRenderer(this, 24, 8);
    this.stem.addBox(-0.5F, -9.0F, 0.0F, 1, 3, 1, f);
    this.stem.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
    this.stem.setTextureSize(64, 32);
    this.leaf = new ModelRenderer[3];
    for (int i = 0; i < 3; i++) {
      this.leaf[i] = new ModelRenderer(this, 0, 22);
      this.leaf[i].addBox(-1.5F, -9.0F, -5.25F, 3, 1, 5, f);
      this.leaf[i].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      this.leaf[i].setTextureSize(64, 32);
    } 
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.bipedHead.render(f5);
    this.bipedBody.render(f5);
    this.bipedRightArm.render(f5);
    this.bipedLeftArm.render(f5);
    this.bipedRightLeg.render(f5);
    this.bipedLeftLeg.render(f5);
    this.bipedHeadwear.render(f5);
    this.stem.render(f5);
    for (int i = 0; i < 3; i++) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.8125F, 0.0F);
      GL11.glRotatef((this.leaf[i]).rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef((this.leaf[i]).rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslatef(0.0F, -0.8125F, 0.0F);
      (this.leaf[i]).rotateAngleX = 0.0F;
      (this.leaf[i]).rotateAngleY = 2.0F * (i + 1.0F);
      this.leaf[i].render(f5);
      GL11.glPopMatrix();
    } 
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.bipedHead.rotateAngleY = f3 / 57.29578F;
    this.bipedHead.rotateAngleX = f4 / 57.29578F;
    this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
    this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
    for (int i = 0; i < 3; i++) {
      (this.leaf[i]).rotateAngleY = this.stem.rotateAngleY;
      this.stem.rotateAngleX *= 0.5F;
    } 
    this.bipedRightArm.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.8F * f1;
    this.bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.8F * f1;
    this.bipedLeftLeg.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.8F * f1;
    EntityGroveSprite groveSprite = (EntityGroveSprite)entity;
    if (groveSprite.isBegging()) {
      this.bipedLeftArm.rotateAngleX = -1.919862F;
    } else {
      this.bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.8F * f1;
    } 
  }
}
