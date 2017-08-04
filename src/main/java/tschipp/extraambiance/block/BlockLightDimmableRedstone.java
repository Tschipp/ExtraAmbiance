package tschipp.extraambiance.block;

import java.util.List;
import java.util.Random;

import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.ILightEditor;
import tschipp.extraambiance.api.ILightViewer;
import tschipp.extraambiance.api.LightData;
import tschipp.tschipplib.helper.ColorHelper;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLightDimmableRedstone extends BlockLightBase
{

	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public static final PropertyInteger LIGHT = PropertyInteger.create("light", 0, 7);

	private boolean inverted;

	public BlockLightDimmableRedstone(boolean inverted)
	{
		super("light_dimmable_redstone" + (inverted ? "_inverted" : ""));
		this.setDefaultState(this.blockState.getBaseState().withProperty(LIGHT, Integer.valueOf(0)).withProperty(POWERED, Boolean.valueOf(false)));
		this.inverted = inverted;

	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		boolean flag = false;
		if (meta > 7)
		{
			meta -= 8;
			flag = true;
		}
		return this.getDefaultState().withProperty(LIGHT, Integer.valueOf(meta)).withProperty(POWERED, Boolean.valueOf(flag));
	}

	@Override
	public int getLightValue(IBlockState state)
	{
		return inverted ? ((isPowered(state) ? 0 : state.getValue(LIGHT)) * 2) : ((isPowered(state) ? state.getValue(LIGHT) : 0) * 2);

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{

		if (!playerIn.getHeldItem(hand).isEmpty() && playerIn.getHeldItem(hand).getItem() instanceof ILightEditor)
		{
			worldIn.setBlockState(pos, state.cycleProperty(LIGHT));
			((ILightEditor) playerIn.getHeldItem(hand).getItem()).onLightEdit(playerIn, pos, hand);
			playerIn.sendStatusMessage(new TextComponentString("Light: " + worldIn.getBlockState(pos).getValue(LIGHT) * 2), true);
			return true;
		}
		return false;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(LIGHT) + (state.getValue(POWERED) ? 8 : 0);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, LIGHT, POWERED);
	}

	public boolean isPowered(IBlockState state)
	{
		return state.getValue(POWERED);
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
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!worldIn.isRemote)
		{
			if (worldIn.isBlockPowered(pos) && !isPowered(state))
			{
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)));
			} else if (!worldIn.isBlockPowered(pos) && isPowered(state))
			{
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)));
			}
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
			} else if (!worldIn.isBlockPowered(pos) && isPowered(state))
			{
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)));
			}
		}
	}
	
	@Override
	public Particle getViewingParticle(World world, IBlockState state, BlockPos pos, Random rand)
	{
		float[] rgb = new float[3];
		if (!inverted)
		{
			if (isPowered(state))
				rgb = new float[] { 0.83529f, 0.1411764f, 0.0431372f };
			else
				rgb = new float[] { 0.219f, 0.219f, 0.219f };
		} else
		{
			if (isPowered(state))
				rgb = new float[] { 0.219f, 0.219f, 0.219f };
			else
				rgb = new float[] { 0.83529f, 0.1411764f, 0.0431372f };

		}
		rgb = ColorHelper.darker(0.0589f * (14 - state.getValue(LIGHT) * 2), rgb);
		return EA.proxy.getLightParticle(world, pos, 0.045f, rgb);
	}

	@Override
	public void onHover(EntityPlayer player, BlockPos pos, EnumHand hand, boolean editer)
	{
		ItemStack stack = player.getHeldItem(hand);
		if (editer)
		{
			LightData light = LightData.getLightDataFromStack(stack);
			if (light != null)
			{
				if (light.isDataCompatible(this))
					player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + I18n.translateToLocal("text.lighteditor.compatible") + TextFormatting.RESET + " Light: " + player.world.getBlockState(pos).getValue(LIGHT) * 2), true);
				else
					player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("text.lighteditor.incompatible") + TextFormatting.RESET + " Light: " + player.world.getBlockState(pos).getValue(LIGHT) * 2), true);

			} else
				player.sendStatusMessage(new TextComponentString("Light: " + player.world.getBlockState(pos).getValue(LIGHT) * 2), true);
		}
	}
	
	@Override
	public void onShiftRightClick(EntityPlayer player, World world, BlockPos pos, ItemStack stack, EnumHand hand)
	{
		LightData lightData = LightData.getLightData(world, pos);
		if(lightData != null)
		{
			lightData.saveToStack(stack);
		}
	}
}
