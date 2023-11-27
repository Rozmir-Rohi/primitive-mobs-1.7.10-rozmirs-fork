package net.daveyx0.primitivemobs.entity.monster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityFestiveCreeper extends EntityCreeper {
  private int path;
  
  private int lastActiveTime;
  
  private int timeSinceIgnited;
  
  private int fuseTime = 30;
  
  private int explosionRadius = 3;
  
  public EntityFestiveCreeper(World par1World) {
    super(par1World);
    this.isImmuneToFire = true;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.damageType == "explosion")
      return false; 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  public boolean canDespawn() {
    return true;
  }
  
  public void onDeath(DamageSource par1DamageSource) {
    if (par1DamageSource.getEntity() != null && par1DamageSource.getEntity() instanceof EntityPlayer) {
      EntityPlayer var3 = (EntityPlayer)par1DamageSource.getEntity();
      if (var3 != null)
        var3.addStat((StatBase)PrimitiveMobsAchievementPage.redCreepSurvive, 1); 
    } 
    super.onDeath(par1DamageSource);
  }
  
  protected void fall(float par1) {
    super.fall(par1);
  }
  
  public void onUpdate() {
    this.timeSinceIgnited = 0;
    if (getAttackTarget() != null)
      if (!this.worldObj.isRemote) {
        boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
        EntityPlayer entityplayer = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 10.0D);
        EntityPlayer entityplayer1 = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 8.0D);
        EntityPlayer entityplayer2 = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 4.5D);
        EntityPlayer entityplayer3 = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 4.0D);
        EntityPlayer entityplayer4 = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 6.5D);
        if (entityplayer != null) {
          if (entityplayer2 != null && this.rand.nextInt(20) == 0) {
            int i = (int)this.posX;
            int j = (int)this.posY;
            int k = (int)this.posZ;
            if ((this.worldObj.getBlock(i, j, k) == Blocks.air || this.worldObj.getBlock(i, j, k) == Blocks.snow) && this.worldObj.getBlock(i, j - 1, k) == Blocks.air)
              this.worldObj.setBlock(i, j, k, (Block)Blocks.fire, 0, 0); 
          } 
          if (entityplayer3 != null && getHealth() >= 5.0F) {
            this.motionX = entityplayer3.posX - this.posX;
            this.motionZ = entityplayer3.posZ - this.posZ;
            double d = -0.7D / (this.motionX * this.motionX + this.motionZ * this.motionZ + 0.0625D);
            this.motionX *= d;
            this.motionZ *= d;
          } else if (entityplayer1 != null || entityplayer != null) {
            this.path++;
            if (entityplayer4 != null) {
              this.motionX = 0.0D;
              this.motionZ = 0.0D;
            } 
            if (this.path == this.fuseTime && canEntityBeSeen((Entity)entityplayer) && !this.worldObj.isRemote && var2) {
              EntityTNTPrimed entity = new EntityTNTPrimed(this.worldObj);
              entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
              this.worldObj.spawnEntityInWorld((Entity)entity);
              entity.motionX = (entityplayer.posX - this.posX) / 20.0D;
              entity.motionY = (entityplayer.posY - this.posY) / 20.0D + 0.5D;
              entity.motionZ = (entityplayer.posZ - this.posZ) / 20.0D;
              entity.fuse = 55;
              this.worldObj.playSoundAtEntity((Entity)entity, "random.fuse", 1.0F, 0.5F);
              this.path = 0;
              this.fuseTime = 50;
            } else if (this.path >= this.fuseTime) {
              this.path = this.fuseTime - 1;
            } 
            this.hasAttacked = true;
          } 
        } 
      }  
    setCreeperState(0);
    super.onUpdate();
  }
  
  public String getTexture() {
    return "/mods/PrimitiveMobs/models/redcreeper.png";
  }
  
  protected Item getDropItem() {
    return Items.flint_and_steel;
  }
  
  @SideOnly(Side.CLIENT)
  public float getCreeperFlashIntensity(float par1) {
    return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / (this.fuseTime - 2);
  }
}
