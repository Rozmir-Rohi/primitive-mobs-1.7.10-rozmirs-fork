package net.daveyx0.primitivemobs.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityPeacefulTNTPrimed extends Entity {
  public int fuse;
  
  private EntityLivingBase tntPlacedBy;
  
  private static final String __OBFID = "CL_00001681";
  
  public EntityPeacefulTNTPrimed(World p_i1729_1_) {
    super(p_i1729_1_);
    this.preventEntitySpawning = true;
    setSize(0.98F, 0.98F);
    this.yOffset = this.height / 2.0F;
  }
  
  public EntityPeacefulTNTPrimed(World p_i1730_1_, double p_i1730_2_, double p_i1730_4_, double p_i1730_6_, EntityLivingBase p_i1730_8_) {
    this(p_i1730_1_);
    setPosition(p_i1730_2_, p_i1730_4_, p_i1730_6_);
    float f = (float)(Math.random() * Math.PI * 2.0D);
    this.motionX = (-((float)Math.sin(f)) * 0.02F);
    this.motionY = 0.20000000298023224D;
    this.motionZ = (-((float)Math.cos(f)) * 0.02F);
    this.fuse = 80;
    this.prevPosX = p_i1730_2_;
    this.prevPosY = p_i1730_4_;
    this.prevPosZ = p_i1730_6_;
    this.tntPlacedBy = p_i1730_8_;
  }
  
  protected void entityInit() {}
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return !this.isDead;
  }
  
  public void onUpdate() {
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    this.motionY -= 0.03999999910593033D;
    moveEntity(this.motionX, this.motionY, this.motionZ);
    this.motionX *= 0.9800000190734863D;
    this.motionY *= 0.9800000190734863D;
    this.motionZ *= 0.9800000190734863D;
    if (this.onGround) {
      this.motionX *= 0.699999988079071D;
      this.motionZ *= 0.699999988079071D;
      this.motionY *= -0.5D;
    } 
    if (this.fuse-- <= 0) {
      setDead();
      if (!this.worldObj.isRemote)
        explode(); 
    } else {
      this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
    } 
  }
  
  private void explode() {
    boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
    float f = 4.0F;
    this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, flag);
  }
  
  protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
    p_70014_1_.setByte("Fuse", (byte)this.fuse);
  }
  
  protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
    this.fuse = p_70037_1_.getByte("Fuse");
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.0F;
  }
  
  public EntityLivingBase getTntPlacedBy() {
    return this.tntPlacedBy;
  }
}
