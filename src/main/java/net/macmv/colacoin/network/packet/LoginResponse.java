package net.macmv.colacoin.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class LoginResponse implements IMessage {
  public boolean success;

  public LoginResponse() {
  }
  public LoginResponse(boolean success) {
    this.success = success;
  }

  @Override
  public void fromBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    success = buf.readBoolean();
  }

  @Override
  public void toBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    buf.writeBoolean(success);
  }
}
