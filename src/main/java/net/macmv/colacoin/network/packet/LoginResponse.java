package net.macmv.colacoin.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class LoginResponse implements IMessage {
  public boolean success;
  public String message;

  public LoginResponse() {
  }
  public LoginResponse(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  @Override
  public void fromBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    success = buf.readBoolean();
    message = buf.readString(64);
  }

  @Override
  public void toBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    buf.writeBoolean(success);
    buf.writeString(message);
  }
}
