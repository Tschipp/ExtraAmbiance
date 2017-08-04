package tschipp.extraambiance.inventory.gui.particleemitter;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.helper.ParticleHelper;
import tschipp.extraambiance.inventory.gui.GuiBase;
import tschipp.extraambiance.tileentity.TileEntityParticleEmitter;
import tschipp.tschipplib.helper.NetworkHelper;

public class GuiParticleEmitter extends GuiBase
{
	private GuiButton rightButton;
	private GuiButton leftButton;
	private GuiButton particleButton;
	private GuiButton movementButton;
	private GuiButton visualButton;
	private GuiButton otherButton;
	private TileEntityParticleEmitter te;


	public GuiParticleEmitter(TileEntityParticleEmitter tile)
	{
		super(tile);
		this.te = tile;
		this.xSize = 176;
		this.ySize = 121;
		this.texture = new ResourceLocation(EA.MODID, "textures/gui/particle_emitter.png");	
	}

	

	@Override
	public void initGui()
	{
		super.initGui();

		this.buttonList.clear();
		this.rightButton = this.addButton(new GuiButton(0, this.guiLeft + 149, this.guiTop + 13, 18, 20, ">"));
		this.leftButton = this.addButton(new GuiButton(1, this.guiLeft + 7, this.guiTop + 13, 18, 20, "<"));
		this.particleButton = this.addButton(new GuiButton(2, this.guiLeft + 27, this.guiTop + 13, 120, 20, te.particleName));
		this.movementButton = this.addButton(new GuiButton(3, this.guiLeft + 27, this.guiTop + 38, 120, 20, I18n.translateToLocal("gui.movement")));
		this.visualButton = this.addButton(new GuiButton(4, this.guiLeft + 27, this.guiTop + 63, 120, 20, I18n.translateToLocal("gui.visual")));
		this.otherButton = this.addButton(new GuiButton(5, this.guiLeft + 27, this.guiTop + 88, 120, 20, I18n.translateToLocal("gui.other")));

		this.rightButton.enabled = true;
		this.leftButton.enabled = true;
		this.particleButton.enabled = true;
		this.movementButton.enabled = true;
		this.visualButton.enabled = true;
		this.otherButton.enabled = true;
	}

	@Override
	protected void updateElements()
	{
		this.particleButton.displayString = te.particleName;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.enabled)
		{
			switch(button.id)
			{
			case 0:
				this.te.particleName = ParticleHelper.getNext(this.particleButton.displayString);
				break;
			case 1:
				this.te.particleName = ParticleHelper.getPrevious(this.particleButton.displayString);
				break;
			case 2:
				break;
			case 3:
				Minecraft.getMinecraft().player.openGui(EA.instance, 1, te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				break;
			case 4:
				Minecraft.getMinecraft().player.openGui(EA.instance, 2, te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				break;
			case 5:
				Minecraft.getMinecraft().player.openGui(EA.instance, 3, te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				break;
			}
		}
	}
	
	
}
