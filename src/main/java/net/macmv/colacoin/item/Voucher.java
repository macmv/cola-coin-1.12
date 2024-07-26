package net.macmv.colacoin.item;

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
}
