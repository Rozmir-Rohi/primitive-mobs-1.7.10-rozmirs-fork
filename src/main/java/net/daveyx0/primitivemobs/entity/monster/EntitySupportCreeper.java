package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySupportCreeper extends EntityPrimitiveCreeper {
  EntityMob supportMob;
  
  int followDelay;
  
  public EntitySupportCreeper(World world) {
    super(world);
    setPlayerOwned(false);
    this.supportMob = null;
    this.followDelay = 0;
    this.isImmuneToFire = true;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(22, new Integer(0));
    this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(20, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(21, "");
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (this.supportMob == null && !getPlayerOwned()) {
      this.supportMob = findMob();
    } else if (this.supportMob != null) {
      setCreeperState(0);
      this.timeSinceIgnited = 0;
      if (--this.followDelay <= 0) {
        this.followDelay = 10;
        getNavigator().tryMoveToEntityLiving((Entity)this.supportMob, 0.5D);
      } 
      if (getDistanceSqToEntity((Entity)this.supportMob) < 2.0D)
        getNavigator().clearPathEntity(); 
      if (!(this.supportMob instanceof EntityCreeper)) {
        this.supportMob.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 60, 1));
        this.supportMob.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60, 1));
        this.supportMob.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 60, 1));
        addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60, 1));
      } else if (this.supportMob instanceof EntityCreeper) {
        EntityCreeper entitycreeper = (EntityCreeper)this.supportMob;
        if (!entitycreeper.getPowered() && this.worldObj.getClosestPlayerToEntity((Entity)this, 10.0D) != null && !this.worldObj.isRemote) {
          EntityLightningBolt var6 = new EntityLightningBolt(this.worldObj, entitycreeper.posX, entitycreeper.posY, entitycreeper.posZ);
          this.worldObj.spawnEntityInWorld((Entity)var6);
        } 
        this.supportMob.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 60, 1));
      } 
      if (this.supportMob.isDead || this.supportMob.getHealth() < 0.0F)
        this.supportMob = null; 
    } 
    EntityPlayer entityplayer2 = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 5.0D);
    if (entityplayer2 != null && !getPlayerOwned())
      entityplayer2.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60, 1)); 
    EntityMob mob = findMob();
    if (mob != null && getPlayerOwned())
      mob.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60, 1)); 
    if (getPlayerOwned() && !getSitting()) {
      EntityPlayer entityplayer = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 12.0D);
      EntityPlayer entityplayer1 = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 25.0D);
      if (entityplayer != null && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
        if (--this.followDelay <= 0) {
          this.followDelay = 10;
          getNavigator().tryMoveToEntityLiving((Entity)entityplayer, 0.5D);
        } 
        if (getDistanceSqToEntity((Entity)entityplayer) < 2.0D)
          getNavigator().clearPathEntity(); 
        entityplayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 60, 1));
        entityplayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60, 1));
        entityplayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 60, 1));
        addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60, 1));
      } else if (entityplayer1 != null && entityplayer == null && !getSitting() && entityplayer1.getUniqueID().toString().equals(getOwnerUUID())) {
        setPosition(entityplayer1.posX, entityplayer1.posY, entityplayer1.posZ);
      } 
    } 
    if (getPlayerOwned()) {
      if (this.rand.nextInt(200) == 0)
        this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 1.0D, 1.0D, 1.0D); 
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
      setCreeperState(0);
    } 
    if (getPlayerOwned() && getSitting())
      getNavigator().clearPathEntity(); 
    if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && !getPlayerOwned())
      setDead(); 
    int var1 = getGrowingAge();
    if (var1 < 0) {
      var1++;
      setGrowingAge(var1);
    } else if (var1 > 0) {
      var1--;
      setGrowingAge(var1);
    } 
  }
  
  public void onDeath(DamageSource par1DamageSource) {
    super.onDeath(par1DamageSource);
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.damageType == "inWall" && getPlayerOwned())
      return false; 
    if (par1DamageSource.damageType == "fall" && getPlayerOwned())
      return false; 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  public boolean interact(EntityPlayer entityplayer) {
    ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    if (itemstack != null && itemstack.getItem() == Items.melon && !entityplayer.isSneaking()) {
      if (--itemstack.stackSize == 0)
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      setHealth(getMaxHealth());
      return true;
    } 
    if (itemstack == null && getPlayerOwned() && entityplayer.isSneaking() && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("note", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      if (!getSitting()) {
        setSitting(true);
      } else {
        setSitting(false);
      } 
    } 
    if (itemstack != null && itemstack.getItem() == Items.gunpowder && !entityplayer.isSneaking() && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      if (--itemstack.stackSize == 0)
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      setHealth(getMaxHealth());
      return true;
    } 
    return true;
  }
  
  protected Item getDropItem() {
    return Items.fire_charge;
  }
  
  public EntityMob findMob() {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(12.0D, 3.0D, 12.0D));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if (entity instanceof EntityMob && !(entity instanceof EntitySupportCreeper)) {
          EntityMob entitymob = (EntityMob)entity;
          return entitymob;
        }  
    } 
    return null;
  }
  
  public int getGrowingAge() {
    return this.dataWatcher.getWatchableObjectInt(22);
  }
  
  public void setGrowingAge(int par1) {
    this.dataWatcher.updateObject(22, Integer.valueOf(par1));
  }
  
  public boolean getSitting() {
    return ((this.dataWatcher.getWatchableObjectByte(19) & 0x1) != 0);
  }
  
  public void setSitting(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
    } 
  }
  
  public boolean getPlayerOwned() {
    return ((this.dataWatcher.getWatchableObjectByte(20) & 0x1) != 0);
  }
  
  public void setPlayerOwned(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(20, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(20, Byte.valueOf((byte)0));
    } 
  }
  
  public String getOwnerUUID() {
    return this.dataWatcher.getWatchableObjectString(21);
  }
  
  public void setOwnerUUID(String p_152115_1_) {
    this.dataWatcher.updateObject(21, p_152115_1_);
  }
  
  public EntityLivingBase getOwner() {
    try {
      UUID uuid = UUID.fromString(getOwnerUUID());
      return (uuid == null) ? null : (EntityLivingBase)this.worldObj.func_152378_a(uuid);
    } catch (IllegalArgumentException illegalargumentexception) {
      return null;
    } 
  }
  
  public boolean func_152114_e(EntityLivingBase p_152114_1_) {
    return (p_152114_1_ == getOwner());
  }
  
  public boolean canDespawn() {
    if (getPlayerOwned())
    	{return false;} 
    return true;
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    par1NBTTagCompound.setInteger("Age", getGrowingAge());
    par1NBTTagCompound.setBoolean("Tamed", getPlayerOwned());
    par1NBTTagCompound.setBoolean("Sitting", getSitting());
    if (getOwnerUUID() == null) {
      par1NBTTagCompound.setString("OwnerUUID", "");
    } else {
      par1NBTTagCompound.setString("OwnerUUID", getOwnerUUID());
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    setGrowingAge(par1NBTTagCompound.getInteger("Age"));
    setPlayerOwned(par1NBTTagCompound.getBoolean("Tamed"));
    setSitting(par1NBTTagCompound.getBoolean("Sitting"));
    String s = "";
    if (par1NBTTagCompound.hasKey("OwnerUUID", 8)) {
      s = par1NBTTagCompound.getString("OwnerUUID");
    } else {
      String s1 = par1NBTTagCompound.getString("Owner");
      s = PreYggdrasilConverter.func_152719_a(s1);
    } 
    if (s.length() > 0) {
      setOwnerUUID(s);
      setPlayerOwned(true);
    } 
  }
  
  public boolean isChild() {
    return (getGrowingAge() < 0);
  }
}
