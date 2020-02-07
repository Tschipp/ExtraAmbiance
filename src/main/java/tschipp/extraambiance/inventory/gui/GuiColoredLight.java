package tschipp.extraambiance.inventory.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiSlider.FormatHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.tileentity.TileEntityColoredLight;
import tschipp.tschipplib.helper.NetworkHelper;

public class GuiColoredLight extends GuiBase
{

	private TileEntityColoredLight te;

	private GuiButton needRedstone;

	private GuiSlider r;
	private GuiSlider g;
	private GuiSlider b;
	private GuiSlider range;

	public GuiColoredLight(TileEntityColoredLight te)
	{
		super(te);
		this.te = te;
		this.xSize = 176;
		this.ySize = 144;
		this.texture = new ResourceLocation(EA.MODID, "textures/gui/colored_light.png");

	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.needRedstone = this.addButton(new GuiButton(0, this.guiLeft + 69, this.guiTop + 110, 36, 20, "" + te.needsPower));

		this.r = new GuiSlider(new ClassGuiResponder(te), 1, this.guiLeft + 13, this.guiTop + 12, "red", 0f, 255f, te.r, new ClassFormatHelper());
		this.g = new GuiSlider(new ClassGuiResponder(te), 2, this.guiLeft + 13, this.guiTop + 36, "green", 0f, 255f, te.g, new ClassFormatHelper());
		this.b = new GuiSlider(new ClassGuiResponder(te), 3, this.guiLeft + 13, this.guiTop + 60, "blue", 0f, 255f, te.b, new ClassFormatHelper());
		this.range = new GuiSlider(new ClassGuiResponder(te), 4, this.guiLeft + 13, this.guiTop + 84, "range", 0f, 15f, te.range, new ClassFormatHelper());

		this.addButton(r);
		this.addButton(g);
		this.addButton(b);
		this.addButton(range);

	}
	
	@Override
	protected void updateElements()
	{
		this.needRedstone.displayString = "" + te.needsPower;

	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);


		this.drawString(fontRenderer, I18n.translateToLocal("gui.needs"), this.guiLeft + 18, this.guiTop + 111, 16777215);
		this.drawString(fontRenderer, I18n.translateToLocal("gui.redstone") + ":", this.guiLeft + 10, this.guiTop + 120, 16777215);

	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		super.onGuiClosed();
	}

	public static class ClassGuiResponder implements GuiResponder
	{

		private TileEntityColoredLight te;

		public ClassGuiResponder(TileEntityColoredLight te)
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
			case 1:
				this.te.r = (int) value;
				break;
			case 2:
				this.te.g = (int) value;
				break;
			case 3:
				this.te.b = (int) value;
				break;
			case 4:
				this.te.range = (int) value;
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

		r.mousePressed(mc, mouseX, mouseY);
		g.mousePressed(mc, mouseX, mouseY);
		b.mousePressed(mc, mouseX, mouseY);
		range.mousePressed(mc, mouseX, mouseY);
	}

	public static class ClassFormatHelper implements FormatHelper
	{

		@Override
		public String getText(int id, String name, float value)
		{
			switch (id)
			{
			case 1:
				return I18n.translateToLocal("gui.red") + ": " + (int) value;
			case 2:
				return I18n.translateToLocal("gui.green") + ": " + (int) value;
			case 3:
				return I18n.translateToLocal("gui.blue") + ": " + (int) value;
			case 4:
				return I18n.translateToLocal("gui.range") + ": " + (int) value;
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
				te.needsPower = !te.needsPower;
				break;
			}

		}
	}

}
