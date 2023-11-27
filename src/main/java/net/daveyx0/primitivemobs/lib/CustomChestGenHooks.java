package net.daveyx0.primitivemobs.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class CustomChestGenHooks extends ChestGenHooks {
  public static final String MIMIC_CHEST_SKELLY = "mimicChest";
  
  public static final String MIMIC_CHEST_TOOL = "mimicChestTool";
  
  public static final String MIMIC_CHEST_BAT = "mimicChestBat";
  
  public static final String CHEST_SPIDER_CAVE = "chestSpiderCave";
  
  public static final WeightedRandomChestContent[] customMimicContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.bone, 0, 1, 5, 15), new WeightedRandomChestContent(Items.string, 0, 1, 3, 8), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 1), new WeightedRandomChestContent((Item)Items.bow, 0, 1, 1, 1), new WeightedRandomChestContent((Item)Items.leather_helmet, 0, 1, 1, 1) };
  
  public static final WeightedRandomChestContent[] customMimicToolContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.stick, 0, 1, 5, 15), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 3, 3) };
  
  public static final WeightedRandomChestContent[] customMimicBatContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.bone, 0, 1, 5, 15), new WeightedRandomChestContent(Items.string, 0, 1, 3, 8) };
  
  public static final WeightedRandomChestContent[] chestSpiderCaveContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.bone, 0, 1, 5, 15), new WeightedRandomChestContent(Items.string, 0, 1, 3, 8) };
  
  private static final HashMap<String, CustomChestGenHooks> chestInfo = new HashMap<String, CustomChestGenHooks>();
  
  private static boolean hasInit = false;
  
  private String category;
  
  static {
    init();
  }
  
  private static void init() {
    if (hasInit)
      return; 
    hasInit = true;
    addInfo("mimicChest", customMimicContents, 3, 7);
    addInfo("mimicChestTool", customMimicToolContents, 3, 7);
    addInfo("mimicChestBat", customMimicBatContents, 3, 7);
    addInfo("chestSpiderCave", chestSpiderCaveContents, 3, 7);
  }
  
  static void addDungeonLoot(CustomChestGenHooks dungeon, ItemStack item, int weight, int min, int max) {
    dungeon.addItem(new WeightedRandomChestContent(item, min, max, weight));
  }
  
  private static void addInfo(String category, WeightedRandomChestContent[] items, int min, int max) {
    chestInfo.put(category, new CustomChestGenHooks(category, items, min, max));
  }
  
  public static CustomChestGenHooks getInfo(String category) {
    if (!chestInfo.containsKey(category))
      chestInfo.put(category, new CustomChestGenHooks(category)); 
    return chestInfo.get(category);
  }
  
  public static ItemStack[] generateStacks(Random rand, ItemStack source, int min, int max) {
    ItemStack[] ret;
    int count = min + rand.nextInt(max - min + 1);
    if (source.getItem() == null) {
      ret = new ItemStack[0];
    } else if (count > source.getMaxStackSize()) {
      ret = new ItemStack[count];
      for (int x = 0; x < count; x++) {
        ret[x] = source.copy();
        (ret[x]).stackSize = 1;
      } 
    } else {
      ret = new ItemStack[1];
      ret[0] = source.copy();
      (ret[0]).stackSize = count;
    } 
    return ret;
  }
  
  public static WeightedRandomChestContent[] getItems(String category, Random rnd) {
    return getInfo(category).getItems(rnd);
  }
  
  public static int getCount(String category, Random rand) {
    return getInfo(category).getCount(rand);
  }
  
  public static void addItem(String category, WeightedRandomChestContent item) {
    getInfo(category).addItem(item);
  }
  
  public static void removeItem(String category, ItemStack item) {
    getInfo(category).removeItem(item);
  }
  
  public static ItemStack getOneItem(String category, Random rand) {
    return getInfo(category).getOneItem(rand);
  }
  
  private int countMin = 0;
  
  private int countMax = 0;
  
  private ArrayList<WeightedRandomChestContent> contents = new ArrayList<WeightedRandomChestContent>();
  
  public CustomChestGenHooks(String category) {
    super(category);
    this.category = category;
  }
  
  public CustomChestGenHooks(String category, WeightedRandomChestContent[] items, int min, int max) {
    this(category);
    for (WeightedRandomChestContent item : items)
      this.contents.add(item); 
    this.countMin = min;
    this.countMax = max;
  }
  
  public void addItem(WeightedRandomChestContent item) {
    this.contents.add(item);
  }
  
  public void removeItem(ItemStack item) {
    Iterator<WeightedRandomChestContent> itr = this.contents.iterator();
    while (itr.hasNext()) {
      WeightedRandomChestContent cont = itr.next();
      if (item.isItemEqual(cont.theItemId) || (item.getItemDamage() == 32767 && item.getItem() == cont.theItemId.getItem()))
        itr.remove(); 
    } 
  }
  
  public WeightedRandomChestContent[] getItems(Random rnd) {
    ArrayList<WeightedRandomChestContent> ret = new ArrayList<WeightedRandomChestContent>();
    for (WeightedRandomChestContent orig : this.contents) {
      Item item = orig.theItemId.getItem();
      if (item != null) {
        WeightedRandomChestContent n = item.getChestGenBase(this, rnd, orig);
        if (n != null)
          ret.add(n); 
      } 
    } 
    return ret.<WeightedRandomChestContent>toArray(new WeightedRandomChestContent[ret.size()]);
  }
  
  public int getCount(Random rand) {
    return (this.countMin < this.countMax) ? (this.countMin + rand.nextInt(this.countMax - this.countMin)) : this.countMin;
  }
  
  public ItemStack getOneItem(Random rand) {
    WeightedRandomChestContent[] items = getItems(rand);
    WeightedRandomChestContent item = (WeightedRandomChestContent)WeightedRandom.getRandomItem(rand, (WeightedRandom.Item[])items);
    ItemStack[] stacks = ChestGenHooks.generateStacks(rand, item.theItemId, item.theMinimumChanceToGenerateItem, item.theMaximumChanceToGenerateItem);
    return (stacks.length > 0) ? stacks[0] : null;
  }
  
  public int getMin() {
    return this.countMin;
  }
  
  public int getMax() {
    return this.countMax;
  }
  
  public void setMin(int value) {
    this.countMin = value;
  }
  
  public void setMax(int value) {
    this.countMax = value;
  }
}
