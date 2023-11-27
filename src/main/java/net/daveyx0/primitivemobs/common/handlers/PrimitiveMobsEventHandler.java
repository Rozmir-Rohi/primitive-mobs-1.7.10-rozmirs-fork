package net.daveyx0.primitivemobs.common.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationGeneral;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatBase;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class PrimitiveMobsEventHandler {
  @SubscribeEvent
  public void onEntityConstructing(EntityEvent.EntityConstructing event) {}
  
  @SubscribeEvent
  public void onLivingDeathEvent(LivingDeathEvent event) {}
  
  @SubscribeEvent
  public void onEntityJoinWorld(EntityJoinWorldEvent event) {}
  
  @SubscribeEvent
  public void onEntityItemPickup(EntityItemPickupEvent event) {
    if (event.item.getEntityItem() != null && event.item.getEntityItem().getItem() == PrimitiveMobsItems.camouflageDye)
      event.entityPlayer.addStat((StatBase)PrimitiveMobsAchievementPage.camouflageDye, 1); 
  }
  
  @SubscribeEvent
  public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
    ItemStack boots = event.entityLiving.getEquipmentInSlot(1);
    if (boots != null && event.entityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
      NBTTagList nbttaglist = (boots.getItem() == Items.enchanted_book) ? Items.enchanted_book.func_92110_g(boots) : boots.getEnchantmentTagList();
      if (nbttaglist != null)
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
          short short1 = nbttaglist.getCompoundTagAt(i).getShort("id");
          short short2 = nbttaglist.getCompoundTagAt(i).getShort("lvl");
          if (short1 == PrimitiveMobsConfigurationGeneral.getStickyFeetID())
            if (event.entityLiving.isCollidedHorizontally && event.entityLiving.isSneaking()) {
              event.entityLiving.motionY = 0.0D;
            } else if (event.entityLiving.isCollidedHorizontally) {
              event.entityLiving.motionY = 0.2D;
            }  
        }  
    } 
  }
}
