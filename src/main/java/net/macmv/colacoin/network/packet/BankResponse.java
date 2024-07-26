package net.macmv.colacoin.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class BankResponse implements IMessage {
  public boolean loggedIn;
  public String username;
  public int balance;

  public BankResponse() {
  }
  public BankResponse(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }
  public BankResponse(boolean loggedIn, String username, int balance) {
    this.loggedIn = loggedIn;
    this.username = username;
    this.balance = balance;
  }

  @Override
  public void fromBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    loggedIn = buf.readBoolean();
    if (loggedIn) {
      username = buf.readString(64);
      balance = buf.readVarInt();
    }
  }

  @Override
  public void toBytes(ByteBuf bytes) {
    PacketBuffer buf = new PacketBuffer(bytes);

    buf.writeBoolean(loggedIn);
    if (loggedIn) {
      buf.writeString(username);
      buf.writeVarInt(balance);
    }
  }
}
