package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderChameleon extends RenderLiving {
  ModelBase chameleon;
  
  public RenderChameleon(ModelBase par1ModelBase, float par2) {
    super(par1ModelBase, par2);
    this.chameleon = par1ModelBase;
    setRenderPassModel(this.chameleon);
  }
  
  protected ResourceLocation getChameleonTextures(EntityChameleon par1EntityChameleon) {
    ResourceLocation chameleonTexture = new ResourceLocation("primitivemobs:textures/entity/chameleon/chameleon.png");
    return chameleonTexture;
  }
  
  protected ResourceLocation getEntityTexture(Entity entity) {
    return getChameleonTextures((EntityChameleon)entity);
  }
  
  protected int setAlterations(EntityChameleon chameleon, int par2, float par3) {
    GL11.glColor4f(chameleon.getR() / 255.0F, chameleon.getG() / 255.0F, chameleon.getB() / 255.0F, 1.0F);
    return 1;
  }
  
  protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
    return setAlterations((EntityChameleon)par1EntityLivingBase, par2, par3);
  }
}
