package net.macmv.colacoin.block;

import net.macmv.colacoin.ColaCoin;
import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;

public class CCBlocks {
  private final static Map<String, Block> blocks = new HashMap<>();

  public static Bank BANK = register("bank", new Bank());

  private static <T extends Block> T register(String blockName, T block) {
    String name = ColaCoin.MODID + ":" + blockName;

    block.setRegistryName(name);
    block.setUnlocalizedName(name);

    if (blocks.put(name, block) != null) {
      throw new IllegalArgumentException("Duplicate block: " + name);
    }

    return block;
  }

  public static void registerAll(IForgeRegistry<Block> reg) {
    blocks.forEach((name, item) -> reg.register(item));
  }
}
