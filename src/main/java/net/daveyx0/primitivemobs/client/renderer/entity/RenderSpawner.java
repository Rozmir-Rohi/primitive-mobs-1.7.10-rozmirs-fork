package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelEmpty;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSpawner extends RenderLiving {
  public RenderSpawner() {
    this((ModelBase)new ModelEmpty(), 0.0F);
  }
  
  public RenderSpawner(ModelBase p_i1262_1_, float p_i1262_2_) {
    super(p_i1262_1_, p_i1262_2_);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return new ResourceLocation("primitivemobs:textures/entity/festivecreeper/festivecreeper.png");
  }
}
