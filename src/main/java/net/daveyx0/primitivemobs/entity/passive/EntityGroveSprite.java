package net.daveyx0.primitivemobs.entity.passive;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import java.awt.Color;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.common.packets.PrimitiveGroveSpritePacket;
import net.daveyx0.primitivemobs.core.PrimitiveMobsBlocks;
import net.daveyx0.primitivemobs.lib.BlockHelper;
import net.daveyx0.primitivemobs.lib.ImageTester;
import net.daveyx0.primitivemobs.lib.ResourceChecker;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityGroveSprite extends EntityCreature {
  int checkForTreeCount;
  
  Block nearestLog = null;
  
  Block connectingLeaves = null;
  
  int logX;
  
  int logY;
  
  int logZ;
  
  int leafX;
  
  int leafY;
  
  int leafZ;
  
  ItemStack heldItem = null;
  
  boolean hasChecked;
  
  int followDelay;
  
  boolean begging;
  
  int attackAxeCooldown;
  
  int timeUntilNextSapling;
  
  public EntityGroveSprite(World p_i1738_1_) {
    super(p_i1738_1_);
    getNavigator().setAvoidsWater(true);
    getNavigator().setCanSwim(true);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIWander(this, 1.0D));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.checkForTreeCount = 10;
    setLeafKind(-1);
    setSaplings(-1);
    this.hasChecked = false;
    this.begging = false;
    setAmountPlanted(0);
    setNoDespawn(false);
    this.attackAxeCooldown = 0;
    setTimeUntilNextSapling(6 + this.rand.nextInt(5));
    this.timeUntilNextSapling = getTimeUntilNextSapling() * 1000;
  }
  
  public ItemStack getHeldItem() {
    if (getLeafKind() == 22)
      return new ItemStack(Blocks.torch, 1); 
    return this.heldItem;
  }
  
  public void onUpdate() {
    if (getLeafKind() != -1)
      if (getLeafKind() != 22) {
        this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic(this, 2.0D));
      } else {
        this.tasks.removeTask((EntityAIBase)new EntityAIPanic(this, 2.0D));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
      }  
    this.isImmuneToFire = (getLeafKind() == 22);
    if (getBaseColor() == null || getBaseColor().equals(""))
      if (this.worldObj.isRemote)
        changeToSpecies(12.0D);  
    if (this.heldItem == null && this.connectingLeaves != null && (getHeldItemName() == null || getHeldItemName().equals("")) && !this.hasChecked)
      if (this.heldItem == null && this.worldObj.isRemote) {
        int meta = getLeafKind();
        if (getLeafKind() == 20)
          meta = 1; 
        if (getLeafKind() == 21)
          meta = 2; 
        this
          .heldItem = new ItemStack(this.worldObj.getBlock(this.leafX, this.leafY, this.leafZ).getItemDropped(this.worldObj.getBlock(this.leafX, this.leafY, this.leafZ).damageDropped(this.worldObj.getBlockMetadata(this.leafX, this.leafY, this.leafZ)), this.rand, 1), 1, this.worldObj.getBlock(this.leafX, this.leafY, this.leafZ).damageDropped(this.worldObj.getBlockMetadata(this.leafX, this.leafY, this.leafZ)));
        if (this.heldItem != null)
          PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
                getEntityId() + ":" + this.heldItem.getUnlocalizedName() + ":" + '\003')); 
      } else {
        this.hasChecked = true;
      }  
    if (this.heldItem == null && getLeafKind() != -1 && getLeafKind() != 22 && getLeafKind() != 23) {
      this.timeUntilNextSapling = getTimeUntilNextSapling() * 1000;
      this.heldItem = BlockHelper.getSaplingByUnlocalName(getHeldItemName());
    } 
    if (getLeafKind() == 22 && this.rand.nextInt(3) == 0) {
      this.worldObj.spawnParticle("flame", this.posX, this.posY + 1.399999976158142D, this.posZ, 0.0D, 0.0D, 0.0D);
    } else if (getLeafKind() != -1 && getSaplings() == -1) {
      setSaplings(2 + this.rand.nextInt(3));
    } 
    if (isBurning()) {
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + '\026' + ":" + '\004'));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + "" + ":" + Character.MIN_VALUE));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + "" + ":" + '\001'));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + "" + ":" + '\002'));
    } 
    if (getHeldItem() != null && getSaplings() != 0 && getLeafKind() != 22 && this.rand.nextInt(500) == 0)
      if (!this.worldObj.isRemote)
        if (this.worldObj.getBlock((int)this.posX, (int)this.posY - 1, (int)this.posZ) instanceof net.minecraft.block.BlockGrass && this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) == Blocks.air) {
          this.worldObj.playSoundAtEntity((Entity)this, "dig.grass", getSoundVolume(), 1.0F);
          this.worldObj.setBlock((int)this.posX, (int)this.posY, (int)this.posZ, Block.getBlockFromItem(getHeldItem().getItem()));
          this.worldObj.setBlockMetadataWithNotify((int)this.posX, (int)this.posY, (int)this.posZ, getHeldItem().getItemDamage(), 1);
          setAmountPlanted(getAmountPlanted() + 1);
          if (getSaplings() >= 1)
            setSaplings(getSaplings() - 1); 
        }   
    if (getLeafKind() != -1 && getLeafKind() != 22 && getLeafKind() != 23) {
      this.timeUntilNextSapling--;
      float d = (this.timeUntilNextSapling / 1000);
      setTimeUntilNextSapling((int)d);
      if (this.timeUntilNextSapling <= 0 && getLeafKind() != 22 && getLeafKind() != 23 && getLeafKind() != -1) {
        setSaplings(getSaplings() + 1);
        for (int i = 0; i < 8; i++)
          this.worldObj.spawnParticle("cloud", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
        setTimeUntilNextSapling(6 + this.rand.nextInt(5));
        this.timeUntilNextSapling = getTimeUntilNextSapling();
      } 
    } 
    if (getLeafKind() == 22 && this.inWater) {
      for (int i = 0; i < 10; i++)
        this.worldObj.spawnParticle("smoke", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + '\027' + ":" + '\004'));
    } 
    if (getAmountPlanted() == 3) {
      setAmountPlanted(0);
      int amountOfReproduction = this.rand.nextInt(3) + 1;
      int i;
      for (i = 0; i < 12; i++)
        this.worldObj.spawnParticle("happyVillager", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      for (i = 0; i < amountOfReproduction; i++) {
        if (!this.worldObj.isRemote)
          if ((this.worldObj.getBlock((int)this.posX, (int)this.posY - 1, (int)this.posZ + i) instanceof net.minecraft.block.BlockGrass && this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) == Blocks.air) || (this.worldObj
            .getBlock((int)this.posX, (int)this.posY - 1, (int)this.posZ + i) instanceof net.minecraft.block.BlockGrass && this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ).getMaterial() == Material.plants)) {
            this.worldObj.playSoundAtEntity((Entity)this, "dig.grass", getSoundVolume(), 1.0F);
            this.worldObj.setBlock((int)this.posX, (int)this.posY, (int)this.posZ + i, PrimitiveMobsBlocks.blockSapling);
            this.worldObj.setBlockMetadataWithNotify((int)this.posX, (int)this.posY, (int)this.posZ + i, 1, 1);
            setAmountPlanted(getAmountPlanted() + 1);
          } else if ((this.worldObj.getBlock((int)this.posX + i, (int)this.posY - 1, (int)this.posZ) instanceof net.minecraft.block.BlockGrass && this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) == Blocks.air) || (this.worldObj
            .getBlock((int)this.posX, (int)this.posY - 1, (int)this.posZ + i) instanceof net.minecraft.block.BlockGrass && this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ).getMaterial() == Material.plants)) {
            this.worldObj.playSoundAtEntity((Entity)this, "dig.grass", getSoundVolume(), 1.0F);
            this.worldObj.setBlock((int)this.posX + i, (int)this.posY, (int)this.posZ, PrimitiveMobsBlocks.blockSapling);
            this.worldObj.setBlockMetadataWithNotify((int)this.posX + i, (int)this.posY, (int)this.posZ, 1, 1);
            setAmountPlanted(getAmountPlanted() + 1);
          }  
      } 
    } 
    super.onUpdate();
  }
  
  public void onLivingUpdate() {
    EntityPlayer player = findPlayerWithSapling(10.0D);
    EntityPlayer player1 = findPlayerWithLog(10.0D);
    EntityPlayer player2 = findPlayerWithAxe(10.0D);
    EntityItem item = findSapling(10.0D);
    if (getLeafKind() != 1 && getLeafKind() != 22 && getLeafKind() != 23 && getHealth() > 0.0F) {
      if (item != null) {
        getNavigator().tryMoveToEntityLiving((Entity)item, 1.0D);
        if (getDistanceToEntity((Entity)item) <= 1.0D) {
          setSaplings(getSaplings() + (item.getEntityItem()).stackSize);
          item.setDead();
          this.worldObj.playSoundAtEntity((Entity)this, "primitivemobs:mob.grovesprite.thanks", getSoundVolume(), 1.0F);
          for (int i = 0; i < 8; i++)
            this.worldObj.spawnParticle("happyVillager", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
        } 
      } else if (player != null) {
        getLookHelper().setLookPosition(player.posX, player.posY + player.getEyeHeight(), player.posZ, 10.0F, getVerticalFaceSpeed());
        if (--this.followDelay <= 0) {
          this.followDelay = 10;
          getNavigator().tryMoveToEntityLiving((Entity)player, 1.0D);
        } 
        setBegging(true);
      } else if (player1 != null) {
        getLookHelper().setLookPosition(player1.posX, player1.posY + player1.getEyeHeight(), player1.posZ, 10.0F, getVerticalFaceSpeed());
        if (--this.followDelay <= 0) {
          this.followDelay = 10;
          getNavigator().tryMoveToEntityLiving((Entity)player1, 1.0D);
        } 
        if (this.rand.nextInt(3) == 0)
          this.worldObj.spawnParticle("smoke", this.posX, this.posY + 1.0D + this.rand.nextFloat(), this.posZ, 0.0D, 0.0D, 0.0D); 
        if (getDistanceToEntity((Entity)player1) <= 1.0D) {
          player1.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), -1.0F);
          player1.dropOneItem(true);
          this.worldObj.playSoundAtEntity((Entity)this, "primitivemobs:mob.grovesprite.angry", getSoundVolume(), 1.0F);
        } 
      } else if (player2 != null) {
        getLookHelper().setLookPosition(player2.posX, player2.posY + player2.getEyeHeight(), player2.posZ, 10.0F, getVerticalFaceSpeed());
        if (--this.followDelay <= 0) {
          this.followDelay = 10;
          getNavigator().tryMoveToEntityLiving((Entity)player2, 1.0D);
        } 
        if (this.rand.nextInt(3) == 0)
          this.worldObj.spawnParticle("smoke", this.posX, this.posY + 1.0D + this.rand.nextFloat(), this.posZ, 0.0D, 0.0D, 0.0D); 
        if (getDistanceToEntity((Entity)player2) <= 1.0D && this.attackAxeCooldown-- >= 0) {
          this.attackAxeCooldown = 10;
          player2.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 1.0F);
          this.worldObj.playSoundAtEntity((Entity)this, "primitivemobs:mob.grovesprite.angry", getSoundVolume(), 1.0F);
        } 
      } 
      if (player == null)
        setBegging(false); 
    } 
    super.onLivingUpdate();
  }
  
  public boolean interact(EntityPlayer entityplayer) {
    ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    if (itemstack != null && getHeldItem() != null && itemstack.getItem() == getHeldItem().getItem() && itemstack.getItemDamage() == getHeldItem().getItemDamage()) {
      if (--itemstack.stackSize == 0)
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
      setSaplings(getSaplings() + 1);
      this.worldObj.playSoundAtEntity((Entity)this, "primitivemobs:mob.grovesprite.thanks", getSoundVolume(), 1.0F);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("happyVillager", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      return true;
    } 
    if (getLeafKind() == 23 && itemstack != null && itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15) {
      if (--itemstack.stackSize == 0)
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("happyVillager", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      setHealth(15.0F);
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + -1 + ":" + '\004'));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + (new Color(0.3529412F, 0.30588236F, 0.23921569F)).hashCode() + ":" + Character.MIN_VALUE));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + (new Color(0.7529412F, 0.6313726F, 0.49411765F)).hashCode() + ":" + '\001'));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + (new Color(0.35686275F, 0.64705884F, 0.16078432F)).hashCode() + ":" + '\002'));
      return true;
    } 
    if (getLeafKind() != 23 && itemstack != null && itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15) {
      if (--itemstack.stackSize == 0)
        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
      setNoDespawn(true);
      setHealth(15.0F);
      this.worldObj.playSoundAtEntity((Entity)this, "primitivemobs:mob.grovesprite.thanks", getSoundVolume(), 1.0F);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      if (this.worldObj.isRemote) {
        String saplings, planted;
        if (getSaplings() == 1) {
          saplings = "1 sapling";
        } else if (getSaplings() <= 0) {
          saplings = "no saplings";
        } else {
          saplings = "" + getSaplings() + " saplings";
        } 
        if (getAmountPlanted() <= 0) {
          planted = "none";
        } else {
          planted = "" + getAmountPlanted();
        } 
        entityplayer.addChatComponentMessage((IChatComponent)new ChatComponentText("This Grove Sprite has " + saplings + " and has planted " + planted + " of them."));
      } 
      return true;
    } 
    if (itemstack != null && itemstack.getItem() == Items.wheat_seeds) {
      if (this.worldObj.isRemote)
        entityplayer.addChatComponentMessage((IChatComponent)new ChatComponentText("This Grove Sprite has " + this.timeUntilNextSapling + " ticks to go before creating a new sapling.")); 
      return true;
    } 
    return super.interact(entityplayer);
  }
  
  public boolean canDespawn() {
    return !getNoDespawn();
  }
  
  public boolean isBegging() {
    return this.begging;
  }
  
  public void setBegging(boolean f) {
    this.begging = f;
  }
  
  public EntityPlayer findPlayerWithSapling(double d) {
    EntityPlayer player = this.worldObj.getClosestPlayerToEntity((Entity)this, d);
    if (player != null && player.getHeldItem() != null && getHeldItem() != null) {
      ItemStack useItem = player.getHeldItem();
      if ((useItem.getItem() == getHeldItem().getItem() && useItem.getItemDamage() == getHeldItem().getItemDamage()) || (player
        .getHeldItem().getItem() == Items.dye && player.getHeldItem().getItemDamage() == 15))
        return player; 
    } 
    return null;
  }
  
  public EntityPlayer findPlayerWithLog(double d) {
    EntityPlayer player = this.worldObj.getClosestPlayerToEntity((Entity)this, d);
    if (player != null && player.getHeldItem() != null && Block.getBlockFromItem(player.getHeldItem().getItem()) != null) {
      Block log = Block.getBlockFromItem(player.getHeldItem().getItem());
      if (log instanceof net.minecraft.block.BlockLog || (log.getMaterial() == Material.wood && log instanceof net.minecraft.block.BlockRotatedPillar))
        return player; 
    } 
    return null;
  }
  
  public EntityPlayer findPlayerWithAxe(double d) {
    EntityPlayer player = this.worldObj.getClosestPlayerToEntity((Entity)this, d);
    if (player != null && player.getHeldItem() != null) {
      Item item = player.getHeldItem().getItem();
      if (item instanceof net.minecraft.item.ItemAxe)
        return player; 
    } 
    return null;
  }
  
  public EntityItem findSapling(double d) {
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(d, 4.0D, d));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity != null)
        if (entity instanceof EntityItem)
          if (entity instanceof EntityItem) {
            EntityItem item = (EntityItem)entity;
            ItemStack stack = item.getEntityItem();
            if (getHeldItem() != null && stack.getItem() == getHeldItem().getItem() && stack.getItemDamage() == getHeldItem().getItemDamage())
              return item; 
          }   
    } 
    return null;
  }
  
  protected String getLivingSound() {
    if (getLeafKind() == 23)
      return "primitivemobs:mob.grovesprite.death"; 
    return "primitivemobs:mob.grovesprite.idle";
  }
  
  protected String getHurtSound() {
    return "primitivemobs:mob.grovesprite.hurt";
  }
  
  protected String getDeathSound() {
    return "primitivemobs:mob.grovesprite.death";
  }
  
  protected float getSoundVolume() {
    if (getLeafKind() == 23)
      return 0.1F; 
    return 0.4F;
  }
  
  protected float getSoundPitch() {
    if (getLeafKind() == 22)
      return 0.7F; 
    return 1.0F;
  }
  
  public boolean isAIEnabled() {
    return true;
  }
  
  public void searchTree(double d) {
    AxisAlignedBB axisalignedbb = this.boundingBox.expand(d, d, d);
    int n = MathHelper.floor_double(axisalignedbb.minX);
    int o = MathHelper.floor_double(axisalignedbb.maxX);
    int p = MathHelper.floor_double(axisalignedbb.minY);
    int q = MathHelper.floor_double(axisalignedbb.maxY);
    int n1 = MathHelper.floor_double(axisalignedbb.minZ);
    int o1 = MathHelper.floor_double(axisalignedbb.maxZ);
    for (int p1 = n; p1 < o; p1++) {
      if (getLeafKind() == 23)
        break; 
      for (int q1 = p; q1 < q; q1++) {
        if (getLeafKind() == 23)
          break; 
        for (int n2 = n1; n2 < o1; n2++) {
          if (getLeafKind() == 23)
            break; 
          Block o2 = this.worldObj.getBlock(p1, q1, n2);
          if (o2 != null && o2 != Blocks.air)
            if (o2 != null && (o2 instanceof net.minecraft.block.BlockLog || (o2.getMaterial() == Material.wood && o2 instanceof net.minecraft.block.BlockRotatedPillar) || (o2.getMaterial() == Material.wood && o2.canSustainLeaves((IBlockAccess)this.worldObj, p1, q1, n2)))) {
              int meta = this.worldObj.getBlockMetadata(p1, q1, n2);
              if (o2.getIcon(2, meta) == o2.getIcon(3, meta) && o2.getIcon(3, meta) == o2.getIcon(4, meta)) {
                this.nearestLog = o2;
                this.logX = p1;
                this.logY = q1;
                this.logZ = n2;
                for (int l = 0; l < 128; l++) {
                  Block temp = this.worldObj.getBlock(p1, q1 + l, n2);
                  if (temp != null && temp != Blocks.air)
                    if (temp != null && (temp instanceof net.minecraft.block.BlockLeaves || temp.getMaterial() == Material.leaves)) {
                      this.connectingLeaves = temp;
                      this.leafX = p1;
                      this.leafY = q1 + l;
                      this.leafZ = n2;
                      break;
                    }  
                } 
              } 
            }  
        } 
      } 
    } 
  }
  
  public void searchTreeOnFire(double d) {
    AxisAlignedBB axisalignedbb = this.boundingBox.expand(d, d, d);
    int n = MathHelper.floor_double(axisalignedbb.minX);
    int o = MathHelper.floor_double(axisalignedbb.maxX);
    int p = MathHelper.floor_double(axisalignedbb.minY);
    int q = MathHelper.floor_double(axisalignedbb.maxY);
    int n1 = MathHelper.floor_double(axisalignedbb.minZ);
    int o1 = MathHelper.floor_double(axisalignedbb.maxZ);
    for (int p1 = n; p1 < o; p1++) {
      if (getLeafKind() == 23)
        break; 
      for (int q1 = p; q1 < q; q1++) {
        if (getLeafKind() == 23)
          break; 
        for (int n2 = n1; n2 < o1; n2++) {
          if (getLeafKind() == 23)
            break; 
          Block o2 = this.worldObj.getBlock(p1, q1, n2);
          if (o2 != null && o2 != Blocks.air)
            if (o2 != null && o2 instanceof net.minecraft.block.BlockFire) {
              this.nearestLog = null;
              this.connectingLeaves = null;
              PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
                    getEntityId() + ":" + '\026' + ":" + '\004'));
              break;
            }  
        } 
      } 
    } 
  }
  
  public void changeToSpecies(double d) {
    if (this.nearestLog == null && getLeafKind() != 22 && getLeafKind() != 23)
      searchTree(d); 
    if (getLeafKind() == -1 && getLeafKind() != 22 && this.nearestLog != null && this.connectingLeaves != null)
      searchTreeOnFire(d); 
    if (getLeafKind() == -1 && this.nearestLog != null) {
      int meta = this.worldObj.getBlockMetadata(this.logX, this.logY, this.logZ);
      changeColor(this.nearestLog, meta, 0, 0);
      if (this.connectingLeaves != null) {
        int foliage = 0;
        foliage = this.connectingLeaves.colorMultiplier((IBlockAccess)this.worldObj, this.leafX, this.leafY, this.leafZ);
        int meta2 = this.worldObj.getBlockMetadata(this.leafX, this.leafY, this.leafZ);
        changeColor(this.connectingLeaves, meta, meta2, foliage);
      } else {
        PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
              getEntityId() + ":" + '\027' + ":" + '\004'));
      } 
    } else if ((getBaseColor() != null && getBaseColor().equals("")) || (getInnerColor() != null && getInnerColor().equals("")) || (getLeavesColor() != null && getLeavesColor().equals(""))) {
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + (new Color(0.3529412F, 0.30588236F, 0.23921569F)).hashCode() + ":" + Character.MIN_VALUE));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + (new Color(0.7529412F, 0.6313726F, 0.49411765F)).hashCode() + ":" + '\001'));
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + (new Color(0.35686275F, 0.64705884F, 0.16078432F)).hashCode() + ":" + '\002'));
    } 
  }
  
  public void changeColor(Block m, int meta, int meta2, int foliage) {
    if (m != null && m != Blocks.air && m == this.nearestLog) {
      IIcon icon = m.getIcon(2, meta);
      if (icon != null)
        changeColorFromTexture(icon, 0); 
      IIcon icon2 = m.getIcon(0, meta);
      if (icon2 != null)
        changeColorFromTexture(icon2, 1); 
    } else if (m != null && m != Blocks.air && m == this.connectingLeaves) {
      IIcon icon = m.getIcon(0, meta2);
      int color = foliage;
      PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
            getEntityId() + ":" + meta + ":" + '\004'));
      if (this.nearestLog instanceof net.minecraft.block.BlockOldLog && meta == 1) {
        if (this.connectingLeaves instanceof net.minecraft.block.BlockOldLeaf)
          color = ColorizerFoliage.getFoliageColorPine(); 
        PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
              getEntityId() + ":" + '\024' + ":" + '\004'));
      } 
      if (this.nearestLog instanceof net.minecraft.block.BlockOldLog && meta == 2) {
        if (this.connectingLeaves instanceof net.minecraft.block.BlockOldLeaf)
          color = ColorizerFoliage.getFoliageColorBirch(); 
        PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
              getEntityId() + ":" + '\025' + ":" + '\004'));
      } 
      Color colour = new Color(color, true);
      if (colour.getRed() == colour.getGreen() && colour.getRed() == colour.getBlue() && colour.getGreen() == colour.getBlue()) {
        changeColorFromTexture(icon, 2);
      } else {
        PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
              getEntityId() + ":" + (new Color(colour.getRed() / 255.0F, colour.getGreen() / 255.0F, colour.getBlue() / 255.0F)).hashCode() + ":" + '\002'));
      } 
    } 
  }
  
  public void changeColorFromTexture(IIcon icon, int index) {
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
      if (rgb != null)
        PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveGroveSpritePacket(
              getEntityId() + ":" + (new Color(rgb[0] / 255.0F, rgb[1] / 255.0F, rgb[2] / 255.0F)).hashCode() + ":" + index)); 
    } 
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_) {
    float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
    int i = 0;
    if (p_70652_1_ instanceof EntityLivingBase) {
      f += EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase)this, (EntityLivingBase)p_70652_1_);
      i += EnchantmentHelper.getKnockbackModifier((EntityLivingBase)this, (EntityLivingBase)p_70652_1_);
    } 
    boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), f);
    if (flag) {
      if (i > 0) {
        p_70652_1_.addVelocity((-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F), 0.1D, (MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F));
        this.motionZ *= 0.6D;
      } 
      int j = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)this);
      if (j > 0)
        p_70652_1_.setFire(j * 4); 
      if (p_70652_1_ instanceof EntityLivingBase)
        EnchantmentHelper.func_151384_a((EntityLivingBase)p_70652_1_, (Entity)this); 
      EnchantmentHelper.func_151385_b((EntityLivingBase)this, p_70652_1_);
      if (getLeafKind() == 22)
        p_70652_1_.setFire(10); 
      this.hasAttacked = true;
    } 
    return flag;
  }
  
  protected Entity findPlayerToAttack() {
    EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity((Entity)this, 16.0D);
    return (entityplayer != null && canEntityBeSeen((Entity)entityplayer)) ? (Entity)entityplayer : null;
  }
  
  public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
    if (isEntityInvulnerable())
      return false; 
    if (super.attackEntityFrom(p_70097_1_, p_70097_2_)) {
      Entity entity = p_70097_1_.getEntity();
      if (this.riddenByEntity != entity && this.ridingEntity != entity) {
        if (entity != this)
          this.entityToAttack = entity; 
        return true;
      } 
      return true;
    } 
    return false;
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
    int j = this.rand.nextInt(5) + 1;
    Item holder = null;
    int meta = 0;
    int amount = 1;
    if (getHeldItem() != null) {
      holder = getHeldItem().getItem();
      meta = getHeldItem().getItemDamage();
      amount = getSaplings();
    } 
    if (holder != null && getLeafKind() != 22)
      entityDropItem(new ItemStack(holder, amount, meta), 0.0F); 
    for (int i = 0; i < j; i++)
      entityDropItem(new ItemStack(Items.stick, 1), 0.0F); 
    if (getLeafKind() == 22 && this.rand.nextInt(10) == 0)
      entityDropItem(new ItemStack(PrimitiveMobsBlocks.blockSapling, 1, 0), 0.0F); 
  }
  
  protected void attackEntity(Entity p_70785_1_, float p_70785_2_) {
    if (this.attackTime <= 0 && p_70785_2_ < 2.0F && p_70785_1_.boundingBox.maxY > this.boundingBox.minY && p_70785_1_.boundingBox.minY < this.boundingBox.maxY) {
      this.attackTime = 20;
      attackEntityAsMob(p_70785_1_);
    } 
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(17, "");
    this.dataWatcher.addObject(18, "");
    this.dataWatcher.addObject(19, "");
    this.dataWatcher.addObject(20, "");
    this.dataWatcher.addObject(21, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(22, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(23, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(24, Byte.valueOf((byte)0));
  }
  
  public String getBaseColor() {
    return this.dataWatcher.getWatchableObjectString(17);
  }
  
  public void setBaseColor(String p_152115_1_) {
    this.dataWatcher.updateObject(17, p_152115_1_);
  }
  
  public String getInnerColor() {
    return this.dataWatcher.getWatchableObjectString(18);
  }
  
  public void setInnerColor(String p_152115_1_) {
    this.dataWatcher.updateObject(18, p_152115_1_);
  }
  
  public String getLeavesColor() {
    return this.dataWatcher.getWatchableObjectString(19);
  }
  
  public void setLeavesColor(String p_152115_1_) {
    this.dataWatcher.updateObject(19, p_152115_1_);
  }
  
  public String getHeldItemName() {
    return this.dataWatcher.getWatchableObjectString(20);
  }
  
  public void setHeldItemName(String p_152115_1_) {
    this.dataWatcher.updateObject(20, p_152115_1_);
  }
  
  public int getLeafKind() {
    return this.dataWatcher.getWatchableObjectByte(16);
  }
  
  public void setLeafKind(int par1) {
    this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
  }
  
  public int getSaplings() {
    return this.dataWatcher.getWatchableObjectByte(21);
  }
  
  public void setSaplings(int par1) {
    this.dataWatcher.updateObject(21, Byte.valueOf((byte)par1));
  }
  
  public int getAmountPlanted() {
    return this.dataWatcher.getWatchableObjectByte(22);
  }
  
  public void setAmountPlanted(int par1) {
    this.dataWatcher.updateObject(22, Byte.valueOf((byte)par1));
  }
  
  public boolean getNoDespawn() {
    return ((this.dataWatcher.getWatchableObjectByte(23) & 0x1) != 0);
  }
  
  public void setNoDespawn(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(23, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(23, Byte.valueOf((byte)0));
    } 
  }
  
  public int getTimeUntilNextSapling() {
    return this.dataWatcher.getWatchableObjectByte(24);
  }
  
  public void setTimeUntilNextSapling(int par1) {
    this.dataWatcher.updateObject(24, Byte.valueOf((byte)par1));
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    super.writeEntityToNBT(nbttagcompound);
    nbttagcompound.setInteger("Leaf", getLeafKind());
    nbttagcompound.setInteger("Sapling", getSaplings());
    nbttagcompound.setInteger("Planted", getAmountPlanted());
    nbttagcompound.setBoolean("Despawn", getNoDespawn());
    nbttagcompound.setInteger("TimeSapling", getTimeUntilNextSapling());
    if (getBaseColor() == null) {
      nbttagcompound.setString("BaseColor", "");
    } else {
      nbttagcompound.setString("BaseColor", getBaseColor());
    } 
    if (getInnerColor() == null) {
      nbttagcompound.setString("InnerColor", "");
    } else {
      nbttagcompound.setString("InnerColor", getInnerColor());
    } 
    if (getLeavesColor() == null) {
      nbttagcompound.setString("LeavesColor", "");
    } else {
      nbttagcompound.setString("LeavesColor", getLeavesColor());
    } 
    if (getHeldItemName() == null) {
      nbttagcompound.setString("HeldItem", "");
    } else {
      nbttagcompound.setString("HeldItem", getHeldItemName());
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    super.readEntityFromNBT(nbttagcompound);
    setLeafKind(nbttagcompound.getInteger("Leaf"));
    setSaplings(nbttagcompound.getInteger("Sapling"));
    setAmountPlanted(nbttagcompound.getInteger("Planted"));
    setBaseColor(nbttagcompound.getString("BaseColor"));
    setInnerColor(nbttagcompound.getString("InnerColor"));
    setLeavesColor(nbttagcompound.getString("LeavesColor"));
    setHeldItemName(nbttagcompound.getString("HeldItem"));
    setNoDespawn(nbttagcompound.getBoolean("Despawn"));
    setTimeUntilNextSapling(nbttagcompound.getInteger("TimeSapling"));
  }
}
