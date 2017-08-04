package tschipp.extraambiance.inventory.gui.particleemitter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.inventory.gui.GuiBase;
import tschipp.extraambiance.inventory.gui.NumericTextField;
import tschipp.extraambiance.tileentity.TileEntityParticleEmitter;

public class GuiPEMovement extends GuiBase
{
	private GuiButton useRandomMotion;
	private GuiButton back;

	private NumericTextField[] spawn = new NumericTextField[3], radius = new NumericTextField[3], motion = new NumericTextField[3];

	private TileEntityParticleEmitter te;

	public GuiPEMovement(TileEntityParticleEmitter tile)
	{
		super(tile);
		this.te = tile;
		this.xSize = 176;
		this.ySize = 180;
		this.texture = new ResourceLocation(EA.MODID, "textures/gui/particle_emitter_movement.png");
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		for (int i = 0; i < 3; i++)
		{
			spawn[i].updateCursorCounter();
			radius[i].updateCursorCounter();
			motion[i].updateCursorCounter();
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.useRandomMotion = this.addButton(new GuiButton(0, this.guiLeft + 130, this.guiTop + 85, 36, 20, "" + te.randomMotion));
		this.back = this.addButton(new GuiButton(1, this.guiLeft + 130, this.guiTop + 132, 36, 20, I18n.translateToLocal("gui.back")));
		this.useRandomMotion.enabled = true;
		this.back.enabled = true;

		for (int i = 0; i < 3; i++)
		{
			spawn[i] = new NumericTextField(0 + i, fontRendererObj, this.guiLeft + 51, this.guiTop + 9 + (24 * i), 35, 18, false, true);
			radius[i] = new NumericTextField(3 + i, fontRendererObj, this.guiLeft + 51, this.guiTop + 86 + (24 * i), 35, 18, false, false);
			motion[i] = new NumericTextField(6 + i, fontRendererObj, this.guiLeft + 135, this.guiTop + 9 + (24 * i), 35, 18, false, true);
		}

		spawn[0].setText("" + te.spawnX);
		spawn[1].setText("" + te.spawnY);
		spawn[2].setText("" + te.spawnZ);

		radius[0].setText("" + te.spawnRadiusX);
		radius[1].setText("" + te.spawnRadiusY);
		radius[2].setText("" + te.spawnRadiusZ);

		motion[0].setText("" + te.motionX);
		motion[1].setText("" + te.motionY);
		motion[2].setText("" + te.motionZ);
	}

	@Override
	protected void updateElements()
	{
		this.useRandomMotion.displayString = "" + te.randomMotion;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		for (int i = 0; i < 3; i++)
		{
			spawn[i].drawTextBox();
			radius[i].drawTextBox();
			motion[i].drawTextBox();
		}

		for (int i = 0; i < 3; i++)
		{
			this.drawString(fontRendererObj, I18n.translateToLocal("gui.spawn." + i) + ":", this.guiLeft + 5, this.guiTop + 14 + (24 * i), 16777215);
			this.drawString(fontRendererObj, I18n.translateToLocal("gui.radius." + i) + ":", this.guiLeft + 5, this.guiTop + 91 + (24 * i), 16777215);
			this.drawString(fontRendererObj, I18n.translateToLocal("gui.motion." + i) + ":", this.guiLeft + 90, this.guiTop + 14 + (24 * i), 16777215);
		}

		this.drawString(fontRendererObj, I18n.translateToLocal("gui.random"), this.guiLeft + 90, this.guiTop + 85, 16777215);
		this.drawString(fontRendererObj, I18n.translateToLocal("gui.motion") + ":", this.guiLeft + 90, this.guiTop + 94, 16777215);

	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		super.onGuiClosed();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.enabled)
		{
			switch (button.id)
			{
			case 0:
				te.randomMotion = !te.randomMotion;
				break;
			case 1:
				mc.player.openGui(EA.instance, 0, te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				break;
			}
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		for (int i = 0; i < 3; i++)
		{
			if (!spawn[i].textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else
			{
				if (!spawn[i].getText().isEmpty())
					try
					{
						switch (i)
						{
						case 0:
							this.te.spawnX = Double.parseDouble(spawn[i].getText());
							break;
						case 1:
							this.te.spawnY = Double.parseDouble(spawn[i].getText());
							break;
						case 2:
							this.te.spawnZ = Double.parseDouble(spawn[i].getText());
							break;
						}
					}
					catch (NumberFormatException e)
					{
					}
				else
					switch (i)
					{
					case 0:
						this.te.spawnX = 0;
						break;
					case 1:
						this.te.spawnY = 0;
						break;
					case 2:
						this.te.spawnZ = 0;
						break;
					}
			}
			if (!radius[i].textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else
			{
				if (!radius[i].getText().isEmpty())
					try
					{
						switch (i)
						{
						case 0:
							this.te.spawnRadiusX = Double.parseDouble(radius[i].getText());
							break;
						case 1:
							this.te.spawnRadiusY = Double.parseDouble(radius[i].getText());
							break;
						case 2:
							this.te.spawnRadiusZ = Double.parseDouble(radius[i].getText());
							break;
						}
					}
					catch (NumberFormatException e)
					{
					}
				else
					switch (i)
					{
					case 0:
						this.te.spawnRadiusX = 0;
						break;
					case 1:
						this.te.spawnRadiusY = 0;
						break;
					case 2:
						this.te.spawnRadiusZ = 0;
						break;
					}
			}
			if (!motion[i].textboxKeyTyped(typedChar, keyCode))
				super.keyTyped(typedChar, keyCode);
			else
			{
				if (!motion[i].getText().isEmpty())
					try
					{
						switch (i)
						{
						case 0:
							this.te.motionX = Double.parseDouble(motion[i].getText());
							break;
						case 1:
							this.te.motionY = Double.parseDouble(motion[i].getText());
							break;
						case 2:
							this.te.motionZ = Double.parseDouble(motion[i].getText());
							break;
						}
					}
					catch (NumberFormatException e)
					{
					}

				else
					switch (i)
					{
					case 0:
						this.te.motionX = 0;
						break;
					case 1:
						this.te.motionY = 0;
						break;
					case 2:
						this.te.motionZ = 0;
						break;
					}
			}

		}

	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for (int i = 0; i < 3; i++)
		{
			spawn[i].mouseClicked(mouseX, mouseY, mouseButton);
			radius[i].mouseClicked(mouseX, mouseY, mouseButton);
			motion[i].mouseClicked(mouseX, mouseY, mouseButton);

		}
	}

}
