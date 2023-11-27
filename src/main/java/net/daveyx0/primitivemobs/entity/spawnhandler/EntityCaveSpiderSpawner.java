package net.daveyx0.primitivemobs.entity.spawnhandler;

import java.util.List;
import net.daveyx0.primitivemobs.entity.monster.EntityHangingSpider;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCaveSpiderSpawner extends EntityLiving {
  int timeTillNextCheck = 10;
  
  public EntityCaveSpiderSpawner(World world) {
    super(world);
    setCustomNameTag("Cave Spawn Handler");
    setSize(0.1F, 0.1F);
    setArea(8);
  }
  
  public boolean isAIEnabled() {
    return false;
  }
  
  public void onUpdate() {
    super.onUpdate();
    this.timeTillNextCheck--;
    if (this.timeTillNextCheck < 0) {
      spawnMobsInTheArea();
      this.timeTillNextCheck = 10;
    } 
  }
  
  protected boolean canDespawn() {
    return false;
  }
  
  public void spawnMobsInTheArea() {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(10.0D, 10.0D, 10.0D));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if (entity instanceof net.minecraft.entity.player.EntityPlayer || (entity.getUniqueID() != null && entity.getUniqueID().toString().equals(getSpawnedUUID())))
          return;  
    } 
    for (int j = 0; j < this.rand.nextInt(5) + 4; j++) {
      if (!this.worldObj.isRemote) {
        EntitySpider entityspider = new EntitySpider(this.worldObj);
        EntityCaveSpider entitycavespider = new EntityCaveSpider(this.worldObj);
        EntityMotherSpider entitymotherspider = new EntityMotherSpider(this.worldObj);
        EntityHangingSpider entityhangingspider = new EntityHangingSpider(this.worldObj);
        Entity[] entities = { (Entity)entityspider, (Entity)entitycavespider, (Entity)entityhangingspider };
        int d = this.rand.nextInt(entities.length);
        if (entities[d] != null) {
          int x = (int)this.posX + this.rand.nextInt(getArea()) - this.rand.nextInt(getArea());
          int y = (int)this.posY + 1;
          int z = (int)this.posZ + this.rand.nextInt(getArea()) - this.rand.nextInt(getArea());
          entities[d].setLocationAndAngles(x, y, z, 0.0F, 0.0F);
          setSpawnedUUID(entities[d].getUniqueID().toString());
          this.worldObj.spawnEntityInWorld(entities[d]);
        } 
      } 
    } 
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(17, "");
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    setArea(nbttagcompound.getInteger("Area"));
    String var2 = nbttagcompound.getString("Owner");
    String s = "";
    if (nbttagcompound.hasKey("OwnerUUID", 8))
      s = nbttagcompound.getString("OwnerUUID"); 
    if (s.length() > 0)
      setSpawnedUUID(s); 
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    nbttagcompound.setInteger("Area", getArea());
    if (getSpawnedUUID() == null) {
      nbttagcompound.setString("SpawnedUUID", "");
    } else {
      nbttagcompound.setString("SpawnedUUID", getSpawnedUUID());
    } 
  }
  
  public String getSpawnedUUID() {
    return this.dataWatcher.getWatchableObjectString(17);
  }
  
  public void setSpawnedUUID(String p_152115_1_) {
    this.dataWatcher.updateObject(17, p_152115_1_);
  }
  
  public int getArea() {
    return this.dataWatcher.getWatchableObjectByte(16);
  }
  
  public void setArea(int par1) {
    this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
  }
  
  public void moveEntity(double d, double d1, double d2) {}
  
  public boolean canBePushed() {
    return false;
  }
  
  public boolean isMovementCeased() {
    return true;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    return false;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  protected void updateEntityActionState() {}
  
  public boolean canBeCollidedWith() {
    return true;
  }
  
  protected String getHurtSound() {
    return null;
  }
  
  protected String getDeathSound() {
    return null;
  }
}
