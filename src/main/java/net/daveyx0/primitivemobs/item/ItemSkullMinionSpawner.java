package net.daveyx0.primitivemobs.item;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.entity.monster.EntityDMinion;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSkullMinionSpawner extends Item {
  public ItemSkullMinionSpawner() {
    this.maxStackSize = 64;
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
    setUnlocalizedName("SkullMinionOrb");
    BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinionBehavior);
  }
  
  public void registerIcons(IIconRegister iconRegister) {
    this.itemIcon = iconRegister.registerIcon("primitivemobs:skullminionorb");
  }
  
  public String getUnlocalizedName(ItemStack par1ItemStack) {
    String localName = getUnlocalizedName().replace("item.", "");
    return "item.primitivemobs." + localName;
  }
  
  private static final IBehaviorDispenseItem dispenserMinionBehavior = (IBehaviorDispenseItem)new BehaviorDefaultDispenseItem() {
      public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
        World world = p_82487_1_.getWorld();
        double d0 = p_82487_1_.getX() + (enumfacing.getFrontOffsetX() * 1.125F);
        double d1 = p_82487_1_.getY() + (enumfacing.getFrontOffsetY() * 1.125F);
        double d2 = p_82487_1_.getZ() + (enumfacing.getFrontOffsetZ() * 1.125F);
        int i = p_82487_1_.getXInt() + enumfacing.getFrontOffsetX();
        int j = p_82487_1_.getYInt() + enumfacing.getFrontOffsetY();
        int k = p_82487_1_.getZInt() + enumfacing.getFrontOffsetZ();
        Block block = world.getBlock(i, j, k);
        EntityDMinion skull = new EntityDMinion(world);
        skull.setLocationAndAngles(d0, d1, d2, 0.0F, 0.0F);
        skull.setOwnerUUID("0");
        world.spawnEntityInWorld((Entity)skull);
        p_82487_2_.splitStack(1);
        return p_82487_2_;
      }
      
      protected void playDispenseSound(IBlockSource p_82485_1_) {
        p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
      }
    };
  
  public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
    if (!par3EntityPlayer.capabilities.isCreativeMode)
      par1ItemStack.stackSize--; 
    par2World.playSoundAtEntity((Entity)par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand
        .nextFloat() * 0.4F + 0.8F));
    if (!par2World.isRemote) {
      EntityDMinion skull = new EntityDMinion(par2World);
      skull.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, par3EntityPlayer.rotationYaw, 0.0F);
      skull.setOwnerUUID(par3EntityPlayer.getUniqueID().toString());
      skull.setFlames(itemRand.nextInt(6) + 1);
      par2World.spawnEntityInWorld((Entity)skull);
    } 
    return par1ItemStack;
  }
}
