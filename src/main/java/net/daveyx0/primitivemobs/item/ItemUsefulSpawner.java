package net.daveyx0.primitivemobs.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.entity.monster.EntityDMimic;
import net.daveyx0.primitivemobs.entity.monster.EntityDMinion;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemUsefulSpawner extends Item {
  private IIcon theIcon;
  
  public ItemUsefulSpawner() {
    this.maxStackSize = 1;
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
    setUnlocalizedName("Spawner");
  }
  
  public static String[] names = new String[] { 
      "EvilMimic", "SkeletonWarriorMimic", "HauntedToolMimic", "BatMimic", "ZombieMimic", "ExplosionMimic", "EnderMimic", "PetTreasureSlime", "PreciousPickaxe", "RedSkullMinion", 
      "BlueSkullMinion", "GreenSkullMinion", "YellowSkullMinion", "MagentaSkullMinion", "WitherSkullMinion" };
  
  @SideOnly(Side.CLIENT)
  private IIcon[] icons;
  
  public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
    if (!par3EntityPlayer.capabilities.isCreativeMode)
      par1ItemStack.stackSize--; 
    if (!par2World.isRemote)
      if (getDamage(par1ItemStack) <= 6) {
        EntityDMimic entity = new EntityDMimic(par2World);
        entity.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, par3EntityPlayer.rotationYaw, 0.0F);
        entity.setMimic(getDamage(par1ItemStack) + 1);
        par2World.spawnEntityInWorld((Entity)entity);
      } else if (getDamage(par1ItemStack) == 7) {
        EntityTreasureSlime entity = new EntityTreasureSlime(par2World);
        entity.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, par3EntityPlayer.rotationYaw, 0.0F);
        entity.setItem(20);
        par2World.spawnEntityInWorld((Entity)entity);
      } else if (getDamage(par1ItemStack) == 8) {
        EntityHauntedTool entity = new EntityHauntedTool(par2World);
        entity.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, par3EntityPlayer.rotationYaw, 0.0F);
        entity.setTool(21);
        par2World.spawnEntityInWorld((Entity)entity);
      } else if (getDamage(par1ItemStack) > 8 && getDamage(par1ItemStack) <= 14) {
        EntityDMinion entity = new EntityDMinion(par2World);
        entity.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, par3EntityPlayer.rotationYaw, 0.0F);
        entity.setOwnerUUID(par3EntityPlayer.getUniqueID().toString());
        entity.setFlames(getDamage(par1ItemStack) - 8);
        par2World.spawnEntityInWorld((Entity)entity);
      }  
    return par1ItemStack;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean requiresMultipleRenderPasses() {
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister par1IconRegister) {
    this.icons = new IIcon[15];
    for (int i = 0; i < this.icons.length; i++)
      this.icons[i] = par1IconRegister.registerIcon("primitivemobs:spawner"); 
  }
  
  public IIcon func_77617_a(int par1) {
    return this.icons[par1];
  }
  
  @SideOnly(Side.CLIENT)
  public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_) {
    if (getDamage(p_82790_1_) <= 6)
      return 11237677; 
    if (getDamage(p_82790_1_) == 7)
      return 4877080; 
    if (getDamage(p_82790_1_) == 8)
      return 4797973; 
    if (getDamage(p_82790_1_) > 8 && getDamage(p_82790_1_) <= 14)
      return 5526114; 
    return 11237677;
  }
  
  public String getUnlocalizedName(ItemStack par1ItemStack) {
    int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
    String localName = getUnlocalizedName().replace("item.", "");
    localName = names[i] + localName;
    return "item.primitivemobs." + localName;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_150895_a(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
    for (int x = 0; x < names.length; x++)
      par3List.add(new ItemStack(this, 1, x)); 
  }
}
