package net.daveyx0.primitivemobs.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.entity.item.EntityCustomSpawnEgg;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemCustomEgg extends Item {
  protected EntityCustomSpawnEgg entityEgg;
  
  private IIcon theIcon;
  
  public ItemCustomEgg() {
    this.maxStackSize = 1;
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
    setUnlocalizedName("Egg");
  }
  
  public static String[] names = new String[] { "Spider", "Mystery" };
  
  public static String[] textureNames = new String[] { "spider", "mystery" };
  
  @SideOnly(Side.CLIENT)
  private IIcon[] icons;
  
  public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
    if (!par3EntityPlayer.capabilities.isCreativeMode)
      par1ItemStack.stackSize--; 
    par2World.playSoundAtEntity((Entity)par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand
        .nextFloat() * 0.4F + 0.8F));
    if (!par2World.isRemote) {
      this.entityEgg = new EntityCustomSpawnEgg(par2World, (EntityLivingBase)par3EntityPlayer);
      this.entityEgg.setIndex(MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15));
      par2World.spawnEntityInWorld((Entity)this.entityEgg);
    } 
    return par1ItemStack;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean requiresMultipleRenderPasses() {
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister par1IconRegister) {
    this.icons = new IIcon[2];
    for (int i = 0; i < this.icons.length; i++)
      this.icons[i] = par1IconRegister.registerIcon("primitivemobs:" + textureNames[i] + "egg"); 
  }
  
  public IIcon func_77617_a(int par1) {
    return this.icons[par1];
  }
  
  public String getUnlocalizedName(ItemStack par1ItemStack) {
    int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
    String localName = getUnlocalizedName().replace("item.", "");
    localName = names[i] + localName;
    return "item.primitivemobs." + localName;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_150895_a(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
    for (int x = 0; x < 2; x++)
      par3List.add(new ItemStack(this, 1, x)); 
  }
}
