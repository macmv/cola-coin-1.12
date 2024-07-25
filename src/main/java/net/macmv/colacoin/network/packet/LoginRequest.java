package net.macmv.colacoin.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class LoginRequest implements IMessage {
  public String secret;

  public LoginRequest() {
  }
  public LoginRequest(String secret) {
    this.secret = secret;
  }

  @Override
  public void fromBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    secret = buf.readString(40);
  }

  @Override
  public void toBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    buf.writeString(secret);
  }
}
