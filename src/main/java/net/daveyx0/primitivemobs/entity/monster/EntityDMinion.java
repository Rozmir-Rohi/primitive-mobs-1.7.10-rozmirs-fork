package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;
import java.util.UUID;
import net.daveyx0.primitivemobs.client.particle.PrimitiveMobsParticles;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationGeneral;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDMinion extends EntityCreature {
  public int courseChangeCooldown;
  
  public double waypointX;
  
  public double waypointY;
  
  public double waypointZ;
  
  public int checkDelay;
  
  public boolean onSpawn;
  
  public EntityDMinion(World p_i1738_1_) {
    super(p_i1738_1_);
    setSize(0.5F, 0.5F);
    setFlames(0);
    this.onSpawn = false;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(18, "");
  }
  
  public boolean isAIEnabled() {
    return false;
  }
  
  protected void playStepSound(int par1, int par2, int par3, int par4) {}
  
  protected Entity findPlayerToAttack() {
    return null;
  }
  
  public void onUpdate() {
    if (getOwner() != null) {
      if (this.entityToAttack != null && !this.entityToAttack.isEntityAlive())
        this.entityToAttack = null; 
      if (getOwner() instanceof EntityCreature) {
        if (((EntityCreature)getOwner()).getEntityToAttack() != null && !(((EntityCreature)getOwner()).getEntityToAttack() instanceof EntityDMinion)) {
          this.entityToAttack = ((EntityCreature)getOwner()).getEntityToAttack();
        } else if (((EntityCreature)getOwner()).getAttackTarget() != null && !(((EntityCreature)getOwner()).getAttackTarget() instanceof EntityDMinion)) {
          this.entityToAttack = (Entity)((EntityCreature)getOwner()).getAttackTarget();
        } 
      } else if (getOwner() instanceof EntityPlayer) {
        if (getOwner().getLastAttacker() != null && !(getOwner().getLastAttacker() instanceof EntityDMinion))
          this.entityToAttack = (Entity)((EntityPlayer)getOwner()).getLastAttacker(); 
        if (getOwner().getAITarget() != null && !(getOwner().getAITarget() instanceof EntityDMinion)) {
          this.entityToAttack = (Entity)((EntityPlayer)getOwner()).getAITarget();
        } else {
          this.entityToAttack = (Entity)findAttacker(15.0D);
        } 
      } 
    } else {
      this.entityToAttack = (Entity)findAttacker(15.0D);
    } 
    if (this.entityToAttack != null && this.hasAttacked) {
      for (int j = 0; j < 12; j++)
        this.worldObj.spawnParticle("cloud", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + 1.0D + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      if (!this.worldObj.isRemote)
        setDead(); 
    } 
    float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
    float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
    this.rotationYaw += var8;
    super.onUpdate();
  }
  
  public void onLivingUpdate() {
    if (!this.onSpawn) {
      for (int j = 0; j < 12; j++)
        this.worldObj.spawnParticle("cloud", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + 1.0D + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      if (getFlames() == 2)
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D); 
      this.onSpawn = true;
    } 
    if (getOwnerUUID() == null || getOwnerUUID().equals("") || (getOwner() != null && !getOwner().isEntityAlive()))
      if (!this.worldObj.isRemote)
        setDead();  
    if (this.isDead)
      for (int j = 0; j < 12; j++)
        this.worldObj.spawnParticle("cloud", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + 1.0D + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);  
    if (getFlames() == 0)
      setFlames(this.rand.nextInt(3) + 1); 
    if (getFlames() == 1) {
      this.worldObj.spawnParticle("flame", this.posX + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), 0.0D, 0.0D, 0.0D);
    } else if (getFlames() == 2 && this.worldObj.isRemote) {
      PrimitiveMobsParticles.spawnParticle("bluefire", this.posX + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), 0.0D, 0.0D, 0.0D);
    } else if (getFlames() == 3 && this.worldObj.isRemote) {
      PrimitiveMobsParticles.spawnParticle("greenfire", this.posX + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), 0.0D, 0.0D, 0.0D);
    } else if (getFlames() == 4 && this.worldObj.isRemote) {
      PrimitiveMobsParticles.spawnParticle("yellowfire", this.posX + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), 0.0D, 0.0D, 0.0D);
    } else if (getFlames() == 5 && this.worldObj.isRemote) {
      PrimitiveMobsParticles.spawnParticle("purplefire", this.posX + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), 0.0D, 0.0D, 0.0D);
    } else if (getFlames() == 6 && this.worldObj.isRemote) {
      PrimitiveMobsParticles.spawnParticle("blackfire", this.posX + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + ((this.rand.nextFloat() - this.rand.nextFloat()) / 3.0F), 0.0D, 0.0D, 0.0D);
    } 
    super.onLivingUpdate();
  }
  
  public EntityLiving findAttacker(double d) {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(d, 4.0D, d));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if ((entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntityMob) && !(entity instanceof net.minecraft.entity.monster.EntityCreeper) && !(entity instanceof EntityBabySpider) && !(entity instanceof EntityDMinion))
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
  
  public boolean canDespawn() {
    return (getOwner() == null);
  }
  
  public boolean isOnLadder() {
    return false;
  }
  
  public boolean climbing() {
    return isOnLadder();
  }
  
  public void jump() {}
  
  protected void updateFallState(double par1, boolean par3) {}
  
  protected void updateWanderPath() {}
  
  public boolean getIsFlying() {
    return true;
  }
  
  protected void updateEntityActionState() {
    super.updateEntityActionState();
    double d = this.waypointX - this.posX;
    double d1 = this.waypointY - this.posY;
    double d2 = this.waypointZ - this.posZ;
    double d3 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
    int i = this.worldObj.getHeightValue((int)this.waypointX, (int)this.waypointZ);
    int j = MathHelper.floor_double(this.posX);
    int k = MathHelper.floor_double(this.posY);
    int l = MathHelper.floor_double(this.posZ);
    Block m = this.worldObj.getBlock(j, k - 1, l);
    if (getIsFlying() || m == Blocks.water || m == Blocks.flowing_water || m == Blocks.lava || m == Blocks.flowing_lava) {
      if ((d3 < 1.0D || d3 > 60.0D || this.waypointY > (i + 5) || (this.waypointY <= (i + 1) && this.waypointY > (i - 1))) && 
        getOwner() == null) {
        this.waypointX = this.posX + ((this.rand.nextFloat() * 2.0F - 1.0F) * 3.0F);
        this.waypointY = this.posY + ((this.rand.nextFloat() * 2.0F - 1.0F) * 3.0F);
        this.waypointZ = this.posZ + ((this.rand.nextFloat() * 2.0F - 1.0F) * 3.0F);
      } else if (this.entityToAttack != null) {
        this.waypointX = this.entityToAttack.posX;
        this.waypointY = this.entityToAttack.posY + 0.5D;
        this.waypointZ = this.entityToAttack.posZ;
      } else if (getOwner() != null) {
        this.waypointX = (getOwner()).posX + ((this.rand.nextFloat() - this.rand.nextFloat()) * 2.0F - 1.0F);
        this.waypointY = (getOwner()).posY + 2.0D;
        this.waypointZ = (getOwner()).posZ + ((this.rand.nextFloat() - this.rand.nextFloat()) * 2.0F - 1.0F);
      } 
      if (this.courseChangeCooldown-- <= 0) {
        this.courseChangeCooldown = 1;
        if (getOwner() == null || this.entityToAttack != null) {
          this.motionX += d / d3 * 0.08D;
          this.motionY += d1 / d3 * 0.08D;
          this.motionZ += d2 / d3 * 0.08D;
        } else {
          this.motionX += d / d3 * 0.1D;
          this.motionY += d1 / d3 * 0.1D;
          this.motionZ += d2 / d3 * 0.1D;
        } 
      } 
    } 
    if (this.inWater)
      this.motionY += 0.5D; 
  }
  
  public void moveEntityWithHeading(float par1, float par2) {
    if (isInWater()) {
      moveFlying(par1, par2, 0.02F);
      moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.800000011920929D;
      this.motionY *= 0.800000011920929D;
      this.motionZ *= 0.800000011920929D;
    } else if (handleLavaMovement()) {
      moveFlying(par1, par2, 0.02F);
      moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.5D;
      this.motionY *= 0.5D;
      this.motionZ *= 0.5D;
    } else {
      float f2 = 0.91F;
      if (this.onGround) {
        f2 = 0.54600006F;
        Block i = this.worldObj.getBlock(
            MathHelper.floor_double(this.posX), 
            MathHelper.floor_double(this.boundingBox.minY) - 1, 
            MathHelper.floor_double(this.posZ));
        if (i != Blocks.air)
          f2 = i.slipperiness * 0.91F; 
      } 
      float f3 = 0.16277136F / f2 * f2 * f2;
      moveFlying(par1, par2, this.onGround ? (0.1F * f3) : 0.02F);
      f2 = 0.91F;
      if (this.onGround) {
        f2 = 0.54600006F;
        Block j = this.worldObj.getBlock(
            MathHelper.floor_double(this.posX), 
            MathHelper.floor_double(this.boundingBox.minY) - 1, 
            MathHelper.floor_double(this.posZ));
        if (j != Blocks.air)
          f2 = j.slipperiness * 0.91F; 
      } 
      moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= f2;
      this.motionY *= f2;
      this.motionZ *= f2;
    } 
    this.prevLimbSwingAmount = this.limbSwingAmount;
    double d0 = this.posX - this.prevPosX;
    double d1 = this.posZ - this.prevPosZ;
    float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;
    if (f4 > 1.0F)
      f4 = 1.0F; 
    this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
    this.limbSwing += this.limbSwingAmount;
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_) {
    float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
    int i = 0;
    if (p_70652_1_ instanceof EntityLivingBase) {
      f += EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase)this, (EntityLivingBase)p_70652_1_);
      i += EnchantmentHelper.getKnockbackModifier((EntityLivingBase)this, (EntityLivingBase)p_70652_1_);
    } 
    boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), f);
    if (flag) {
      if (i > 0) {
        p_70652_1_.addVelocity((-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F), 0.1D, (MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F));
        this.motionZ *= 0.6D;
      } 
      int j = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)this);
      if (j > 0)
        p_70652_1_.setFire(j * 4); 
      if (p_70652_1_ instanceof EntityLivingBase)
        EnchantmentHelper.func_151384_a((EntityLivingBase)p_70652_1_, (Entity)this); 
      EnchantmentHelper.func_151385_b((EntityLivingBase)this, p_70652_1_);
      if (getFlames() == 1) {
        p_70652_1_.setFire(10);
      } else if (getFlames() == 3) {
        ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.poison.id, 240, 0));
      } else if (getFlames() == 2 && p_70652_1_ instanceof EntityPlayer) {
        ((EntityPlayer)p_70652_1_).dropOneItem(true);
      } else if (getFlames() == 2 && p_70652_1_ instanceof EntityLiving && PrimitiveMobsConfigurationGeneral.getCanBlueMinionSteal()) {
        if (((EntityLiving)p_70652_1_).getHeldItem() != null) {
          if (!this.worldObj.isRemote)
            ((EntityLiving)p_70652_1_).entityDropItem(((EntityLiving)p_70652_1_).getHeldItem(), 1.0F); 
          ((EntityLiving)p_70652_1_).setCurrentItemOrArmor(0, null);
        } 
      } else if (getFlames() == 4) {
        if (!p_70652_1_.isEntityAlive())
          p_70652_1_.dropItem(Items.gold_ingot, 3 + this.rand.nextInt(10)); 
      } else if (getFlames() == 5) {
        ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.weakness.id, 240, 0));
      } else if (getFlames() == 6) {
        ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.wither.id, 240, 0));
      } 
      this.hasAttacked = true;
    } 
    return flag;
  }
  
  protected void attackEntity(Entity p_70785_1_, float p_70785_2_) {
    if (this.attackTime <= 0 && p_70785_2_ < 1.5F && p_70785_1_.boundingBox.maxY > this.boundingBox.minY && p_70785_1_.boundingBox.minY < this.boundingBox.maxY) {
      this.attackTime = 20;
      attackEntityAsMob(p_70785_1_);
    } 
  }
  
  protected void fall(float f) {}
  
  protected String getLivingSound() {
    return null;
  }
  
  protected String getHurtSound() {
    return "mob.skeleton.hurt";
  }
  
  protected String getDeathSound() {
    return "mob.skeleton.death";
  }
  
  protected float getSoundVolume() {
    return 0.4F;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public EntityLivingBase findSummoner(double d) {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(d, 4.0D, d));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if (entity instanceof EntityLivingBase) {
          EntityLivingBase entitysummoner = (EntityLivingBase)entity;
          if (entitysummoner.getUniqueID().toString().equals(getOwnerUUID()))
            return entitysummoner; 
        }  
    } 
    return null;
  }
  
  public boolean interact(EntityPlayer entityplayer) {
    return super.interact(entityplayer);
  }
  
  public int getFlames() {
    return this.dataWatcher.getWatchableObjectByte(16);
  }
  
  public void setFlames(int par1) {
    this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
  }
  
  public boolean getPlayerOwned() {
    return ((this.dataWatcher.getWatchableObjectByte(17) & 0x1) != 0);
  }
  
  public void setPlayerOwned(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)0));
    } 
  }
  
  public String getOwnerUUID() {
    return this.dataWatcher.getWatchableObjectString(18);
  }
  
  public void setOwnerUUID(String p_152115_1_) {
    this.dataWatcher.updateObject(18, p_152115_1_);
  }
  
  public EntityLivingBase getOwner() {
    try {
      UUID uuid = UUID.fromString(getOwnerUUID());
      if (uuid == null)
        return null; 
      if (this.worldObj.func_152378_a(uuid) != null)
        return (EntityLivingBase)this.worldObj.func_152378_a(uuid); 
      return findSummoner(6.0D);
    } catch (IllegalArgumentException illegalargumentexception) {
      return null;
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setInteger("Flames", getFlames());
    nbttagcompound.setBoolean("Tamed", getPlayerOwned());
    if (getOwnerUUID() == null) {
      nbttagcompound.setString("OwnerUUID", "");
    } else {
      nbttagcompound.setString("OwnerUUID", getOwnerUUID());
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setFlames(nbttagcompound.getInteger("Flames"));
    setPlayerOwned(nbttagcompound.getBoolean("Tamed"));
    String var2 = nbttagcompound.getString("Owner");
    String s = "";
    if (nbttagcompound.hasKey("OwnerUUID", 8))
      s = nbttagcompound.getString("OwnerUUID"); 
    if (s.length() > 0) {
      setOwnerUUID(s);
      setPlayerOwned(true);
    } 
  }
}
