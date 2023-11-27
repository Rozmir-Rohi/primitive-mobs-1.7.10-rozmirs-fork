package net.daveyx0.primitivemobs.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityHangingSpider extends EntitySpider {
  int direction;
  
  int movementCount;
  
  public EntityHangingSpider(World world) {
    super(world);
    setStringY(0);
    setIsHanging(false);
    this.direction = 1;
    this.movementCount = 0;
  }
  
  public void onUpdate() {
    if (this.ridingEntity != null || this.entityToAttack != null) {
      setIsHanging(false);
      setStringY(0);
      setMaxY(0);
    } 
    if (!getIsHanging() && this.rand.nextInt(25) == 0 && this.entityToAttack == null) {
      attemptToHang();
    } else if (getIsHanging()) {
      updateString();
      this.motionX = 0.0D;
      this.motionZ = 0.0D;
      if (getStringY() <= 0) {
        this.direction = -1;
      } else if (getStringY() >= getMaxY() - 1) {
        this.direction = 1;
      } 
      if (this.movementCount <= 0) {
        int random = this.rand.nextInt(10);
        if (random == 0) {
          this.direction = 1;
        } else if (random == 1) {
          this.direction = -1;
        } else {
          this.direction = 0;
        } 
        this.movementCount = this.rand.nextInt(100);
      } else {
        this.movementCount--;
        this.motionY = 0.1D * this.direction;
      } 
    } 
    if (getIsHanging()) {
      this.noClip = true;
      setSize(1.0F, getStringY());
    } else {
      this.noClip = false;
      setSize(1.4F, 0.9F);
    } 
    if (!this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(1.0D, 1.0D, 1.0D)).isEmpty()) {
      setIsHanging(false);
      setStringY(0);
      setMaxY(0);
    } 
    super.onUpdate();
  }
  
  public void attemptToHang() {
    for (int i = 0; i < 32; i++) {
      int locY = (int)this.posY + i;
      if (!this.worldObj.isAirBlock((int)this.posX, locY, (int)this.posZ) && this.worldObj.getBlock((int)this.posX, locY, (int)this.posZ) != Blocks.web) {
        setStringY((int)(locY - this.posY));
        setMaxY((int)(locY - this.posY));
        if (getStringY() >= 3) {
          setIsHanging(true);
          if (!this.worldObj.isRemote)
            this.worldObj.setBlock((int)this.posX, locY - 1, (int)this.posZ, Blocks.web, 0, 3); 
        } 
      } 
    } 
  }
  
  public void updateString() {
    for (int i = 0; i < 32; i++) {
      int locY = (int)this.posY + i;
      if (!this.worldObj.isAirBlock((int)this.posX, locY, (int)this.posZ) && this.worldObj.getBlock((int)this.posX, locY, (int)this.posZ) != Blocks.web)
        setStringY((int)(locY - this.posY)); 
    } 
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.800000011920929D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(17, new Byte((byte)0));
    this.dataWatcher.addObject(18, new Byte((byte)0));
    this.dataWatcher.addObject(19, new Byte((byte)0));
  }
  
  public boolean getIsHanging() {
    return ((this.dataWatcher.getWatchableObjectByte(17) & 0x1) != 0);
  }
  
  public void setIsHanging(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)0));
    } 
  }
  
  public int getStringY() {
    return this.dataWatcher.getWatchableObjectByte(18);
  }
  
  public void setStringY(int par1) {
    this.dataWatcher.updateObject(18, Byte.valueOf((byte)par1));
  }
  
  public int getMaxY() {
    return this.dataWatcher.getWatchableObjectByte(19);
  }
  
  public void setMaxY(int par1) {
    this.dataWatcher.updateObject(19, Byte.valueOf((byte)par1));
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setBoolean("Hanging", getIsHanging());
    nbttagcompound.setInteger("StringY", getStringY());
    nbttagcompound.setInteger("MaxY", getMaxY());
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setIsHanging(nbttagcompound.getBoolean("Hanging"));
    setStringY(nbttagcompound.getInteger("StringY"));
    setMaxY(nbttagcompound.getInteger("MaxY"));
  }
  
  public boolean isAIEnabled() {
    return false;
  }
  
  protected void attackEntity(Entity par1Entity, float par2) {
    if (!getIsHanging())
      super.attackEntity(par1Entity, par2); 
  }
  
  public boolean isMovementCeased() {
    return getIsHanging();
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.damageType == "inWall" || par1DamageSource.damageType == "fall")
      return false; 
    setIsHanging(false);
    return super.attackEntityFrom(par1DamageSource, par2);
  }
}
