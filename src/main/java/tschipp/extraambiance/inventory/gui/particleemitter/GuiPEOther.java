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
import tschipp.extraambiance.handler.ConfigHandler;
import tschipp.extraambiance.inventory.gui.GuiBase;
import tschipp.extraambiance.inventory.gui.NumericTextField;
import tschipp.extraambiance.inventory.gui.NumericTextFieldWithLimit;
import tschipp.extraambiance.tileentity.TileEntityParticleEmitter;
import tschipp.tschipplib.helper.NetworkHelper;

public class GuiPEOther extends GuiBase
{
	private TileEntityParticleEmitter te;

	private GuiButton back;
	private GuiButton needsPower;

	private NumericTextField spawnTime;
	private NumericTextFieldWithLimit spawnCount;
	private NumericTextFieldWithLimit particleSize;
	private NumericTextFieldWithLimit particleMaxAge;

	public GuiPEOther(TileEntityParticleEmitter te)
	{
		super(te);
		this.te = te;
		this.xSize = 176;
		this.ySize = 92;
		this.texture = new ResourceLocation(EA.MODID, "textures/gui/particle_emitter_other.png");
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		spawnTime.updateCursorCounter();
		spawnCount.updateCursorCounter();
		particleSize.updateCursorCounter();
		particleMaxAge.updateCursorCounter();

	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();

		this.back = this.addButton(new GuiButton(0, this.guiLeft + 133, this.guiTop + 62, 36, 20, I18n.translateToLocal("gui.back")));
		this.needsPower = this.addButton(new GuiButton(1, this.guiLeft + 71, this.guiTop + 62, 36, 20, "" + te.needsPower));

		this.spawnTime = new NumericTextField(0, fontRenderer, guiLeft + 49, this.guiTop + 11, 36, 18, true, false);
		this.spawnTime.setText("" + te.spawnTime);

		this.spawnCount = new NumericTextFieldWithLimit(1, fontRenderer, guiLeft + 49, guiTop + 34, 36, 18, true, false, ConfigHandler.maxSpawnCount);
		this.spawnCount.setText("" + te.spawnCount);

		this.particleSize = new NumericTextFieldWithLimit(2, fontRenderer, guiLeft + 132, guiTop + 11, 36, 18, false, false, ConfigHandler.maxParticleSize);
		this.particleSize.setText("" + te.particleSize);

		this.particleMaxAge = new NumericTextFieldWithLimit(3, fontRenderer, guiLeft + 132, guiTop + 34, 36, 18, true, false, ConfigHandler.maxParticleMaxAge);
		this.particleMaxAge.setText("" + te.particleMaxAge);
	}

	@Override
	protected void updateElements()
	{
		this.needsPower.displayString = "" + te.needsPower;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.spawnTime.drawTextBox();
		this.spawnCount.drawTextBox();
		this.particleSize.drawTextBox();
		this.particleMaxAge.drawTextBox();

		this.drawString(fontRenderer, I18n.translateToLocal("gui.spawn"), this.guiLeft + 10, this.guiTop + 10, 16777215);
		this.drawString(fontRenderer, I18n.translateToLocal("gui.delay") + ":", this.guiLeft + 11, this.guiTop + 19, 16777215);

		this.drawString(fontRenderer, I18n.translateToLocal("gui.spawn"), this.guiLeft + 10, this.guiTop + 33, 16777215);
		this.drawString(fontRenderer, I18n.translateToLocal("gui.count") + ":", this.guiLeft + 11, this.guiTop + 42, 16777215);

		this.drawString(fontRenderer, I18n.translateToLocal("gui.size") + ":", this.guiLeft + 96, this.guiTop + 15, 16777215);
		this.drawString(fontRenderer, I18n.translateToLocal("gui.maxage") + ":", this.guiLeft + 88, this.guiTop + 38, 16777215);

		this.drawString(fontRenderer, I18n.translateToLocal("gui.needs"), this.guiLeft + 18, this.guiTop + 63, 16777215);
		this.drawString(fontRenderer, I18n.translateToLocal("gui.redstone") + ":", this.guiLeft + 10, this.guiTop + 72, 16777215);

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
		this.spawnCount.mouseClicked(mouseX, mouseY, mouseButton);
		this.particleSize.mouseClicked(mouseX, mouseY, mouseButton);
		this.particleMaxAge.mouseClicked(mouseX, mouseY, mouseButton);

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

			if (!this.spawnCount.textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else if (!spawnCount.getText().isEmpty())
				this.te.spawnCount = Integer.parseInt(spawnCount.getText());
			else
				this.te.spawnCount = 0;

			if (!this.particleSize.textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else if (!particleSize.getText().isEmpty())
				this.te.particleSize = Float.parseFloat(particleSize.getText());
			else
				this.te.particleSize = 0f;

			if (!this.particleMaxAge.textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else if (!particleMaxAge.getText().isEmpty())
				this.te.particleMaxAge = Integer.parseInt(particleMaxAge.getText());
			else
				this.te.particleMaxAge = 0;

		}
		catch (NumberFormatException e)
		{
		}

		super.keyTyped(typedChar, keyCode);

	}

}
