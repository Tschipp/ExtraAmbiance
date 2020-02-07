package tschipp.extraambiance.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tschipp.extraambiance.inventory.gui.GuiColoredLight;
import tschipp.extraambiance.inventory.gui.particleemitter.GuiPEMovement;
import tschipp.extraambiance.inventory.gui.particleemitter.GuiPEOther;
import tschipp.extraambiance.inventory.gui.particleemitter.GuiPEVisual;
import tschipp.extraambiance.inventory.gui.particleemitter.GuiParticleEmitter;
import tschipp.extraambiance.inventory.gui.soundemitter.GuiSEOther;
import tschipp.extraambiance.inventory.gui.soundemitter.GuiSEPosition;
import tschipp.extraambiance.inventory.gui.soundemitter.GuiSESound;
import tschipp.extraambiance.inventory.gui.soundemitter.GuiSoundEmitter;
import tschipp.extraambiance.tileentity.TileEntityColoredLight;
import tschipp.extraambiance.tileentity.TileEntityParticleEmitter;
import tschipp.extraambiance.tileentity.TileEntitySoundEmitter;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
		case 0:
			return new GuiParticleEmitter((TileEntityParticleEmitter)world.getTileEntity(new BlockPos(x,y,z)));
		case 1:
			return new GuiPEMovement((TileEntityParticleEmitter)world.getTileEntity(new BlockPos(x,y,z)));
		case 2:
			return new GuiPEVisual((TileEntityParticleEmitter)world.getTileEntity(new BlockPos(x,y,z)));
		case 3:
			return new GuiPEOther((TileEntityParticleEmitter)world.getTileEntity(new BlockPos(x,y,z)));
		case 4:
			return new GuiSoundEmitter((TileEntitySoundEmitter)world.getTileEntity(new BlockPos(x,y,z)));
		case 5:
			return new GuiSESound((TileEntitySoundEmitter)world.getTileEntity(new BlockPos(x,y,z)));
		case 6:
			return new GuiSEPosition((TileEntitySoundEmitter)world.getTileEntity(new BlockPos(x,y,z)));
		case 7:
			return new GuiSEOther((TileEntitySoundEmitter)world.getTileEntity(new BlockPos(x,y,z)));
		case 8:
			return new GuiColoredLight((TileEntityColoredLight)world.getTileEntity(new BlockPos(x,y,z)));


		}
		
		return null;
	}
}
