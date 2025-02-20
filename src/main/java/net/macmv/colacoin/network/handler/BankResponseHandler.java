package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.gui.BankScreen;
import net.macmv.colacoin.network.packet.BankResponse;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BankResponseHandler implements IMessageHandler<BankResponse, IMessage> {

  @Override
  @SideOnly(Side.CLIENT)
  public IMessage onMessage(BankResponse message, MessageContext ctx) {
    if (Minecraft.getMinecraft().currentScreen instanceof BankScreen) {
      ((BankScreen) Minecraft.getMinecraft().currentScreen).onBankResponse(message);
    }

    return null;
  }
}
