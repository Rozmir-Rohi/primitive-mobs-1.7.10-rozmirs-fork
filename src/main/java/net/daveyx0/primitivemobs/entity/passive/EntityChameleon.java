package net.daveyx0.primitivemobs.entity.passive;

import java.awt.Color;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;
import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.lib.ImageTester;
import net.daveyx0.primitivemobs.lib.ResourceChecker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;

public class EntityChameleon extends EntityAnimal {
  private float R;
  
  private float G;
  
  private float B;
  
  private float NewR;
  
  private float NewG;
  
  private float NewB;
  
  private boolean hasChanged;
  
  private Block currentBlock;
  
  private int currentMeta;
  
  private int currentFoliage;
  
  public double tongueX;
  
  public double tongueY;
  
  public double tongueZ;
  
  private boolean stretched;
  
  public int followDelay;
  
  private PathEntity pathToOwner;
  
  public EntityChameleon(World par1World) {
    super(par1World);
    setSize(0.7F, 0.5F);
    getNavigator().setAvoidsWater(true);
    getNavigator().setCanSwim(true);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 2.0D));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.25D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.currentBlock = Blocks.bedrock;
    this.stretched = false;
    this.followDelay = 0;
    setSitting(false);
    setPlayerOwned(false);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(20, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(21, "");
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.damageType == "inWall" && getPlayerOwned())
      return false; 
    if (par1DamageSource.damageType == "fall" && getPlayerOwned())
      return false; 
    if (par1DamageSource.damageType == "drown" && getPlayerOwned())
      return false; 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  public void onUpdate() {
    if (isInWater() && !this.isCollidedHorizontally)
      this.motionY = 0.02D; 
    if (this.worldObj.isRemote)
      changeColor(); 
    if (this.R != this.NewR || this.G != this.NewG || this.B != this.NewB) {
      this.hasChanged = false;
      if (this.R > this.NewR) {
        this.R--;
      } else if (this.R < this.NewR) {
        this.R++;
      } 
      if (this.G > this.NewG) {
        this.G--;
      } else if (this.G < this.NewG) {
        this.G++;
      } 
      if (this.B > this.NewB) {
        this.B--;
      } else if (this.B < this.NewB) {
        this.B++;
      } 
    } else {
      this.hasChanged = true;
    } 
    if (getPlayerOwned() && !getSitting()) {
      EntityPlayer entityplayer = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 12.0D);
      EntityPlayer entityplayer1 = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 25.0D);
      if (entityplayer != null && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
        if (--this.followDelay <= 0) {
          this.followDelay = 10;
          getNavigator().tryMoveToEntityLiving((Entity)entityplayer, 1.0D);
        } 
        if (getDistanceSqToEntity((Entity)entityplayer) < 2.0D)
          setPathToEntity(null); 
      } else if (entityplayer1 != null && entityplayer == null && !getSitting() && entityplayer1.getUniqueID().toString().equals(getOwnerUUID())) {
        setPosition(entityplayer1.posX, entityplayer1.posY, entityplayer1.posZ);
      } 
    } 
    if (getPlayerOwned()) {
      if (this.rand.nextInt(200) == 0)
        this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 1.0D, 1.0D, 1.0D); 
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
    } 
    if (getPlayerOwned() && getSitting())
      getNavigator().clearPathEntity(); 
    super.onUpdate();
  }
  
  public void changeColor() {
    int i = MathHelper.floor_double(this.posX);
    int j = MathHelper.floor_double(this.boundingBox.minY);
    int k = MathHelper.floor_double(this.posZ);
    if (this.worldObj.getBlock(i, j, k) == Blocks.air)
      j = MathHelper.floor_double(this.boundingBox.minY - 0.1D); 
    Block m = this.worldObj.getBlock(i, j, k);
    int meta = this.worldObj.getBlockMetadata(i, j, k);
    int foliage = 0;
    if (m instanceof BlockGrass) {
      BlockGrass blockGrass = (BlockGrass)m;
      foliage = blockGrass.colorMultiplier((IBlockAccess)this.worldObj, i, j, k);
    } else if (m instanceof BlockLeaves) {
      BlockLeaves blockLeaves = (BlockLeaves)m;
      foliage = blockLeaves.colorMultiplier((IBlockAccess)this.worldObj, i, j, k);
    } else if (m instanceof BlockLilyPad) {
      BlockLilyPad blockLilyPad = (BlockLilyPad)m;
      foliage = blockLilyPad.colorMultiplier((IBlockAccess)this.worldObj, i, j, k);
    } 
    if (m instanceof net.minecraft.block.BlockVine)
      m = this.currentBlock; 
    if (m != Blocks.air && this.hasChanged && (m != this.currentBlock || meta != this.currentMeta || foliage != this.currentFoliage)) {
      this.currentBlock = m;
      this.currentMeta = meta;
      this.currentFoliage = foliage;
      IIcon icon = m.getIcon(1, meta);
      if (icon != null && !(m instanceof BlockGrass) && !(m instanceof BlockLeaves) && !(m instanceof BlockFluidBase) && !(m instanceof BlockTallGrass)) {
        changeColorFromTexture(icon);
      } else if (m instanceof BlockGrass) {
        BlockGrass blockGrass = (BlockGrass)m;
        int color = blockGrass.colorMultiplier((IBlockAccess)this.worldObj, i, j, k);
        icon = m.getIcon(0, meta);
        Color colour = new Color(color, true);
        if (colour.getRed() == colour.getGreen() && colour.getRed() == colour.getBlue() && colour.getGreen() == colour.getBlue()) {
          changeColorFromTexture(icon);
        } else {
          setNewR(colour.getRed());
          setNewG(colour.getGreen());
          setNewB(colour.getBlue());
        } 
      } else if (m instanceof BlockTallGrass) {
        BlockTallGrass blockTallGrass = (BlockTallGrass)m;
        int color = blockTallGrass.colorMultiplier((IBlockAccess)this.worldObj, i, j, k);
        icon = m.getIcon(0, meta);
        Color colour = new Color(color, true);
        if (colour.getRed() == colour.getGreen() && colour.getRed() == colour.getBlue() && colour.getGreen() == colour.getBlue()) {
          changeColorFromTexture(icon);
        } else {
          setNewR(colour.getRed());
          setNewG(colour.getGreen());
          setNewB(colour.getBlue());
        } 
      } else if (m instanceof BlockLeaves) {
        BlockLeaves blockLeaves = (BlockLeaves)m;
        int color = blockLeaves.colorMultiplier((IBlockAccess)this.worldObj, i, j, k);
        if (meta == 1 || meta == 5)
          color = ColorizerFoliage.getFoliageColorPine(); 
        if (meta == 2 || meta == 6)
          color = ColorizerFoliage.getFoliageColorBirch(); 
        icon = m.getIcon(0, meta);
        Color colour = new Color(color, true);
        if (colour.getRed() == colour.getGreen() && colour.getRed() == colour.getBlue() && colour.getGreen() == colour.getBlue()) {
          changeColorFromTexture(icon);
        } else {
          setNewR(colour.getRed());
          setNewG(colour.getGreen());
          setNewB(colour.getBlue());
        } 
      } else if (m instanceof BlockLilyPad) {
        BlockLilyPad blockLilyPad = (BlockLilyPad)m;
        int color = blockLilyPad.colorMultiplier((IBlockAccess)this.worldObj, i, j, k);
        icon = m.getIcon(0, meta);
        Color colour = new Color(color, true);
        if (colour.getRed() == colour.getGreen() && colour.getRed() == colour.getBlue() && colour.getGreen() == colour.getBlue()) {
          changeColorFromTexture(icon);
        } else {
          setNewR(colour.getRed());
          setNewG(colour.getGreen());
          setNewB(colour.getBlue());
        } 
      } else if (m instanceof BlockFluidBase) {
        BlockFluidBase blockFluidBase = (BlockFluidBase)m;
        int color = blockFluidBase.colorMultiplier((IBlockAccess)this.worldObj, i, j, k);
        icon = m.getIcon(0, meta);
        Color colour = new Color(color, true);
        if (colour.getRed() == colour.getGreen() && colour.getRed() == colour.getBlue() && colour.getGreen() == colour.getBlue()) {
          changeColorFromTexture(icon);
        } else {
          setNewR(colour.getRed());
          setNewG(colour.getGreen());
          setNewB(colour.getBlue());
        } 
      } 
    } 
  }
  
  public void changeColorFromTexture(IIcon icon) {
    SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
    Set set = resourceManager.getResourceDomains();
    Object[] domains = set.toArray();
    IResource resource = null;
    for (int i = 0; i < domains.length; i++) {
      resource = ResourceChecker.getResource(domains[i].toString(), "textures/blocks/" + icon.getIconName() + ".png");
      if (resource == null) {
        String[] divided = icon.getIconName().split(":");
        resource = ResourceChecker.getResource(domains[i].toString(), "textures/blocks/" + divided[1] + ".png");
      } 
      if (resource != null)
        break; 
    } 
    if (resource != null) {
      InputStream stream = resource.getInputStream();
      int[] rgb = null;
      try {
        rgb = ImageTester.main(stream);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (rgb != null) {
        setNewR(rgb[0]);
        setNewG(rgb[1]);
        setNewB(rgb[2]);
      } 
    } 
  }
  
  public boolean isAIEnabled() {
    return true;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
  }
  
  protected float getSoundVolume() {
    return 0.4F;
  }
  
  protected Item getDropItem() {
    return PrimitiveMobsItems.camouflageDye;
  }
  
  public boolean interact(EntityPlayer entityplayer) {
    ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    if (super.interact(entityplayer))
      return true; 
    if (!getPlayerOwned() && itemstack != null && itemstack.getItem() == Items.melon && !entityplayer.isSneaking()) {
      if (--itemstack.stackSize == 0)
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
      setPlayerOwned(true);
      setOwnerUUID(entityplayer.getUniqueID().toString());
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.chameleonFriend, 1);
      return true;
    } 
    if (itemstack != null && itemstack.getItem() == Items.spider_eye && !entityplayer.isSneaking() && getPlayerOwned()) {
      if (--itemstack.stackSize == 0)
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      setHealth(30.0F);
      return true;
    } 
    if (itemstack == null && getPlayerOwned() && entityplayer.isSneaking() && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("note", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      if (!getSitting()) {
        setSitting(true);
      } else {
        setSitting(false);
      } 
    } 
    return true;
  }
  
  protected void dropFewItems(boolean par1, int par2) {
    int j = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
    for (int k = 0; k < j; k++)
      dropItem(PrimitiveMobsItems.camouflageDye, 1); 
  }
  
  public EntityChameleon spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
    return new EntityChameleon(this.worldObj);
  }
  
  public EntityAgeable createChild(EntityAgeable par1EntityAgeable) {
    return (EntityAgeable)spawnBabyAnimal(par1EntityAgeable);
  }
  
  public float getR() {
    return this.R;
  }
  
  public float getG() {
    return this.G;
  }
  
  public float getB() {
    return this.B;
  }
  
  public void setR(float set) {
    this.R = set;
  }
  
  public void setG(float set) {
    this.G = set;
  }
  
  public void setB(float set) {
    this.B = set;
  }
  
  public float getNewR() {
    return this.NewR;
  }
  
  public float getNewG() {
    return this.NewG;
  }
  
  public float getNewB() {
    return this.NewB;
  }
  
  public void setNewR(float set) {
    this.NewR = set;
  }
  
  public void setNewG(float set) {
    this.NewG = set;
  }
  
  public void setNewB(float set) {
    this.NewB = set;
  }
  
  public boolean getSitting() {
    return ((this.dataWatcher.getWatchableObjectByte(20) & 0x1) != 0);
  }
  
  public void setSitting(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(20, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(20, Byte.valueOf((byte)0));
    } 
  }
  
  public boolean getPlayerOwned() {
    return ((this.dataWatcher.getWatchableObjectByte(19) & 0x1) != 0);
  }
  
  public void setPlayerOwned(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
    } 
  }
  
  public String getOwnerUUID() {
    return this.dataWatcher.getWatchableObjectString(21);
  }
  
  public void setOwnerUUID(String p_152115_1_) {
    this.dataWatcher.updateObject(21, p_152115_1_);
  }
  
  public EntityLivingBase getOwner() {
    try {
      UUID uuid = UUID.fromString(getOwnerUUID());
      return (uuid == null) ? null : (EntityLivingBase)this.worldObj.func_152378_a(uuid);
    } catch (IllegalArgumentException illegalargumentexception) {
      return null;
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setBoolean("Tamed", getPlayerOwned());
    nbttagcompound.setBoolean("Sitting", getSitting());
    if (getOwnerUUID() == null) {
      nbttagcompound.setString("OwnerUUID", "");
    } else {
      nbttagcompound.setString("OwnerUUID", getOwnerUUID());
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setPlayerOwned(nbttagcompound.getBoolean("Tamed"));
    setSitting(nbttagcompound.getBoolean("Sitting"));
    String var2 = nbttagcompound.getString("Owner");
    String s = "";
    if (nbttagcompound.hasKey("OwnerUUID", 8)) {
      s = nbttagcompound.getString("OwnerUUID");
    } else {
      String s1 = nbttagcompound.getString("Owner");
      s = PreYggdrasilConverter.func_152719_a(s1);
    } 
    if (s.length() > 0) {
      setOwnerUUID(s);
      setPlayerOwned(true);
    } 
  }
}
