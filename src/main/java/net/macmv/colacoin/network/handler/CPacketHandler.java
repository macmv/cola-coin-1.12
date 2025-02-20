package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.ColaCoin;
import net.macmv.colacoin.network.packet.*;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

public class CPacketHandler {
  public static void register() {
    registerServer(new LoginRequestHandler(), LoginRequest.class);
    registerServer(new BankRequestHandler(), BankRequest.class);
    registerServer(new CreateVoucherRequestHandler(), CreateVoucherRequest.class);

    registerClient(new LoginResponseHandler(), LoginResponse.class);
    registerClient(new BankResponseHandler(), BankResponse.class);
    registerClient(new CreateVoucherResponseHandler(), CreateVoucherResponse.class);
  }

  // Note that packet IDs are shared between client and server.
  private static int index = 0;
  private static <REQ extends IMessage, REPLY extends IMessage> void registerServer(IMessageHandler<REQ, REPLY> handler, Class<REQ> clazz) {
    ColaCoin.NETWORK.registerMessage(handler, clazz, index, Side.SERVER);
    index++;
  }

  private static <REQ extends IMessage, REPLY extends IMessage> void registerClient(IMessageHandler<REQ, REPLY> handler, Class<REQ> clazz) {
    ColaCoin.NETWORK.registerMessage(handler, clazz, index, Side.CLIENT);
    index++;
  }
}
