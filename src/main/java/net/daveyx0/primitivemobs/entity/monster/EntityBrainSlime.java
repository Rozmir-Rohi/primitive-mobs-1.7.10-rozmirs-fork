package net.daveyx0.primitivemobs.entity.monster;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityBrainSlime extends EntityCreature implements IMob {
  public float suckingb;
  
  public float suckingc;
  
  public float suckingd;
  
  public float suckinge;
  
  public float suckingh;
  
  public float squishAmount;
  
  public float squishFactor;
  
  public float prevSquishFactor;
  
  public float field_768_a;
  
  public float field_767_b;
  
  public int slimeJumpDelay;
  
  public int drainage;
  
  public boolean dismount;
  
  public boolean dropyou;
  
  public EntityBrainSlime(World world) {
    super(world);
    int i = 1 << this.rand.nextInt(2);
    this.yOffset = 0.0F;
    this.drainage = 0;
    this.suckingb = 0.0F;
    this.suckingc = 0.0F;
    this.suckingh = 1.0F;
    this.slimeJumpDelay = this.rand.nextInt(20) + 10;
    setSlimeSize(i);
    setHealth(getMaxHealth());
  }
  
  public boolean canTriggerWalking() {
    return false;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(16, new Byte((byte)1));
  }
  
  protected void setSlimeSize(int p_70799_1_) {
    this.dataWatcher.updateObject(16, new Byte((byte)p_70799_1_));
    setSize(0.6F * p_70799_1_, 0.6F * p_70799_1_);
    setPosition(this.posX, this.posY, this.posZ);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((p_70799_1_ * p_70799_1_));
    setHealth(getMaxHealth());
    this.experienceValue = p_70799_1_;
  }
  
  public int getSlimeSize() {
    return this.dataWatcher.getWatchableObjectByte(16);
  }
  
  public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
    super.writeEntityToNBT(p_70014_1_);
    p_70014_1_.setInteger("Size", getSlimeSize() - 1);
    p_70014_1_.setByte("Drainage", (byte)this.drainage);
  }
  
  public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
    super.readEntityFromNBT(p_70037_1_);
    int i = p_70037_1_.getInteger("Size");
    if (i < 0)
      i = 0; 
    setSlimeSize(i + 1);
    this.drainage = p_70037_1_.getByte("Drainage") & 0xFF;
  }
  
  public boolean canDespawn() {
    return true;
  }
  
  public double getYOffset() {
    if (this.ridingEntity instanceof EntityPlayer)
      return (this.yOffset - 1.2F); 
    return this.yOffset;
  }
  
  public boolean attackEntityFrom(DamageSource damagesource, float i) {
    Entity entity = damagesource.getEntity();
    if (entity == this.ridingEntity && !(entity instanceof EntityPlayer) && this.rand.nextInt(64) != 0)
      return false; 
    boolean flag = super.attackEntityFrom(damagesource, i);
    if (this.ridingEntity != null && entity == this.ridingEntity)
      for (int j = 0; entity.riddenByEntity instanceof EntityBrainSlime && j < 21; j++) {
        if (!this.worldObj.isRemote) {
          entity = entity.riddenByEntity;
          entity.motionX *= 1.5D;
          entity.motionY += 0.25D;
          entity.motionZ *= 1.5D;
          mountEntity(entity);
          this.suckingb = 0.0F;
          this.suckingc = 0.0F;
          this.suckingh = 1.0F;
        } 
      }  
    if (entity instanceof EntityLiving) {
      this.entityToAttack = entity;
    } else if (entity instanceof EntityArrow) {
      entity = (EntityArrow)entity;
      this.setTarget(((EntityCreature)entity).getAITarget());
    } 
    return flag;
  }
  
  public void setTarget(Entity entity) {}
  
  public boolean interact(EntityPlayer entityplayer) {
    this.rotationYaw = entityplayer.rotationYaw;
    if (!this.worldObj.isRemote) {
      mountEntity((Entity)entityplayer);
      this.worldObj.playSoundAtEntity((Entity)this, "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small"), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      this.motionX = entityplayer.motionX * 5.0D;
      this.motionY = entityplayer.motionY / 2.0D + 0.15000000596046448D;
      this.motionZ = entityplayer.motionZ * 5.0D;
      this.dropyou = true;
      return true;
    } 
    return false;
  }
  
  public void onUpdate() {
    Entity entity = this.ridingEntity;
    if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && getSlimeSize() > 0)
      this.isDead = true; 
    this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
    this.prevSquishFactor = this.squishFactor;
    boolean flag = this.onGround;
    super.onUpdate();
    if (this.onGround && !flag) {
      this.dropyou = false;
      EntityBrainSlime pinkslime = this;
      for (int k = 0; pinkslime.riddenByEntity instanceof EntityBrainSlime && k < 3; k++) {
        if (!this.worldObj.isRemote) {
          pinkslime = (EntityBrainSlime)pinkslime.riddenByEntity;
          pinkslime.motionX *= 1.5D;
          pinkslime.motionY = 0.0D;
          pinkslime.motionZ *= 1.5D;
          pinkslime.dropyou = true;
          pinkslime.mountEntity(entity);
          this.ridingEntity.riddenByEntity = null;
          this.ridingEntity = null;
        } 
      } 
      int i = getSlimeSize();
      for (int j = 0; j < i * 8; j++) {
        float f = this.rand.nextFloat() * 3.1415927F * 2.0F;
        float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
        float f2 = MathHelper.sin(f) * i * 0.5F * f1;
        float f3 = MathHelper.cos(f) * i * 0.5F * f1;
        this.worldObj.spawnParticle(getSlimeParticle(), this.posX + f2, this.boundingBox.minY, this.posZ + f3, 0.0D, 0.0D, 0.0D);
      } 
      if (makesSoundOnLand())
        playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F); 
      this.squishAmount = -0.5F;
    } else if (!this.onGround && flag) {
      this.squishAmount = 1.0F;
    } 
    alterSquishAmount();
    if (this.worldObj.isRemote) {
      int i = getSlimeSize();
      setSize(0.6F * i, 0.6F * i);
    } 
  }
  
  protected void alterSquishAmount() {
    this.squishAmount *= 0.6F;
  }
  
  public void onLivingUpdate() {
    this.suckinge = this.suckingb;
    this.suckingd = this.suckingc;
    if (this.ridingEntity != null) {
      this.suckingc = (float)(this.suckingc + 3.2D);
      if (this.suckingc < 0.0F)
        this.suckingc = 0.0F; 
      if (this.suckingc > 0.2F)
        this.suckingc = 0.2F; 
      if (this.suckingh < 0.2F)
        this.suckingh = 0.2F; 
      this.suckingh = (float)(this.suckingh * 0.9D);
      this.suckingb += this.suckingh * 2.0F;
    } 
    super.onLivingUpdate();
  }
  
  protected String getSlimeParticle() {
    return "portal";
  }
  
  protected void updateEntityActionState() {
    if (this.ridingEntity != null && !(this.ridingEntity instanceof EntityBrainSlime)) {
      this.drainage++;
      if (this.drainage >= 15) {
        this.worldObj.playSoundAtEntity((Entity)this, "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small"), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        for (int i = 0; i < 8; i++)
          this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
        this.ridingEntity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), getSlimeSize());
        this.drainage = 0;
      } 
      super.updateEntityActionState();
      return;
    } 
    this.drainage = 0;
    Object obj = null;
    if (this.entityToAttack instanceof EntityBrainSlime)
      this.entityToAttack = null; 
    if (this.entityToAttack == null) {
      obj = this.worldObj.getClosestVulnerablePlayerToEntity((Entity)this, 16.0D);
      this.entityToAttack = (Entity)obj;
    } else {
      obj = this.entityToAttack;
    } 
    if (obj != null)
      faceEntity((Entity)obj, 10.0F, 10.0F); 
    if (this.onGround && this.slimeJumpDelay-- <= 0) {
      boolean flag = false;
      if (obj != null && canEntityBeSeen((Entity)obj)) {
        if (this.slimeJumpDelay == -1)
          this.worldObj.playSoundAtEntity((Entity)this, "primitivemobs:mob.brainslime.slimecharge", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F); 
        this.field_768_a -= 0.25F * (getSlimeSize() + 1) / 3.0F;
        this.field_767_b -= 0.25F * (getSlimeSize() + 1) / 3.0F;
        if (this.slimeJumpDelay > -15)
          return; 
        flag = true;
        EntityLivingBase entityliving = (EntityLivingBase)this.entityToAttack;
        if (entityliving.getHealth() <= 0.0F || entityliving.isDead)
          this.entityToAttack = null; 
      } 
      if (obj == null)
        this.rotationYaw = 360.0F * this.rand.nextFloat(); 
      this.slimeJumpDelay = this.rand.nextInt(20) + 10;
      if (obj != null)
        this.slimeJumpDelay /= 3; 
      if (obj != null && getDistanceToEntity((Entity)obj) > 16.0D) {
        obj = null;
        this.entityToAttack = null;
      } 
      this.isJumping = true;
      if (makesSoundOnJump())
        playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F); 
      if (getSlimeSize() > 1)
        this.worldObj.playSoundAtEntity((Entity)this, "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small"), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F); 
      if (flag && (EntityLivingBase)obj != null) {
        this.motionX += (((EntityLivingBase)obj).posX - this.posX) / 8.0D;
        this.motionY += (((EntityLivingBase)obj).posY - this.posY) / 8.0D;
        if (this.motionY <= 0.0D)
          this.motionY = 0.0D; 
        this.motionY += 0.5D;
        this.motionZ += (((EntityLivingBase)obj).posZ - this.posZ) / 8.0D;
      } 
      this.field_768_a = 1.0F;
      this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
      this.moveForward = (1 * getSlimeSize());
    } else {
      this.isJumping = false;
      if (this.onGround)
        this.moveStrafing = this.moveForward = 0.0F; 
    } 
  }
  
  public void setDead() {
    int i = getSlimeSize();
    if (this.ridingEntity != null && !this.worldObj.isRemote)
      mountEntity(null); 
    if (!this.worldObj.isRemote && i > 1 && getHealth() <= 0.0F) {
      int j = 2 + this.rand.nextInt(3);
      for (int k = 0; k < j; k++) {
        float f = ((k % 2) - 0.5F) * i / 4.0F;
        float f1 = ((k / 2) - 0.5F) * i / 4.0F;
        EntityBrainSlime entityslime = createInstance();
        entityslime.setSlimeSize(i / 2);
        entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
        this.worldObj.spawnEntityInWorld((Entity)entityslime);
      } 
    } 
    super.setDead();
  }
  
  protected EntityBrainSlime createInstance() {
    return new EntityBrainSlime(this.worldObj);
  }
  
  public void applyEntityCollision(Entity entity) {
    if (getHealth() > 0.0F && this.ridingEntity == null && entity == this.entityToAttack && !this.dropyou && canEntityBeSeen(entity) && getDistanceToEntity(entity) < 0.6D * (getSlimeSize() + 1)) {
      int i;
      for (i = 0; entity.riddenByEntity != null && i < 11; i++)
        entity = entity.riddenByEntity; 
      if (i < 11 && !this.worldObj.isRemote)
        if (entity instanceof EntityPlayer || entity instanceof net.minecraft.entity.monster.EntityZombie || entity instanceof net.minecraft.entity.monster.EntitySkeleton || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntityCreeper || entity instanceof net.minecraft.entity.monster.EntityWitch || entity instanceof net.minecraft.entity.monster.EntitySpider) {
          this.rotationYaw = entity.rotationYaw;
          mountEntity(entity);
          this.worldObj.playSoundAtEntity((Entity)this, "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small"), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        }  
    } 
    super.applyEntityCollision(entity);
  }
  
  protected int getAttackStrength() {
    return getSlimeSize();
  }
  
  protected String getHurtSound() {
    return "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small");
  }
  
  protected String getDeathSound() {
    return "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small");
  }
  
  protected Item getDropItem() {
    return (getSlimeSize() == 1) ? PrimitiveMobsItems.pinkSlimeBall : Item.getItemById(0);
  }
  
  public boolean getCanSpawnHere() {
    for (; this.posY > 72.0D; this.posY -= 16.0D);
    for (; this.posY < 64.0D; this.posY += 16.0D);
    int i = (int)this.posX;
    int j = (int)this.posY;
    int k = (int)this.posZ;
    return (this.posY > 61.0D && this.posY < 66.0D && this.worldObj.getBlock(i, j, k) == Blocks.air && (this.worldObj.getBlock(i, j - 1, k) == Blocks.sand || this.worldObj.getBlock(i, j - 1, k) == Blocks.gravel));
  }
  
  protected float getSoundVolume() {
    return 0.4F * getSlimeSize();
  }
  
  public int getVerticalFaceSpeed() {
    return 0;
  }
  
  protected String getJumpSound() {
    return "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small");
  }
  
  protected boolean makesSoundOnJump() {
    return (getSlimeSize() > 0);
  }
  
  protected boolean makesSoundOnLand() {
    return (getSlimeSize() > 2);
  }
}
