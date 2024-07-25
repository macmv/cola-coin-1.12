package net.macmv.colacoin;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ColaCoin.MODID, version = ColaCoin.VERSION)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ColaCoin.MODID)
public class ColaCoin {
  public static final String MODID = "colacoin";
  public static final String VERSION = "1.0";
  public static Logger LOG = LogManager.getLogger(ColaCoin.MODID);

  @SubscribeEvent
  public void registerItems(RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> reg = event.getRegistry();
    // TODO
  }

  @SubscribeEvent
  public void registerBlocks(RegistryEvent.Register<Block> event) {
    IForgeRegistry<Block> reg = event.getRegistry();
    // TODO
  }

  @SubscribeEvent
  public static void registerRenders(ModelRegistryEvent event) {
    // TODO: register models
  }

  private static void registerModel(Item item) {
    ModelResourceLocation location = new ModelResourceLocation(item.getRegistryName(), "inventory");
    ModelLoader.setCustomModelResourceLocation(item, 0, location);
  }

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent e) {
    FMLCommonHandler.instance().bus().register(this);
    MinecraftForge.EVENT_BUS.register(this);
  }
}
