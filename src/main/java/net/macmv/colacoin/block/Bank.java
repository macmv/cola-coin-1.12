package net.macmv.colacoin.block;

import net.macmv.colacoin.Utils;
import net.macmv.colacoin.gui.BankScreen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    // All the interaction is client-side only.
    if (!worldIn.isRemote) {
      return true;
    }

    Minecraft.getMinecraft().displayGuiScreen(new BankScreen());
    return true;
  }
}
