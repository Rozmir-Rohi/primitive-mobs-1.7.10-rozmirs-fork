package net.daveyx0.primitivemobs.entity.monster;

import java.util.ArrayList;
import java.util.Random;
import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.lib.CustomChestGenHooks;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class EntityDMimic extends EntityMob {
  ArrayList<WeightedRandomChestContent> contents = new ArrayList<WeightedRandomChestContent>();
  
  public float nommingb;
  
  public float nommingc;
  
  public float nommingd;
  
  public float nomminge;
  
  public float nommingh;
  
  public float rotation;
  
  public TileEntityChest var12;
  
  int explosionTimer = 0;
  
  public EntityDMimic(World world) {
    super(world);
    setSize(0.9F, 0.9F);
    this.nommingb = 0.0F;
    this.nommingc = 0.0F;
    this.nommingh = 1.0F;
    this.rotation = 0.0F;
    setMimic(0);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackOnCollide((EntityCreature)this, EntityPlayer.class, 1.0D, false));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true));
    this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, 0, true));
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
  }
  
  public boolean isAIEnabled() {
    if (getMimic() == 0)
      return false; 
    return true;
  }
  
  public boolean canDespawn() {
    return true;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  protected void despawnEntity() {
    EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity((Entity)this, -1.0D);
    if (var1 != null) {
      double var2 = var1.posX - this.posX;
      double var4 = var1.posY - this.posY;
      double var6 = var1.posZ - this.posZ;
      double var8 = var2 * var2 + var4 * var4 + var6 * var6;
      if (canDespawn() && var8 > 16384.0D)
        setDead(); 
      if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && var8 > 1024.0D && canDespawn()) {
        setDead();
      } else if (var8 < 1024.0D) {
        this.entityAge = 0;
      } 
    } 
  }
  
  public void onLivingUpdate() {
    if (getMimic() == 1 || getMimic() == 7) {
      if (this.onGround && (this.motionX > 0.05D || this.motionZ > 0.05D || this.motionX < -0.05D || this.motionZ < -0.05D))
        this.motionY = 0.4D; 
      if (this.isCollidedHorizontally)
        this.motionY = 0.5D; 
    } else {
      this.rotationYaw = 0.0F;
    } 
    if (getMimic() == 5)
      if (this.ridingEntity != null) {
        this.prevRotationYaw = this.rotationYaw = this.ridingEntity.rotationYaw;
        this.rotationPitch = this.ridingEntity.rotationPitch * 0.5F;
        setRotation(this.rotationYaw, this.rotationPitch);
      }  
    this.nomminge = this.nommingb;
    this.nommingd = this.nommingc;
    if (getMimic() == 1 || getMimic() == 7) {
      this.nommingc = (float)(this.nommingc + 3.2D);
      if (this.nommingc < 0.0F)
        this.nommingc = 0.0F; 
      if (this.nommingc > 0.2F)
        this.nommingc = 0.2F; 
      if (this.nommingh < 0.2F)
        this.nommingh = 0.2F; 
      this.nommingh = (float)(this.nommingh * 0.9D);
      this.nommingb += this.nommingh * 2.0F;
      this.rotation = -0.7853982F;
      if (this.rand.nextInt(80) == 0) {
        float f = 0.01745278F;
        double d = this.posX - Math.sin((this.rotationYaw * f)) / 3.0D;
        double d1 = this.posY + this.rand.nextDouble() / 3.0D;
        double d2 = this.posZ + Math.cos((this.rotationYaw * f)) / 3.0D;
        for (int j = 0; j < 12; j++)
          this.worldObj.spawnParticle("splash", d, d1 + 0.3D, d2, 0.0D, 0.0D, 0.0D); 
      } 
      if (getMimic() == 7) {
        this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + 1.0D + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
      } 
    } else if (getMimic() == 2) {
      this.nommingc = (float)(this.nommingc - 0.9D);
      if (this.nommingc < -8.0F) {
        for (int j = 0; j < 12; j++)
          this.worldObj.spawnParticle("cloud", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + 1.0D + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
        if (!this.worldObj.isRemote) {
          EntityDSkeletonWarrior entity = new EntityDSkeletonWarrior(this.worldObj);
          entity.setLocationAndAngles(this.posX, this.posY + 1.0D, this.posZ, this.rotationYaw, 0.0F);
          entity.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
          entity.setCurrentItemOrArmor(1, getEquipmentInSlot(1));
          entity.setCurrentItemOrArmor(2, getEquipmentInSlot(2));
          entity.setCurrentItemOrArmor(3, getEquipmentInSlot(3));
          entity.setCurrentItemOrArmor(4, new ItemStack((Item)Items.leather_helmet));
          this.worldObj.spawnEntityInWorld((Entity)entity);
          CustomChestGenHooks info = CustomChestGenHooks.getInfo("mimicChest");
          generateStructureChestContents(this.worldObj, this.worldObj.rand, 0, 0, 1, info.getItems(this.worldObj.rand), info.getCount(this.worldObj.rand));
          setDead();
        } 
      } 
    } else if (getMimic() == 3) {
      this.nommingc = (float)(this.nommingc - 0.9D);
      if (this.nommingc < -8.0F) {
        for (int j = 0; j < 12; j++)
          this.worldObj.spawnParticle("cloud", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + 1.0D + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
        if (!this.worldObj.isRemote) {
          EntityHauntedTool entity = new EntityHauntedTool(this.worldObj);
          entity.setLocationAndAngles(this.posX, this.posY + 1.0D, this.posZ, this.rotationYaw, 0.0F);
          this.worldObj.spawnEntityInWorld((Entity)entity);
          CustomChestGenHooks info = CustomChestGenHooks.getInfo("mimicChestTool");
          generateStructureChestContents(this.worldObj, this.worldObj.rand, 0, 0, 1, info.getItems(this.worldObj.rand), info.getCount(this.worldObj.rand));
          setDead();
        } 
      } 
    } else if (getMimic() == 4) {
      this.nommingc = (float)(this.nommingc - 0.9D);
      if (this.nommingc < -8.0F) {
        for (int j = 0; j < 12; j++)
          this.worldObj.spawnParticle("cloud", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + 1.0D + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
        if (!this.worldObj.isRemote) {
          int k = this.rand.nextInt(5) + 1;
          for (int i = 0; i < k; i++) {
            EntityBat entity = new EntityBat(this.worldObj);
            entity.setLocationAndAngles(this.posX, this.posY + 1.0D, this.posZ, this.rotationYaw, 0.0F);
            this.worldObj.spawnEntityInWorld((Entity)entity);
          } 
          CustomChestGenHooks info = CustomChestGenHooks.getInfo("mimicChestBat");
          generateStructureChestContents(this.worldObj, this.worldObj.rand, 0, 0, 1, info.getItems(this.worldObj.rand), info.getCount(this.worldObj.rand));
          setDead();
        } 
      } 
    } else if (getMimic() == 5) {
      for (int j = 0; j < 12; j++)
        this.worldObj.spawnParticle("cloud", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + 1.0D + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      if (!this.worldObj.isRemote) {
        EntityZombie entity = new EntityZombie(this.worldObj);
        entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        entity.setCurrentItemOrArmor(4, new ItemStack(Item.getItemFromBlock((Block)Blocks.chest)));
        this.worldObj.spawnEntityInWorld((Entity)entity);
        mountEntity((Entity)entity);
        setDead();
      } 
    } else if (getMimic() == 6) {
      this.worldObj.spawnParticle("smoke", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + 1.0D + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);
      if (this.explosionTimer == 0) {
        playSound("creeper.primed", 1.0F, 0.5F);
      } else if (this.explosionTimer >= 30 && !this.worldObj.isRemote) {
        this.worldObj.createExplosion((Entity)this, this.posX, this.posY, this.posZ, 2.0F, true);
        setDead();
      } 
      this.explosionTimer++;
    } 
    despawnEntity();
    super.onLivingUpdate();
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_) {
    if (getMimic() == 0)
      return false; 
    return super.attackEntityAsMob(p_70652_1_);
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (getMimic() == 0)
      return false; 
    if (par1DamageSource.damageType == "inWall" || par1DamageSource.damageType == "fall")
      return false; 
    if (this.ridingEntity != null && this.ridingEntity instanceof EntityZombie)
      return false; 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  public void moveEntity(double d, double d1, double d2) {
    if (getMimic() == 1 || getMimic() == 7)
      super.moveEntity(d, d1, d2); 
  }
  
  protected void attackEntity(Entity par1Entity, float par2) {
    if (getMimic() == 1 || getMimic() == 7)
      super.attackEntity(par1Entity, par2); 
  }
  
  protected void updateEntityActionState() {
    if (getMimic() == 1 || getMimic() == 7)
      super.updateEntityActionState(); 
  }
  
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
    return (getMimic() == 0);
  }
  
  public boolean interact(EntityPlayer entityplayer) {
    int j = MathHelper.floor_double(this.posX);
    int k = MathHelper.floor_double(this.posY);
    int l = MathHelper.floor_double(this.posZ);
    boolean nextToChest = (this.worldObj.getBlock(j, k, l) == Blocks.chest);
    nextToChest = (this.worldObj.getBlock(j, k, l + 1) == Blocks.chest);
    nextToChest = (this.worldObj.getBlock(j, k, l - 1) == Blocks.chest);
    nextToChest = (this.worldObj.getBlock(j + 1, k, l) == Blocks.chest);
    nextToChest = (this.worldObj.getBlock(j - 1, k, l) == Blocks.chest);
    int i = this.rand.nextInt(100);
    if (!this.worldObj.isRemote && getMimic() == 0) {
      this.worldObj.playSoundAtEntity((Entity)this, "random.chestopen", getSoundVolume(), 1.0F);
      if (i <= 2) {
        ChestGenHooks info = ChestGenHooks.getInfo("mineshaftCorridor");
        generateStructureChestContents(this.worldObj, this.worldObj.rand, 0, 0, 1, info.getItems(this.worldObj.rand), info.getCount(this.worldObj.rand));
        entityplayer.displayGUIChest((IInventory)this.var12);
        entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.mimicDungeon, 1);
        setDead();
      } else if (i > 2 && i <= 7 && !nextToChest) {
        entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.mimicFake, 1);
        setMimic(2);
      } else if (i > 7 && i <= 15 && !nextToChest) {
        entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.mimicFake, 1);
        setMimic(3);
      } else if (i > 15 && i <= 25 && !nextToChest) {
        entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.mimicFake, 1);
        setMimic(4);
      } else if (i > 25 && i <= 45) {
        entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.mimicFake, 1);
        setMimic(5);
      } else if (i > 45 && i <= 46) {
        setMimic(7);
      } else if (i > 46 && i <= 55) {
        entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.mimicFake, 1);
        setMimic(6);
      } else {
        entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.mimicFake, 1);
        setMimic(1);
      } 
    } 
    return super.interact(entityplayer);
  }
  
  protected boolean generateStructureChestContents(World par1World, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, int par8) {
    int var9 = MathHelper.floor_double(this.posX);
    int var10 = MathHelper.floor_double(this.posY);
    int var11 = MathHelper.floor_double(this.posZ);
    if (par1World.getBlock(var9, var10, var11) != Blocks.chest) {
      par1World.setBlock(var9, var10, var11, (Block)Blocks.chest, 0, 0);
      this.var12 = (TileEntityChest)par1World.getTileEntity(var9, var10, var11);
      if (this.var12 != null)
        WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, (IInventory)this.var12, par8); 
      return true;
    } 
    return false;
  }
  
  public WeightedRandomChestContent[] getItems(Random rnd) {
    ArrayList<WeightedRandomChestContent> ret = new ArrayList<WeightedRandomChestContent>();
    for (WeightedRandomChestContent orig : this.contents) {
      Item item = orig.theItemId.getItem();
      if (item != null) {
        ChestGenHooks info = ChestGenHooks.getInfo("mineshaftCorridor");
        WeightedRandomChestContent n = item.getChestGenBase(info, rnd, orig);
        if (n != null)
          ret.add(n); 
      } 
    } 
    return ret.<WeightedRandomChestContent>toArray(new WeightedRandomChestContent[ret.size()]);
  }
  
  public boolean canBeCollidedWith() {
    return !this.isDead;
  }
  
  public int getMimic() {
    return this.dataWatcher.getWatchableObjectByte(15);
  }
  
  public void setMimic(int par1) {
    this.dataWatcher.updateObject(15, Byte.valueOf((byte)par1));
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    par1NBTTagCompound.setInteger("Mimic", getMimic());
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    setMimic(par1NBTTagCompound.getInteger("Mimic"));
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
  
  protected String getHurtSound() {
    return "random.chestopen";
  }
  
  protected String getDeathSound() {
    return "random.chestclosed";
  }
  
  protected void dropFewItems(boolean par1, int par2) {
    if (getMimic() == 7) {
      dropItem(Item.getItemFromBlock(Blocks.ender_chest), 1);
    } else {
      dropItem(Item.getItemFromBlock((Block)Blocks.chest), 1);
    } 
  }
}
