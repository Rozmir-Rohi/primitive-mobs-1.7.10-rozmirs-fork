package net.daveyx0.primitivemobs.entity.monster;

import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityHauntedTool extends EntityMob {
  public float floatingb;
  
  public float floatingc;
  
  public float floatingd;
  
  public float floatinge;
  
  public float floatingh;
  
  public EntityHauntedTool(World world) {
    super(world);
    setSize(1.0F, 1.0F);
    this.floatingb = 0.0F;
    this.floatingc = 0.0F;
    this.floatingh = 1.0F;
    setTool(0);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackOnCollide((EntityCreature)this, EntityPlayer.class, 1.0D, false));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true));
    this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, 0, true));
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
  }
  
  public ItemStack getHeldItem() {
    switch (getTool()) {
      case 0:
        return super.getHeldItem();
      case 1:
        return new ItemStack(Items.wooden_pickaxe, 1);
      case 2:
        return new ItemStack(Items.stone_pickaxe, 1);
      case 3:
        return new ItemStack(Items.golden_pickaxe, 1);
      case 4:
        return new ItemStack(Items.iron_pickaxe, 1);
      case 5:
        return new ItemStack(Items.diamond_pickaxe, 1);
      case 6:
        return new ItemStack(Items.wooden_axe, 1);
      case 7:
        return new ItemStack(Items.stone_axe, 1);
      case 8:
        return new ItemStack(Items.golden_axe, 1);
      case 9:
        return new ItemStack(Items.iron_axe, 1);
      case 10:
        return new ItemStack(Items.diamond_axe, 1);
      case 11:
        return new ItemStack(Items.wooden_shovel, 1);
      case 12:
        return new ItemStack(Items.stone_shovel, 1);
      case 13:
        return new ItemStack(Items.golden_shovel, 1);
      case 14:
        return new ItemStack(Items.iron_shovel, 1);
      case 15:
        return new ItemStack(Items.diamond_shovel, 1);
      case 16:
        return new ItemStack(Items.wooden_sword, 1);
      case 17:
        return new ItemStack(Items.stone_sword, 1);
      case 18:
        return new ItemStack(Items.golden_sword, 1);
      case 19:
        return new ItemStack(Items.iron_sword, 1);
      case 20:
        return new ItemStack(Items.diamond_sword, 1);
      case 21:
        return new ItemStack(PrimitiveMobsItems.preciousPickaxe, 1);
    } 
    return super.getHeldItem();
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataWatcher().addObject(12, Byte.valueOf((byte)0));
    getDataWatcher().addObject(13, Byte.valueOf((byte)0));
    getDataWatcher().addObject(14, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
  }
  
  public boolean isAIEnabled() {
    return true;
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_) {
    boolean flag = super.attackEntityAsMob(p_70652_1_);
    if (flag) {
      int i = this.worldObj.difficultySetting.getDifficultyId();
      if (getHeldItem() == null && isBurning() && this.rand.nextFloat() < i * 0.3F)
        p_70652_1_.setFire(2 * i); 
    } 
    return flag;
  }
  
  public void chooseTool() {
    if (getTool() == 0 && !this.worldObj.isRemote) {
      int tool = this.rand.nextInt(4);
      int material = this.rand.nextInt(100) + 1;
      if (tool == 0) {
        if (material <= 35) {
          setTool(1);
        } else if (material <= 65) {
          setTool(2);
        } else if (material <= 85) {
          setTool(3);
        } else if (material <= 95) {
          setTool(4);
        } else if (material <= 99) {
          setTool(5);
        } else if (material <= 10) {
          setTool(21);
        } 
      } else if (tool == 1) {
        if (material <= 35) {
          setTool(6);
        } else if (material <= 65) {
          setTool(7);
        } else if (material <= 85) {
          setTool(8);
        } else if (material <= 95) {
          setTool(9);
        } else if (material < 100) {
          setTool(10);
        } 
      } else if (tool == 2) {
        if (material <= 35) {
          setTool(11);
        } else if (material <= 65) {
          setTool(12);
        } else if (material <= 85) {
          setTool(13);
        } else if (material <= 95) {
          setTool(14);
        } else if (material < 100) {
          setTool(15);
        } 
      } else if (tool == 3) {
        if (material <= 35) {
          setTool(16);
        } else if (material <= 65) {
          setTool(17);
        } else if (material <= 85) {
          setTool(18);
        } else if (material <= 95) {
          setTool(19);
        } else if (material < 100) {
          setTool(20);
        } 
      } 
      getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(getAttackStrength((Entity)this));
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getMaxEntityHealth());
    } 
  }
  
  public void onDeath(DamageSource par1DamageSource) {
    super.onDeath(par1DamageSource);
    if (par1DamageSource.getEntity() instanceof EntityPlayer) {
      EntityPlayer var3 = (EntityPlayer)par1DamageSource.getEntity();
      if (var3 != null && (getTool() == 5 || getTool() == 10 || getTool() == 15 || getTool() == 20)) {
        var3.addStat((StatBase)PrimitiveMobsAchievementPage.hauntedDiamond, 1);
      } else if (var3 != null && getTool() == 21) {
        var3.addStat((StatBase)PrimitiveMobsAchievementPage.hauntedPrecious, 1);
      } 
    } 
  }
  
  protected void playStepSound(int par1, int par2, int par3, int par4) {}
  
  public void onLivingUpdate() {
    chooseTool();
    this.floatinge = this.floatingb;
    this.floatingd = this.floatingc;
    this.floatingc = (float)(this.floatingc + 3.2D);
    if (this.floatingc < 0.0F)
      this.floatingc = 0.0F; 
    if (this.floatingc > 0.2F)
      this.floatingc = 0.2F; 
    if (this.floatingh < 0.2F)
      this.floatingh = 0.2F; 
    this.floatingh = (float)(this.floatingh * 0.9D);
    this.floatingb += this.floatingh * 2.0F;
    this.worldObj.spawnParticle("crit", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()) + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);
    super.onLivingUpdate();
  }
  
  public boolean canDespawn() {
    return true;
  }
  
  public int getMaxEntityHealth() {
    if (getTool() == 1 || getTool() == 6 || getTool() == 11 || getTool() == 16)
      return 10; 
    if (getTool() == 2 || getTool() == 7 || getTool() == 12 || getTool() == 17)
      return 20; 
    if (getTool() == 3 || getTool() == 8 || getTool() == 13 || getTool() == 18)
      return 5; 
    if (getTool() == 4 || getTool() == 9 || getTool() == 14 || getTool() == 19)
      return 30; 
    if (getTool() == 5 || getTool() == 10 || getTool() == 15 || getTool() == 20)
      return 50; 
    return 20;
  }
  
  public int getAttackStrength(Entity par1Entity) {
    if (getTool() == 1 || getTool() == 6 || getTool() == 11 || getTool() == 16)
      return 1; 
    if (getTool() == 2 || getTool() == 7 || getTool() == 12 || getTool() == 17)
      return 2; 
    if (getTool() == 3 || getTool() == 8 || getTool() == 13 || getTool() == 18)
      return 4; 
    if (getTool() == 4 || getTool() == 9 || getTool() == 14 || getTool() == 19)
      return 3; 
    if (getTool() == 5 || getTool() == 10 || getTool() == 15 || getTool() == 20)
      return 4; 
    return 2;
  }
  
  public boolean isOnLadder() {
    return this.isCollidedHorizontally;
  }
  
  public boolean climbing() {
    return isOnLadder();
  }
  
  public void jump() {}
  
  protected void fall(float f) {}
  
  protected String getLivingSound() {
    return null;
  }
  
  protected String getHurtSound() {
    return "random.break";
  }
  
  protected String getDeathSound() {
    return "random.break";
  }
  
  protected void dropFewItems(boolean par1, int par2) {
    entityDropItem(getHeldItem(), 1.0F);
  }
  
  protected float getSoundVolume() {
    return 0.4F;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public boolean interact(EntityPlayer entityplayer) {
    return super.interact(entityplayer);
  }
  
  public int getTool() {
    return this.dataWatcher.getWatchableObjectByte(15);
  }
  
  public void setTool(int par1) {
    this.dataWatcher.updateObject(15, Byte.valueOf((byte)par1));
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setInteger("Tool", getTool());
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setTool(nbttagcompound.getInteger("Tool"));
  }
}
