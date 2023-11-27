package net.daveyx0.primitivemobs.entity.monster;

import java.util.UUID;
import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityTreasureSlime extends EntityPrimitiveSlime {
  private int slimeJumpDelay;
  
  public EntityTreasureSlime(World par1World) {
    super(par1World);
    setItem(chooseItem());
    setPlayerOwned(false);
    setParticle(7);
    setSitting(false);
    setR(0.0F);
    setG(0.0F);
    setB(0.0F);
    int i = 1 << this.rand.nextInt(2);
    setSlimeSize(i);
    this.slimeJumpDelay = this.rand.nextInt(20) + 10;
  }
  
  protected String getSlimeParticle() {
    switch (getItem()) {
      case 0:
        return "slime";
      case 1:
        return "magicCrit";
      case 2:
        return "heart";
      case 3:
        return "townaura";
      case 4:
        return "magicCrit";
      case 5:
        return "note";
      case 6:
        return "slime";
      case 7:
        return "portal";
      case 8:
        return "crit";
      case 9:
        return "magicCrit";
      case 10:
        return "smoke";
      case 11:
        return "crit";
      case 12:
        return "reddust";
      case 13:
        return "townaura";
      case 14:
        return "dripLava";
      case 15:
        return "splash";
      case 16:
        return "flame";
      case 17:
        return "snowballpoof";
      case 18:
        return "portal";
      case 19:
        return "flame";
      case 20:
        return getChosenParticle();
      case 21:
        return "slime";
    } 
    return "slime";
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.damageType == "inWall" && getPlayerOwned())
      return false; 
    if (par1DamageSource.damageType == "fall" && getPlayerOwned())
      return false; 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  protected boolean canDamagePlayer() {
    if (getItem() == 20)
      return false; 
    return (getSlimeSize() > 1);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(20, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(21, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(22, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(23, "");
  }
  
  public void onLivingUpdate() {
    if (getSitting()) {
      EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity((Entity)this, 6.0D);
      if (var1 != null)
        faceEntity((Entity)var1, 10.0F, 10.0F); 
    } 
    if (getPlayerOwned())
      if (this.rand.nextInt(200) == 0)
        this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 1.0D, 1.0D, 1.0D);  
    super.onLivingUpdate();
  }
  
  protected void updateEntityActionState() {
    if (getItem() != 20 && !getPlayerOwned())
      despawnEntity(); 
    EntityPlayer var1 = null;
    if (getItem() == 20 && !getPlayerOwned() && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
      var1 = this.worldObj.getClosestPlayerToEntity((Entity)this, 16.0D);
    } else if (getItem() == 20 && getPlayerOwned()) {
      EntityPlayer var2 = this.worldObj.getClosestPlayerToEntity((Entity)this, 16.0D);
      if (var2 != null && var2.getUniqueID().toString().equals(getOwnerUUID()))
        var1 = var2; 
    } else if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
      var1 = this.worldObj.getClosestVulnerablePlayerToEntity((Entity)this, 16.0D);
    } 
    if (var1 != null)
      faceEntity((Entity)var1, 10.0F, 20.0F); 
    if (this.onGround && this.slimeJumpDelay-- <= 0 && !getSitting()) {
      this.slimeJumpDelay = getJumpDelay();
      if (var1 != null)
        this.slimeJumpDelay /= 3; 
      this.isJumping = true;
      if (makesSoundOnJump())
        playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F); 
      this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
      this.moveForward = (1 * getSlimeSize());
    } else {
      this.isJumping = false;
      if (this.onGround)
        this.moveStrafing = this.moveForward = 0.0F; 
    } 
  }
  
  protected void despawnEntity() {
    if (getItem() != 20 && !getPlayerOwned())
      super.despawnEntity(); 
  }
  
  protected int getAttackStrength() {
    if (getItem() != 20)
      return getSlimeSize(); 
    return 0;
  }
  
  public int chooseItem() {
    int var1 = getSlimeSize();
    if (var1 > 1) {
      int i = this.rand.nextInt(165);
      if (i <= 10)
        return 0; 
      if (i <= 20)
        return 3; 
      if (i <= 30)
        return 7; 
      if (i <= 40)
        return 8; 
      if (i <= 50)
        return 9; 
      if (i <= 60)
        return 10; 
      if (i <= 70)
        return 11; 
      if (i <= 80)
        return 12; 
      if (i <= 85)
        return 2; 
      if (i <= 90)
        return 4; 
      if (i <= 93)
        return 1; 
      if (i <= 96)
        return 6; 
      if (i <= 98)
        return 5; 
      if (i <= 100)
        return 13; 
      if (i <= 110)
        return 19; 
      if (i <= 120)
        return 15; 
      if (i <= 123)
        return 16; 
      if (i <= 133)
        return 17; 
      if (i <= 135)
        return 18; 
      if (i <= 140)
        return 14; 
      if (i < 165) {
        setSlimeSize(3);
        return 20;
      } 
      return 21;
    } 
    return 21;
  }
  
  protected void dropFewItems(boolean par1, int par2) {
    if (getItem() == 0) {
      dropItem(Items.slime_ball, 1);
    } else if (getItem() == 1) {
      dropItem(Items.golden_apple, 1);
    } else if (getItem() == 2) {
      dropItem(Items.cake, 1);
    } else if (getItem() == 3) {
      dropItem(Items.cookie, 1);
    } else if (getItem() == 4) {
      dropItem(Items.diamond, 1);
    } else if (getItem() == 5) {
      int k = this.rand.nextInt(12);
      if (k == 0)
        dropItem(Items.record_far, 1); 
      if (k == 1) {
        dropItem(Items.record_13, 1);
      } else if (k == 2) {
        dropItem(Items.record_cat, 1);
      } else if (k == 3) {
        dropItem(Items.record_blocks, 1);
      } else if (k == 4) {
        dropItem(Items.record_chirp, 1);
      } else if (k == 5) {
        dropItem(Items.record_mall, 1);
      } else if (k == 6) {
        dropItem(Items.record_mellohi, 1);
      } else if (k == 7) {
        dropItem(Items.record_stal, 1);
      } else if (k == 8) {
        dropItem(Items.record_strad, 1);
      } else if (k == 9) {
        dropItem(Items.record_wait, 1);
      } else if (k == 10) {
        dropItem(Items.record_ward, 1);
      } else {
        dropItem(Items.record_11, 1);
      } 
    } else if (getItem() == 6) {
      dropItem(Items.emerald, 1);
    } else if (getItem() == 7) {
      dropItem(Items.ender_pearl, 1);
    } else if (getItem() == 8) {
      dropItem(Items.glowstone_dust, 1);
    } else if (getItem() == 9) {
      dropItem(Items.gold_ingot, 1);
    } else if (getItem() == 10) {
      dropItem(Items.gunpowder, 1);
    } else if (getItem() == 11) {
      dropItem(Items.iron_ingot, 1);
    } else if (getItem() == 12) {
      dropItem(Items.redstone, 1);
    } else if (getItem() == 13) {
      dropItem(Items.saddle, 1);
    } else if (getItem() == 14) {
      dropItem(Items.magma_cream, 1);
    } else if (getItem() == 15) {
      entityDropItem(new ItemStack(Items.dye, 1, 4), 0.0F);
    } else if (getItem() == 16) {
      dropItem(Items.fire_charge, 1);
    } else if (getItem() == 17) {
      dropItem(Items.snowball, 1);
    } else if (getItem() == 18) {
      dropItem(Items.ender_eye, 1);
    } else if (getItem() == 19) {
      dropItem(Items.blaze_powder, 1);
    } else if (getItem() == 20) {
      dropItem(Items.slime_ball, 1);
    } else if (getItem() == 21) {
      dropItem(Items.slime_ball, 1);
    } 
  }
  
  public double getYOffset() {
    if (this.ridingEntity != null)
      return this.yOffset - 1.2D; 
    return this.yOffset;
  }
  
  public boolean interact(EntityPlayer entityplayer) {
    super.interact(entityplayer);
    ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    if (getItem() == 20 && !entityplayer.isSneaking() && !getPlayerOwned() && (this.ridingEntity == null || this.ridingEntity == entityplayer)) {
      setPlayerOwned(true);
      this.worldObj.playSoundAtEntity((Entity)this, "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small"), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      mountEntity((Entity)entityplayer);
      setOwnerUUID(entityplayer.getUniqueID().toString());
      entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.treasureSlimeFriend, 1);
    } 
    if (itemstack == null && getItem() == 20 && !entityplayer.isSneaking() && getPlayerOwned() && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      this.worldObj.playSoundAtEntity((Entity)this, "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small"), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      if (this.ridingEntity == null) {
        mountEntity((Entity)entityplayer);
      } else {
        mountEntity(null);
      } 
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
    if (itemstack != null) {
      int itemdamage = entityplayer.inventory.getCurrentItem().getItemDamage();
      if (itemstack.getItem() == Items.slime_ball) {
        if (--itemstack.stackSize == 0)
          entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
        setHealth(getSlimeMaxHealth());
        for (int i = 0; i < 8; i++)
          this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      } 
      if (itemstack.getItem() == Items.coal && !this.worldObj.isRemote && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
        if (--itemstack.stackSize == 0)
          entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
        setR(0.0F);
        setG(0.0F);
        setB(0.0F);
      } 
      if (itemstack.getItem() == Items.dye && itemdamage == 1 && !this.worldObj.isRemote && getR() < 126.0F && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
        if (--itemstack.stackSize == 0)
          entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
        setR(getR() + 2.0F);
        if (getB() == 126.0F && getG() == 126.0F && getR() == 126.0F)
          entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.treasureSlimeWhite, 1); 
        return true;
      } 
      if (itemstack.getItem() == Items.dye && itemdamage == 2 && !this.worldObj.isRemote && getG() < 126.0F && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
        if (--itemstack.stackSize == 0)
          entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
        setG(getG() + 2.0F);
        if (getB() == 126.0F && getG() == 126.0F && getR() == 126.0F)
          entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.treasureSlimeWhite, 1); 
        return true;
      } 
      if (itemstack.getItem() == Items.dye && itemdamage == 4 && !this.worldObj.isRemote && getB() < 126.0F && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
        if (--itemstack.stackSize == 0)
          entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null); 
        setB(getB() + 2.0F);
        if (getB() == 126.0F && getG() == 126.0F && getR() == 126.0F)
          entityplayer.addStat((StatBase)PrimitiveMobsAchievementPage.treasureSlimeWhite, 1); 
        return true;
      } 
    } 
    if (itemstack != null && itemstack.getItem() == Items.blaze_powder && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(1);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Items.ender_pearl && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(2);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Items.snowball && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(3);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Items.water_bucket && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(4);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Items.lava_bucket && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(5);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.noteblock) && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(6);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Items.cake && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(7);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Items.gunpowder && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(8);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock((Block)Blocks.brown_mushroom) && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(9);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Items.glowstone_dust && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(10);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Items.redstone && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(11);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Items.book && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(12);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Items.reeds && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(0);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.tnt) && entityplayer.getUniqueID().toString().equals(getOwnerUUID())) {
      setParticle(13);
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle(getChosenParticle(), this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
    } 
    return super.interact(entityplayer);
  }
  
  public float getSlimeMaxHealth() {
    if (getItem() == 20)
      return 10.0F; 
    int var1 = getSlimeSize();
    return (var1 * var1);
  }
  
  public String getChosenParticle() {
    if (getParticle() == 1)
      return "flame"; 
    if (getParticle() == 2)
      return "portal"; 
    if (getParticle() == 3)
      return "snowballpoof"; 
    if (getParticle() == 4)
      return "splash"; 
    if (getParticle() == 5)
      return "dripLava"; 
    if (getParticle() == 6)
      return "note"; 
    if (getParticle() == 7)
      return "heart"; 
    if (getParticle() == 8)
      return "smoke"; 
    if (getParticle() == 9)
      return "townaura"; 
    if (getParticle() == 10)
      return "crit"; 
    if (getParticle() == 11)
      return "reddust"; 
    if (getParticle() == 12)
      return "enchantmenttable"; 
    if (getParticle() == 13)
      return "hugeexplosion"; 
    return "slime";
  }
  
  public void setDead() {
    int var1 = getSlimeSize();
    if (!this.worldObj.isRemote && var1 > 1 && getHealth() <= 0.0F) {
      int var2 = 2 + this.rand.nextInt(3);
      for (int var3 = 0; var3 < var2; var3++) {
        float var4 = ((var3 % 2) - 0.5F) * var1 / 4.0F;
        float var5 = ((var3 / 2) - 0.5F) * var1 / 4.0F;
        EntityTreasureSlime var6 = createInstance();
        var6.setSlimeSize(var1 / 2);
        var6.setItem(0);
        var6.setLocationAndAngles(this.posX + var4, this.posY + 0.5D, this.posZ + var5, this.rand.nextFloat() * 360.0F, 0.0F);
        this.worldObj.spawnEntityInWorld((Entity)var6);
      } 
    } 
    super.setDead();
  }
  
  public boolean canDespawn() {
    if (getPlayerOwned())
    	{return false;} 
    return true;
  }
  
  protected EntityTreasureSlime createInstance() {
    return new EntityTreasureSlime(this.worldObj);
  }
  
  public int getItem() {
    return this.dataWatcher.getWatchableObjectByte(15);
  }
  
  public void setItem(int par1) {
    this.dataWatcher.updateObject(15, Byte.valueOf((byte)par1));
  }
  
  public boolean getPlayerOwned() {
    return ((this.dataWatcher.getWatchableObjectByte(17) & 0x1) != 0);
  }
  
  public void setPlayerOwned(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)0));
    } 
  }
  
  public float getR() {
    return this.dataWatcher.getWatchableObjectByte(18);
  }
  
  public void setR(float par1) {
    this.dataWatcher.updateObject(18, Byte.valueOf((byte)(int)par1));
  }
  
  public float getG() {
    return this.dataWatcher.getWatchableObjectByte(19);
  }
  
  public void setG(float par1) {
    this.dataWatcher.updateObject(19, Byte.valueOf((byte)(int)par1));
  }
  
  public float getB() {
    return this.dataWatcher.getWatchableObjectByte(20);
  }
  
  public void setB(float par1) {
    this.dataWatcher.updateObject(20, Byte.valueOf((byte)(int)par1));
  }
  
  public boolean getSitting() {
    return ((this.dataWatcher.getWatchableObjectByte(21) & 0x1) != 0);
  }
  
  public void setSitting(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(21, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(21, Byte.valueOf((byte)0));
    } 
  }
  
  public int getParticle() {
    return this.dataWatcher.getWatchableObjectByte(22);
  }
  
  public void setParticle(int par1) {
    this.dataWatcher.updateObject(22, Byte.valueOf((byte)par1));
  }
  
  public String getOwnerUUID() {
    return this.dataWatcher.getWatchableObjectString(23);
  }
  
  public void setOwnerUUID(String p_152115_1_) {
    this.dataWatcher.updateObject(23, p_152115_1_);
  }
  
  public EntityLivingBase getOwner() {
    try {
      UUID uuid = UUID.fromString(getOwnerUUID());
      return (uuid == null) ? null : (EntityLivingBase)this.worldObj.func_152378_a(uuid);
    } catch (IllegalArgumentException illegalargumentexception) {
      return null;
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    par1NBTTagCompound.setInteger("Size", getSlimeSize() - 1);
    par1NBTTagCompound.setInteger("Item", getItem());
    par1NBTTagCompound.setBoolean("Tamed", getPlayerOwned());
    if (getPlayerOwned()) {
      par1NBTTagCompound.setFloat("R", getR());
      par1NBTTagCompound.setFloat("G", getG());
      par1NBTTagCompound.setFloat("B", getB());
      par1NBTTagCompound.setBoolean("Sitting", getSitting());
      par1NBTTagCompound.setInteger("Particle", getParticle());
      if (getOwnerUUID() == null) {
        par1NBTTagCompound.setString("OwnerUUID", "");
      } else {
        par1NBTTagCompound.setString("OwnerUUID", getOwnerUUID());
      } 
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    setSlimeSize(par1NBTTagCompound.getInteger("Size") + 1);
    setItem(par1NBTTagCompound.getInteger("Item"));
    setPlayerOwned(par1NBTTagCompound.getBoolean("Tamed"));
    if (getPlayerOwned()) {
      setR(par1NBTTagCompound.getFloat("R"));
      setG(par1NBTTagCompound.getFloat("G"));
      setB(par1NBTTagCompound.getFloat("B"));
      setSitting(par1NBTTagCompound.getBoolean("Sitting"));
      setParticle(par1NBTTagCompound.getInteger("Particle"));
      String s = "";
      if (par1NBTTagCompound.hasKey("OwnerUUID", 8)) {
        s = par1NBTTagCompound.getString("OwnerUUID");
      } else {
        String s1 = par1NBTTagCompound.getString("Owner");
        s = PreYggdrasilConverter.func_152719_a(s1);
      } 
      if (s.length() > 0) {
        setOwnerUUID(s);
        setPlayerOwned(true);
      } 
    } 
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
    return ((this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes((Entity)this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && isValidLightLevel() && this.posY < 40.0D) || super.getCanSpawnHere());
  }
}
