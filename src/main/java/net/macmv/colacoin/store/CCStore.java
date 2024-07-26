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

  private final HashMap<UUID, String> secrets = new HashMap<>();

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
    NBTTagCompound players = nbt.getCompoundTag("secrets");
    for (String key : players.getKeySet()) {
      UUID uuid = UUID.fromString(key);
      this.secrets.put(uuid, players.getString(key));
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    NBTTagCompound secrets = new NBTTagCompound();
    for (Map.Entry<UUID, String> pair : this.secrets.entrySet()) {
      secrets.setString(pair.getKey().toString(), pair.getValue());
    }
    compound.setTag("secrets", secrets);
    return compound;
  }

  public void setPlayerSecret(EntityPlayerMP player, String secret) {
    this.secrets.put(player.getUniqueID(), secret);
    this.markDirty();
  }

  public String getPlayerSecret(EntityPlayerMP player) {
    return this.secrets.get(player.getUniqueID());
  }
}
