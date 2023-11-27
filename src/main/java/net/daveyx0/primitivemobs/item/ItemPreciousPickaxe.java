package net.daveyx0.primitivemobs.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPreciousPickaxe extends ItemPickaxe {
  public ItemPreciousPickaxe() {
    super(Item.ToolMaterial.EMERALD);
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
    setUnlocalizedName("PreciousPickaxe");
  }
  
  public void registerIcons(IIconRegister iconRegister) {
    this.itemIcon = iconRegister.registerIcon("minecraft:iron_pickaxe");
  }
  
  public String getUnlocalizedName(ItemStack par1ItemStack) {
    String localName = getUnlocalizedName().replace("item.", "");
    return "item.primitivemobs." + localName;
  }
  
  public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_) {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_) {
    return 16744491;
  }
  
  public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
    if (p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D)
      p_150894_1_.damageItem(1, p_150894_7_); 
    if (p_150894_7_ instanceof EntityPlayer && p_150894_2_.rand.nextInt(50) == 0)
      ((EntityPlayer)p_150894_7_).addPotionEffect(new PotionEffect(Potion.digSpeed.id, 180, 1)); 
    return true;
  }
}
