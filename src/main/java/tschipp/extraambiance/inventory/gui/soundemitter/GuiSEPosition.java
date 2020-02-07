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

public class GuiSEPosition extends GuiBase
{
	private TileEntitySoundEmitter te;

	private GuiButton back;
	
	private NumericTextField spawnX;
	private NumericTextField spawnY;
	private NumericTextField spawnZ;

	public GuiSEPosition(TileEntitySoundEmitter te)
	{
		super(te);
		this.te = te;
		this.xSize = 91;
		this.ySize = 106;
		this.texture = new ResourceLocation(EA.MODID, "textures/gui/sound_emitter_position.png");
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		spawnX.updateCursorCounter();
		spawnY.updateCursorCounter();
		spawnZ.updateCursorCounter();

	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();

		this.back = this.addButton(new GuiButton(0, this.guiLeft + 47, this.guiTop + 79, 36, 20, I18n.translateToLocal("gui.back")));

		this.spawnX = new NumericTextField(0, fontRenderer, guiLeft + 47, guiTop + 8, 36, 18, false, true);
		this.spawnX.setText("" + te.spawnX);

		this.spawnY = new NumericTextField(1, fontRenderer, guiLeft + 47, guiTop + 31, 36, 18, false, true);
		this.spawnY.setText("" + te.spawnY);

		this.spawnZ = new NumericTextField(2, fontRenderer, guiLeft + 47, guiTop + 54, 36, 18, false, true);
		this.spawnZ.setText("" + te.spawnZ);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.spawnX.drawTextBox();
		this.spawnY.drawTextBox();
		this.spawnZ.drawTextBox();

		
		this.drawString(fontRenderer, I18n.translateToLocal("gui.spawn.0") + ":", this.guiLeft + 3, this.guiTop + 13, 16777215);

		this.drawString(fontRenderer, I18n.translateToLocal("gui.spawn.1") + ":", this.guiLeft + 3, this.guiTop + 36, 16777215);

		this.drawString(fontRenderer, I18n.translateToLocal("gui.spawn.2") + ":", this.guiLeft + 3, this.guiTop + 59, 16777215);
		
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

		this.spawnX.mouseClicked(mouseX, mouseY, mouseButton);
		this.spawnY.mouseClicked(mouseX, mouseY, mouseButton);
		this.spawnZ.mouseClicked(mouseX, mouseY, mouseButton);

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
			}

		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		try
		{
			if (!this.spawnX.textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else if (!spawnX.getText().isEmpty())
				this.te.spawnX = Double.parseDouble(spawnX.getText());
			else
				this.te.spawnX = 0;

			if (!this.spawnY.textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else if (!spawnY.getText().isEmpty())
				this.te.spawnY = Double.parseDouble(spawnY.getText());
			else
				this.te.spawnY = 0;
		
			if (!this.spawnZ.textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else if (!spawnZ.getText().isEmpty())
				this.te.spawnZ = Double.parseDouble(spawnZ.getText());
			else
				this.te.spawnZ = 0;

		}
		catch (NumberFormatException e)
		{
		}

		super.keyTyped(typedChar, keyCode);

	}

}
