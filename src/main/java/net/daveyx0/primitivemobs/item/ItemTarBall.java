package net.daveyx0.primitivemobs.item;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.entity.item.EntityCustomBall;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTarBall extends Item {
  protected EntityCustomBall entityBall;
  
  public ItemTarBall() {
    this.maxStackSize = 64;
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
    setUnlocalizedName("TarBall");
  }
  
  public void registerIcons(IIconRegister iconRegister) {
    this.itemIcon = iconRegister.registerIcon("primitivemobs:tarball");
  }
  
  public String getUnlocalizedName(ItemStack par1ItemStack) {
    String localName = getUnlocalizedName().replace("item.", "");
    return "item.primitivemobs." + localName;
  }
  
  public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
    if (!par3EntityPlayer.capabilities.isCreativeMode)
      par1ItemStack.stackSize--; 
    par2World.playSoundAtEntity((Entity)par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand
        .nextFloat() * 0.4F + 0.8F));
    if (!par2World.isRemote) {
      this.entityBall = new EntityCustomBall(par2World, (EntityLivingBase)par3EntityPlayer);
      this.entityBall.setIndex(0);
      par2World.spawnEntityInWorld((Entity)this.entityBall);
    } 
    return par1ItemStack;
  }
}
