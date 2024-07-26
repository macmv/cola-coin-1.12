package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.network.packet.CreateVoucherResponse;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreateVoucherResponseHandler implements IMessageHandler<CreateVoucherResponse, IMessage> {

  @Override
  @SideOnly(Side.CLIENT)
  public IMessage onMessage(CreateVoucherResponse message, MessageContext ctx) {
    return null;
  }
}
