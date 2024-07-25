package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.network.packet.LoginRequest;
import net.macmv.colacoin.network.packet.LoginResponse;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LoginRequestHandler implements IMessageHandler<LoginRequest, LoginResponse> {
  @Override
  public LoginResponse onMessage(LoginRequest message, MessageContext ctx) {
    EntityPlayerMP player = ctx.getServerHandler().player;
    // TODO: Block in here (this is a network thread) and make a request to Fauna.

    return new LoginResponse(true);
  }
}
