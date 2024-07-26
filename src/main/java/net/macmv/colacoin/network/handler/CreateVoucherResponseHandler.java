package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.network.packet.CreateVoucherResponse;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CreateVoucherResponseHandler implements IMessageHandler<CreateVoucherResponse, IMessage> {

  @Override
  public IMessage onMessage(CreateVoucherResponse message, MessageContext ctx) {
    return null;
  }
}
