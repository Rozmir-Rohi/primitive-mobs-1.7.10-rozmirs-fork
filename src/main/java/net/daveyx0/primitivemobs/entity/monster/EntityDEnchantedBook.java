package net.daveyx0.primitivemobs.entity.monster;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityDEnchantedBook extends EntityMob {
  public float floatingb;
  
  public float floatingc;
  
  public float floatingd;
  
  public float floatinge;
  
  public float floatingh;
  
  Enchantment theEnchantment;
  
  public EntityDEnchantedBook(World world) {
    super(world);
    setSize(1.0F, 1.0F);
    this.floatingb = 0.0F;
    this.floatingc = 0.0F;
    this.floatingh = 1.0F;
    setEnchantment(0);
    setLevel(0);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackOnCollide((EntityCreature)this, EntityPlayer.class, 1.0D, false));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true));
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
  }
  
  protected void playStepSound(int par1, int par2, int par3, int par4) {}
  
  public void chooseBook() {
    if (getEnchantment() == 0) {
      int i = this.rand.nextInt(52);
      if (((i >= 0 && i <= 7) || (i >= 16 && i <= 21) || (i >= 32 && i <= 35) || (i >= 48 && i <= 51)) && Enchantment.enchantmentsList[i] != null)
        setEnchantment(i); 
    } else {
      this.theEnchantment = Enchantment.enchantmentsList[getEnchantment()];
    } 
    if (getLevel() == 0 && this.theEnchantment != null) {
      int j = this.rand.nextInt(100);
      if (j <= 40) {
        setLevel(1);
      } else if (j <= 70 && this.theEnchantment.getMaxLevel() > 1) {
        setLevel(2);
      } else if (j <= 85 && this.theEnchantment.getMaxLevel() > 2) {
        setLevel(3);
      } else if (j <= 95 && this.theEnchantment.getMaxLevel() > 3) {
        setLevel(4);
      } else if (j <= 100 && this.theEnchantment.getMaxLevel() > 4) {
        setLevel(5);
      } 
    } 
  }
  
  public boolean isAIEnabled() {
    return true;
  }
  
  protected Entity findPlayerToAttack() {
    return null;
  }
  
  public void onLivingUpdate() {
    chooseBook();
    this.floatinge = this.floatingb;
    this.floatingd = this.floatingc;
    this.floatingc = (float)(this.floatingc + 3.2D);
    if (this.floatingc < 0.0F)
      this.floatingc = 0.0F; 
    if (this.floatingc > 0.2F)
      this.floatingc = 0.2F; 
    if (this.floatingh < 0.2F)
      this.floatingh = 0.2F; 
    this.floatingh = (float)(this.floatingh * 0.9D);
    this.floatingb += this.floatingh * 2.0F;
    this.worldObj.spawnParticle("enchantmenttable", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()) + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);
    super.onLivingUpdate();
  }
  
  public boolean canDespawn() {
    return true;
  }
  
  public boolean isOnLadder() {
    return this.isCollidedHorizontally;
  }
  
  public boolean climbing() {
    return isOnLadder();
  }
  
  public void jump() {}
  
  protected void updateEntityActionState() {
    super.updateEntityActionState();
  }
  
  protected void fall(float f) {}
  
  protected String getLivingSound() {
    return null;
  }
  
  protected String getHurtSound() {
    return "step.cloth";
  }
  
  protected String getDeathSound() {
    return "step.cloth";
  }
  
  protected void dropFewItems(boolean par1, int par2) {
    EntityItem drop = entityDropItem(new ItemStack((Item)Items.enchanted_book), 1.0F);
    ItemStack stack = drop.getEntityItem();
    if (stack != null && stack.getItem() == Items.enchanted_book && this.theEnchantment != null && getLevel() != 0) {
      ItemEnchantedBook book = (ItemEnchantedBook)stack.getItem();
      book.addEnchantment(stack, new EnchantmentData(this.theEnchantment, getLevel()));
      stack = new ItemStack((Item)book, 1);
    } 
  }
  
  protected float getSoundVolume() {
    return 0.4F;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public boolean interact(EntityPlayer entityplayer) {
    return super.interact(entityplayer);
  }
  
  public int getEnchantment() {
    return this.dataWatcher.getWatchableObjectByte(15);
  }
  
  public void setEnchantment(int par1) {
    this.dataWatcher.updateObject(15, Byte.valueOf((byte)par1));
  }
  
  public int getLevel() {
    return this.dataWatcher.getWatchableObjectByte(16);
  }
  
  public void setLevel(int par1) {
    this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setInteger("Enchantment", getEnchantment());
    nbttagcompound.setInteger("Level", getLevel());
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setEnchantment(nbttagcompound.getInteger("Enchantment"));
    setLevel(nbttagcompound.getInteger("Level"));
  }
  
  protected boolean isValidLightLevel() {
    int var1 = MathHelper.floor_double(this.posX);
    int var2 = MathHelper.floor_double(this.boundingBox.minY);
    int var3 = MathHelper.floor_double(this.posZ);
    if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32))
      return false; 
    int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
    if (this.worldObj.isThundering()) {
      int var5 = this.worldObj.skylightSubtracted;
      this.worldObj.skylightSubtracted = 10;
      var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
      this.worldObj.skylightSubtracted = var5;
    } 
    return (var4 <= this.rand.nextInt(8));
  }
  
  public boolean getCanSpawnHere() {
    return (this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes((Entity)this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && isValidLightLevel() && this.posY < 40.0D);
  }
}
