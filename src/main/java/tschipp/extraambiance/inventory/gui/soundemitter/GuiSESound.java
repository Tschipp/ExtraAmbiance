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

public class GuiSESound extends GuiBase
{
	private TileEntitySoundEmitter te;

	private GuiButton back;
	private GuiButton randomVolume;
	private GuiButton randomPitch;

	private GuiTextField sound;
	private NumericTextField volume;
	private NumericTextField pitch;

	public GuiSESound(TileEntitySoundEmitter te)
	{
		super(te);
		this.te = te;
		this.xSize = 176;
		this.ySize = 114;
		this.texture = new ResourceLocation(EA.MODID, "textures/gui/sound_emitter_sound.png");
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		sound.updateCursorCounter();
		volume.updateCursorCounter();
		pitch.updateCursorCounter();

	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();

		this.back = this.addButton(new GuiButton(0, this.guiLeft + 130, this.guiTop + 87, 36, 20, I18n.translateToLocal("gui.back")));
		this.randomVolume = this.addButton(new GuiButton(1, this.guiLeft + 130, this.guiTop + 36, 36, 20, "" + te.useRandomPitch));
		this.randomPitch = this.addButton(new GuiButton(2, this.guiLeft + 130, this.guiTop + 60, 36, 20, "" + te.needsPower));

		this.sound = new GuiTextField(0, fontRenderer, guiLeft + 51, this.guiTop + 9, 115, 18);
		this.sound.setMaxStringLength(32000);
		this.sound.setText(te.soundName);

		this.volume = new NumericTextField(1, fontRenderer, guiLeft + 51, guiTop + 37, 36, 18, false, false);
		this.volume.setText("" + te.volume);

		this.pitch = new NumericTextField(2, fontRenderer, guiLeft + 51, guiTop + 61, 36, 18, false, false);
		this.pitch.setText("" + te.pitch);

	}

	@Override
	protected void updateElements()
	{
		this.randomVolume.displayString = "" + te.useRandomVolume;
		this.randomPitch.displayString = "" + te.useRandomPitch;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.sound.drawTextBox();
		this.volume.drawTextBox();
		this.pitch.drawTextBox();

		
		this.drawString(fontRenderer, I18n.translateToLocal("gui.sound") + ":", this.guiLeft + 11, this.guiTop + 14, 16777215);

		this.drawString(fontRenderer, I18n.translateToLocal("gui.volume") + ":", this.guiLeft + 11, this.guiTop + 42, 16777215);

		this.drawString(fontRenderer, I18n.translateToLocal("gui.pitch") + ":", this.guiLeft + 13, this.guiTop + 65, 16777215);

		this.drawString(fontRenderer, I18n.translateToLocal("gui.random"), this.guiLeft + 91, this.guiTop + 37, 16777215);
		this.drawString(fontRenderer, I18n.translateToLocal("gui.volume") + ":", this.guiLeft + 91, this.guiTop + 46, 16777215);

		this.drawString(fontRenderer, I18n.translateToLocal("gui.random"), this.guiLeft + 91, this.guiTop + 62, 16777215);
		this.drawString(fontRenderer, I18n.translateToLocal("gui.pitch") + ":", this.guiLeft + 95, this.guiTop + 71, 16777215);
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

		this.sound.mouseClicked(mouseX, mouseY, mouseButton);
		this.volume.mouseClicked(mouseX, mouseY, mouseButton);
		this.pitch.mouseClicked(mouseX, mouseY, mouseButton);

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
				te.useRandomVolume = !te.useRandomVolume;
				break;
			case 2:
				te.useRandomPitch = !te.useRandomPitch;
				break;
			}

		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		try
		{

			if (!this.sound.textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else 
				this.te.soundName = sound.getText();
	

			if (!this.volume.textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else if (!volume.getText().isEmpty())
				this.te.volume = Float.parseFloat(volume.getText());
			else
				this.te.volume = 0f;

			if (!this.pitch.textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else if (!pitch.getText().isEmpty())
				this.te.pitch = Float.parseFloat(pitch.getText());
			else
				this.te.pitch = 0f;

		}
		catch (NumberFormatException e)
		{
		}

		super.keyTyped(typedChar, keyCode);

	}

}
