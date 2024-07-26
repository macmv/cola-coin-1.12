package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.database.CCDatabase;
import net.macmv.colacoin.database.LoginResult;
import net.macmv.colacoin.database.QueryResponse;
import net.macmv.colacoin.database.QuerySuccess;
import net.macmv.colacoin.network.packet.BankRequest;
import net.macmv.colacoin.network.packet.BankResponse;
import net.macmv.colacoin.store.CCStore;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BankRequestHandler implements IMessageHandler<BankRequest, BankResponse> {
  @Override
  public BankResponse onMessage(BankRequest message, MessageContext ctx) {
    EntityPlayerMP player = ctx.getServerHandler().player;

    String secret = CCStore.get(player.world).getPlayerSecret(player);
    if (secret == null) {
      return new BankResponse(false);
    }

    QueryResponse<LoginResult> res = CCDatabase.INSTANCE.login(player, secret);

    if (res instanceof QuerySuccess) {
      LoginResult out = ((QuerySuccess<LoginResult>) res).value;

      // TODO: Fetch balance instead of `login` above.
      return new BankResponse(true, out.username, 5);
    } else {
      return new BankResponse(false);
    }
  }
}
