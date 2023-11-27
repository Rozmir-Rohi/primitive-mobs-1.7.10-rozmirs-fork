package net.daveyx0.primitivemobs.entity.passive;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class EntitySheepmanSmith extends EntitySheepman {
  ItemStack budget = null;
  
  int costs;
  
  public static final List<ItemStack> ore_gold_nugget =  OreDictionary.getOres("nuggetGold");
  
  public EntitySheepmanSmith(World par1World) {
    super(par1World);
    setAIMoveSpeed(0.3F);
    setWealth(0);
    this.costs = 0;
    setCustomNameTag("Sheepman Smith");
  }
  
  protected boolean canDespawn() {
    return !this.getInhabitant();
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataWatcher.addObject(21, new Integer(0));
  }
  
  public boolean interact(EntityPlayer par1EntityPlayer) {
    ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
    if (var2 != null && (var2.getItem() instanceof net.minecraft.item.ItemTool 
    		|| var2.getItem() instanceof net.minecraft.item.ItemBow
    		|| var2.getItem() instanceof net.minecraft.item.ItemSword
    		|| var2.getItem() instanceof net.minecraft.item.ItemFlintAndSteel
    		|| var2.getItem() instanceof net.minecraft.item.ItemFishingRod
    		|| var2.getItem() instanceof net.minecraft.item.ItemFishingRod
    		|| var2.getItem() instanceof net.minecraft.item.ItemShears
    		|| var2.getItem() instanceof net.minecraft.item.ItemCarrotOnAStick
    		|| var2.getItem() instanceof net.minecraft.item.ItemArmor)) 
    {
      if ((var2.getItem() == Items.wooden_pickaxe || var2.getItem() == Items.wooden_axe || var2.getItem() == Items.wooden_hoe || var2.getItem() == Items.wooden_sword || var2.getItem() == Items.wooden_shovel) && var2.getItemDamage() != 0) {
        this.costs = 1;
      }
      
      else if ((var2.getItem() == Items.stone_pickaxe || var2.getItem() == Items.stone_axe || var2.getItem() == Items.stone_hoe || var2.getItem() == Items.stone_sword || var2.getItem() == Items.stone_shovel || var2.getItem() == Items.leather_helmet || var2.getItem() == Items.leather_boots || var2.getItem() == Items.leather_chestplate || var2.getItem() == Items.leather_leggings) && var2.getItemDamage() != 0) {
        this.costs = 2;
      }
      
      else if ((var2.getItem() == Items.iron_pickaxe || var2.getItem() == Items.iron_axe || var2.getItem() == Items.iron_hoe || var2.getItem() == Items.iron_sword || var2.getItem() == Items.iron_shovel || var2.getItem() == Items.iron_helmet || var2.getItem() == Items.iron_boots || var2.getItem() == Items.iron_chestplate || var2.getItem() == Items.iron_leggings) && var2.getItemDamage() != 0) {
        this.costs = 20;
      }
      
      else if ((var2.getItem() == Items.golden_pickaxe || var2.getItem() == Items.golden_axe || var2.getItem() == Items.golden_hoe || var2.getItem() == Items.golden_sword || var2.getItem() == Items.golden_shovel || var2.getItem() == Items.golden_helmet || var2.getItem() == Items.golden_boots || var2.getItem() == Items.golden_chestplate || var2.getItem() == Items.golden_leggings) && var2.getItemDamage() != 0) {
        this.costs = 10;
      }
      
      else if ((var2.getItem() == Items.diamond_pickaxe || var2.getItem() == Items.diamond_axe || var2.getItem() == Items.diamond_hoe || var2.getItem() == Items.diamond_sword || var2.getItem() == Items.diamond_shovel || var2.getItem() == Items.diamond_helmet || var2.getItem() == Items.diamond_boots || var2.getItem() == Items.diamond_chestplate || var2.getItem() == Items.diamond_leggings) && var2.getItemDamage() != 0) {
        this.costs = 100;
      }
      
      else if (var2.getItem() == Items.bow && var2.getItemDamage() != 0) {
        this.costs = 15;
      }
      
      else if (var2.getItem() == Items.fishing_rod && var2.getItemDamage() != 0) {
        this.costs = 10;
      }
      
      else if (var2.getItem() == Items.carrot_on_a_stick && var2.getItemDamage() != 0) {
        this.costs = 10;
      }
      
      else if (var2.getItem() == Items.flint_and_steel && var2.getItemDamage() != 0) {
        this.costs = 10;
      }
      
      else if (var2.getItem() == Items.shears && var2
        .getItemDamage() != 0) {
        this.costs = 10;
      }
      
      else if (var2 != null && var2.getItemDamage() != 0) {
        this.costs = 100;
      } 
      
      
      int total = 0;
      
      
      for (int var4 = 0; var4 < par1EntityPlayer.inventory.mainInventory.length; var4++) 
      {
    	  if (par1EntityPlayer.inventory.mainInventory[var4] != null)
    	  {
    		  for (ItemStack ore : ore_gold_nugget) 
        		{
        			if (ore.getItem() == par1EntityPlayer.inventory.mainInventory[var4].getItem())
        				{total += (par1EntityPlayer.inventory.mainInventory[var4]).stackSize;} 
        		}
    	  }
      } 
      if (total >= this.costs && this.costs > 0) 
      {
    	  for (int var5 = 0; var5 < par1EntityPlayer.inventory.mainInventory.length; var5++) 
    	  {
    		  if (par1EntityPlayer.inventory.mainInventory[var5] != null)
          	  	{
    			  Item playerItem = par1EntityPlayer.inventory.mainInventory[var5].getItem();
    			  
    			  int playerItemID = par1EntityPlayer.inventory.mainInventory[var5].getItemDamage();
    			  
        	  		for (ItemStack ore : ore_gold_nugget) 
        	  		{	
        	  			Item oreItem = ore.getItem();
        	  			
        	  			int oreItemID = ore.getItemDamage();
        	  			
        	  			if (playerItem == oreItem && playerItemID == oreItemID)
        	  			{
        	  				if ((par1EntityPlayer.inventory.mainInventory[var5]).stackSize - this.costs <= 0)
        	  				{
        	  					this.costs -= (par1EntityPlayer.inventory.mainInventory[var5]).stackSize;
        	  					par1EntityPlayer.inventory.setInventorySlotContents(var5, null);
        	  				}
        	  				else
        	  				{
        	  					this.budget = par1EntityPlayer.inventory.mainInventory[var5];
        	  					this.budget.stackSize -= this.costs;
        	  					par1EntityPlayer.inventory.setInventorySlotContents(var5, this.budget);
        	  					var2.setItemDamage(0);
        	  					this.worldObj.playSoundAtEntity((Entity)this, "random.anvil_use", 1.0F, 1.0F);
        	  					for (int i = 0; i < 8; i++)
        	  						{this.worldObj.spawnParticle("crit", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);} 
        	  					this.costs = 0;
        	  					this.budget = null;
        	  					return true;
        	  				}  
        	  			}
        	  		}
          	  	}
    	  }
      }
      
      else
      {
        for (int i = 0; i < 8; i++)
          this.worldObj.spawnParticle("angryVillager", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
        this.costs = 0;
      } 
    }
    
    else if (var2 != null && var2.getItem() == Item.getItemFromBlock(Blocks.gold_block))
    {
      if (--var2.stackSize == 0)
        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null); 
      for (int i = 0; i < 8; i++)
        this.worldObj.spawnParticle("note", this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat() + 1.0D, this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D); 
      setInhabitant(true);
      return true;
    } 
    return false;
  }
  
  
  
  
  public int getWealth() {
    return this.dataWatcher.getWatchableObjectInt(21);
  }
  
  public void setWealth(int par1) {
    this.dataWatcher.updateObject(21, Integer.valueOf(par1));
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    par1NBTTagCompound.setInteger("Wealth", getWealth());
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    setWealth(par1NBTTagCompound.getInteger("Wealth"));
  }
}
