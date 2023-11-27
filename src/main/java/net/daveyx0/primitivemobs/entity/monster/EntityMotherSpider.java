package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMotherSpider extends EntitySpider {
  public EntityMotherSpider(World world) {
    super(world);
    setSize(1.4F, 0.9F);
    setBabies(false);
    setMotherID(this.rand.nextInt(100));
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
  }
  
  public int getAttackStrength(Entity par1Entity) {
    return 4;
  }
  
  public boolean canDespawn() {
    return true;
  }
  
  protected Entity findPlayerToAttack() {
    double var2 = 6.0D;
    return (Entity)this.worldObj.getClosestVulnerablePlayerToEntity((Entity)this, var2);
  }
  
  public EntityLiving findBabyAttacker(double d) {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(d, 1.0D, d));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if (entity instanceof EntityBabySpider) {
          EntityBabySpider entitybaby = (EntityBabySpider)entity;
          if (entitybaby.getBabyID() == getMotherID() && entitybaby.getEntityToAttack() != null)
            if (entitybaby.getEntityToAttack() instanceof EntityLiving)
              return (EntityLiving)entitybaby.getEntityToAttack();  
        }  
    } 
    return null;
  }
  
  public double getMountedYOffset() {
    return this.height - 0.2D;
  }
  
  protected String getHurtSound() {
    return "primitivemobs:mob.motherspider.spiderscreech";
  }
  
  protected String getDeathSound() {
    return "primitivemobs:mob.motherspider.spiderscreech";
  }
  
  protected float getSoundVolume() {
    return 0.25F;
  }
  
  public void onUpdate() {
    if (!getBabies()) {
      spawnBabies();
      setBabies(true);
    } 
    EntityLiving entity = findBabyAttacker(10.0D);
    if (entity != null)
      this.entityToAttack = (Entity)entity; 
    super.onUpdate();
  }
  
  public void spawnBabies() {
    for (int i = 0; i < 6; i++) {
      if (!this.worldObj.isRemote && this.rand.nextInt(2) == 0) {
        EntityBabySpider entity = new EntityBabySpider(this.worldObj);
        entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        entity.setBabyID(getMotherID());
        entity.setAge(0);
        this.worldObj.spawnEntityInWorld((Entity)entity);
      } 
    } 
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
    super.dropFewItems(p_70628_1_, p_70628_2_);
    if (p_70628_1_ && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0))
      dropItem(Items.spider_eye, 1); 
    if (this.rand.nextInt(4) == 0)
      entityDropItem(new ItemStack(PrimitiveMobsItems.customEgg, 1, 0), 1.0F); 
  }
  
  public boolean getBabies() {
    return ((this.dataWatcher.getWatchableObjectByte(17) & 0x1) != 0);
  }
  
  public void setBabies(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)0));
    } 
  }
  
  public int getMotherID() {
    return this.dataWatcher.getWatchableObjectByte(18);
  }
  
  public void setMotherID(int par1) {
    this.dataWatcher.updateObject(18, Byte.valueOf((byte)par1));
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setBoolean("Baby", getBabies());
    nbttagcompound.setInteger("Mother", getMotherID());
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setBabies(nbttagcompound.getBoolean("Baby"));
    setMotherID(nbttagcompound.getInteger("Mother"));
  }
}
