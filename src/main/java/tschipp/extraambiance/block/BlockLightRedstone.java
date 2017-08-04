package tschipp.extraambiance.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.ILightViewer;
import tschipp.extraambiance.particle.ParticleLight;

public class BlockLightRedstone extends BlockLightBase {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	private boolean inverted = false;

	public BlockLightRedstone(boolean inverted) {
		super("light_redstone" + (inverted ? "_inverted" : ""));
		this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.valueOf(false)));
		this.inverted = inverted;

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(POWERED, Boolean.valueOf(meta > 0));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(POWERED) == Boolean.valueOf(false) ? 0 : 1;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {POWERED});
	}

	@Override
	public int getLightValue(IBlockState state) {
		if(inverted)
			return isPowered(state) ? 0 : 15;
		else
			return isPowered(state) ? 15 : 0;

	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!worldIn.isRemote)
		{
			if (worldIn.isBlockPowered(pos) && !isPowered(state))
			{
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)));
			}
			else if(!worldIn.isBlockPowered(pos) && isPowered(state))
			{
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)));
			}
		}
	}

	public boolean isPowered(IBlockState state)
	{
		return state.getValue(POWERED);
	}

	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
	{
		return true;
	}
	
	@Override
	public Particle getViewingParticle(World world, IBlockState state, BlockPos pos, Random rand)
	{
		if(!inverted)
		{
			if(isPowered(state))
				return EA.proxy.getLightParticle(world, pos, 0.075f, 0.83529f, 0.1411764f, 0.0431372f);
			else
				return EA.proxy.getLightParticle(world, pos, 0.025f, 0.219f, 0.219f, 0.219f);

		}
		else
		{
			if(isPowered(state))
				return EA.proxy.getLightParticle(world, pos, 0.025f, 0.219f, 0.219f, 0.219f);
			else
				return EA.proxy.getLightParticle(world, pos, 0.075f, 0.83529f, 0.1411764f, 0.0431372f);

		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) 
	{
		if (!worldIn.isRemote)
		{
			if (worldIn.isBlockPowered(pos) && !isPowered(state))
			{
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)));
			}
			else if(!worldIn.isBlockPowered(pos) && isPowered(state))
			{
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)));
			}
		}
	}


}
