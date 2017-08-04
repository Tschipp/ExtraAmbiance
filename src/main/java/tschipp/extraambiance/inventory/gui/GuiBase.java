package tschipp.extraambiance.inventory.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import tschipp.tschipplib.helper.NetworkHelper;

public class GuiBase extends GuiScreen
{


	public int guiLeft;
	public int guiTop;
	public int xSize;
	public int ySize;
	public static ResourceLocation texture;
	private TileEntity tile;
	
	public GuiBase(TileEntity te)
	{
		tile = te;
	}
	
	@Override
	public void initGui()
	{
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int marginHorizontal = (width - xSize) / 2;
		int marginVertical = (height - ySize) / 2;
		drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	@Override
	public void onGuiClosed()
	{
		NetworkHelper.sendServerTileUpdate(tile);
	}
	
	@Override
	public void updateScreen()
	{
		updateElements();
	}


	protected void updateElements()
	{
		
	}
	
	
}
