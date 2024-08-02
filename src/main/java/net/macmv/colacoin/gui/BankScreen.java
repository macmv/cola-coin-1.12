package net.macmv.colacoin.gui;

import net.macmv.colacoin.ColaCoin;
import net.macmv.colacoin.network.packet.BankRequest;
import net.macmv.colacoin.network.packet.BankResponse;
import net.macmv.colacoin.network.packet.CreateVoucherRequest;
import net.macmv.colacoin.network.packet.CreateVoucherResponse;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class BankScreen extends GuiScreen {
  private static final ResourceLocation LOGIN_BACKGROUND = new ResourceLocation("colacoin:textures/gui/bank_background.png");

  protected final int windowWidth = 368;
  protected final int windowHeight = 224;
  private int x;
  private int y;

  private boolean loggedIn = false;
  private String username = "";
  private int balance = 0;

  private String typedAmount = "";

  private String message = "";
  private boolean success = false;

  @Override
  public void initGui() {
    this.x = (this.width - this.windowWidth) / 2;
    this.y = (this.height - this.windowHeight) / 2;

    buttonList.add(new CreateVoucherButton(100, x + 220, y + 150));

    ColaCoin.NETWORK.sendToServer(new BankRequest());
  }

  public void onBankResponse(BankResponse res) {
    System.out.println("Got response: " + res.loggedIn + " " + res.username + " " + res.balance);
    this.loggedIn = res.loggedIn;
    this.username = res.username;
    this.balance = res.balance;

    if (!loggedIn) {
      mc.displayGuiScreen(new LoginScreen());
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

    if (loggedIn) {
      drawString(fontRenderer, "Logged in as " + username, 50, 70, 0xffffff);
      drawString(fontRenderer, "Balance: " + balance, 50, 100, 0xffffff);
    } else {
      // TODO: Loading icon when not logged in (you wish lmao)
      drawString(fontRenderer, "Not logged in", 50, 100, 0xffffff);
    }

    int width = fontRenderer.getStringWidth(typedAmount);
    drawString(fontRenderer, typedAmount, 76, 158, 0xffffff);
    drawRect(76 + width, 157, 77 + width, 158 + fontRenderer.FONT_HEIGHT, 0xff000000);

    drawCenteredString(fontRenderer, message, windowWidth / 2, 190, success ? 0x00ff00 : 0xff0000);

    GlStateManager.popMatrix();

    super.drawScreen(mouseX, mouseY, partialTicks);
  }

  @Override
  protected void keyTyped(char typed, int keyCode) throws IOException {
    super.keyTyped(typed, keyCode);

    if ((typed >= 'a' && typed <= 'z') || (typed >= 'A' && typed <= 'Z') || (typed >= '0' && typed <= '9') || typed == '_' || typed == '-') {
      typedAmount += typed;
    }

    if (typed == '\b' && !typedAmount.isEmpty()) {
      typedAmount = typedAmount.substring(0, typedAmount.length() - 1);
    }

    if (typed == 22 /* Ctrl+V */) {
      typedAmount += GuiScreen.getClipboardString();
    }

    if (typedAmount.length() > 32) {
      typedAmount = typedAmount.substring(0, 32);
    }
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    if (button instanceof CreateVoucherButton) {
      try {
        int amount = Integer.parseInt(typedAmount);
        ColaCoin.NETWORK.sendToServer(new CreateVoucherRequest(amount));
      } catch (NumberFormatException ignored) {
        this.message = "Invalid amount";
        this.success = false;
      }
    }
  }

  public void onCreateVoucherResponse(CreateVoucherResponse message) {
    this.success = message.success;
    if (this.success) {
      this.message = "Created voucher for " + message.amount;
      this.balance -= message.amount;
    } else {
      this.message = "Failed to create voucher";
    }
  }

  @SideOnly(Side.CLIENT)
  private class CreateVoucherButton extends GuiButton {
    public CreateVoucherButton(int buttonId, int x, int y) {
      super(buttonId, x, y, 100, 20, "Create Voucher");
    }
  }
}
