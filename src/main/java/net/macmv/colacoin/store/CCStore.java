package net.macmv.colacoin.store;

import net.macmv.colacoin.ColaCoin;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CCStore extends WorldSavedData {
  private static final String DATA_NAME = ColaCoin.MODID + "_store";

  private final HashMap<UUID, PlayerStore> players = new HashMap<>();

  public CCStore() {
    super(DATA_NAME);
  }
  public CCStore(String name) {
    super(name);
  }

  public static CCStore get(World world) {
    MapStorage storage = world.getMapStorage();
    CCStore instance = (CCStore) storage.getOrLoadData(CCStore.class, DATA_NAME);

    if (instance == null) {
      instance = new CCStore();
      storage.setData(DATA_NAME, instance);
    }

    return instance;
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    NBTTagCompound players = nbt.getCompoundTag("players");
    for (String key : players.getKeySet()) {
      UUID uuid = UUID.fromString(key);
      PlayerStore value = PlayerStore.fromNBT(players.getCompoundTag(key));
      this.players.put(uuid, value);
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    NBTTagCompound secrets = new NBTTagCompound();
    for (Map.Entry<UUID, PlayerStore> pair : this.players.entrySet()) {
      secrets.setTag(pair.getKey().toString(), pair.getValue().toNBT());
    }
    compound.setTag("secrets", secrets);
    return compound;
  }

  public void setPlayer(EntityPlayerMP player, PlayerStore playerStore) {
    this.players.put(player.getUniqueID(), playerStore);
    this.markDirty();
  }

  public PlayerStore getPlayer(EntityPlayerMP player) {
    return this.players.get(player.getUniqueID());
  }
}
