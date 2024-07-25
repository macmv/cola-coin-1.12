package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.gui.LoginScreen;
import net.macmv.colacoin.network.packet.LoginResponse;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LoginResponseHandler implements IMessageHandler<LoginResponse, IMessage> {
  @Override
  public IMessage onMessage(LoginResponse message, MessageContext ctx) {
    if (Minecraft.getMinecraft().currentScreen instanceof LoginScreen) {
      ((LoginScreen) Minecraft.getMinecraft().currentScreen).onLoginResponse(message);
    }

    return null;
  }
}
