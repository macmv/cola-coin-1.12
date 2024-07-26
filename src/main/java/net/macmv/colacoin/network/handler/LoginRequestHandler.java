package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.database.AccountStatus;
import net.macmv.colacoin.database.CCDatabase;
import net.macmv.colacoin.database.QueryResponse;
import net.macmv.colacoin.database.QuerySuccess;
import net.macmv.colacoin.network.packet.LoginRequest;
import net.macmv.colacoin.network.packet.LoginResponse;
import net.macmv.colacoin.store.CCStore;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LoginRequestHandler implements IMessageHandler<LoginRequest, LoginResponse> {
  @Override
  public LoginResponse onMessage(LoginRequest message, MessageContext ctx) {
    EntityPlayerMP player = ctx.getServerHandler().player;

    QueryResponse<AccountStatus> res = CCDatabase.INSTANCE.status(message.secret);

    if (res instanceof QuerySuccess) {
      AccountStatus out = ((QuerySuccess<AccountStatus>) res).value;

      CCStore.get(player.world).setPlayerSecret(player, message.secret);

      return new LoginResponse(true, "Logged in as " + out.username);
    } else {
      return new LoginResponse(false, "Login failed");
    }
  }
}
