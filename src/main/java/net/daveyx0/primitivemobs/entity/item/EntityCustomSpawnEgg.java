package net.daveyx0.primitivemobs.entity.item;

import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityCustomSpawnEgg extends EntityThrowable {
  protected int index = 0;
  
  public EntityCustomSpawnEgg(World par1World) {
    super(par1World);
  }
  
  public EntityCustomSpawnEgg(World par1World, EntityLivingBase par2EntityLivingBase) {
    super(par1World, par2EntityLivingBase);
  }
  
  public void setIndex(int dex) {
    this.index = dex;
  }
  
  public int getIndex() {
    return this.index;
  }
  
  protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
    if (par1MovingObjectPosition.entityHit != null)
      par1MovingObjectPosition.entityHit.attackEntityFrom(
          DamageSource.causeThrownDamage((Entity)this, (Entity)getThrower()), 0.0F); 
    if (!this.worldObj.isRemote) {
      EntityLivingBase entityLivingBase = getThrower();
      if (getIndex() == 0) {
        EntityBabySpider babyspider = new EntityBabySpider(this.worldObj);
        if (entityLivingBase != null && entityLivingBase instanceof EntityPlayer) {
          EntityPlayer player = (EntityPlayer)entityLivingBase;
          babyspider.setPlayerOwned(true);
          babyspider.setOwnerUUID(player.getUniqueID().toString());
          player.addStat((StatBase)PrimitiveMobsAchievementPage.spiderOwner, 1);
        } 
        babyspider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        this.worldObj.spawnEntityInWorld((Entity)babyspider);
        babyspider.dropItem(PrimitiveMobsItems.spiderEggShell, 1);
      } else if (getIndex() == 1) {
        EntitySupportCreeper creeper = new EntitySupportCreeper(this.worldObj);
        if (entityLivingBase != null && entityLivingBase instanceof EntityPlayer) {
          EntityPlayer player = (EntityPlayer)entityLivingBase;
          creeper.setPlayerOwned(true);
          creeper.setHealth(30.0F);
          creeper.setOwnerUUID(player.getUniqueID().toString());
          creeper.setGrowingAge(-24000);
        } 
        creeper.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        this.worldObj.spawnEntityInWorld((Entity)creeper);
      } 
    } 
    for (int j = 0; j < 8; j++)
      this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D); 
    if (!this.worldObj.isRemote)
      setDead(); 
  }
}
