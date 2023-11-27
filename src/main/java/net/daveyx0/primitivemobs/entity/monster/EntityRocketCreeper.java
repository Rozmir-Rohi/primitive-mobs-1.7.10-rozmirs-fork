package net.daveyx0.primitivemobs.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityRocketCreeper extends EntityCreeper {
  private int lastActiveTime;
  
  private int timeSinceIgnited;
  
  private int fuseTime = 30;
  
  private int explosionRadius = 3;
  
  public int rocketstate;
  
  public EntityRocketCreeper(World par1World) {
    super(par1World);
    this.rocketstate = 0;
  }
  
  public boolean canDespawn() {
    return true;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
  }
  
  protected void fall(float par1) {
    if (this.rocketstate != 0 && !this.worldObj.isRemote) {
      boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
      if (getPowered()) {
        this.worldObj.createExplosion((Entity)this, this.posX, this.posY, this.posZ, (this.explosionRadius * 2), var2);
        setDead();
      } else {
        this.worldObj.createExplosion((Entity)this, this.posX, this.posY, this.posZ, this.explosionRadius, var2);
        setDead();
      } 
    } else {
      super.fall(par1);
    } 
  }
  
  public void onDeath(DamageSource par1DamageSource) {
    super.onDeath(par1DamageSource);
  }
  
  public void onUpdate() {
    if (isEntityAlive()) {
      int var1 = getCreeperState();
      if (var1 > 0 && this.onGround && getAttackTarget() != null) {
        playSound("random.fuse", 1.0F, 0.5F);
        this.motionY = 1.2000000476837158D;
        this.rocketstate = 1;
        this.motionX = ((getAttackTarget()).posX - this.posX) / 10.0D;
        this.motionZ = ((getAttackTarget()).posZ - this.posZ) / 10.0D;
      } 
      this.worldObj.spawnParticle("smoke", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY - (this.rand.nextFloat() - this.rand.nextFloat()) - 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);
    } 
    super.onUpdate();
  }
  
  protected Item getDropItem() {
    return Item.getItemFromBlock(Blocks.tnt);
  }
  
  public float getCreeperFlashIntensity(float par1) {
    return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / (this.fuseTime - 2);
  }
}
