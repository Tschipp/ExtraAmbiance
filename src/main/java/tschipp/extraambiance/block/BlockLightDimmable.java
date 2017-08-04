package tschipp.extraambiance.block;

import java.util.Random;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.ILightEditor;
import tschipp.extraambiance.api.LightData;
import tschipp.tschipplib.helper.ColorHelper;

public class BlockLightDimmable extends BlockLightBase
{

	public static final PropertyInteger LIGHT = PropertyInteger.create("light", 0, 15);

	public BlockLightDimmable()
	{
		super("light_dimmable");
		this.setDefaultState(this.blockState.getBaseState().withProperty(LIGHT, Integer.valueOf(0)));

	}

	public BlockLightDimmable(String name)
	{
		super(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LIGHT, Integer.valueOf(0)));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(LIGHT, Integer.valueOf(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(LIGHT);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { LIGHT });
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{

		if (!playerIn.getHeldItem(hand).isEmpty() && playerIn.getHeldItem(hand).getItem() instanceof ILightEditor)
		{
			worldIn.setBlockState(pos, state.cycleProperty(LIGHT));
			((ILightEditor) playerIn.getHeldItem(hand).getItem()).onLightEdit(playerIn, pos, hand);
			playerIn.sendStatusMessage(new TextComponentString("Light: " + worldIn.getBlockState(pos).getValue(LIGHT)), true);
			return true;
		}
		return false;
	}

	@Override
	public int getLightValue(IBlockState state)
	{
		return state.getValue(LIGHT);

	}

	@Override
	public Particle getViewingParticle(World world, IBlockState state, BlockPos pos, Random rand)
	{
		float[] rgb = { 0.929411f, 0.8862745f, 0.50980392f };
		rgb = ColorHelper.darker(0.055f * (15 - state.getValue(LIGHT)), rgb);
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
					player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + I18n.translateToLocal("text.lighteditor.compatible") + TextFormatting.RESET + " Light: " + player.world.getBlockState(pos).getValue(LIGHT)), true);
				else
					player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("text.lighteditor.incompatible") + TextFormatting.RESET + " Light: " + player.world.getBlockState(pos).getValue(LIGHT) ), true);

			} else
				player.sendStatusMessage(new TextComponentString("Light: " + player.world.getBlockState(pos).getValue(LIGHT)), true);
		}
	}

	@Override
	public void onShiftRightClick(EntityPlayer player, World world, BlockPos pos, ItemStack stack, EnumHand hand)
	{
		LightData lightData = LightData.getLightData(world, pos);
		if (lightData != null)
		{
			lightData.saveToStack(stack);
		}
	}

}
