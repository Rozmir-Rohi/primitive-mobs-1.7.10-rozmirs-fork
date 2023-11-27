package net.daveyx0.primitivemobs.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class EntityPrimitiveSlime extends EntityLiving implements IMob {
  public float squishAmount;
  
  public float squishFactor;
  
  public float prevSquishFactor;
  
  private int slimeJumpDelay;
  
  private static final String __OBFID = "CL_00001698";
  
  public EntityPrimitiveSlime(World p_i1742_1_) {
    super(p_i1742_1_);
    int i = 1 << this.rand.nextInt(3);
    this.yOffset = 0.0F;
    this.slimeJumpDelay = this.rand.nextInt(20) + 10;
    setSlimeSize(i);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(16, new Byte((byte)1));
  }
  
  public void setSlimeSize(int p_70799_1_) {
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
  }
  
  public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
    super.readEntityFromNBT(p_70037_1_);
    int i = p_70037_1_.getInteger("Size");
    if (i < 0)
      i = 0; 
    setSlimeSize(i + 1);
  }
  
  protected String getSlimeParticle() {
    return "slime";
  }
  
  protected String getJumpSound() {
    return "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small");
  }
  
  public void onUpdate() {
    if (this instanceof EntityTreasureSlime) {
      EntityTreasureSlime slime = (EntityTreasureSlime)this;
      if (slime.getItem() != 20 && !this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && getSlimeSize() > 0)
        this.isDead = true; 
    } 
    this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
    this.prevSquishFactor = this.squishFactor;
    boolean flag = this.onGround;
    super.onUpdate();
    if (this.onGround && !flag) {
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
  
  protected void updateEntityActionState() {
    EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity((Entity)this, 16.0D);
    if (entityplayer != null)
      faceEntity((Entity)entityplayer, 10.0F, 20.0F); 
    if (this.onGround && this.slimeJumpDelay-- <= 0) {
      this.slimeJumpDelay = getJumpDelay();
      if (entityplayer != null)
        this.slimeJumpDelay /= 3; 
      this.isJumping = true;
      if (makesSoundOnJump())
        playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F); 
      this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
      this.moveForward = (1 * getSlimeSize());
    } else {
      this.isJumping = false;
      if (this.onGround)
        this.moveStrafing = this.moveForward = 0.0F; 
    } 
  }
  
  protected void alterSquishAmount() {
    this.squishAmount *= 0.6F;
  }
  
  protected int getJumpDelay() {
    return this.rand.nextInt(20) + 10;
  }
  
  protected EntityPrimitiveSlime createInstance() {
    return new EntityPrimitiveSlime(this.worldObj);
  }
  
  public void setDead() {
    int i = getSlimeSize();
    if (!this.worldObj.isRemote && i > 1 && getHealth() <= 0.0F) {
      int j = 2 + this.rand.nextInt(3);
      for (int k = 0; k < j; k++) {
        float f = ((k % 2) - 0.5F) * i / 4.0F;
        float f1 = ((k / 2) - 0.5F) * i / 4.0F;
        EntityPrimitiveSlime entityslime = createInstance();
        entityslime.setSlimeSize(i / 2);
        entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
        this.worldObj.spawnEntityInWorld((Entity)entityslime);
      } 
    } 
    super.setDead();
  }
  
  public void onCollideWithPlayer(EntityPlayer p_70100_1_) {
    if (canDamagePlayer()) {
      int i = getSlimeSize();
      if (canEntityBeSeen((Entity)p_70100_1_) && getDistanceSqToEntity((Entity)p_70100_1_) < 0.6D * i * 0.6D * i && p_70100_1_.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), getAttackStrength()))
        playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F); 
    } 
  }
  
  protected boolean canDamagePlayer() {
    return (getSlimeSize() > 1);
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
    return (getSlimeSize() == 1) ? Items.slime_ball : Item.getItemById(0);
  }
  
  public boolean getCanSpawnHere() {
    Chunk chunk = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
    if (this.worldObj.getWorldInfo().getTerrainType().handleSlimeSpawnReduction(this.rand, this.worldObj))
      return false; 
    if (getSlimeSize() == 1 || this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
      BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
      if (biomegenbase == BiomeGenBase.swampland && this.posY > 50.0D && this.posY < 70.0D && this.rand.nextFloat() < 0.5F && this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor() && this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) <= this.rand.nextInt(8))
        return super.getCanSpawnHere(); 
      if (this.rand.nextInt(10) == 0 && chunk.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0D)
        return super.getCanSpawnHere(); 
    } 
    return false;
  }
  
  protected float getSoundVolume() {
    return 0.4F * getSlimeSize();
  }
  
  public int getVerticalFaceSpeed() {
    return 0;
  }
  
  protected boolean makesSoundOnJump() {
    return (getSlimeSize() > 0);
  }
  
  protected boolean makesSoundOnLand() {
    return (getSlimeSize() > 2);
  }
}
