package net.daveyx0.primitivemobs.item;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.io.InputStream;
import java.util.Set;
import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.common.packets.PrimitiveColorPacket;
import net.daveyx0.primitivemobs.lib.ImageTester;
import net.daveyx0.primitivemobs.lib.ResourceChecker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;

public class ItemCamouflageArmor extends ItemArmor {
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
  
  byte camo = 0;
  
  byte camoChanged;
  
  String[] names = new String[] { "CamouflageHelmet", "CamouflageChestplate", "CamouflageLeggings", "CamouflageBoots" };
  
  private Color thisArmorColor = new Color(255, 255, 255);
  
  public ItemCamouflageArmor(int p_i45325_3_) {
    super(ItemArmor.ArmorMaterial.CLOTH, 0, p_i45325_3_);
    setCreativeTab((CreativeTabs)PrimitiveMobs.tabPrimitiveMobs);
    setUnlocalizedName(this.names[p_i45325_3_]);
    this.currentBlock = Blocks.bedrock;
  }
  
  public String getUnlocalizedName(ItemStack par1ItemStack) {
    String localName = getUnlocalizedName().replace("item.", "");
    return "item.primitivemobs." + localName;
  }
  
  public boolean hasContainerItem(ItemStack stack) {
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister p_94581_1_) {
    this.iconString = "minecraft:leather_helmet";
    super.registerIcons(p_94581_1_);
    switch (this.armorType) {
      case 0:
        this.itemIcon = p_94581_1_.registerIcon("minecraft:leather_helmet");
        return;
      case 1:
        this.itemIcon = p_94581_1_.registerIcon("minecraft:leather_chestplate");
        return;
      case 2:
        this.itemIcon = p_94581_1_.registerIcon("minecraft:leather_leggings");
        return;
      case 3:
        this.itemIcon = p_94581_1_.registerIcon("minecraft:leather_boots");
        return;
    } 
    this.itemIcon = p_94581_1_.registerIcon("minecraft:leather_helmet");
  }
  
  @SideOnly(Side.CLIENT)
  public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_) {
    return func_82814_b(p_82790_1_);
  }
  
  public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
    this.camoChanged = getNoCamo(armor);
    if (player != null && this.camoChanged != this.camo) {
      this.camo = this.camoChanged;
      surroundWithParticles(player);
    } 
    if (player != null && world.isRemote && getNoCamo(armor) == 0)
      changeColor(world, player); 
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
      if (this.armorType == 0) {
        PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveColorPacket("3" + (new Color(this.R / 255.0F, this.G / 255.0F, this.B / 255.0F)).hashCode()));
      } else if (this.armorType == 1) {
        PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveColorPacket("2" + (new Color(this.R / 255.0F, this.G / 255.0F, this.B / 255.0F)).hashCode()));
      } else if (this.armorType == 2) {
        PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveColorPacket("1" + (new Color(this.R / 255.0F, this.G / 255.0F, this.B / 255.0F)).hashCode()));
      } else if (this.armorType == 3) {
        PrimitiveMobs.network.sendToServer((IMessage)new PrimitiveColorPacket("0" + (new Color(this.R / 255.0F, this.G / 255.0F, this.B / 255.0F)).hashCode()));
      } 
    } else {
      this.hasChanged = true;
    } 
    int currentColor = func_82814_b(armor);
    if (currentColor == -9345685 && this.hasChanged) {
      player.addStat((StatBase)PrimitiveMobsAchievementPage.specialColor1, 1);
    } else if (currentColor == -13279398 && this.hasChanged) {
      player.addStat((StatBase)PrimitiveMobsAchievementPage.specialColor2, 1);
    } else if (currentColor == -13380919 && this.hasChanged) {
      player.addStat((StatBase)PrimitiveMobsAchievementPage.specialColor3, 1);
    } 
  }
  
  public int func_82814_b(ItemStack p_82814_1_) {
    NBTTagCompound nbttagcompound = p_82814_1_.getTagCompound();
    if (nbttagcompound == null)
      return 10511680; 
    NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
    return (nbttagcompound1 == null) ? 10511680 : (nbttagcompound1.hasKey("color", 3) ? nbttagcompound1.getInteger("color") : 10511680);
  }
  
  public byte getNoCamo(ItemStack p_82814_1_) {
    NBTTagCompound nbttagcompound = p_82814_1_.getTagCompound();
    if (nbttagcompound == null)
      return 0; 
    NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
    return (nbttagcompound1 == null) ? 0 : (nbttagcompound1.hasKey("camo", 1) ? nbttagcompound1.getByte("camo") : 0);
  }
  
  public void setNoCamo(ItemStack p_82814_1_, byte set) {
    NBTTagCompound nbttagcompound = p_82814_1_.getTagCompound();
    if (nbttagcompound == null) {
      nbttagcompound = new NBTTagCompound();
      p_82814_1_.setTagCompound(nbttagcompound);
    } 
    NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
    if (!nbttagcompound.hasKey("display", 10))
      nbttagcompound.setTag("display", (NBTBase)nbttagcompound1); 
    nbttagcompound1.setByte("camo", set);
  }
  
  public void surroundWithParticles(EntityPlayer player) {
    for (int i = 0; i < 8; i++)
      player.worldObj.spawnParticle("magicCrit", player.posX + (itemRand.nextFloat() - itemRand.nextFloat()), player.posY + (itemRand
          .nextFloat() - itemRand.nextFloat()), player.posZ + (itemRand.nextFloat() - itemRand.nextFloat()), 0.0D, 0.0D, 0.0D); 
  }
  
  public void changeColor(World worldObj, EntityPlayer player) {
    int i = MathHelper.floor_double(player.posX);
    int j = MathHelper.floor_double(player.boundingBox.minY);
    int k = MathHelper.floor_double(player.posZ);
    if (worldObj.getBlock(i, j, k) == Blocks.air)
      j = MathHelper.floor_double(player.boundingBox.minY - 0.1D); 
    Block m = worldObj.getBlock(i, j, k);
    int meta = worldObj.getBlockMetadata(i, j, k);
    int foliage = 0;
    if (m instanceof BlockGrass) {
      BlockGrass blockGrass = (BlockGrass)m;
      foliage = blockGrass.colorMultiplier((IBlockAccess)worldObj, i, j, k);
    } else if (m instanceof BlockLeaves) {
      BlockLeaves blockLeaves = (BlockLeaves)m;
      foliage = blockLeaves.colorMultiplier((IBlockAccess)worldObj, i, j, k);
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
        int color = blockGrass.colorMultiplier((IBlockAccess)worldObj, i, j, k);
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
        int color = blockTallGrass.colorMultiplier((IBlockAccess)worldObj, i, j, k);
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
        int color = blockLeaves.colorMultiplier((IBlockAccess)worldObj, i, j, k);
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
        int color = blockLilyPad.colorMultiplier((IBlockAccess)worldObj, i, j, k);
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
        int color = blockFluidBase.colorMultiplier((IBlockAccess)worldObj, i, j, k);
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
}
