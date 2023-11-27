package net.daveyx0.primitivemobs.entity.monster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBlazingJuggernaut extends EntityMob {
  private float heightOffset = 0.5F;
  
  private int heightOffsetUpdateTime;
  
  private int attackCooldown = 0;
  
  private static final String __OBFID = "CL_00001682";
  
  public EntityBlazingJuggernaut(World p_i1731_1_) {
    super(p_i1731_1_);
    this.isImmuneToFire = true;
    this.experienceValue = 10;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(16, new Byte((byte)0));
  }
  
  protected String getLivingSound() {
    return "mob.blaze.breathe";
  }
  
  protected float getSoundPitch() {
    return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
  }
  
  protected String getHurtSound() {
    return "mob.blaze.hit";
  }
  
  protected String getDeathSound() {
    return "mob.blaze.death";
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender(float p_70070_1_) {
    return 15728880;
  }
  
  public void onDeath(DamageSource par1DamageSource) {
    if (getEntityToAttack() != null && getEntityToAttack() instanceof EntityPlayer && par1DamageSource == DamageSource.drown) {
      EntityPlayer var3 = (EntityPlayer)getEntityToAttack();
      if (var3 != null)
        var3.addStat((StatBase)PrimitiveMobsAchievementPage.blazingJugger, 1); 
    } 
    super.onDeath(par1DamageSource);
  }
  
  public float getBrightness(float p_70013_1_) {
    return 1.0F;
  }
  
  public void onLivingUpdate() {
    if (!this.worldObj.isRemote) {
      if (isWet())
        attackEntityFrom(DamageSource.drown, 1.0F); 
      this.heightOffsetUpdateTime--;
      if (this.heightOffsetUpdateTime <= 0) {
        this.heightOffsetUpdateTime = 100;
        this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
      } 
      if (getEntityToAttack() != null && (getEntityToAttack()).posY + getEntityToAttack().getEyeHeight() > this.posY + getEyeHeight() + this.heightOffset)
        this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D; 
    } 
    if (this.rand.nextInt(24) == 0)
      this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.fire", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F); 
    if (!this.onGround && this.motionY < 0.0D)
      this.motionY *= 0.6D; 
    for (int i = 0; i < 2; i++)
      this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D); 
    super.onLivingUpdate();
  }
  
  protected void attackEntity(Entity p_70785_1_, float p_70785_2_) {
    if (p_70785_2_ < 2.0F && p_70785_1_.boundingBox.maxY > this.boundingBox.minY && p_70785_1_.boundingBox.minY < this.boundingBox.maxY) {
      attackEntityAsMob(p_70785_1_);
    } else if (p_70785_2_ < 30.0F) {
      double d0 = p_70785_1_.posX - this.posX;
      double d1 = p_70785_1_.boundingBox.maxY + (p_70785_1_.height / 2.0F) - this.posY + (this.height / 1.5F);
      double d2 = p_70785_1_.posZ - this.posZ;
      this.attackCooldown++;
      if (this.attackCooldown > 10) {
        this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.5D;
        this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.5D;
        this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.5D;
        float f = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
        float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
        this.moveForward = 0.75F;
        this.rotationYaw += f1;
        this.attackCooldown = 0;
      } 
      this.hasAttacked = true;
    } 
  }
  
  protected void fall(float p_70069_1_) {}
  
  protected Item getDropItem() {
    return Items.blaze_powder;
  }
  
  public boolean isBurning() {
    return func_70845_n();
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
    if (p_70628_1_) {
      int j = this.rand.nextInt(3 + p_70628_2_);
      for (int k = 0; k < j; k++)
        dropItem(Items.blaze_powder, 1); 
    } 
  }
  
  public boolean func_70845_n() {
    return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
  }
  
  public void func_70844_e(boolean p_70844_1_) {
    byte b0 = this.dataWatcher.getWatchableObjectByte(16);
    if (p_70844_1_) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.dataWatcher.updateObject(16, Byte.valueOf(b0));
  }
  
  protected boolean isValidLightLevel() {
    return true;
  }
}
