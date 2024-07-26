package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.database.CCDatabase;
import net.macmv.colacoin.database.LoginResult;
import net.macmv.colacoin.database.QueryResponse;
import net.macmv.colacoin.database.QuerySuccess;
import net.macmv.colacoin.network.packet.LoginRequest;
import net.macmv.colacoin.network.packet.LoginResponse;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LoginRequestHandler implements IMessageHandler<LoginRequest, LoginResponse> {
  @Override
  public LoginResponse onMessage(LoginRequest message, MessageContext ctx) {
    EntityPlayerMP player = ctx.getServerHandler().player;

    QueryResponse<LoginResult> res = CCDatabase.INSTANCE.login(player, message.secret);

    if (res instanceof QuerySuccess) {
      LoginResult out = ((QuerySuccess<LoginResult>) res).value;
      return new LoginResponse(true, out.username);
    } else {
      return new LoginResponse(false, "Login failed");
    }
  }
}
