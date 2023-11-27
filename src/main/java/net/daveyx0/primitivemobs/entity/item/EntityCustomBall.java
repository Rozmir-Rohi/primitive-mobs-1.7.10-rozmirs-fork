package net.daveyx0.primitivemobs.entity.item;

import net.daveyx0.primitivemobs.client.particle.PrimitiveMobsParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityCustomBall extends EntityThrowable {
  protected int index = 0;
  
  public EntityCustomBall(World par1World) {
    super(par1World);
  }
  
  public EntityCustomBall(World par1World, EntityLivingBase par2EntityLivingBase) {
    super(par1World, par2EntityLivingBase);
  }
  
  public void setIndex(int dex) {
    this.index = dex;
  }
  
  public int getIndex() {
    return this.index;
  }
  
  protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
    if (par1MovingObjectPosition.entityHit != null) {
      par1MovingObjectPosition.entityHit.attackEntityFrom(
          DamageSource.causeThrownDamage((Entity)this, (Entity)getThrower()), 0.0F);
      if (par1MovingObjectPosition.entityHit instanceof EntityLivingBase)
        ((EntityLivingBase)par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 180, 1)); 
    } 
    if (this.worldObj.isRemote)
      for (int j = 0; j < 8; j++)
        PrimitiveMobsParticles.spawnParticle("tar", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);  
    if (!this.worldObj.isRemote)
      setDead(); 
  }
}
