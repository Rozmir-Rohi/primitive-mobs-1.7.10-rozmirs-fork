package net.daveyx0.primitivemobs.core;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.entity.item.EntityCustomBall;
import net.daveyx0.primitivemobs.entity.item.EntityCustomSpawnEgg;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityBlazingJuggernaut;
import net.daveyx0.primitivemobs.entity.monster.EntityBrainSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityDEnchantedBook;
import net.daveyx0.primitivemobs.entity.monster.EntityDMimic;
import net.daveyx0.primitivemobs.entity.monster.EntityDMinion;
import net.daveyx0.primitivemobs.entity.monster.EntityDSkeletonWarrior;
import net.daveyx0.primitivemobs.entity.monster.EntityDSummoner;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityLily;
import net.daveyx0.primitivemobs.entity.monster.EntityLilyLurker;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.daveyx0.primitivemobs.entity.passive.EntityLostMiner;
import net.daveyx0.primitivemobs.entity.passive.EntitySheepman;
import net.daveyx0.primitivemobs.entity.passive.EntitySheepmanSmith;
import net.daveyx0.primitivemobs.entity.passive.EntityTravelingMerchant;
import net.minecraft.entity.EntityList;

public class PrimitiveMobsEntities {
  public static void init() {
    addEntities(EntityRocketCreeper.class, "RocketCreeper", 1, 5024208, 0);
    addEntities(EntityFestiveCreeper.class, "FestiveCreeper", 2, 12334600, 0);
    addEntities(EntitySupportCreeper.class, "SupportCreeper", 3, 14400815, 0);
    addEntities(EntityChameleon.class, "Chameleon", 4, 16777215, 16777215);
    addEntities(EntityTreasureSlime.class, "TreasureSlime", 5, 4877080, 2241797);
    addEntities(EntityHauntedTool.class, "HauntedTool", 6, 4797973, 6835742);
    addEntities(EntityDMimic.class, "MimicChest", 7, 11237677, 2762013);
    addEntities(EntityDEnchantedBook.class, "BewitchedTome", 8, 12024373, 13684944);
    addEntities(EntityTravelingMerchant.class, "TravelingMerchant", 9, 6316113, 12422002);
    addEntities(EntityLostMiner.class, "LostMiner", 10, 7091750, 12422002);
    addEntities(EntityBrainSlime.class, "BrainSlime", 11, 13014704, 13739453);
    addEntities(EntityDSkeletonWarrior.class, "SkeletonWarrior", 12, 11248008, 7098937);
    addEntities(EntityMotherSpider.class, "MotherSpider", 13, 2426146, 11013646);
    addEntities(EntityBabySpider.class, "BabySpider", 14);
    addEntities(EntityBlazingJuggernaut.class, "BlazingJuggernaut", 15, 3151900, 11577656);
    addEntities(EntitySheepman.class, "Sheepman", 16, 2500134, 11769472);
    addEntities(EntityCustomSpawnEgg.class, "SpawnEgg", 17);
    addEntities(EntityDSummoner.class, "Summoner", 18, 5526114, 11509119);
    addEntities(EntityDMinion.class, "Minion", 19);
    addEntities(EntitySheepmanSmith.class, "SheepmanSmith", 20, 2500134, 4868682);
    addEntities(EntityLilyLurker.class, "LilyLurker", 21, 5848361, 4013084);
    addEntities(EntityLily.class, "Lily", 22);
    addEntities(EntityCustomBall.class, "Ball", 23);
  }
  
  private static void addEntities(Class var1, String name1, int entityid, int bkEggColor, int fgEggColor) {
    int trackingRange = 80;
    int updateFrequency = 3;
    boolean sendsVelocityUpdates = true;
    EntityRegistry.registerModEntity(var1, name1, entityid, PrimitiveMobs.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
    int id = entityid + 900;
    EntityList.IDtoClassMapping.put(id, var1);
    EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, bkEggColor, fgEggColor));
  }
  
  private static void addEntities(Class var1, String name1, int entityid) {
      int trackingRange = 80;
      int updateFrequency = 3;
      boolean sendsVelocityUpdates = true;
      EntityRegistry.registerModEntity(var1, name1, entityid, PrimitiveMobs.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
      int id = entityid + 900;
      EntityList.IDtoClassMapping.put(id, var1);
   }
}
