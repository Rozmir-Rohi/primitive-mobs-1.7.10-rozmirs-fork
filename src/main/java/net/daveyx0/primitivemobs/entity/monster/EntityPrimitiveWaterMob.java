package net.daveyx0.primitivemobs.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityPrimitiveWaterMob extends EntityCreature {
  public int courseChangeCooldown;
  
  public double waypointX;
  
  public double waypointY;
  
  public double waypointZ;
  
  public boolean onSpawn;
  
  public int outOfWater;
  
  public EntityPrimitiveWaterMob(World p_i1738_1_) {
    super(p_i1738_1_);
    this.onSpawn = false;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
  }
  
  public boolean isAIEnabled() {
    return false;
  }
  
  protected void playStepSound(int par1, int par2, int par3, int par4) {}
  
  public void onLivingUpdate() {
    setAir(0);
    if (isSwimming()) {
      this.outOfWater = 0;
    } else {
      this.outOfWater++;
      if (this.outOfWater > 10)
        setPathToEntity(null); 
      if (this.outOfWater > 200 && this.outOfWater % 30 == 0) {
        this.motionY += 0.3D;
        this.motionX = (float)(Math.random() * 0.2D - 0.1D);
        this.motionZ = (float)(Math.random() * 0.2D - 0.1D);
        attackEntityFrom(DamageSource.drown, 1.0F);
      } 
    } 
    super.onLivingUpdate();
  }
  
  public boolean canDespawn() {
    return true;
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
  
  public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
    if (isEntityInvulnerable())
      return false; 
    if (super.attackEntityFrom(p_70097_1_, p_70097_2_)) {
      Entity entity = p_70097_1_.getEntity();
      if (this.riddenByEntity != entity && this.ridingEntity != entity) {
        if (entity != this)
          this.entityToAttack = entity; 
        return true;
      } 
      return true;
    } 
    return false;
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
    if (isSwimming() && (d3 < 1.0D || d3 > 60.0D || this.waypointY > (i + 5) || (this.waypointY <= (i + 1) && this.waypointY > (i - 1))) && this.entityToAttack == null) {
      this.waypointX = this.posX + ((this.rand.nextFloat() * 2.0F - 1.0F) * this.rand.nextInt(6));
      this.waypointY = this.posY + ((this.rand.nextFloat() * 2.0F - 1.0F) * this.rand.nextInt(6));
      this.waypointZ = this.posZ + ((this.rand.nextFloat() * 2.0F - 1.0F) * this.rand.nextInt(6));
    } else if (this.entityToAttack != null) {
      this.waypointX = this.entityToAttack.posX;
      this.waypointY = this.entityToAttack.posY;
      this.waypointZ = this.entityToAttack.posZ;
    } 
    if (this.courseChangeCooldown-- <= 0) {
      this.courseChangeCooldown = 1;
      if (m != null && this.worldObj.getBlock((int)this.waypointX, (int)this.waypointY, (int)this.waypointZ).getMaterial() == Material.water && 
        isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
        if (this.entityToAttack != null) {
          this.motionX += d / d3 * 0.025D;
          this.motionY += d1 / d3 * 0.025D;
          this.motionZ += d2 / d3 * 0.025D;
        } else {
          this.motionX += d / d3 * 0.025D;
          this.motionY += d1 / d3 * 0.025D;
          this.motionZ += d2 / d3 * 0.025D;
        } 
      } else {
        this.waypointX = this.posX;
        this.waypointY = this.posY;
        this.waypointZ = this.posZ;
      } 
    } 
  }
  
  public void moveEntityWithHeading(float par1, float par2) {
    if (handleLavaMovement()) {
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
      this.hasAttacked = true;
    } 
    return flag;
  }
  
  protected void attackEntity(Entity p_70785_1_, float p_70785_2_) {
    if (this.attackTime <= 0 && p_70785_2_ < 2.0F && p_70785_1_.boundingBox.maxY > this.boundingBox.minY && p_70785_1_.boundingBox.minY < this.boundingBox.maxY) {
      this.attackTime = 20;
      attackEntityAsMob(p_70785_1_);
    } 
  }
  
  public static float distanceToSurface(EntityLivingBase entityLivingBase, World world) {
    int i = MathHelper.floor_double(entityLivingBase.posX);
    int j = MathHelper.floor_double(entityLivingBase.posY);
    int k = MathHelper.floor_double(entityLivingBase.posZ);
    Block l = world.getBlock(i, j, k);
    if (l != null && l.getMaterial() == Material.water)
      for (int j1 = 1; j1 < 64; j1++) {
        Block i1 = world.getBlock(i, j + j1, k);
        if (i1 == Blocks.air || i1.getMaterial() != Material.water)
          return j1; 
      }  
    return 0.0F;
  }
  
  protected void fall(float f) {
    if (!isSwimming())
      super.fall(f); 
  }
  
  public boolean isSwimming() {
    return isInsideOfMaterial(Material.water);
  }
  
  protected String getLivingSound() {
    return null;
  }
  
  protected float getSoundVolume() {
    return 0.4F;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public boolean handleWaterMovement() {
    return false;
  }
  
  public boolean interact(EntityPlayer entityplayer) {
    return super.interact(entityplayer);
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
  }
  
  private boolean isCourseTraversable(double d, double d1, double d2, double d3) {
    double d4 = (this.waypointX - this.posX) / d3;
    double d5 = (this.waypointY - this.posY) / d3;
    double d6 = (this.waypointZ - this.posZ) / d3;
    AxisAlignedBB axisalignedbb = this.boundingBox.copy();
    for (int i = 1; i < d3; i++) {
      axisalignedbb.offset(d4, d5, d6);
      if (this.worldObj.func_147461_a(axisalignedbb).size() > 0)
        return false; 
    } 
    return true;
  }
}
