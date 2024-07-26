package net.macmv.colacoin.network.handler;

import net.macmv.colacoin.database.CCDatabase;
import net.macmv.colacoin.database.QueryResponse;
import net.macmv.colacoin.database.QuerySuccess;
import net.macmv.colacoin.database.Voucher;
import net.macmv.colacoin.item.CCItems;
import net.macmv.colacoin.network.packet.CreateVoucherRequest;
import net.macmv.colacoin.network.packet.CreateVoucherResponse;
import net.macmv.colacoin.store.CCStore;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CreateVoucherRequestHandler implements IMessageHandler<CreateVoucherRequest, CreateVoucherResponse> {

  @Override
  public CreateVoucherResponse onMessage(CreateVoucherRequest message, MessageContext ctx) {
    EntityPlayerMP player = ctx.getServerHandler().player;

    String secret = CCStore.get(player.world).getPlayerSecret(player);
    if (secret == null) {
      return new CreateVoucherResponse(false);
    }

    QueryResponse<Voucher> res = CCDatabase.INSTANCE.create_voucher(secret, message.amount);

    if (res instanceof QuerySuccess) {
      Voucher out = ((QuerySuccess<Voucher>) res).value;

      ItemStack stack = CCItems.VOUCHER.createStack(out.id, out.amount);
      player.inventory.addItemStackToInventory(stack);

      return new CreateVoucherResponse(true, out.id, out.amount);
    } else {
      return new CreateVoucherResponse(false);
    }
  }
}
