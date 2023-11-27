package net.daveyx0.primitivemobs.common;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.item.ItemStack;
import net.daveyx0.primitivemobs.core.PrimitiveMobsEntities;

public class PrimitiveMobsThaumcraftAspects {

	public static void addThaumcraftAspects() {
		
		
		// Items
		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.pinkSlimeBall), new AspectList().add(Aspect.SLIME, 2));

		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.camouflageDye), new AspectList().add(Aspect.BEAST, 1).add(Aspect.SENSES, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.camouflageHelmet), new AspectList().add(Aspect.CLOTH, 7).add(Aspect.BEAST, 3).add(Aspect.ARMOR, 1).add(Aspect.SENSES, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.camouflageChest), new AspectList().add(Aspect.CLOTH, 12).add(Aspect.BEAST, 6).add(Aspect.ARMOR, 3).add(Aspect.SENSES, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.camouflageLegs), new AspectList().add(Aspect.CLOTH, 10).add(Aspect.BEAST, 5).add( Aspect.ARMOR, 2).add( Aspect.SENSES, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.camouflageBoots), new AspectList().add(Aspect.CLOTH, 6).add( Aspect.BEAST, 3).add(Aspect.ARMOR, 1).add(Aspect.SENSES, 3));

		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.customEgg, 0, 0), new AspectList().add(Aspect.SLIME, 1).add(Aspect.LIFE, 2).add(Aspect.BEAST, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.spiderEggShell), new AspectList().add(Aspect.BEAST, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.customEgg, 0, 1), new AspectList().add(Aspect.SLIME, 1).add(Aspect.LIFE, 2).add(Aspect.BEAST, 2).add(Aspect.MAGIC, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.tarBall), new AspectList().add(Aspect.EARTH, 1).add(Aspect.SLIME, 1).add(Aspect.TRAP, 1));

		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.preciousPickaxe), new AspectList().add(Aspect.METAL, 9).add(Aspect.GREED, 12).add(Aspect.TREE, 1).add(Aspect.MINE, 4).add(Aspect.MAGIC, 2));

		ThaumcraftApi.registerObjectTag(new ItemStack(PrimitiveMobsItems.skullMinionOrb), new AspectList().add(Aspect.MAGIC, 5).add(Aspect.UNDEAD, 4).add(Aspect.ENTROPY, 4));
		
		
		
		
		//Mobs
		ThaumcraftApi.registerEntityTag("primitivemobs.RocketCreeper", new AspectList().add(Aspect.PLANT, 2).add(Aspect.FIRE, 2).add(Aspect.MOTION, 2));

		ThaumcraftApi.registerEntityTag("primitivemobs.FestiveCreeper", new AspectList().add(Aspect.PLANT, 2).add(Aspect.FIRE, 10).add(Aspect.ENTROPY, 10));

		ThaumcraftApi.registerEntityTag("primitivemobs.SupportCreeper", new AspectList().add(Aspect.PLANT, 2).add(Aspect.FIRE, 2).add(Aspect.MAGIC, 2).add(Aspect.WEATHER, 1));

		ThaumcraftApi.registerEntityTag("primitivemobs.Chameleon", new AspectList().add(Aspect.BEAST, 1).add(Aspect.SENSES, 3));

		ThaumcraftApi.registerEntityTag("primitivemobs.TreasureSlime", new AspectList().add(Aspect.WATER, 2).add(Aspect.SLIME, 2).add(Aspect.GREED, 1));

		ThaumcraftApi.registerEntityTag("primitivemobs.HauntedTool", new AspectList().add(Aspect.TOOL, 2).add(Aspect.MAGIC, 1).add(Aspect.ENTROPY, 1));

		ThaumcraftApi.registerEntityTag("primitivemobs.MimicChest", new AspectList().add(Aspect.TREE, 3).add(Aspect.HUNGER, 2).add(Aspect.ENTROPY, 1));

		ThaumcraftApi.registerEntityTag("primitivemobs.BewitchedTome", new AspectList().add(Aspect.MAGIC, 2).add(Aspect.CLOTH, 2));

		ThaumcraftApi.registerEntityTag("primitivemobs.TravelingMerchant", new AspectList().add(Aspect.AIR, 2).add(Aspect.MAN, 3));

		ThaumcraftApi.registerEntityTag("primitivemobs.LostMiner", new AspectList().add(Aspect.MINE, 2).add(Aspect.MAN, 3));

		ThaumcraftApi.registerEntityTag("primitivemobs.BrainSlime", new AspectList().add(Aspect.WATER, 2).add(Aspect.SLIME, 2).add(Aspect.HUNGER, 2).add(Aspect.ENTROPY, 1));

		ThaumcraftApi.registerEntityTag("primitivemobs.SkeletonWarrior", new AspectList().add(Aspect.UNDEAD, 2).add(Aspect.MAN, 1).add(Aspect.WEAPON, 3));

		ThaumcraftApi.registerEntityTag("primitivemobs.MotherSpider", new AspectList().add(Aspect.BEAST, 4).add(Aspect.ENTROPY, 2));

		ThaumcraftApi.registerEntityTag("primitivemobs.BabySpider", new AspectList().add(Aspect.BEAST, 1).add(Aspect.ENTROPY, 1));

		ThaumcraftApi.registerEntityTag("primitivemobs.BlazingJuggernaut", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.FIRE, 1).add(Aspect.EARTH, 1));

		ThaumcraftApi.registerEntityTag("primitivemobs.Sheepman", new AspectList().add(Aspect.MAN, 2).add(Aspect.BEAST, 1).add(Aspect.FIRE, 2));

		ThaumcraftApi.registerEntityTag("primitivemobs.Summoner", new AspectList().add(Aspect.MAN, 3).add(Aspect.MAGIC, 3).add(Aspect.ENTROPY, 2));

		ThaumcraftApi.registerEntityTag("primitivemobs.Minion", new AspectList().add(Aspect.UNDEAD, 1).add(Aspect.MAGIC, 1));

		ThaumcraftApi.registerEntityTag("primitivemobs.SheepmanSmith", new AspectList().add(Aspect.MAN, 2).add(Aspect.BEAST, 1).add(Aspect.FIRE, 2));

		ThaumcraftApi.registerEntityTag("primitivemobs.LilyLurker", new AspectList().add(Aspect.WATER, 1).add(Aspect.BEAST, 1).add(Aspect.TRAP, 1));

		ThaumcraftApi.registerEntityTag("primitivemobs.Lily", new AspectList().add(Aspect.WATER, 1).add(Aspect.PLANT, 1));
	}
}
