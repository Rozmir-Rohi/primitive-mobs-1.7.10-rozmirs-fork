package net.daveyx0.primitivemobs.entity.passive;

import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationGeneral;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityLostMiner extends EntityVillager {
  int followDelay;
  
  public EntityLostMiner(World par1World) {
    super(par1World);
    setRewarded(false);
    this.followDelay = 0;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.8D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
  }
  
  public boolean interact(EntityPlayer par1EntityPlayer) {
    if (this.posY < 60.0D)
      return false; 
    if (!getRewarded()) {
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("happyVillager", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      if (!this.worldObj.isRemote)
      {  
        dropFewItems(true, 1);
        
       //this block of code replaces the lost miner with a normal villager when it rewards the player
        final EntityVillager villager = new EntityVillager(this.worldObj);    
        villager.copyLocationAndAnglesFrom(this);
        villager.worldObj.spawnEntityInWorld(villager);
        this.setDead();
        
      par1EntityPlayer.addStat((StatBase)PrimitiveMobsAchievementPage.lostMinerHelp, 1);
      setRewarded(true);
      }
    } 
    return true;
  }
  
  protected boolean canDespawn() {
    return true;
  }
  
  public String getTexture() {
    return "/mods/PrimitiveMobs/models/lostminer.png";
  }
  
  public ItemStack getHeldItem() {
    return new ItemStack(Items.iron_pickaxe, 1);
  }
  
  public boolean getRewarded() {
    return ((this.dataWatcher.getWatchableObjectByte(15) & 0x1) != 0);
  }
  
  public void setRewarded(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(15, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(15, Byte.valueOf((byte)0));
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setBoolean("Reward", getRewarded());
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setRewarded(nbttagcompound.getBoolean("Reward"));
  }
  
  protected void dropFewItems(boolean par1, int par2) {
    if (getHealth() > 0.0F) {
      int j = this.rand.nextInt(5) + 1;
      for (int i = 0; i < j; i++)
        dropItem(Items.emerald, 1); 
    } 
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    EntityPlayer entityplayer = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 8.0D);
    EntityPlayer entityplayer1 = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 2.0D);
    if (--this.followDelay <= 0 && !getRewarded() && entityplayer != null && entityplayer1 == null) {
      this.followDelay = 10;
      getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.8D);
      getNavigator().tryMoveToEntityLiving((Entity)entityplayer, 0.5D);
    } 
    if (!getRewarded() && this.posY < 60.0D) {
      float f = 0.01745278F;
      double d = this.posX - Math.sin((this.rotationYaw * f)) / 3.0D;
      double d1 = this.posY + this.rand.nextDouble() / 3.0D;
      double d2 = this.posZ + Math.cos((this.rotationYaw * f)) / 3.0D;
      this.worldObj.spawnParticle("splash", d, d1 + 1.8D, d2, 0.0D, 0.0D, 0.0D);
    } else if (getRewarded()) {
      getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    } 
  }
  
  protected boolean isValidLightLevel() {
    int var1 = MathHelper.floor_double(this.posX);
    int var2 = MathHelper.floor_double(this.boundingBox.minY);
    int var3 = MathHelper.floor_double(this.posZ);
    if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32))
      return false; 
    int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
    if (this.worldObj.isThundering()) {
      int var5 = this.worldObj.skylightSubtracted;
      this.worldObj.skylightSubtracted = 10;
      var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
      this.worldObj.skylightSubtracted = var5;
    } 
    return (var4 <= this.rand.nextInt(8));
  }
  
  public boolean getCanSpawnHere() {
    return (this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes((Entity)this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && isValidLightLevel() && this.posY < 60.0D);
  }
  
  protected String getHurtSound() {
    if (PrimitiveMobsConfigurationGeneral.getVillagerSounds())
      return "mob.villager.hit"; 
    return "game.neutral.hurt";
  }
  
  protected String getLivingSound() {
    if (PrimitiveMobsConfigurationGeneral.getVillagerSounds())
      return isTrading() ? "mob.villager.haggle" : "mob.villager.idle"; 
    return "game.neutral.hurt";
  }
  
  protected String getDeathSound() {
    if (PrimitiveMobsConfigurationGeneral.getVillagerSounds())
      return "mob.villager.death"; 
    return "game.neutral.hurt";
  }
}
