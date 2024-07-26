package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.database.AccountStatus;
import net.macmv.colacoin.database.CCDatabase;
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

    QueryResponse<AccountStatus> res = CCDatabase.INSTANCE.status(secret);

    if (res instanceof QuerySuccess) {
      AccountStatus out = ((QuerySuccess<AccountStatus>) res).value;

      return new BankResponse(true, out.username, out.balance);
    } else {
      return new BankResponse(false);
    }
  }
}
