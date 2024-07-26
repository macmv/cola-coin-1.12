package net.macmv.colacoin.block;

import net.macmv.colacoin.Utils;
import net.macmv.colacoin.gui.BankScreen;
import net.macmv.colacoin.item.CCItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Bank extends Block {
  private static final AxisAlignedBB BOUNDING_BOX = Utils.aabb(1, 0, 1, 15, 16, 15);

  public Bank() {
    super(Material.IRON);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return BOUNDING_BOX;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (player.getHeldItem(hand).getItem() == CCItems.VOUCHER) {
      if (!world.isRemote) {
        // Redeeming the voucher is serverside.
        boolean success = CCItems.VOUCHER.redeem(player, player.getHeldItem(hand));

        if (success) {
          world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1, 1);
        }
      }
    } else {
      if (world.isRemote) {
        openBankScreen();
      }
    }

    return true;
  }

  @SideOnly(Side.CLIENT)
  private void openBankScreen() {
    // NB: Can't refer to `BankScreen` in server-side functions.
    Minecraft.getMinecraft().displayGuiScreen(new BankScreen());
  }
}
