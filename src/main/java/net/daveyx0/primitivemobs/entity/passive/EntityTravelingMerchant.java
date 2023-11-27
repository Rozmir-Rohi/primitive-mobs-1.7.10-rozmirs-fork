package net.daveyx0.primitivemobs.entity.passive;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigurationGeneral;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.World;

public class EntityTravelingMerchant extends EntityVillager {
  public EntityTravelingMerchant(World par1World) {
    super(par1World);
    setCustomNameTag("Traveling Merchant");
  }
  
  protected boolean canDespawn() {
    return false;
  }
  
  public boolean getCanSpawnHere() {
    return (super.getCanSpawnHere() && this.posY > 60.0D);
  }
  
  protected String getHurtSound() {
    if (PrimitiveMobsConfigurationGeneral.getVillagerSounds())
      return "mob.villager.hit"; 
    return "game.neutral.hurt";
  }
  
  protected String getLivingSound() {
    if (PrimitiveMobsConfigurationGeneral.getVillagerSounds())
      return isTrading() ? "mob.villager.haggle" : "mob.villager.idle"; 
    return "game.neutral.hurt";
  }
  
  protected String getDeathSound() {
    if (PrimitiveMobsConfigurationGeneral.getVillagerSounds())
      return "mob.villager.death"; 
    return "game.neutral.hurt";
  }
}
