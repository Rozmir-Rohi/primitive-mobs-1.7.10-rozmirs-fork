package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;
import java.util.UUID;
import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityBabySpider extends EntityPrimitiveSpider {
  public int followDelay;
  
  private PathEntity pathToMother;
  
  public boolean attackStance;
  
  public int healCooldown;
  
  public EntityBabySpider(World world) {
    super(world);
    setSize(0.65F, 0.5F);
    this.followDelay = 0;
    setPlayerOwned(false);
    this.attackStance = false;
    setAge(0);
    setSitting(false);
    this.healCooldown = 0;
  }
  
  public boolean isOnLadder() {
    int j = MathHelper.floor_double(this.posX);
    int k = MathHelper.floor_double(this.posY);
    int l = MathHelper.floor_double(this.posZ);
    Block m = this.worldObj.getBlock(j, k - 1, l);
    Block n = this.worldObj.getBlock(j, k + 1, l);
    Block o = this.worldObj.getBlock(j - 1, k, l);
    Block p = this.worldObj.getBlock(j + 1, k, l);
    return (isBesideClimbableBlock() && m != Blocks.fence && n != Blocks.fence && o != Blocks.fence && p != Blocks.fence);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.800000011920929D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(20, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(21, "");
    this.dataWatcher.addObject(22, Byte.valueOf((byte)0));
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.damageType == "inWall" && getPlayerOwned())
      return false; 
    if (par1DamageSource.damageType == "fall" && getPlayerOwned())
      return false; 
    if (par1DamageSource.damageType == "drown" && getPlayerOwned())
      return false; 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  protected Entity findPlayerToAttack() {
    return null;
  }
  
  public int getAttackStrength(Entity par1Entity) {
    if (getAge() < 5)
      return 1; 
    return 4;
  }
  
  public EntityMotherSpider findMother(double d) {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(d, 4.0D, d));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if (entity instanceof EntityMotherSpider) {
          EntityMotherSpider entitymother = (EntityMotherSpider)entity;
          if (entitymother.getMotherID() == getBabyID())
            return entitymother; 
        }  
    } 
    return null;
  }
  
  protected void fall(float f) {}
  
  protected void attackEntity(Entity p_70785_1_, float p_70785_2_) {
    if (getPlayerOwned())
      ((EntityPlayer)getOwner()).addStat((StatBase)PrimitiveMobsAchievementPage.spiderAttack, 1); 
    super.attackEntity(p_70785_1_, p_70785_2_);
  }
  
  public EntityLiving findAttacker(double d) {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(d, 4.0D, d));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if ((entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntityMob) && !(entity instanceof net.minecraft.entity.monster.EntityCreeper) && !(entity instanceof EntityBabySpider))
          if (entity instanceof EntityTreasureSlime) {
            EntityTreasureSlime entityslime = (EntityTreasureSlime)entity;
            if (entityslime.getItem() != 20)
              return entityslime; 
          } else if (entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntityMob) {
            return (EntityLiving)entity;
          }   
    } 
    return null;
  }
  
  public void attemptRideMother() {
    EntityMotherSpider entity = findMother(1.0D);
    if (entity != null)
      if (entity.riddenByEntity == null && entity.getEntityToAttack() == null && !this.worldObj.isRemote)
        mountEntity((Entity)entity);  
  }
  
  public void jump() {
    if (this.riddenByEntity == null)
      super.jump(); 
  }
  
  public final void onUpdate() {
    int j = MathHelper.floor_double(this.posX);
    int k = MathHelper.floor_double(this.posY);
    int l = MathHelper.floor_double(this.posZ);
    Block m = this.worldObj.getBlock(j, k, l);
    if (m != null && m.getMaterial() == Material.water && !this.isCollidedHorizontally)
      this.motionY = 0.01D; 
    attemptRideMother();
    float f = 0.01745278F;
    double d = this.posX - Math.sin((this.rotationYaw * f)) / 3.0D;
    double d1 = this.posY + this.rand.nextDouble() / 3.0D;
    double d2 = this.posZ + Math.cos((this.rotationYaw * f)) / 3.0D;
    EntityMotherSpider entitymother = findMother(6.0D);
    EntityMotherSpider entitymother1 = findMother(15.0D);
    if (entitymother != null) {
      if (--this.followDelay <= 0) {
        this.followDelay = 10;
        this.pathToMother = this.worldObj.getPathEntityToEntity((Entity)this, (Entity)entitymother, 16.0F, true, false, false, true);
        setPathToEntity(this.pathToMother);
      } 
      if (getDistanceSqToEntity((Entity)entitymother) < 2.0D)
        setPathToEntity(null); 
      if (entitymother.getEntityToAttack() != null) {
        this.pathToMother = null;
        this.entityToAttack = entitymother.getEntityToAttack();
      } 
    } else if (entitymother1 != null && entitymother == null) {
      setPosition(entitymother1.posX, entitymother1.posY, entitymother1.posZ);
    } 
    if (!getPlayerOwned() && this.entityToAttack == null && entitymother == null)
      this.worldObj.spawnParticle("splash", d, d1 + 0.3D, d2, 0.0D, 0.0D, 0.0D); 
    if (this.entityToAttack != null || this.attackStance)
      this.worldObj.spawnParticle("smoke", this.posX, this.posY + this.rand.nextFloat(), this.posZ, 0.0D, 0.0D, 0.0D); 
    if (getPlayerOwned() && !getSitting()) {
      EntityPlayer entityplayer = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 12.0D);
      EntityPlayer entityplayer1 = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 25.0D);
      if (getEntityToAttack() != getOwner() || getPlayerOwned());
      if (entityplayer != null && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
        if (--this.followDelay <= 0) {
          this.followDelay = 10;
          this.pathToMother = this.worldObj.getPathEntityToEntity((Entity)this, (Entity)entityplayer, 16.0F, true, false, false, true);
          setPathToEntity(this.pathToMother);
        } 
        if (getDistanceSqToEntity((Entity)entityplayer) < 2.0D)
          setPathToEntity(null); 
        if (this.attackStance)
          if (entityplayer != null && entityplayer.getLastAttacker() != null && canEntityBeSeen((Entity)entityplayer.getLastAttacker())) {
            this.entityToAttack = (Entity)entityplayer.getLastAttacker();
          } else if (entityplayer != null && entityplayer.getAITarget() != null && canEntityBeSeen((Entity)entityplayer.getAITarget())) {
            this.entityToAttack = (Entity)entityplayer.getAITarget();
          } else {
            this.entityToAttack = (Entity)findAttacker(3.0D);
          }  
      } else if (entityplayer1 != null && entityplayer == null && !getSitting() && entityplayer1.getUniqueID().toString().equals(getOwnerUUID())) {
        setPosition(entityplayer1.posX, entityplayer1.posY, entityplayer1.posZ);
      } 
    } 
    if (getPlayerOwned()) {
      if (this.rand.nextInt(200) == 0)
        this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 1.0D, 1.0D, 1.0D); 
      if (this.healCooldown > 0) {
        this.healCooldown--;
        this.worldObj.spawnParticle("instantSpell", this.posX, this.posY + this.rand.nextFloat(), this.posZ, 0.0D, 0.0D, 0.0D);
      } 
    } 
    if (getPlayerOwned() && getSitting())
      getNavigator().clearPathEntity(); 
    if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && !getPlayerOwned())
      setDead(); 
    if (getAge() <= 5) {
      setSize(1.4F, 0.9F);
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
      getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
    } 
    super.onUpdate();
  }
  
  public boolean canDespawn() {
    if (getPlayerOwned())
      {return false;} 
    if (findMother(6.0D) != null)
      {return false;} 
    return true;
  }
  
  public boolean interact(EntityPlayer entityplayer) {
    ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    if (itemstack == null && getPlayerOwned() && getAge() < 5 && !entityplayer.isSneaking() && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      if (!this.attackStance) {
        this.attackStance = true;
      } else {
        this.attackStance = false;
        this.entityToAttack = null;
      } 
      return true;
    } 
    if (itemstack != null && getPlayerOwned() && itemstack.getItem() == Items.string && getAge() >= 5 && !entityplayer.isSneaking() && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      if (!this.attackStance) {
        this.attackStance = true;
      } else {
        this.attackStance = false;
        this.entityToAttack = null;
      } 
      return true;
    } 
    if (super.interact(entityplayer))
      return true; 
    if (itemstack == null && getPlayerOwned() && getAge() >= 5 && !this.worldObj.isRemote && !entityplayer.isSneaking() && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      entityplayer.rotationYaw = this.rotationYaw;
      entityplayer.rotationPitch = this.rotationPitch;
      entityplayer.posY = this.posY;
      entityplayer.mountEntity((Entity)this);
      setSitting(false);
      return true;
    } 
    if (itemstack != null && itemstack.getItem() == PrimitiveMobsItems.spiderEggShell && getAge() < 5 && !entityplayer.isSneaking()) {
      if (--itemstack.stackSize == 0)
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("splash", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      if (!this.worldObj.isRemote)
        dropItem((new ItemStack(PrimitiveMobsItems.customEgg, 1, 1)).getItem(), 1); 
      if (!getPlayerOwned())
        entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.spiderStealer, 1); 
      attackEntityFrom(DamageSource.causePlayerDamage(entityplayer), 1.0F);
      setDead();
      return true;
    } 
    if (itemstack != null && itemstack.getItem() == Items.fermented_spider_eye && getAge() < 5 && !entityplayer.isSneaking() && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      if (--itemstack.stackSize == 0)
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("smoke", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      setAge(getAge() + 1);
      if (getAge() >= 5) {
        entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.spiderGrown, 1);
        setHealth(30.0F);
      } 
      return true;
    } 
    if (itemstack != null && itemstack.getItem() == Items.rotten_flesh && !entityplayer.isSneaking() && getPlayerOwned() && this.healCooldown == 0) {
      if (--itemstack.stackSize == 0)
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      this.healCooldown = 300;
      setHealth(30.0F);
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
    return true;
  }
  
  public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
    if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
      this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
      this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      p_70612_1_ = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
      p_70612_2_ = ((EntityLivingBase)this.riddenByEntity).moveForward;
      if (p_70612_2_ <= 0.0F)
        p_70612_2_ *= 0.25F; 
      if (!this.worldObj.isRemote)
        setBesideClimbableBlock(this.isCollidedHorizontally); 
      this.stepHeight = 1.0F;
      this.jumpMovementFactor = getAIMoveSpeed() * 0.1F;
      if (!this.worldObj.isRemote) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
        super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
      } 
      this.prevLimbSwingAmount = this.limbSwingAmount;
      double d1 = this.posX - this.prevPosX;
      double d0 = this.posZ - this.prevPosZ;
      float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
      if (f4 > 1.0F)
        f4 = 1.0F; 
      this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
      this.limbSwing += this.limbSwingAmount;
      if (getIsJumping())
        jump(); 
      if (this.onGround)
        setIsJumping(false); 
    } else {
      this.stepHeight = 0.5F;
      this.jumpMovementFactor = 0.02F;
      super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
    } 
    super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
  }
  
  public boolean isMovementCeased() {
    return (this.riddenByEntity instanceof EntityPlayer || getSitting());
  }
  
  public double getMountedYOffset() {
    return this.height - 0.2D;
  }
  
  public boolean getSitting() {
    return ((this.dataWatcher.getWatchableObjectByte(20) & 0x1) != 0);
  }
  
  public void setSitting(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(20, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(20, Byte.valueOf((byte)0));
    } 
  }
  
  public int getBabyID() {
    return this.dataWatcher.getWatchableObjectByte(17);
  }
  
  public void setBabyID(int par1) {
    this.dataWatcher.updateObject(17, Byte.valueOf((byte)par1));
  }
  
  public boolean getPlayerOwned() {
    return ((this.dataWatcher.getWatchableObjectByte(18) & 0x1) != 0);
  }
  
  public void setPlayerOwned(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(18, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(18, Byte.valueOf((byte)0));
    } 
  }
  
  public int getAge() {
    return this.dataWatcher.getWatchableObjectByte(19);
  }
  
  public void setAge(int par1) {
    this.dataWatcher.updateObject(19, Byte.valueOf((byte)par1));
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
  
  public boolean getIsJumping() {
    return ((this.dataWatcher.getWatchableObjectByte(22) & 0x1) != 0);
  }
  
  public void setIsJumping(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(22, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(22, Byte.valueOf((byte)0));
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setBoolean("Tamed", getPlayerOwned());
    nbttagcompound.setInteger("Mother", getBabyID());
    nbttagcompound.setInteger("Age", getAge());
    nbttagcompound.setBoolean("Sitting", getSitting());
    if (getOwnerUUID() == null) {
      nbttagcompound.setString("OwnerUUID", "");
    } else {
      nbttagcompound.setString("OwnerUUID", getOwnerUUID());
    } 
    nbttagcompound.setBoolean("Jumping", getIsJumping());
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setPlayerOwned(nbttagcompound.getBoolean("Tamed"));
    setBabyID(nbttagcompound.getInteger("Mother"));
    setAge(nbttagcompound.getInteger("Age"));
    setSitting(nbttagcompound.getBoolean("Sitting"));
    setIsJumping(nbttagcompound.getBoolean("Jumping"));
    String var2 = nbttagcompound.getString("Owner");
    String s = "";
    if (nbttagcompound.hasKey("OwnerUUID", 8)) {
      s = nbttagcompound.getString("OwnerUUID");
    } else {
      String s1 = nbttagcompound.getString("Owner");
      s = PreYggdrasilConverter.func_152719_a(s1);
    } 
    if (s.length() > 0) {
      setOwnerUUID(s);
      setPlayerOwned(true);
    } 
  }
}
