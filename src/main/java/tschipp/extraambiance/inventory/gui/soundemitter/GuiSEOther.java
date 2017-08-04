package tschipp.extraambiance.inventory.gui.soundemitter;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.handler.ConfigHandler;
import tschipp.extraambiance.inventory.gui.GuiBase;
import tschipp.extraambiance.inventory.gui.NumericTextField;
import tschipp.extraambiance.inventory.gui.NumericTextFieldWithLimit;
import tschipp.extraambiance.tileentity.TileEntitySoundEmitter;

public class GuiSEOther extends GuiBase
{
	private TileEntitySoundEmitter te;

	private GuiButton back;
	private GuiButton needsRedstone;

	private NumericTextField spawnTime;


	public GuiSEOther(TileEntitySoundEmitter te)
	{
		super(te);
		this.te = te;
		this.xSize = 91;
		this.ySize = 85;
		this.texture = new ResourceLocation(EA.MODID, "textures/gui/sound_emitter_other.png");
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		spawnTime.updateCursorCounter();

	}
	
	@Override
	protected void updateElements()
	{
		this.needsRedstone.displayString = "" + te.needsPower;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();

		this.back = this.addButton(new GuiButton(0, this.guiLeft + 47, this.guiTop + 57, 36, 20, I18n.translateToLocal("gui.back")));
		this.needsRedstone = this.addButton(new GuiButton(1, this.guiLeft + 47, this.guiTop + 30, 36, 20, "" + te.needsPower));

		this.spawnTime = new NumericTextField(0, fontRendererObj, guiLeft + 47, guiTop + 8, 36, 18, true, false);
		this.spawnTime.setText("" + te.spawnTime);

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.spawnTime.drawTextBox();
		
		this.drawString(fontRendererObj, I18n.translateToLocal("gui.spawn"), this.guiLeft + 7, this.guiTop + 7, 16777215);
		this.drawString(fontRendererObj, I18n.translateToLocal("gui.delay") + ":", this.guiLeft + 8, this.guiTop + 17, 16777215);

		this.drawString(fontRendererObj, I18n.translateToLocal("gui.needs"), this.guiLeft + 9, this.guiTop + 32, 16777215);
		this.drawString(fontRendererObj, I18n.translateToLocal("gui.redstone") + ":", this.guiLeft + 3, this.guiTop + 40, 16777215);
		
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		super.onGuiClosed();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		this.spawnTime.mouseClicked(mouseX, mouseY, mouseButton);

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.enabled)
		{
			switch (button.id)
			{
			case 0:
				mc.player.openGui(EA.instance, 4, te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				break;
			case 1:
				te.needsPower = !te.needsPower;
				break;
			}

		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		try
		{
			if (!this.spawnTime.textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else if (!spawnTime.getText().isEmpty())
				this.te.spawnTime = Integer.parseInt(spawnTime.getText());
			else
				this.te.spawnTime = 0;

		}
		catch (NumberFormatException e)
		{
		}

		super.keyTyped(typedChar, keyCode);

	}

}
