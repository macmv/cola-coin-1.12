package net.macmv.colacoin.item;

import net.macmv.colacoin.ColaCoin;
import net.macmv.colacoin.block.CCBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;

public class CCItems {
  private final static Map<String, Item> items = new HashMap<>();

  public static Voucher VOUCHER = register("voucher", new Voucher());

  public static ItemBlock BANK = register(CCBlocks.BANK);

  private static ItemBlock register(Block block) {
    return register(block.getRegistryName().getResourcePath(), new ItemBlock(block));
  }

  private static <T extends Item> T register(String itemName, T item) {
    String name = ColaCoin.MODID + ":" + itemName;

    item.setRegistryName(name);
    item.setUnlocalizedName(name);

    if (items.put(name, item) != null) {
      throw new IllegalArgumentException("Duplicate item: " + name);
    }

    return item;
  }

  public static void registerAll(IForgeRegistry<Item> reg) {
    items.forEach((name, item) -> reg.register(item));
  }

  @SideOnly(Side.CLIENT)
  public static void registerModels() {
    items.forEach((name, item) -> {
      ModelResourceLocation location = new ModelResourceLocation(item.getRegistryName(), "inventory");
      ModelLoader.setCustomModelResourceLocation(item, 0, location);
    });
  }
}
