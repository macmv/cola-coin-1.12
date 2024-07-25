package net.macmv.colacoin.block;

import net.macmv.colacoin.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

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
}
