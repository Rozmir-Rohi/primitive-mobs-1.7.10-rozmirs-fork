package net.daveyx0.primitivemobs.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityDSkeletonWarrior extends EntitySkeleton {
  private int switchCoolDown;
  
  public EntityDSkeletonWarrior(World par1World) {
    super(par1World);
    this.switchCoolDown = 0;
  }
  
  public ItemStack getBackItem() {
    ItemStack var1 = getHeldItem();
    if (var1 != null) {
      if (var1.getItem() == Items.iron_sword)
        return new ItemStack((Item)Items.bow); 
      return new ItemStack(Items.iron_sword);
    } 
    return null;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
    if (attackEntityFrom(par1DamageSource, par2)) {
      Entity var3 = par1DamageSource.getEntity();
      int i = this.rand.nextInt(2);
      if (var3 != null)
        dodgeAttack(); 
      return attackEntityFrom(par1DamageSource, par2);
    } 
    return false;
  }
  
  public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
    setCurrentItemOrArmor(0, new ItemStack((Item)Items.bow));
    setCurrentItemOrArmor(1, null);
    setCurrentItemOrArmor(2, null);
    setCurrentItemOrArmor(3, null);
    setCurrentItemOrArmor(4, new ItemStack((Item)Items.leather_helmet));
    return par1EntityLivingData;
  }
  
  public boolean canDespawn() {
    return true;
  }
  
  public void switchWeaponToBow() {
    EntityDSkeletonWarrior entity = new EntityDSkeletonWarrior(this.worldObj);
    entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
    entity.setHealth(getHealth());
    entity.setCurrentItemOrArmor(0, new ItemStack((Item)Items.bow));
    entity.setCurrentItemOrArmor(1, getEquipmentInSlot(1));
    entity.setCurrentItemOrArmor(2, getEquipmentInSlot(2));
    entity.setCurrentItemOrArmor(3, getEquipmentInSlot(3));
    entity.setCurrentItemOrArmor(4, new ItemStack((Item)Items.leather_helmet));
    if (!this.worldObj.isRemote) {
      this.worldObj.spawnEntityInWorld((Entity)entity);
      setDead();
    } 
  }
  
  public void switchWeaponToSword() {
    EntityDSkeletonWarrior entity = new EntityDSkeletonWarrior(this.worldObj);
    entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
    entity.setHealth(getHealth());
    entity.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
    entity.setCurrentItemOrArmor(1, getEquipmentInSlot(1));
    entity.setCurrentItemOrArmor(2, getEquipmentInSlot(2));
    entity.setCurrentItemOrArmor(3, getEquipmentInSlot(3));
    entity.setCurrentItemOrArmor(4, new ItemStack((Item)Items.leather_helmet));
    if (!this.worldObj.isRemote) {
      this.worldObj.spawnEntityInWorld((Entity)entity);
      setDead();
    } 
  }
  
  public void dodgeAttack() {
    EntityPlayer attacker = this.worldObj.getClosestVulnerablePlayerToEntity((Entity)this, 16.0D);
    if (attacker != null)
      if (attacker.posX > this.posX) {
        this.motionZ++;
      } else if (attacker.posX < this.posX) {
        this.motionZ--;
      } else if (attacker.posZ > this.posZ) {
        this.motionX++;
      } else if (attacker.posZ < this.posZ) {
        this.motionX--;
      }  
  }
  
  public void onUpdate() {
    EntityPlayer attacker = this.worldObj.getClosestVulnerablePlayerToEntity((Entity)this, 16.0D);
    if (attacker != null && this.switchCoolDown == 0) {
      float d = getDistanceToEntity((Entity)attacker);
      ItemStack var1 = getHeldItem();
      if (var1 != null && d <= 5.0F && var1.getItem() != Items.iron_sword) {
        this.switchCoolDown = 3000;
        switchWeaponToSword();
      } else if (var1 != null && d > 5.0F && var1.getItem() != Items.bow) {
        this.switchCoolDown = 3000;
        switchWeaponToBow();
      } else if (d > 5.0F && d < 7.0F) {
        this.motionX = attacker.posX - this.posX;
        this.motionZ = attacker.posZ - this.posZ;
        double e = -0.7D / (this.motionX * this.motionX + this.motionZ * this.motionZ + 0.0625D);
        this.motionX *= e;
        this.motionZ *= e;
      } 
    } 
    if (this.switchCoolDown != 0)
      this.switchCoolDown--; 
    super.onUpdate();
  }
}
