package net.macmv.colacoin.item;

import net.macmv.colacoin.database.*;
import net.macmv.colacoin.store.CCStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

public class Voucher extends Item {
  public Voucher() {
    setMaxStackSize(1);
  }

  public ItemStack createStack(long id, int amount) {
    ItemStack stack = new ItemStack(this);

    NBTTagCompound tag = new NBTTagCompound();
    tag.setLong("id", id);
    tag.setInteger("amount", amount);

    stack.setTagCompound(tag);

    StringBuilder sb = new StringBuilder();
    sb.append(TextFormatting.RESET);
    sb.append(TextFormatting.GOLD);
    sb.append("Voucher for ");
    sb.append(amount);
    sb.append("cc");
    stack.setStackDisplayName(sb.toString());

    return stack;
  }

  // This is on both sides for the integrated server, but logically only exists on the server.
  public boolean redeem(EntityPlayer player0, ItemStack stack) {
    EntityPlayerMP player = (EntityPlayerMP) player0;

    String secret = CCStore.get(player.world).getPlayerSecret(player);
    if (secret == null) {
      return false;
    }

    NBTTagCompound tag = stack.getTagCompound();
    if (tag == null) {
      return false;
    }

    long id = tag.getLong("id");
    QueryResponse<RedeemedVoucher> res = CCDatabase.INSTANCE.redeem_voucher(secret, id);

    if (res instanceof QuerySuccess) {
      RedeemedVoucher out = ((QuerySuccess<RedeemedVoucher>) res).value;

      stack.shrink(1);

      return true;
    } else {
      System.out.println(((QueryFailure<?>) res).message);

      return false;
    }
  }
}
