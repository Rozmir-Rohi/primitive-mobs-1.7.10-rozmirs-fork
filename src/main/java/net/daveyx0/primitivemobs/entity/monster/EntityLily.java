package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityLily extends EntityLiving {
  public EntityLily(World world) {
    super(world);
    setSize(1.0F, 0.1F);
  }
  
  public boolean isAIEnabled() {
    return false;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(18, "");
  }
  
  public void onLivingUpdate() {
    if (getOwner() != null && (getOwner()).riddenByEntity == null)
      mountEntity((Entity)getOwner()); 
    if (this.ridingEntity != null && this.ridingEntity instanceof EntityLilyLurker)
      this.rotationYaw = ((EntityLilyLurker)this.ridingEntity).rotationYaw; 
    if (getOwnerUUID() == null || getOwnerUUID().equals("") || (getOwner() != null && !getOwner().isEntityAlive()) || getOwner() == null)
      if (!this.worldObj.isRemote)
        setDead();  
    super.onLivingUpdate();
  }
  
  public EntityLilyLurker findLurker(double d) {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(d, 2.0D, d));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if (entity instanceof EntityLilyLurker) {
          EntityLilyLurker entitylurker = (EntityLilyLurker)entity;
          if (entitylurker.getUniqueID().toString().equals(getOwnerUUID()))
            return entitylurker; 
        }  
    } 
    return null;
  }
  
  public boolean canDespawn() {
    return (getOwner() == null);
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  protected void updateEntityActionState() {}
  
  public boolean interact(EntityPlayer entityplayer) {
    super.interact(entityplayer);
    ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    if (itemstack != null)
      if (itemstack.getItem() != null && itemstack.getItem() == Items.shears && !this.worldObj.isRemote) {
        dropItem((new ItemStack(Item.getItemFromBlock(Blocks.waterlily), 1, 0)).getItem(), 1);
        getOwner().attackEntityFrom(DamageSource.drown, 5.0F);
        ((EntityLilyLurker)getOwner()).setIsAggro(true);
        setDead();
      }  
    return true;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.getEntity() != null && par1DamageSource.getEntity() instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer)par1DamageSource.getEntity();
      if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears && !this.worldObj.isRemote) {
        dropItem((new ItemStack(Item.getItemFromBlock(Blocks.waterlily), 1, 0)).getItem(), 1);
        this.ridingEntity.attackEntityFrom(DamageSource.drown, 5.0F);
        ((EntityLilyLurker)this.ridingEntity).setIsAggro(true);
        setDead();
      } 
    } 
    return false;
  }
  
  public void onUpdate() {
    despawnEntity();
    super.onUpdate();
  }
  
  public void moveEntity(double d, double d1, double d2) {}
  
  public AxisAlignedBB getCollisionBox(Entity p_70114_1_) {
    return p_70114_1_.boundingBox;
  }
  
  public AxisAlignedBB getBoundingBox() {
    return this.boundingBox;
  }
  
  public boolean canBePushed() {
    return false;
  }
  
  public boolean isMovementCeased() {
    return true;
  }
  
  public boolean canBeCollidedWith() {
    return !this.isDead;
  }
  
  protected String getHurtSound() {
    return null;
  }
  
  protected String getDeathSound() {
    return null;
  }
  
  protected void dropFewItems(boolean par1, int par2) {
    dropItem(Item.getItemFromBlock(Blocks.waterlily), 1);
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
      return (EntityLivingBase)findLurker(4.0D);
    } catch (IllegalArgumentException illegalargumentexception) {
      return null;
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    if (getOwnerUUID() == null) {
      nbttagcompound.setString("OwnerUUID", "");
    } else {
      nbttagcompound.setString("OwnerUUID", getOwnerUUID());
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    String var2 = nbttagcompound.getString("Owner");
    String s = "";
    if (nbttagcompound.hasKey("OwnerUUID", 8))
      s = nbttagcompound.getString("OwnerUUID"); 
    if (s.length() > 0)
      setOwnerUUID(s); 
  }
}
