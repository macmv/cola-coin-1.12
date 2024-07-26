package net.macmv.colacoin.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CreateVoucherResponse implements IMessage {
  public boolean success;
  public long id;
  public int amount;

  public CreateVoucherResponse() {
  }
  public CreateVoucherResponse(boolean success) {
    this.success = success;
  }
  public CreateVoucherResponse(boolean success, long id, int amount) {
    this.success = success;
    this.id = id;
    this.amount = amount;
  }

  @Override
  public void fromBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    success = buf.readBoolean();
    if (success) {
      id = buf.readLong();
      amount = buf.readVarInt();
    }
  }

  @Override
  public void toBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    buf.writeBoolean(success);
    if (success) {
      buf.writeLong(id);
      buf.writeVarInt(amount);
    }
  }
}
