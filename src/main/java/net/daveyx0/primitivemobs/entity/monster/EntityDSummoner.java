package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationGeneral;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityDSummoner extends EntityMob {
  public int summonCooldown;
  
  public EntityDSummoner(World p_i1744_1_) {
    super(p_i1744_1_);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityPlayer.class, 8.0F, 0.5D, 1.0D));
    this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, 0, true));
    this.summonCooldown = 0;
    setHasMinions(false);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
  }
  
  public void onUpdate() {
    if (!getHasMinions() && (getAttackTarget() != null || getEntityToAttack() != null)) {
      if ((getAttackTarget() != null && getAttackTarget().getDistanceSqToEntity((Entity)this) < 100.0D) || (
        getEntityToAttack() != null && getEntityToAttack().getDistanceSqToEntity((Entity)this) < 100.0D)) {
        spawnMinions();
        setHasMinions(true);
      } 
    } else if (findMinion(8.0D) == null) {
      this.summonCooldown++;
      if (this.summonCooldown == 80) {
        setHasMinions(false);
        this.summonCooldown = 0;
      } 
    } 
    super.onUpdate();
  }
  
  public void spawnMinions() {
    for (int i = 0; i < this.rand.nextInt(3) + 1; i++) {
      if (!this.worldObj.isRemote) {
        EntityDMinion entity = new EntityDMinion(this.worldObj);
        entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        entity.setOwnerUUID(getUniqueID().toString());
        entity.setFlames(this.rand.nextInt(3) + 1);
        this.worldObj.spawnEntityInWorld((Entity)entity);
      } 
    } 
  }
  
  public boolean isAIEnabled() {
    return true;
  }
  
  public EntityDMinion findMinion(double d) {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(d, 4.0D, d));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if (entity instanceof EntityDMinion) {
          EntityDMinion entitymother = (EntityDMinion)entity;
          if (entitymother.getOwnerUUID().equals(getUniqueID().toString()))
            return entitymother; 
        }  
    } 
    return null;
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
    int j = p_70628_2_ + 1 + this.rand.nextInt(30);
    for (int i = 0; i < j; i++) {
      int k = this.rand.nextInt(100);
      if (k <= 1) {
        entityDropItem(new ItemStack(PrimitiveMobsItems.customEgg, 1, 1), 0.0F);
      } else if (k > 1 && k <= 50) {
        dropItem(PrimitiveMobsItems.skullMinionOrb, 1);
      } 
    } 
  }
  
  protected String getHurtSound() {
    if (PrimitiveMobsConfigurationGeneral.getVillagerSounds())
      return "mob.villager.hit"; 
    return "game.neutral.hurt";
  }
  
  protected String getDeathSound() {
    if (PrimitiveMobsConfigurationGeneral.getVillagerSounds())
      return "mob.villager.death"; 
    return "game.neutral.hurt";
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
  }
  
  public boolean getHasMinions() {
    return ((this.dataWatcher.getWatchableObjectByte(17) & 0x1) != 0);
  }
  
  public void setHasMinions(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)0));
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setBoolean("Has", getHasMinions());
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setHasMinions(nbttagcompound.getBoolean("Has"));
  }
}
