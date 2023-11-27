package net.daveyx0.primitivemobs.common.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.stats.StatBase;

public class PrimitiveMobsFMLEventHandler {
  @SubscribeEvent
  public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
    if (event.crafting != null && (event.crafting.getItem() == PrimitiveMobsItems.camouflageHelmet || event.crafting.getItem() == PrimitiveMobsItems.camouflageLegs || event.crafting
      .getItem() == PrimitiveMobsItems.camouflageChest || event.crafting.getItem() == PrimitiveMobsItems.camouflageBoots))
      event.player.addStat((StatBase)PrimitiveMobsAchievementPage.camouflageArmor, 1); 
  }
}
