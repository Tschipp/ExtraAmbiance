package tschipp.extraambiance.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tschipp.extraambiance.EA;
import tschipp.tschipplib.helper.ColorHelper;

public class BlockLightComparator extends BlockLightDimmable
{

	private boolean inverted;

	public BlockLightComparator(boolean inverted)
	{
		super("light_dimmable_comparator" + (inverted ? "_inverted" : ""));
		this.inverted = inverted;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return false;
	}

	@Override
	public Particle getViewingParticle(World world, IBlockState state, BlockPos pos, Random rand)
	{
		float[] rgb = { 0.83529f, 0.1411764f, 0.0431372f };

		rgb = ColorHelper.darker(0.055f * (15 - state.getValue(LIGHT)), rgb);

		return EA.proxy.getLightParticle(world, pos, 0.045f, rgb);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!worldIn.isRemote)
		{
			if (worldIn.isBlockPowered(pos))
			{
				int power = worldIn.isBlockIndirectlyGettingPowered(pos);
				if (!inverted)
					worldIn.setBlockState(pos, state.withProperty(LIGHT, Integer.valueOf(power)));
				else
					worldIn.setBlockState(pos, state.withProperty(LIGHT, Integer.valueOf(15 - power)));

			} else if (!worldIn.isBlockPowered(pos))
			{
				if (!inverted)
					worldIn.setBlockState(pos, state.withProperty(LIGHT, Integer.valueOf(0)));
				else
					worldIn.setBlockState(pos, state.withProperty(LIGHT, Integer.valueOf(15)));

			}
		}
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return false;
	}
	
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return false;
	}
	
	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
	{
		return true;
	}
	

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!worldIn.isRemote)
		{
			if (worldIn.isBlockPowered(pos))
			{
				int power = worldIn.isBlockIndirectlyGettingPowered(pos);
				if (!inverted)
					worldIn.setBlockState(pos, state.withProperty(LIGHT, Integer.valueOf(power)));
				else
					worldIn.setBlockState(pos, state.withProperty(LIGHT, Integer.valueOf(15 - power)));
			} else if (inverted)
			{
				worldIn.setBlockState(pos, state.withProperty(LIGHT, Integer.valueOf(15)));
			}
		}
	}

}
