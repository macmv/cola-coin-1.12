package net.macmv.colacoin;

import net.minecraft.util.math.AxisAlignedBB;

public class Utils {
  public static AxisAlignedBB aabb(int x0, int y0, int z0, int x1, int y1, int z1) {
    if (x0 < 0 || x1 < 0 || x1 > 16 || x0 > x1) {
      throw new IllegalArgumentException("Invalid x coordinates");
    }
    if (y0 < 0 || y1 < 0 || y1 > 16 || y0 > y1) {
      throw new IllegalArgumentException("Invalid y coordinates");
    }
    if (z0 < 0 || z1 < 0 || z1 > 16 || z0 > z1) {
      throw new IllegalArgumentException("Invalid z coordinates");
    }

    return new AxisAlignedBB(x0 / 16.0, y0 / 16.0, z0 / 16.0, x1 / 16.0, y1 / 16.0, z1 / 16.0);
  }
}
