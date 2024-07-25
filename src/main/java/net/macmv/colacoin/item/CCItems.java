package net.macmv.colacoin.item;

import net.macmv.colacoin.ColaCoin;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;

public class CCItems {
  private final static Map<String, Item> items = new HashMap<>();

  public static Voucher voucher = register("voucher", new Voucher());

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
}
