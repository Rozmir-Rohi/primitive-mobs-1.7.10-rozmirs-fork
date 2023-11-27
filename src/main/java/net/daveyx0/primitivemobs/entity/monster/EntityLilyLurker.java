package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationGeneral;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityLilyLurker extends EntityPrimitiveWaterMob {
  public EntityLilyLurker(World world) {
    super(world);
    setSize(1.0F, 0.5F);
    setIsAggro(false);
    setOnSpawn(false);
  }
  
  public void onUpdate() {
    if (!getOnSpawn()) {
      if (this.riddenByEntity == null)
        if (!this.worldObj.isRemote) {
          EntityLily entity = new EntityLily(this.worldObj);
          entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
          entity.setOwnerUUID(getUniqueID().toString());
          this.worldObj.spawnEntityInWorld((Entity)entity);
        }  
      if (distanceToSurface((EntityLivingBase)this, this.worldObj) > 1.0F) {
        moveEntity(0.0D, 0.1D, 0.0D);
      } else {
        setOnSpawn(true);
      } 
    } 
    if (!getIsAggro()) {
      this.rotationYaw = 0.25F;
      if (this.entityToAttack == null)
        findEntityToAwake(1.0D); 
      if (!isSwimming())
        setIsAggro(true); 
    } else {
      if (!isSwimming())
        this.motionY -= 0.05D; 
      if (getEntityToAttack() == null || !getEntityToAttack().isInWater())
        findEntityToAwake(4.0D); 
      float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
      float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
      this.rotationYaw += var8;
    } 
    super.onUpdate();
  }
  
  public void findEntityToAwake(double d) {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(d, d, d));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if (!(entity instanceof EntityLilyLurker) && !(entity instanceof EntityLily))
          if (entity instanceof EntityLivingBase && entity.isInWater()) {
            EntityLivingBase base = (EntityLivingBase)entity;
            if (!base.canBreatheUnderwater()) {
              setIsAggro(true);
              this.entityToAttack = entity;
            } 
          }   
    } 
  }
  
  public boolean func_85031_j(Entity p_85031_1_) {
    setIsAggro(true);
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
    setIsAggro(true);
    return super.attackEntityFrom(p_70097_1_, p_70097_2_);
  }
  
  public double getMountedYOffset() {
    if (!getIsAggro() && PrimitiveMobsConfigurationGeneral.getWaterLilyAdaptive())
      return this.height + 0.42D; 
    if (!getIsAggro() && !PrimitiveMobsConfigurationGeneral.getWaterLilyAdaptive())
      return this.height + 0.45D; 
    return this.height + 0.21D;
  }
  
  public AxisAlignedBB getCollisionBox(Entity p_70114_1_) {
    return p_70114_1_.boundingBox;
  }
  
  public AxisAlignedBB getBoundingBox() {
    return this.boundingBox;
  }
  
  protected void updateEntityActionState() {
    if (getIsAggro())
      super.updateEntityActionState(); 
  }
  
  protected void fall(float f) {
    if (!isSwimming() && !getIsAggro())
      super.fall(f); 
  }
  
  public void moveEntity(double d, double d1, double d2) {
    if (!getOnSpawn() || getIsAggro())
      super.moveEntity(d, d1, d2); 
  }
  
  public boolean canBePushed() {
    return false;
  }
  
  public boolean isMovementCeased() {
    if (!getIsAggro())
      return true; 
    return false;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
  }
  
  public boolean canBeCollidedWith() {
    if (!getIsAggro())
      return false; 
    return !this.isDead;
  }
  
  public boolean getIsAggro() {
    return ((this.dataWatcher.getWatchableObjectByte(15) & 0x1) != 0);
  }
  
  public void setIsAggro(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(15, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(15, Byte.valueOf((byte)0));
    } 
  }
  
  public boolean getOnSpawn() {
    return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
  }
  
  public void setOnSpawn(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setBoolean("Aggro", getIsAggro());
    nbttagcompound.setBoolean("Spawn", getOnSpawn());
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setIsAggro(nbttagcompound.getBoolean("Aggro"));
    setOnSpawn(nbttagcompound.getBoolean("Spawn"));
  }
  
  public boolean getCanSpawnHere() {
    int i = (int)this.posX;
    int j = (int)this.posY;
    int k = (int)this.posZ;
    return (this.rand.nextInt(4) == 0 && this.posY > 50.0D && this.posY < 70.0D && this.worldObj.getBlock(i, j, k).getMaterial() == Material.water);
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
    int j = p_70628_2_ + 1 + this.rand.nextInt(20);
    if (this.riddenByEntity != null)
      entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.waterlily), 1, 0), 0.0F); 
    for (int i = 0; i < j; i++) {
      int k = this.rand.nextInt(100);
      if (k <= 50)
        entityDropItem(new ItemStack(PrimitiveMobsItems.tarBall, 1), 0.0F); 
    } 
  }
}
