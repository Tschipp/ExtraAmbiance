package tschipp.extraambiance.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.ILightEditor;
import tschipp.extraambiance.api.LightData;
import tschipp.extraambiance.handler.ItemHandler;
import tschipp.extraambiance.tileentity.TileEntityParticleEmitter;
import tschipp.tschipplib.helper.ItemStackHelper;

public class BlockParticleEmitterAdvanced extends BlockLightBase implements ITileEntityProvider
{

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockParticleEmitterAdvanced()
	{
		super("particle_emitter_advanced");
		this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.valueOf(false)));
		this.hasTileEntity = true;
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
		return new BlockStateContainer(this, POWERED);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
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
			}
			else if (!worldIn.isBlockPowered(pos) && isPowered(state))
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
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!worldIn.isRemote)
		{
			if (worldIn.isBlockPowered(pos) && !isPowered(state))
			{
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)));
			}
			else if (!worldIn.isBlockPowered(pos) && isPowered(state))
			{
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)));
			}
		}
	}

	@Override
	public Particle getViewingParticle(World world, IBlockState state, BlockPos pos, Random rand)
	{
		return EA.proxy.getLightParticle(world, pos, 0.1f, 0.0f, 1f, 0.6f);

	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityParticleEmitter();
	}

	@Override
	public void onBreak(EntityPlayer player, World world, BlockPos pos)
	{
		LightData lightData = LightData.getLightData(world, pos);
		ItemStack drop = new ItemStack(ItemHandler.particleEmitterAdvanced);
		lightData.getNbt().setInteger("timer", 0);
		lightData.saveToStack(drop);

		Block.spawnAsEntity(world, pos, drop);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		LightData lightData = LightData.getLightDataFromStack(stack);
		if (lightData != null)
		{
			lightData.apply(worldIn, pos);
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
					player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + I18n.translateToLocal("text.lighteditor.compatible")), true);
				else
					player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("text.lighteditor.incompatible")), true);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (ItemStackHelper.hasItemHeld(ILightEditor.class, playerIn))
		{
			EnumHand hand1 = ItemStackHelper.getHandForType(ILightEditor.class, playerIn);
			if (hand1 == hand)
			{
				playerIn.openGui(EA.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
				Item item = playerIn.getHeldItem(hand1).getItem();
				((ILightEditor) item).onLightEdit(playerIn, pos, hand1);
				return true;
			}
		}
		return false;
	}

}
