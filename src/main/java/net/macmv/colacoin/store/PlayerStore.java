package net.macmv.colacoin.store;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerStore {
  public final String username;
  public final String secret;

  public PlayerStore(String username, String secret) {
    this.username = username;
    this.secret = secret;
  }

  public static PlayerStore fromNBT(NBTTagCompound compoundTag) {
    return new PlayerStore(
      compoundTag.getString("username"),
      compoundTag.getString("secret")
    );
  }

  public NBTBase toNBT() {
    NBTTagCompound compoundTag = new NBTTagCompound();
    compoundTag.setString("username", username);
    compoundTag.setString("secret", secret);
    return compoundTag;
  }
}
