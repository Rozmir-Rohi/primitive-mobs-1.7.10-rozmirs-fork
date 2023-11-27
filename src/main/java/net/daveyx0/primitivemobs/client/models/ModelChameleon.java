package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelChameleon extends ModelBase {
  ModelRenderer body1;
  
  ModelRenderer body2;
  
  ModelRenderer tail;
  
  ModelRenderer head;
  
  ModelRenderer head2;
  
  ModelRenderer eyeleft;
  
  ModelRenderer eyeright;
  
  ModelRenderer legfrontleft1;
  
  ModelRenderer legfrontright1;
  
  ModelRenderer legfrontleft2;
  
  ModelRenderer legfrontright2;
  
  ModelRenderer legbackleft1;
  
  ModelRenderer legbackright1;
  
  ModelRenderer legbackleft2;
  
  ModelRenderer legbackright2;
  
  ModelRenderer tongue1;
  
  ModelRenderer tongue2;
  
  public ModelChameleon() {
    this.textureWidth = 64;
    this.textureHeight = 32;
    this.body1 = new ModelRenderer(this, 0, 0);
    this.body1.addBox(-2.0F, -1.0F, 0.0F, 4, 4, 6);
    this.body1.setRotationPoint(0.0F, 20.0F, -4.0F);
    this.body1.setTextureSize(64, 32);
    this.body1.mirror = true;
    setRotation(this.body1, 0.1487144F, 0.0F, 0.0F);
    this.body2 = new ModelRenderer(this, 0, 11);
    this.body2.addBox(-1.5F, -2.0F, 5.5F, 3, 3, 2);
    this.body2.setRotationPoint(0.0F, 20.0F, -4.0F);
    this.body2.setTextureSize(64, 32);
    this.body2.mirror = true;
    setRotation(this.body2, -0.0743572F, 0.0F, 0.0F);
    this.tail = new ModelRenderer(this, 0, 17);
    this.tail.addBox(-1.0F, -1.0F, -0.2F, 2, 4, 4);
    this.tail.setRotationPoint(0.0F, 20.0F, 3.0F);
    this.tail.setTextureSize(64, 32);
    this.tail.mirror = true;
    setRotation(this.tail, -0.4461433F, 0.0F, -0.5948578F);
    this.head = new ModelRenderer(this, 25, 0);
    this.head.addBox(-1.5F, -1.5F, -3.5F, 3, 3, 4);
    this.head.setRotationPoint(0.0F, 21.0F, -4.0F);
    this.head.setTextureSize(64, 32);
    this.head.mirror = true;
    setRotation(this.head, 0.2974289F, 0.0F, 0.0F);
    this.head2 = new ModelRenderer(this, 25, 8);
    this.head2.addBox(-1.0F, -2.0F, -3.0F, 2, 4, 2);
    this.head2.setRotationPoint(0.0F, 21.0F, -4.0F);
    this.head2.setTextureSize(64, 32);
    this.head2.mirror = true;
    setRotation(this.head2, -0.7063936F, 0.0F, 0.0F);
    this.eyeleft = new ModelRenderer(this, 34, 8);
    this.eyeleft.addBox(1.3F, -0.5F, -2.0F, 1, 1, 1);
    this.eyeleft.setRotationPoint(0.0F, 21.0F, -4.0F);
    this.eyeleft.setTextureSize(64, 32);
    this.eyeleft.mirror = true;
    setRotation(this.eyeleft, 0.1115358F, 0.2230717F, -0.0743572F);
    this.eyeright = new ModelRenderer(this, 34, 11);
    this.eyeright.addBox(-2.3F, -0.5F, -2.0F, 1, 1, 1);
    this.eyeright.setRotationPoint(0.0F, 21.0F, -4.0F);
    this.eyeright.setTextureSize(64, 32);
    this.eyeright.mirror = true;
    setRotation(this.eyeright, 0.1115358F, -0.2230705F, 0.074351F);
    this.legfrontleft1 = new ModelRenderer(this, 45, 0);
    this.legfrontleft1.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1);
    this.legfrontleft1.setRotationPoint(2.0F, 21.0F, -3.5F);
    this.legfrontleft1.setTextureSize(64, 32);
    this.legfrontleft1.mirror = true;
    setRotation(this.legfrontleft1, 1.115358F, 0.2974367F, 0.1115513F);
    this.legfrontright1 = new ModelRenderer(this, 50, 0);
    this.legfrontright1.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1);
    this.legfrontright1.setRotationPoint(-2.0F, 21.0F, -3.5F);
    this.legfrontright1.setTextureSize(64, 32);
    this.legfrontright1.mirror = true;
    setRotation(this.legfrontright1, 1.115358F, -0.297439F, -0.111544F);
    this.legfrontleft2 = new ModelRenderer(this, 45, 4);
    this.legfrontleft2.addBox(-0.5F, -0.5F, 2.0F, 1, 3, 1);
    this.legfrontleft2.setRotationPoint(2.0F, 21.0F, -3.5F);
    this.legfrontleft2.setTextureSize(64, 32);
    this.legfrontleft2.mirror = true;
    setRotation(this.legfrontleft2, -0.4635966F, 0.297439F, 0.111544F);
    this.legfrontright2 = new ModelRenderer(this, 50, 4);
    this.legfrontright2.addBox(-0.5F, -0.5F, 2.0F, 1, 3, 1);
    this.legfrontright2.setRotationPoint(-2.0F, 21.0F, -3.5F);
    this.legfrontright2.setTextureSize(64, 32);
    this.legfrontright2.mirror = true;
    setRotation(this.legfrontright2, -0.4635966F, -0.297439F, -0.111544F);
    this.legbackleft1 = new ModelRenderer(this, 45, 9);
    this.legbackleft1.addBox(-0.7F, 0.0F, -0.5F, 1, 2, 1);
    this.legbackleft1.setRotationPoint(1.0F, 21.0F, 3.0F);
    this.legbackleft1.setTextureSize(64, 32);
    this.legbackleft1.mirror = true;
    setRotation(this.legbackleft1, -1.152532F, -0.8179311F, -0.1487144F);
    this.legbackright1 = new ModelRenderer(this, 50, 9);
    this.legbackright1.addBox(-0.3F, 0.0F, -0.5F, 1, 2, 1);
    this.legbackright1.setRotationPoint(-1.0F, 21.0F, 3.0F);
    this.legbackright1.setTextureSize(64, 32);
    this.legbackright1.mirror = true;
    setRotation(this.legbackright1, -1.152528F, 0.8179334F, 0.1487195F);
    this.legbackleft2 = new ModelRenderer(this, 45, 13);
    this.legbackleft2.addBox(-0.7F, -0.5F, -3.0F, 1, 3, 1);
    this.legbackleft2.setRotationPoint(1.0F, 21.0F, 3.0F);
    this.legbackleft2.setTextureSize(64, 32);
    this.legbackleft2.mirror = true;
    setRotation(this.legbackleft2, 0.426418F, -0.8179311F, -0.1487195F);
    this.legbackright2 = new ModelRenderer(this, 50, 13);
    this.legbackright2.addBox(-0.3F, -0.5F, -3.0F, 1, 3, 1);
    this.legbackright2.setRotationPoint(-1.0F, 21.0F, 3.0F);
    this.legbackright2.setTextureSize(64, 32);
    this.legbackright2.mirror = true;
    setRotation(this.legbackright2, 0.426418F, 0.8179311F, 0.1487195F);
    this.tongue1 = new ModelRenderer(this, 25, 15);
    this.tongue1.addBox(-0.5F, 0.5F, -7.4F, 1, 0, 4);
    this.tongue1.setRotationPoint(0.0F, 21.0F, -4.0F);
    this.tongue1.setTextureSize(64, 32);
    this.tongue1.mirror = true;
    setRotation(this.tongue1, 0.2974216F, 0.0F, 0.0F);
    this.tongue2 = new ModelRenderer(this, 25, 15);
    this.tongue2.addBox(-0.5F, 0.0F, -8.4F, 1, 1, 1);
    this.tongue2.setRotationPoint(0.0F, 21.0F, -4.0F);
    this.tongue2.setTextureSize(64, 32);
    this.tongue2.mirror = true;
    setRotation(this.tongue2, 0.2974216F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.body1.render(f5);
    this.body2.render(f5);
    this.tail.render(f5);
    this.head.render(f5);
    this.head2.render(f5);
    this.eyeleft.render(f5);
    this.eyeright.render(f5);
    this.legfrontleft1.render(f5);
    this.legfrontright1.render(f5);
    this.legfrontleft2.render(f5);
    this.legfrontright2.render(f5);
    this.legbackleft1.render(f5);
    this.legbackright1.render(f5);
    this.legbackleft2.render(f5);
    this.legbackright2.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.head.rotateAngleX = f4 / 57.295776F + 0.2974289F;
    this.head.rotateAngleY = f3 / 57.295776F;
    this.head2.rotateAngleX = f4 / 57.295776F - 0.7063936F;
    this.head2.rotateAngleY = f3 / 57.295776F;
    this.eyeleft.rotateAngleX = f4 / 57.295776F + 0.1396263F;
    this.eyeleft.rotateAngleY = f3 / 57.295776F;
    this.eyeright.rotateAngleX = f4 / 57.295776F + 0.1396263F;
    this.eyeright.rotateAngleY = f3 / 57.295776F;
    this.tongue1.rotateAngleX = f4 / 57.295776F + 0.2974216F;
    this.tongue1.rotateAngleY = f3 / 57.295776F;
    this.tongue2.rotateAngleX = f4 / 57.295776F + 0.2974216F;
    this.tongue2.rotateAngleY = f3 / 57.295776F;
    this.legfrontleft1.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1 + 1.115358F;
    this.legfrontleft2.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1 - 0.4635966F;
    this.legfrontright1.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1 + 1.115358F;
    this.legfrontright2.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1 - 0.4635966F;
    this.legbackleft1.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1 - 1.152532F;
    this.legbackleft2.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1 + 0.426418F;
    this.legbackright1.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1 - 1.152532F;
    this.legbackright2.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 3.1415927F) * 0.6F * f1 + 0.426418F;
  }
}
