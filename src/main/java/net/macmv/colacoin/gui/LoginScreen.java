package net.macmv.colacoin.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class LoginScreen extends GuiScreen {
  private static final ResourceLocation LOGIN_BACKGROUND = new ResourceLocation("colacoin:textures/gui/login_background.png");

  protected final int windowWidth = 368;
  protected final int windowHeight = 224;
  private int x;
  private int y;
  private String secret = "";

  @Override
  public void initGui() {
    this.x = (this.width - this.windowWidth) / 2;
    this.y = (this.height - this.windowHeight) / 2;
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    this.drawDefaultBackground();

    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, 0);

    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(LOGIN_BACKGROUND);
    drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.windowWidth, this.windowHeight, 512, 256);

    int width = fontRenderer.getStringWidth(secret);
    drawString(fontRenderer, secret, 34, 53, 0xffffff);
    drawRect(34 + width, 52, 35 + width, 53 + fontRenderer.FONT_HEIGHT, 0xff000000);

    GlStateManager.popMatrix();
  }

  @Override
  protected void keyTyped(char typed, int keyCode) throws IOException {
    super.keyTyped(typed, keyCode);

    if ((typed >= 'a' && typed <= 'z') || (typed >= 'A' && typed <= 'Z') || (typed >= '0' && typed <= '9') || typed == '_' || typed == '-') {
      secret += typed;
    }

    if (typed == '\b' && !secret.isEmpty()) {
      secret = secret.substring(0, secret.length() - 1);
    }

    if (typed == 22 /* Ctrl+V */) {
      secret += GuiScreen.getClipboardString();
    }

    if (secret.length() > 40) {
      secret = secret.substring(0, 40);
    }
  }
}
