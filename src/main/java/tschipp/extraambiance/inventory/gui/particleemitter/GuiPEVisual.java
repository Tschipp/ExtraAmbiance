package tschipp.extraambiance.inventory.gui.particleemitter;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiSlider.FormatHelper;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.inventory.gui.GuiBase;
import tschipp.extraambiance.inventory.gui.NumericTextField;
import tschipp.extraambiance.tileentity.TileEntityParticleEmitter;
import tschipp.tschipplib.helper.NetworkHelper;

public class GuiPEVisual extends GuiBase
{
	private TileEntityParticleEmitter te;

	private GuiButton back;
	private GuiButton customColor;
	private GuiButton ignoreRange;

	private GuiSlider r;
	private GuiSlider g;
	private GuiSlider b;

	private GuiTextField particleTexture;
	private GuiTextField arguments;
	private NumericTextField colorChangeAmount;

	public GuiPEVisual(TileEntityParticleEmitter te)
	{
		super(te);
		this.te = te;
		this.xSize = 176;
		this.ySize = 221;
		this.texture = new ResourceLocation(EA.MODID, "textures/gui/particle_emitter_visual.png");
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		if (te.useParticleTexture)
			particleTexture.updateCursorCounter();
		if (te.usesArguments())
			arguments.updateCursorCounter();
		if (te.changeColors)
			colorChangeAmount.updateCursorCounter();

	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();

		this.back = this.addButton(new GuiButton(0, this.guiLeft + 134, this.guiTop + 195, 36, 20, I18n.translateToLocal("gui.back")));
		this.customColor = this.addButton(new GuiButton(1, this.guiLeft + 69, this.guiTop + 7, 36, 20, "" + te.changeColors));
		this.ignoreRange = this.addButton(new GuiButton(2, this.guiLeft + 68, this.guiTop + 175, 36, 20, "" + te.ignoreRange));

		this.r = new GuiSlider(new ClassGuiResponder(te), 3, this.guiLeft + 13, this.guiTop + 30, "red", 0f, 255f, te.r, new ClassFormatHelper());
		this.g = new GuiSlider(new ClassGuiResponder(te), 4, this.guiLeft + 13, this.guiTop + 54, "green", 0f, 255f, te.g, new ClassFormatHelper());
		this.b = new GuiSlider(new ClassGuiResponder(te), 5, this.guiLeft + 13, this.guiTop + 78, "blue", 0f, 255f, te.b, new ClassFormatHelper());

		if (te.changeColors)
		{
			this.addButton(r);
			this.addButton(g);
			this.addButton(b);
		}

		this.colorChangeAmount = new NumericTextField(0, fontRenderer, guiLeft + 68, this.guiTop + 103, 35, 18, false, false);
		this.colorChangeAmount.setText("" + te.colorChangeAmount);

		this.particleTexture = new GuiTextField(1, fontRenderer, guiLeft + 68, guiTop + 128, 96, 18);
		this.particleTexture.setMaxStringLength(32000);
		this.particleTexture.setText(te.particleTexture);

		this.arguments = new GuiTextField(2, fontRenderer, guiLeft + 68, guiTop + 151, 96, 18);
		this.arguments.setMaxStringLength(32000);
		this.arguments.setText(te.arguments);

	}

	@Override
	protected void updateElements()
	{
		this.customColor.displayString = "" + te.changeColors;
		this.ignoreRange.displayString = "" + te.ignoreRange;

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		if (te.changeColors)
		{
			colorChangeAmount.drawTextBox();
		}

		if (te.useParticleTexture)
		{
			this.particleTexture.drawTextBox();
		}

		if (te.usesArguments())
		{
			this.arguments.drawTextBox();
		}

		this.drawString(fontRenderer, I18n.translateToLocal("gui.customcolor") + ":", this.guiLeft + 10, this.guiTop + 13, 16777215);

		if (te.changeColors)
		{
			this.drawString(fontRenderer, I18n.translateToLocal("gui.color"), this.guiLeft + 28, this.guiTop + 103, 16777215);
			this.drawString(fontRenderer, I18n.translateToLocal("gui.var") + ":", this.guiLeft + 18, this.guiTop + 112, 16777215);

		}
		if (te.useParticleTexture)
			this.drawString(fontRenderer, I18n.translateToLocal("gui.texture") + ":", this.guiLeft + 18, this.guiTop + 133, 16777215);

		if (te.usesArguments())
			this.drawString(fontRenderer, I18n.translateToLocal("gui.arguments") + ":", this.guiLeft + 10, this.guiTop + 156, 16777215);

		this.drawString(fontRenderer, I18n.translateToLocal("gui.ignore"), this.guiLeft + 28, this.guiTop + 176, 16777215);
		this.drawString(fontRenderer, I18n.translateToLocal("gui.range") + ":", this.guiLeft + 28, this.guiTop + 185, 16777215);

	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		super.onGuiClosed();
	}

	public static class ClassGuiResponder implements GuiResponder
	{

		private TileEntityParticleEmitter te;

		public ClassGuiResponder(TileEntityParticleEmitter te)
		{
			this.te = te;
		}

		@Override
		public void setEntryValue(int id, boolean value)
		{

		}

		@Override
		public void setEntryValue(int id, float value)
		{
			switch (id)
			{
			case 3:
				this.te.r = (int) value;
				break;
			case 4:
				this.te.g = (int) value;
				break;
			case 5:
				this.te.b = (int) value;
				break;
			}

			NetworkHelper.sendServerTileUpdate(te);
		}

		@Override
		public void setEntryValue(int id, String value)
		{

		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (te.changeColors)
		{
			colorChangeAmount.mouseClicked(mouseX, mouseY, mouseButton);
			r.mousePressed(mc, mouseX, mouseY);
			g.mousePressed(mc, mouseX, mouseY);
			b.mousePressed(mc, mouseX, mouseY);
		}

		if (te.useParticleTexture)
		{
			this.particleTexture.mouseClicked(mouseX, mouseY, mouseButton);
		}

		if (te.usesArguments())
		{
			this.arguments.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	public static class ClassFormatHelper implements FormatHelper
	{

		@Override
		public String getText(int id, String name, float value)
		{
			switch (id)
			{
			case 3:
				return I18n.translateToLocal("gui.red") + ": " + (int) value;
			case 4:
				return I18n.translateToLocal("gui.green") + ": " + (int) value;
			case 5:
				return I18n.translateToLocal("gui.blue") + ": " + (int) value;
			}

			return "";
		}

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.enabled)
		{
			switch (button.id)
			{
			case 0:
				mc.player.openGui(EA.instance, 0, te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				break;
			case 1:
				te.changeColors = !te.changeColors;
				reInitSliders();
				break;
			case 2:
				te.ignoreRange = !te.ignoreRange;
				break;

			}

		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{

		try
		{
			if (te.changeColors)
			{
				if (!this.colorChangeAmount.textboxKeyTyped(typedChar, keyCode))
					super.keyTyped(typedChar, keyCode);
				else if (!colorChangeAmount.getText().isEmpty())
					this.te.colorChangeAmount = Float.parseFloat(colorChangeAmount.getText());
				else
					this.te.colorChangeAmount = 0f;
			}

			if (te.useParticleTexture)
			{
				if (!this.particleTexture.textboxKeyTyped(typedChar, keyCode))
					super.keyTyped(typedChar, keyCode);
				else if (!particleTexture.getText().isEmpty())
					this.te.particleTexture = particleTexture.getText();
				else
					this.te.particleTexture = "";
			}
			if (te.usesArguments())
			{
				if (!this.arguments.textboxKeyTyped(typedChar, keyCode))
					super.keyTyped(typedChar, keyCode);
				else if (!arguments.getText().isEmpty())
					this.te.arguments = arguments.getText();
				else
					this.te.arguments = "";
			}
		}
		catch (NumberFormatException e)
		{
		}

		super.keyTyped(typedChar, keyCode);

	}

	public void reInitSliders()
	{
		if (te.changeColors)
		{
			this.addButton(r);
			this.addButton(g);
			this.addButton(b);
		}
		else
		{
			this.buttonList.remove(r);
			this.buttonList.remove(g);
			this.buttonList.remove(b);
		}
	}

}
