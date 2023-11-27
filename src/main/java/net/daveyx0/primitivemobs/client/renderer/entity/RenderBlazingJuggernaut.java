package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.client.models.ModelBlazingJuggernaut;
import net.daveyx0.primitivemobs.entity.monster.EntityBlazingJuggernaut;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBlazingJuggernaut extends RenderLiving {
  private int field_77068_a;
  
  public RenderBlazingJuggernaut() {
    super((ModelBase)new ModelBlazingJuggernaut(), 0.5F);
    this.field_77068_a = ((ModelBlazingJuggernaut)this.mainModel).func_78104_a();
  }
  
  public void renderBlaze(EntityBlazingJuggernaut par1EntityBlaze, double par2, double par4, double par6, float par8, float par9) {
    int i = ((ModelBlazingJuggernaut)this.mainModel).func_78104_a();
    if (i != this.field_77068_a) {
      this.field_77068_a = i;
      this.mainModel = (ModelBase)new ModelBlazingJuggernaut();
    } 
    doRender((EntityLiving)par1EntityBlaze, par2, par4, par6, par8, par9);
  }
  
  private static final ResourceLocation texture = new ResourceLocation("primitivemobs:textures/entity/blazingjuggernaut/blazingjuggernaut.png");
  
  public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
    renderBlaze((EntityBlazingJuggernaut)par1Entity, par2, par4, par6, par8, par9);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return texture;
  }
}
