package tschipp.extraambiance.tileentity;

import java.awt.Color;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.Method;
import tschipp.extraambiance.api.Constants;
import tschipp.extraambiance.block.BlockColoredLight;
import tschipp.tschipplib.helper.ColorHelper;

@Interface(iface = "elucent.albedo.lighting.ILightProvider", modid = "albedo")
public class TileEntityColoredLight extends TileEntity implements ILightProvider
{

	// VISUAL
	public int r = 255;
	public int g = 255;
	public int b = 255;

	public int range = 15;

	public int color = 0;
	
	public boolean needsPower = false;

	public TileEntityColoredLight()
	{
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagCompound particle = new NBTTagCompound();
		particle.setInteger("red", r);
		particle.setInteger("green", g);
		particle.setInteger("blue", b);
		particle.setInteger("range", range);
		particle.setBoolean("needsPower", needsPower);
		compound.setTag(Constants.LIGHT_DATA_KEY, particle);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagCompound particle = compound.getCompoundTag(Constants.LIGHT_DATA_KEY);
		this.readColorData(particle);

	}

	public void readColorData(NBTTagCompound particle)
	{
		r = particle.getInteger("red");
		g = particle.getInteger("green");
		b = particle.getInteger("blue");
		range = particle.getInteger("range");
		needsPower = particle.getBoolean("needsPower");
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.pos, 0, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag = this.writeToNBT(tag);
		return tag;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return oldState.getBlock() != newSate.getBlock();
	}

	@Method(modid = "albedo")
	public Light provideLight()
	{
		if (world.getBlockState(pos).getBlock() instanceof BlockColoredLight)
		{
			if (needsPower ? world.getBlockState(pos).getValue(BlockColoredLight.POWERED) : !world.getBlockState(pos).getValue(BlockColoredLight.POWERED))
			{
				float[] rgb = ColorHelper.getRGB(r, g, b);
//				float[] rgb = ColorHelper.getRGB(Color.getHSBColor(((float)color/360), 1f, 1f));

//				if(color < 359)
//					color++;
//				else 
//					color = 0;
				return Light.builder().color(rgb[0], rgb[1], rgb[2]).pos(pos).radius((float) range).build();
			}
		}
		return null;
	}

}
