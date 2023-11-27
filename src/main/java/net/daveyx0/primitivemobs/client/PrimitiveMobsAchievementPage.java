package net.daveyx0.primitivemobs.client;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class PrimitiveMobsAchievementPage extends AchievementPage {
  public PrimitiveMobsAchievementPage() {
    super("PrimitiveMobs", new Achievement[] { 
          lostMinerHelp, camouflageArmor, camouflageDye, chameleonFriend, specialColor1, specialColor2, specialColor3, redCreepSurvive, spiderOwner, spiderStealer, 
          spiderAttack, spiderGrown, hauntedDiamond, hauntedPrecious, mimicFake, mimicDungeon, treasureSlimeFriend, treasureSlimeWhite, blazingJugger });
  }
  
  public static Achievement lostMinerHelp = (new Achievement("achievement.primitivemobs.lostminerhelp", "primitivemobs.lostminerhelp", -1, -4, Items.emerald, (Achievement)null)).initIndependentStat().registerStat();
  
  public static Achievement camouflageArmor;
  
  public static Achievement camouflageDye = (new Achievement("achievement.primitivemobs.camouflagedye", "primitivemobs.camouflagedye", -1, -3, PrimitiveMobsItems.camouflageDye, (Achievement)null)).initIndependentStat().registerStat();
  
  public static Achievement chameleonFriend;
  
  public static Achievement specialColor1;
  
  public static Achievement specialColor2;
  
  public static Achievement specialColor3;
  
  public static Achievement redCreepSurvive;
  
  public static Achievement spiderOwner;
  
  public static Achievement spiderStealer;
  
  public static Achievement spiderAttack;
  
  public static Achievement spiderGrown;
  
  public static Achievement hauntedDiamond;
  
  public static Achievement hauntedPrecious;
  
  public static Achievement mimicFake;
  
  public static Achievement mimicDungeon;
  
  public static Achievement treasureSlimeFriend;
  
  public static Achievement treasureSlimeWhite;
  
  public static Achievement blazingJugger;
  
  static {
    camouflageArmor = (new Achievement("achievement.primitivemobs.camouflagearmor", "primitivemobs.camouflagearmor", 1, -3, PrimitiveMobsItems.camouflageHelmet, camouflageDye)).registerStat();
    chameleonFriend = (new Achievement("achievement.primitivemobs.chameleonfriend", "primitivemobs.chameleonfriend", -1, -2, Items.melon, (Achievement)null)).initIndependentStat().registerStat();
    specialColor1 = (new Achievement("achievement.primitivemobs.specialcolor1", "primitivemobs.specialcolor1", 3, -4, PrimitiveMobsItems.camouflageDye, camouflageArmor)).setSpecial().registerStat();
    specialColor2 = (new Achievement("achievement.primitivemobs.specialcolor2", "primitivemobs.specialcolor2", 3, -3, PrimitiveMobsItems.camouflageDye, camouflageArmor)).setSpecial().registerStat();
    specialColor3 = (new Achievement("achievement.primitivemobs.specialcolor3", "primitivemobs.specialcolor3", 3, -2, PrimitiveMobsItems.camouflageDye, camouflageArmor)).setSpecial().registerStat();
    redCreepSurvive = (new Achievement("achievement.primitivemobs.redcreepsurvive", "primitivemobs.redcreepsurvive", 3, -1, Blocks.tnt, (Achievement)null)).initIndependentStat().setSpecial().registerStat();
    spiderOwner = (new Achievement("achievement.primitivemobs.spiderowner", "primitivemobs.spiderowner", -1, 1, new ItemStack(PrimitiveMobsItems.customEgg, 1, 0), (Achievement)null)).initIndependentStat().registerStat();
    spiderStealer = (new Achievement("achievement.primitivemobs.spiderstealer", "primitivemobs.spiderstealer", 1, 0, PrimitiveMobsItems.spiderEggShell, spiderOwner)).registerStat();
    spiderAttack = (new Achievement("achievement.primitivemobs.spiderattack", "primitivemobs.spiderattack", 1, 1, Items.spider_eye, spiderOwner)).registerStat();
    spiderGrown = (new Achievement("achievement.primitivemobs.spidergrown", "primitivemobs.spidergrown", 1, 2, Items.fermented_spider_eye, spiderOwner)).registerStat();
    hauntedDiamond = (new Achievement("achievement.primitivemobs.haunteddiamond", "primitivemobs.haunteddiamond", -1, 3, Items.diamond, (Achievement)null)).initIndependentStat().registerStat();
    hauntedPrecious = (new Achievement("achievement.primitivemobs.hauntedprecious", "primitivemobs.hauntedprecious", 3, 3, PrimitiveMobsItems.preciousPickaxe, (Achievement)null)).initIndependentStat().setSpecial().registerStat();
    mimicFake = (new Achievement("achievement.primitivemobs.mimicfake", "primitivemobs.mimicfake", -1, 4, Items.wooden_sword, (Achievement)null)).initIndependentStat().registerStat();
    mimicDungeon = (new Achievement("achievement.primitivemobs.mimicdungeon", "primitivemobs.mimicdungeon", 3, 4, Items.gold_ingot, (Achievement)null)).initIndependentStat().setSpecial().registerStat();
    treasureSlimeFriend = (new Achievement("achievement.primitivemobs.treasureslimefriend", "primitivemobs.treasureslimefriend", -1, 5, Items.slime_ball, (Achievement)null)).initIndependentStat().registerStat();
    treasureSlimeWhite = (new Achievement("achievement.primitivemobs.treasureslimewhite", "primitivemobs.treasureslimewhite", 3, 5, new ItemStack(Items.dye, 1, 15), treasureSlimeFriend)).setSpecial().registerStat();
    blazingJugger = (new Achievement("achievement.primitivemobs.blazingjugger", "primitivemobs.blazingjugger", 3, 6, Items.blaze_powder, (Achievement)null)).initIndependentStat().setSpecial().registerStat();
  }
}
