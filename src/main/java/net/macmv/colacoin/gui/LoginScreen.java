package net.macmv.colacoin.gui;

import net.macmv.colacoin.ColaCoin;
import net.macmv.colacoin.network.packet.LoginRequest;
import net.macmv.colacoin.network.packet.LoginResponse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
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

  private boolean requested = false;
  private boolean success = false;
  private String message = "";

  @Override
  public void initGui() {
    this.x = (this.width - this.windowWidth) / 2;
    this.y = (this.height - this.windowHeight) / 2;

    buttonList.add(new LoginButton(100, x + 100, y + 150));
  }

  @Override
  protected void actionPerformed(GuiButton button) throws IOException {
    if (button instanceof LoginButton) {
      if (!requested) {
        ColaCoin.NETWORK.sendToServer(new LoginRequest(secret));
        requested = true;
      }
    }
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

    drawCenteredString(fontRenderer, message, windowWidth / 2, 100, success ? 0x00ff00 : 0xff0000);

    GlStateManager.popMatrix();

    super.drawScreen(mouseX, mouseY, partialTicks);
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

    if (secret.length() > 64) {
      secret = secret.substring(0, 64);
    }
  }
  public void onLoginResponse(LoginResponse res) {
    if (this.requested) {
      this.requested = false;
      this.success = res.success;
      this.message = res.message;
    }
  }

  @SideOnly(Side.CLIENT)
  class LoginButton extends GuiButton {
    public LoginButton(int buttonId, int x, int y) {
      super(buttonId, x, y, 200, 20, "Login");
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
      drawHoveringText("Login", mouseX, mouseY);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
      this.enabled = !requested;
      super.drawButton(mc, mouseX, mouseY, partialTicks);
    }
  }
}
