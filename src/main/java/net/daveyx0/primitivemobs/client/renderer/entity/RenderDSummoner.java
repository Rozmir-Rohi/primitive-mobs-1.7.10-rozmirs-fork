package net.daveyx0.primitivemobs.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.monster.EntityDSummoner;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDSummoner extends RenderLiving {
  protected ModelVillager DSummonerModel;
  
  private static final ResourceLocation texture = new ResourceLocation("primitivemobs:textures/entity/summoner/summoner.png");
  
  private static ResourceLocation itemtexture;
  
  public RenderDSummoner() {
    super((ModelBase)new ModelVillager(0.0F), 0.5F);
    this.DSummonerModel = (ModelVillager)this.mainModel;
  }
  
  protected int shouldDSummonerRenderPass(EntityDSummoner par1EntityDSummoner, int par2, float par3) {
    return -1;
  }
  
  public void renderDSummoner(EntityDSummoner par1EntityDSummoner, double par2, double par4, double par6, float par8, float par9) {
    doRender((EntityLiving)par1EntityDSummoner, par2, par4, par6, par8, par9);
  }
  
  protected void renderDSummonerEquipedItems(EntityDSummoner par1EntityDSummoner, float par2) {
    renderEquippedItems((EntityLivingBase)par1EntityDSummoner, par2);
  }
  
  protected void preRenderDSummoner(EntityDSummoner par1EntityDSummoner, float par2) {
    float var3 = 0.9375F;
    GL11.glScalef(var3, var3, var3);
  }
  
  protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {
    preRenderDSummoner((EntityDSummoner)par1EntityLiving, par2);
  }
  
  protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3) {
    return shouldDSummonerRenderPass((EntityDSummoner)par1EntityLiving, par2, par3);
  }
  
  public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
    renderDSummoner((EntityDSummoner)par1Entity, par2, par4, par6, par8, par9);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
    return texture;
  }
}
