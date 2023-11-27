package net.daveyx0.primitivemobs.entity.monster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.entity.ai.EntityAIPrimitiveCreeperSwell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityPrimitiveCreeper extends EntityPrimitiveMob {
  private int lastActiveTime;
  
  public int timeSinceIgnited;
  
  private int fuseTime = 30;
  
  private int explosionRadius = 3;
  
  private static final String __OBFID = "CL_00001684";
  
  public EntityPrimitiveCreeper(World p_i1733_1_) {
    super(p_i1733_1_);
    this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIPrimitiveCreeperSwell(this));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide(this, 1.0D, false));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander(this, 0.8D));
    this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
    this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget(this, false));
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
  }
  
  public boolean isAIEnabled() {
    return true;
  }
  
  public int getMaxSafePointTries() {
    return (getAttackTarget() == null) ? 3 : (3 + (int)(getHealth() - 1.0F));
  }
  
  protected void fall(float p_70069_1_) {
    super.fall(p_70069_1_);
    this.timeSinceIgnited = (int)(this.timeSinceIgnited + p_70069_1_ * 1.5F);
    if (this.timeSinceIgnited > this.fuseTime - 5)
      this.timeSinceIgnited = this.fuseTime - 5; 
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(16, Byte.valueOf((byte)-1));
    this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
  }
  
  public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
    super.writeEntityToNBT(p_70014_1_);
    if (this.dataWatcher.getWatchableObjectByte(17) == 1)
      p_70014_1_.setBoolean("powered", true); 
    p_70014_1_.setShort("Fuse", (short)this.fuseTime);
    p_70014_1_.setByte("ExplosionRadius", (byte)this.explosionRadius);
    p_70014_1_.setBoolean("ignited", func_146078_ca());
  }
  
  public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
    super.readEntityFromNBT(p_70037_1_);
    this.dataWatcher.updateObject(17, Byte.valueOf((byte)(p_70037_1_.getBoolean("powered") ? 1 : 0)));
    if (p_70037_1_.hasKey("Fuse", 99))
      this.fuseTime = p_70037_1_.getShort("Fuse"); 
    if (p_70037_1_.hasKey("ExplosionRadius", 99))
      this.explosionRadius = p_70037_1_.getByte("ExplosionRadius"); 
    if (p_70037_1_.getBoolean("ignited"))
      func_146079_cb(); 
  }
  
  public void onUpdate() {
    if (isEntityAlive()) {
      this.lastActiveTime = this.timeSinceIgnited;
      if (func_146078_ca())
        setCreeperState(1); 
      int i = getCreeperState();
      if (i > 0 && this.timeSinceIgnited == 0)
        playSound("creeper.primed", 1.0F, 0.5F); 
      this.timeSinceIgnited += i;
      if (this.timeSinceIgnited < 0)
        this.timeSinceIgnited = 0; 
      if (this.timeSinceIgnited >= this.fuseTime) {
        this.timeSinceIgnited = this.fuseTime;
        func_146077_cc();
      } 
    } 
    super.onUpdate();
  }
  
  protected String getHurtSound() {
    return "mob.creeper.say";
  }
  
  protected String getDeathSound() {
    return "mob.creeper.death";
  }
  
  public void onDeath(DamageSource p_70645_1_) {
    super.onDeath(p_70645_1_);
    if (p_70645_1_.getEntity() instanceof net.minecraft.entity.monster.EntitySkeleton) {
      int i = Item.getIdFromItem(Items.record_13);
      int j = Item.getIdFromItem(Items.record_wait);
      int k = i + this.rand.nextInt(j - i + 1);
      dropItem(Item.getItemById(k), 1);
    } 
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_) {
    return true;
  }
  
  public boolean getPowered() {
    return (this.dataWatcher.getWatchableObjectByte(17) == 1);
  }
  
  @SideOnly(Side.CLIENT)
  public float getCreeperFlashIntensity(float p_70831_1_) {
    return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
  }
  
  protected Item getDropItem() {
    return Items.gunpowder;
  }
  
  public int getCreeperState() {
    return this.dataWatcher.getWatchableObjectByte(16);
  }
  
  public void setCreeperState(int p_70829_1_) {
    this.dataWatcher.updateObject(16, Byte.valueOf((byte)p_70829_1_));
  }
  
  public void onStruckByLightning(EntityLightningBolt p_70077_1_) {
    super.onStruckByLightning(p_70077_1_);
    this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
  }
  
  protected boolean interact(EntityPlayer p_70085_1_) {
    ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
    if (itemstack != null && itemstack.getItem() == Items.flint_and_steel) {
      this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
      p_70085_1_.swingItem();
      if (!this.worldObj.isRemote) {
        func_146079_cb();
        itemstack.damageItem(1, (EntityLivingBase)p_70085_1_);
        return true;
      } 
    } 
    return super.interact(p_70085_1_);
  }
  
  private void func_146077_cc() {
    if (!this.worldObj.isRemote) {
      boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
      if (getPowered()) {
        this.worldObj.createExplosion((Entity)this, this.posX, this.posY, this.posZ, (this.explosionRadius * 2), flag);
      } else {
        this.worldObj.createExplosion((Entity)this, this.posX, this.posY, this.posZ, this.explosionRadius, flag);
      } 
      setDead();
    } 
  }
  
  public boolean func_146078_ca() {
    return (this.dataWatcher.getWatchableObjectByte(18) != 0);
  }
  
  public void func_146079_cb() {
    this.dataWatcher.updateObject(18, Byte.valueOf((byte)1));
  }
}
