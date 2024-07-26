package net.macmv.colacoin.gui;

import net.macmv.colacoin.ColaCoin;
import net.macmv.colacoin.network.packet.BankRequest;
import net.macmv.colacoin.network.packet.BankResponse;
import net.macmv.colacoin.network.packet.CreateVoucherRequest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BankScreen extends GuiScreen {
  private static final ResourceLocation LOGIN_BACKGROUND = new ResourceLocation("colacoin:textures/gui/login_background.png");

  protected final int windowWidth = 368;
  protected final int windowHeight = 224;
  private int x;
  private int y;

  private boolean loggedIn = false;
  private String username = "";
  private int balance = 0;

  @Override
  public void initGui() {
    this.x = (this.width - this.windowWidth) / 2;
    this.y = (this.height - this.windowHeight) / 2;

    buttonList.add(new CreateVoucherButton(100, x + 100, y + 150));

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
      drawString(fontRenderer, "Logged in as " + username, 50, 100, 0xffffff);
      drawString(fontRenderer, "Balance: " + balance, 50, 130, 0xffffff);
    } else {
      drawString(fontRenderer, "Not logged in", 50, 100, 0xffffff);
    }
    // TODO: Loading icon when not logged in

    GlStateManager.popMatrix();

    super.drawScreen(mouseX, mouseY, partialTicks);
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    if (button instanceof CreateVoucherButton) {
      // TODO: Type in an amount!
      ColaCoin.NETWORK.sendToServer(new CreateVoucherRequest(100));
    }
  }

  @SideOnly(Side.CLIENT)
  private class CreateVoucherButton extends GuiButton {
    public CreateVoucherButton(int buttonId, int x, int y) {
      super(buttonId, x, y, 200, 20, "Create Voucher");
    }
  }
}
