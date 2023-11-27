package net.daveyx0.primitivemobs.entity.passive;

import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntitySheepman extends EntityVillager implements IMerchant, INpc {
  private int randomTickDivider;
  
  private boolean isMating;
  
  private boolean isPlaying;
  
  Village villageObj;
  
  private EntityPlayer buyingPlayer;
  
  private MerchantRecipeList buyingList;
  
  private int timeUntilReset;
  
  private boolean needsInitilization;
  
  private int wealth;
  
  private String lastBuyingPlayer;
  
  private boolean isLookingForHome;
  
  private float field_82191_bN;
  
  public static final Map villagersSellingList = new HashMap<Object, Object>();
  
  public static final Map blacksmithSellingList = new HashMap<Object, Object>();
  
  private static final String __OBFID = "CL_00001707";
  
  public EntitySheepman(World p_i1747_1_) {
    this(p_i1747_1_, 0);
  }
  
  public EntitySheepman(World p_i1748_1_, int p_i1748_2_) {
    super(p_i1748_1_);
    this.isImmuneToFire = true;
    setInhabitant(false);
    setProfession(p_i1748_2_);
    setSize(0.6F, 1.8F);
    getNavigator().setBreakDoors(true);
    getNavigator().setAvoidsWater(true);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
    this.tasks.addTask(1, (EntityAIBase)new EntityAITradePlayer(this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAILookAtTradePlayer(this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveIndoors((EntityCreature)this));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIRestrictOpenDoor((EntityCreature)this));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.6D));
    this.tasks.addTask(6, (EntityAIBase)new EntityAIVillagerMate(this));
    this.tasks.addTask(7, (EntityAIBase)new EntityAIFollowGolem(this));
    this.tasks.addTask(8, (EntityAIBase)new EntityAIPlay(this, 0.32D));
    this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityPlayer.class, 3.0F, 1.0F));
    this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityVillager.class, 5.0F, 0.02F));
    this.tasks.addTask(9, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6D));
    this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
    setCustomNameTag("Sheepman");
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
  }
  
  public boolean isAIEnabled() {
    return true;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(20, Byte.valueOf((byte)0));
  }
  
  public boolean getInhabitant() {
    return ((this.dataWatcher.getWatchableObjectByte(20) & 0x1) != 0);
  }
  
  public void setInhabitant(boolean par1) {
    if (par1) {
      this.dataWatcher.updateObject(20, Byte.valueOf((byte)1));
    } else {
      this.dataWatcher.updateObject(20, Byte.valueOf((byte)0));
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
    super.writeEntityToNBT(p_70014_1_);
    p_70014_1_.setInteger("Profession", getProfession());
    p_70014_1_.setInteger("Riches", this.wealth);
    if (this.buyingList != null)
      p_70014_1_.setTag("Offers", (NBTBase)this.buyingList.getRecipiesAsTags()); 
    p_70014_1_.setBoolean("Village", getInhabitant());
  }
  
  public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
    super.readEntityFromNBT(p_70037_1_);
    setProfession(p_70037_1_.getInteger("Profession"));
    this.wealth = p_70037_1_.getInteger("Riches");
    if (p_70037_1_.hasKey("Offers", 10)) {
      NBTTagCompound nbttagcompound1 = p_70037_1_.getCompoundTag("Offers");
      this.buyingList = new MerchantRecipeList(nbttagcompound1);
    } 
    setInhabitant(p_70037_1_.getBoolean("Village"));
  }
  
  protected boolean canDespawn() {
	  return !this.getInhabitant();
  }
  
  protected void updateAITick() {
    if (--this.randomTickDivider <= 0) {
      this.worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
      this.randomTickDivider = 70 + this.rand.nextInt(50);
      this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);
      if (this.villageObj == null) {
        detachHome();
      } else {
        ChunkCoordinates chunkcoordinates = this.villageObj.getCenter();
        setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, (int)(this.villageObj.getVillageRadius() * 0.6F));
        if (this.isLookingForHome) {
          this.isLookingForHome = false;
          this.villageObj.setDefaultPlayerReputation(5);
        } 
      } 
    } 
    if (!isTrading() && this.timeUntilReset > 0) {
      this.timeUntilReset--;
      if (this.timeUntilReset <= 0) {
        if (this.needsInitilization) {
          if (this.buyingList.size() > 1) {
            Iterator<MerchantRecipe> iterator = this.buyingList.iterator();
            while (iterator.hasNext()) {
              MerchantRecipe merchantrecipe = iterator.next();
              if (merchantrecipe.isRecipeDisabled())
                merchantrecipe.func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2); 
            } 
          } 
          addDefaultEquipmentAndRecipies(1);
          this.needsInitilization = false;
          if (this.villageObj != null && this.lastBuyingPlayer != null) {
            this.worldObj.setEntityState((Entity)this, (byte)14);
            this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
          } 
        } 
        addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
      } 
    } 
    super.updateAITick();
  }
  
  public boolean interact(EntityPlayer par1EntityPlayer) {
    ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
    boolean flag = (itemstack != null && itemstack.getItem() == Items.spawn_egg);
    if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.gold_block)) {
      if (--itemstack.stackSize == 0)
        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null); 
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("note", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      setInhabitant(true);
      return true;
    } 
    if (!flag && isEntityAlive() && !isTrading() && !isChild()) {
      if (!this.worldObj.isRemote) {
        setCustomer(par1EntityPlayer);
        par1EntityPlayer.displayGUIMerchant(this, getCustomNameTag());
      } 
      return true;
    } 
    return super.interact(par1EntityPlayer);
  }
  
  protected String getLivingSound() {
    return "mob.sheep.say";
  }
  
  protected String getHurtSound() {
    return "mob.sheep.say";
  }
  
  protected String getDeathSound() {
    return "mob.sheep.say";
  }
  
  public void setProfession(int p_70938_1_) {
    this.dataWatcher.updateObject(16, Integer.valueOf(p_70938_1_));
  }
  
  public int getProfession() {
    return this.dataWatcher.getWatchableObjectInt(16);
  }
  
  public boolean isMating() {
    return this.isMating;
  }
  
  public void setMating(boolean p_70947_1_) {
    this.isMating = p_70947_1_;
  }
  
  public void setPlaying(boolean p_70939_1_) {
    this.isPlaying = p_70939_1_;
  }
  
  public boolean isPlaying() {
    return this.isPlaying;
  }
  
  public void setRevengeTarget(EntityLivingBase p_70604_1_) {
    super.setRevengeTarget(p_70604_1_);
    if (this.villageObj != null && p_70604_1_ != null) {
      this.villageObj.addOrRenewAgressor(p_70604_1_);
      if (p_70604_1_ instanceof EntityPlayer) {
        byte b0 = -1;
        if (isChild())
          b0 = -3; 
        this.villageObj.setReputationForPlayer(p_70604_1_.getCommandSenderName(), b0);
        if (isEntityAlive())
          this.worldObj.setEntityState((Entity)this, (byte)13); 
      } 
    } 
  }
  
  public void onDeath(DamageSource p_70645_1_) {
    if (this.villageObj != null) {
      Entity entity = p_70645_1_.getEntity();
      if (entity != null) {
        if (entity instanceof EntityPlayer) {
          this.villageObj.setReputationForPlayer(entity.getCommandSenderName(), -2);
        } else if (entity instanceof net.minecraft.entity.monster.IMob) {
          this.villageObj.endMatingSeason();
        } 
      } else if (entity == null) {
        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity((Entity)this, 16.0D);
        if (entityplayer != null)
          this.villageObj.endMatingSeason(); 
      } 
    } 
    super.onDeath(p_70645_1_);
  }
  
  public void setCustomer(EntityPlayer p_70932_1_) {
    this.buyingPlayer = p_70932_1_;
  }
  
  public EntityPlayer getCustomer() {
    return this.buyingPlayer;
  }
  
  public boolean isTrading() {
    return (this.buyingPlayer != null);
  }
  
  public void useRecipe(MerchantRecipe p_70933_1_) {
    p_70933_1_.incrementToolUses();
    this.livingSoundTime = -getTalkInterval();
    playSound("mob.sheep.say", getSoundVolume(), 1.5F);
    if (p_70933_1_.hasSameIDsAs((MerchantRecipe)this.buyingList.get(this.buyingList.size() - 1))) {
      this.timeUntilReset = 40;
      this.needsInitilization = true;
      if (this.buyingPlayer != null) {
        this.lastBuyingPlayer = this.buyingPlayer.getCommandSenderName();
      } else {
        this.lastBuyingPlayer = null;
      } 
    } 
    if (p_70933_1_.getItemToBuy().getItem() == Items.gold_ingot)
      this.wealth += (p_70933_1_.getItemToBuy()).stackSize; 
  }
  
  public void func_110297_a_(ItemStack p_110297_1_) {
    if (!this.worldObj.isRemote && this.livingSoundTime > -getTalkInterval() + 20) {
      this.livingSoundTime = -getTalkInterval();
      if (p_110297_1_ != null) {
        playSound("mob.sheep.say", getSoundVolume(), 1.5F);
      } else {
        playSound("mob.sheep.say", getSoundVolume(), 0.5F);
      } 
    } 
  }
  
  public MerchantRecipeList getRecipes(EntityPlayer p_70934_1_) {
    if (this.buyingList == null)
      addDefaultEquipmentAndRecipies(1); 
    return this.buyingList;
  }
  
  private float adjustProbability(float p_82188_1_) {
    float f1 = p_82188_1_ + this.field_82191_bN;
    return (f1 > 0.9F) ? (0.9F - f1 - 0.9F) : f1;
  }
  
  private void addDefaultEquipmentAndRecipies(int p_70950_1_) {
    int k;
    Item[] aitem, aitem1;
    int j;
    if (this.buyingList != null) {
      this.field_82191_bN = MathHelper.sqrt_float(this.buyingList.size()) * 0.2F;
    } else {
      this.field_82191_bN = 0.0F;
    } 
    MerchantRecipeList merchantrecipelist = new MerchantRecipeList();
    VillagerRegistry.manageVillagerTrades(merchantrecipelist, this, getProfession(), this.rand);
    switch (getProfession()) {
      case 0:
        func_146091_a(merchantrecipelist, Items.wheat, this.rand, adjustProbability(0.9F));
        func_146091_a(merchantrecipelist, Item.getItemFromBlock(Blocks.wool), this.rand, adjustProbability(0.5F));
        func_146091_a(merchantrecipelist, Items.chicken, this.rand, adjustProbability(0.5F));
        func_146091_a(merchantrecipelist, Items.cooked_fished, this.rand, adjustProbability(0.4F));
        func_146089_b(merchantrecipelist, Items.bread, this.rand, adjustProbability(0.9F));
        func_146089_b(merchantrecipelist, Items.melon, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Items.apple, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Items.cookie, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, (Item)Items.shears, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Items.flint_and_steel, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Items.cooked_chicken, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Items.arrow, this.rand, adjustProbability(0.5F));
        if (this.rand.nextFloat() < adjustProbability(0.5F))
          merchantrecipelist.add(new MerchantRecipe(new ItemStack(Blocks.gravel, 10), new ItemStack(Items.gold_ingot), new ItemStack(Items.flint, 4 + this.rand.nextInt(2), 0))); 
        break;
      case 1:
        func_146091_a(merchantrecipelist, Items.paper, this.rand, adjustProbability(0.8F));
        func_146091_a(merchantrecipelist, Items.book, this.rand, adjustProbability(0.8F));
        func_146091_a(merchantrecipelist, Items.written_book, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Item.getItemFromBlock(Blocks.bookshelf), this.rand, adjustProbability(0.8F));
        func_146089_b(merchantrecipelist, Item.getItemFromBlock(Blocks.glass), this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, Items.compass, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, Items.clock, this.rand, adjustProbability(0.2F));
        if (this.rand.nextFloat() < adjustProbability(0.07F)) {
          Enchantment enchantment = Enchantment.enchantmentsBookList[this.rand.nextInt(Enchantment.enchantmentsBookList.length)];
          int i1 = MathHelper.getRandomIntegerInRange(this.rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
          ItemStack itemstack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i1));
          int i = 2 + this.rand.nextInt(5 + i1 * 10) + 3 * i1;
          merchantrecipelist.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.gold_ingot, i), itemstack));
        } 
        break;
      case 2:
        func_146089_b(merchantrecipelist, Items.ender_eye, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Items.experience_bottle, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, Items.redstone, this.rand, adjustProbability(0.4F));
        func_146089_b(merchantrecipelist, Item.getItemFromBlock(Blocks.glowstone), this.rand, adjustProbability(0.3F));
        aitem = new Item[] { Items.iron_sword, Items.diamond_sword, (Item)Items.iron_chestplate, (Item)Items.diamond_chestplate, Items.iron_axe, Items.diamond_axe, Items.iron_pickaxe, Items.diamond_pickaxe };
        aitem1 = aitem;
        j = aitem.length;
        k = 0;
        while (k < j) {
          Item item = aitem1[k];
          if (this.rand.nextFloat() < adjustProbability(0.05F))
            merchantrecipelist.add(new MerchantRecipe(new ItemStack(item, 1, 0), new ItemStack(Items.gold_ingot, 2 + this.rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(item, 1, 0), 5 + this.rand.nextInt(15)))); 
          k++;
        } 
        break;
      case 3:
        func_146091_a(merchantrecipelist, Items.coal, this.rand, adjustProbability(0.7F));
        func_146091_a(merchantrecipelist, Items.iron_ingot, this.rand, adjustProbability(0.5F));
        func_146091_a(merchantrecipelist, Items.gold_ingot, this.rand, adjustProbability(0.5F));
        func_146091_a(merchantrecipelist, Items.diamond, this.rand, adjustProbability(0.5F));
        func_146089_b(merchantrecipelist, Items.iron_sword, this.rand, adjustProbability(0.5F));
        func_146089_b(merchantrecipelist, Items.diamond_sword, this.rand, adjustProbability(0.5F));
        func_146089_b(merchantrecipelist, Items.iron_axe, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Items.diamond_axe, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Items.iron_pickaxe, this.rand, adjustProbability(0.5F));
        func_146089_b(merchantrecipelist, Items.diamond_pickaxe, this.rand, adjustProbability(0.5F));
        func_146089_b(merchantrecipelist, Items.iron_shovel, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, Items.diamond_shovel, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, Items.iron_hoe, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, Items.diamond_hoe, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, (Item)Items.iron_boots, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, (Item)Items.diamond_boots, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, (Item)Items.iron_helmet, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, (Item)Items.diamond_helmet, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, (Item)Items.iron_chestplate, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, (Item)Items.diamond_chestplate, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, (Item)Items.iron_leggings, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, (Item)Items.diamond_leggings, this.rand, adjustProbability(0.2F));
        func_146089_b(merchantrecipelist, (Item)Items.chainmail_boots, this.rand, adjustProbability(0.1F));
        func_146089_b(merchantrecipelist, (Item)Items.chainmail_helmet, this.rand, adjustProbability(0.1F));
        func_146089_b(merchantrecipelist, (Item)Items.chainmail_chestplate, this.rand, adjustProbability(0.1F));
        func_146089_b(merchantrecipelist, (Item)Items.chainmail_leggings, this.rand, adjustProbability(0.1F));
        break;
      case 4:
        func_146091_a(merchantrecipelist, Items.coal, this.rand, adjustProbability(0.7F));
        func_146091_a(merchantrecipelist, Items.porkchop, this.rand, adjustProbability(0.5F));
        func_146091_a(merchantrecipelist, Items.beef, this.rand, adjustProbability(0.5F));
        func_146089_b(merchantrecipelist, Items.saddle, this.rand, adjustProbability(0.1F));
        func_146089_b(merchantrecipelist, (Item)Items.leather_chestplate, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, (Item)Items.leather_boots, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, (Item)Items.leather_helmet, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, (Item)Items.leather_leggings, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Items.cooked_porkchop, this.rand, adjustProbability(0.3F));
        func_146089_b(merchantrecipelist, Items.cooked_beef, this.rand, adjustProbability(0.3F));
        break;
    } 
    if (merchantrecipelist.isEmpty())
      func_146091_a(merchantrecipelist, Items.gold_ingot, this.rand, 1.0F); 
    Collections.shuffle((List<?>)merchantrecipelist);
    if (this.buyingList == null)
      this.buyingList = new MerchantRecipeList(); 
    for (int l = 0; l < p_70950_1_ && l < merchantrecipelist.size(); l++)
      this.buyingList.addToListWithCheck((MerchantRecipe)merchantrecipelist.get(l)); 
  }
  
  @SideOnly(Side.CLIENT)
  public void setRecipes(MerchantRecipeList p_70930_1_) {}
  
  public static void func_146091_a(MerchantRecipeList p_146091_0_, Item p_146091_1_, Random p_146091_2_, float p_146091_3_) {
    if (p_146091_2_.nextFloat() < p_146091_3_)
      p_146091_0_.add(new MerchantRecipe(func_146088_a(p_146091_1_, p_146091_2_), Items.gold_ingot)); 
  }
  
  private static ItemStack func_146088_a(Item p_146088_0_, Random p_146088_1_) {
    return new ItemStack(p_146088_0_, func_146092_b(p_146088_0_, p_146088_1_), 0);
  }
  
  private static int func_146092_b(Item p_146092_0_, Random p_146092_1_) {
    Tuple tuple = (Tuple)villagersSellingList.get(p_146092_0_);
    return (tuple == null) ? 1 : ((((Integer)tuple.getFirst()).intValue() >= ((Integer)tuple.getSecond()).intValue()) ? ((Integer)tuple.getFirst()).intValue() : (((Integer)tuple.getFirst()).intValue() + p_146092_1_.nextInt(((Integer)tuple.getSecond()).intValue() - ((Integer)tuple.getFirst()).intValue())));
  }
  
  public static void func_146089_b(MerchantRecipeList p_146089_0_, Item p_146089_1_, Random p_146089_2_, float p_146089_3_) {
    if (p_146089_2_.nextFloat() < p_146089_3_) {
      ItemStack itemstack, itemstack1;
      int i = func_146090_c(p_146089_1_, p_146089_2_);
      if (i < 0) {
        itemstack = new ItemStack(Items.gold_ingot, 1, 0);
        itemstack1 = new ItemStack(p_146089_1_, -i, 0);
      } else {
        itemstack = new ItemStack(Items.gold_ingot, i, 0);
        itemstack1 = new ItemStack(p_146089_1_, 1, 0);
      } 
      p_146089_0_.add(new MerchantRecipe(itemstack, itemstack1));
    } 
  }
  
  private static int func_146090_c(Item p_146090_0_, Random p_146090_1_) {
    Tuple tuple = (Tuple)blacksmithSellingList.get(p_146090_0_);
    return (tuple == null) ? 1 : ((((Integer)tuple.getFirst()).intValue() >= ((Integer)tuple.getSecond()).intValue()) ? ((Integer)tuple.getFirst()).intValue() : (((Integer)tuple.getFirst()).intValue() + p_146090_1_.nextInt(((Integer)tuple.getSecond()).intValue() - ((Integer)tuple.getFirst()).intValue())));
  }
  
  @SideOnly(Side.CLIENT)
  public void handleHealthUpdate(byte p_70103_1_) {
    if (p_70103_1_ == 12) {
      generateRandomParticles("heart");
    } else if (p_70103_1_ == 13) {
      generateRandomParticles("angryVillager");
    } else if (p_70103_1_ == 14) {
      generateRandomParticles("happyVillager");
    } else {
      super.handleHealthUpdate(p_70103_1_);
    } 
  }
  
  public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
    p_110161_1_ = super.onSpawnWithEgg(p_110161_1_);
    VillagerRegistry.applyRandomTrade(this, this.worldObj.rand);
    return p_110161_1_;
  }
  
  @SideOnly(Side.CLIENT)
  private void generateRandomParticles(String p_70942_1_) {
    for (int i = 0; i < 5; i++) {
      double d0 = this.rand.nextGaussian() * 0.02D;
      double d1 = this.rand.nextGaussian() * 0.02D;
      double d2 = this.rand.nextGaussian() * 0.02D;
      this.worldObj.spawnParticle(p_70942_1_, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 1.0D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2);
    } 
  }
  
  public void setLookingForHome() {
    this.isLookingForHome = true;
  }
  
  public EntityVillager createChild(EntityAgeable p_90011_1_) {
    EntityVillager entityvillager = new EntityVillager(this.worldObj);
    entityvillager.onSpawnWithEgg((IEntityLivingData)null);
    return entityvillager;
  }
  
  public boolean allowLeashing() {
    return false;
  }
  
  static {
    villagersSellingList.put(Items.coal, new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
    villagersSellingList.put(Items.iron_ingot, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
    villagersSellingList.put(Items.gold_ingot, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
    villagersSellingList.put(Items.diamond, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
    villagersSellingList.put(Items.paper, new Tuple(Integer.valueOf(24), Integer.valueOf(36)));
    villagersSellingList.put(Items.book, new Tuple(Integer.valueOf(11), Integer.valueOf(13)));
    villagersSellingList.put(Items.written_book, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    villagersSellingList.put(Items.ender_pearl, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
    villagersSellingList.put(Items.ender_eye, new Tuple(Integer.valueOf(2), Integer.valueOf(3)));
    villagersSellingList.put(Items.porkchop, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
    villagersSellingList.put(Items.beef, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
    villagersSellingList.put(Items.chicken, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
    villagersSellingList.put(Items.cooked_fished, new Tuple(Integer.valueOf(9), Integer.valueOf(13)));
    villagersSellingList.put(Items.wheat_seeds, new Tuple(Integer.valueOf(34), Integer.valueOf(48)));
    villagersSellingList.put(Items.melon_seeds, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
    villagersSellingList.put(Items.pumpkin_seeds, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
    villagersSellingList.put(Items.wheat, new Tuple(Integer.valueOf(18), Integer.valueOf(22)));
    villagersSellingList.put(Item.getItemFromBlock(Blocks.wool), new Tuple(Integer.valueOf(14), Integer.valueOf(22)));
    villagersSellingList.put(Items.rotten_flesh, new Tuple(Integer.valueOf(36), Integer.valueOf(64)));
    blacksmithSellingList.put(Items.flint_and_steel, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
    blacksmithSellingList.put(Items.shears, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
    blacksmithSellingList.put(Items.iron_sword, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
    blacksmithSellingList.put(Items.diamond_sword, new Tuple(Integer.valueOf(12), Integer.valueOf(14)));
    blacksmithSellingList.put(Items.iron_axe, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
    blacksmithSellingList.put(Items.diamond_axe, new Tuple(Integer.valueOf(9), Integer.valueOf(12)));
    blacksmithSellingList.put(Items.iron_pickaxe, new Tuple(Integer.valueOf(7), Integer.valueOf(9)));
    blacksmithSellingList.put(Items.diamond_pickaxe, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
    blacksmithSellingList.put(Items.iron_shovel, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
    blacksmithSellingList.put(Items.diamond_shovel, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
    blacksmithSellingList.put(Items.iron_hoe, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
    blacksmithSellingList.put(Items.diamond_hoe, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
    blacksmithSellingList.put(Items.iron_boots, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
    blacksmithSellingList.put(Items.diamond_boots, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
    blacksmithSellingList.put(Items.iron_helmet, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
    blacksmithSellingList.put(Items.diamond_helmet, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
    blacksmithSellingList.put(Items.iron_chestplate, new Tuple(Integer.valueOf(10), Integer.valueOf(14)));
    blacksmithSellingList.put(Items.diamond_chestplate, new Tuple(Integer.valueOf(16), Integer.valueOf(19)));
    blacksmithSellingList.put(Items.iron_leggings, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
    blacksmithSellingList.put(Items.diamond_leggings, new Tuple(Integer.valueOf(11), Integer.valueOf(14)));
    blacksmithSellingList.put(Items.chainmail_boots, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
    blacksmithSellingList.put(Items.chainmail_helmet, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
    blacksmithSellingList.put(Items.chainmail_chestplate, new Tuple(Integer.valueOf(11), Integer.valueOf(15)));
    blacksmithSellingList.put(Items.chainmail_leggings, new Tuple(Integer.valueOf(9), Integer.valueOf(11)));
    blacksmithSellingList.put(Items.bread, new Tuple(Integer.valueOf(-4), Integer.valueOf(-2)));
    blacksmithSellingList.put(Items.melon, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
    blacksmithSellingList.put(Items.apple, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
    blacksmithSellingList.put(Items.cookie, new Tuple(Integer.valueOf(-10), Integer.valueOf(-7)));
    blacksmithSellingList.put(Item.getItemFromBlock(Blocks.glass), new Tuple(Integer.valueOf(-5), Integer.valueOf(-3)));
    blacksmithSellingList.put(Item.getItemFromBlock(Blocks.bookshelf), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
    blacksmithSellingList.put(Items.leather_chestplate, new Tuple(Integer.valueOf(4), Integer.valueOf(5)));
    blacksmithSellingList.put(Items.leather_boots, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
    blacksmithSellingList.put(Items.leather_helmet, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
    blacksmithSellingList.put(Items.leather_leggings, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
    blacksmithSellingList.put(Items.saddle, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
    blacksmithSellingList.put(Items.experience_bottle, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
    blacksmithSellingList.put(Items.redstone, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
    blacksmithSellingList.put(Items.compass, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
    blacksmithSellingList.put(Items.clock, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
    blacksmithSellingList.put(Item.getItemFromBlock(Blocks.glowstone), new Tuple(Integer.valueOf(-3), Integer.valueOf(-1)));
    blacksmithSellingList.put(Items.cooked_porkchop, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
    blacksmithSellingList.put(Items.cooked_beef, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
    blacksmithSellingList.put(Items.cooked_chicken, new Tuple(Integer.valueOf(-8), Integer.valueOf(-6)));
    blacksmithSellingList.put(Items.ender_eye, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
    blacksmithSellingList.put(Items.arrow, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
  }
}
