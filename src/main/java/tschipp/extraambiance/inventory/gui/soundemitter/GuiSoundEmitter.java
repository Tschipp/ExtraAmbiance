package tschipp.extraambiance.inventory.gui.soundemitter;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.helper.ParticleHelper;
import tschipp.extraambiance.inventory.gui.GuiBase;
import tschipp.extraambiance.tileentity.TileEntitySoundEmitter;

public class GuiSoundEmitter extends GuiBase
{
	private GuiButton soundButton;
	private GuiButton positionButton;
	private GuiButton otherButton;
	private TileEntitySoundEmitter te;


	public GuiSoundEmitter(TileEntitySoundEmitter tile)
	{
		super(tile);
		this.te = tile;
		this.xSize = 176;
		this.ySize = 95;
		this.texture = new ResourceLocation(EA.MODID, "textures/gui/sound_emitter.png");	
	}

	

	@Override
	public void initGui()
	{
		super.initGui();

		this.buttonList.clear();
		this.soundButton = this.addButton(new GuiButton(0, this.guiLeft + 27, this.guiTop + 13, 120, 20, I18n.translateToLocal("gui.sound")));
		this.positionButton = this.addButton(new GuiButton(1, this.guiLeft + 27, this.guiTop + 38, 120, 20, I18n.translateToLocal("gui.position")));
		this.otherButton = this.addButton(new GuiButton(2, this.guiLeft + 27, this.guiTop + 63, 120, 20, I18n.translateToLocal("gui.other")));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.enabled)
		{
			switch(button.id)
			{
			case 0:
				Minecraft.getMinecraft().player.openGui(EA.instance, 5, te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				break;
			case 1:
				Minecraft.getMinecraft().player.openGui(EA.instance, 6, te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				break;
			case 2:
				Minecraft.getMinecraft().player.openGui(EA.instance, 7, te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				break;
			}
		}
	}
	
	
}
