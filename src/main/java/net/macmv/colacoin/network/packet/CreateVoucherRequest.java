package net.macmv.colacoin.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CreateVoucherRequest implements IMessage {
  public int amount;

  public CreateVoucherRequest() {
  }
  public CreateVoucherRequest(int amount) {
    this.amount = amount;
  }

  @Override
  public void fromBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    amount = buf.readVarInt();
  }

  @Override
  public void toBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    buf.writeVarInt(amount);
  }
}
