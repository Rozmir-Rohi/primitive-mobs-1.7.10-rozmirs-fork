package net.daveyx0.primitivemobs.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityCustomFireFX extends EntityFX {
  private float flameScale;
  
  public EntityCustomFireFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10, int x, int y, float red, float green, float blue) {
    this(par1World, par2, par4, par6, 1.0F, par8, par9, par10, x, y, red, green, blue);
  }
  
  public EntityCustomFireFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10, float par11, int y, int x, float red, float green, float blue) {
    super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
    this.motionX = par9;
    this.motionY = par10;
    this.motionZ = par11;
    if (par9 == 0.0F)
      par9 = 1.0F; 
    float var12 = (float)Math.random() * 0.4F + 0.6F;
    this.particleTextureIndexX = x;
    this.particleTextureIndexY = y;
    double d6 = par2 + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
    d6 = par4 + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
    d6 = par6 + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
    this.flameScale = this.particleScale;
    this.particleRed = red / 255.0F;
    this.particleGreen = green / 255.0F;
    this.particleBlue = blue / 255.0F;
    this.particleScale *= 1.4F;
    this.particleScale *= par8;
    this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
    this.noClip = true;
  }
  
  @SideOnly(Side.CLIENT)
  public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
    par1Tessellator.draw();
    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png"));
    par1Tessellator.startDrawingQuads();
    par1Tessellator.setBrightness(200);
    float f6 = (this.particleAge + par2) / this.particleMaxAge;
    this.particleScale = this.flameScale * (1.0F - f6 * f6 * 0.5F);
    super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    par1Tessellator.draw();
    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png"));
    par1Tessellator.startDrawingQuads();
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender(float par1) {
    return 240;
  }
  
  public int getFXLayer() {
    return 2;
  }
  
  public void onUpdate() {
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    if (this.particleAge++ >= this.particleMaxAge)
      setDead(); 
    moveEntity(this.motionX, this.motionY, this.motionZ);
    this.motionX *= 0.9599999785423279D;
    this.motionY *= 0.9599999785423279D;
    this.motionZ *= 0.9599999785423279D;
    if (this.onGround) {
      this.motionX *= 0.699999988079071D;
      this.motionZ *= 0.699999988079071D;
    } 
  }
}
