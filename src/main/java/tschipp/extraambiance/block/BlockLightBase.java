package tschipp.extraambiance.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.Constants;
import tschipp.extraambiance.api.ILight;
import tschipp.tschipplib.block.TSBlock;

public class BlockLightBase extends TSBlock implements ILight
{

	public BlockLightBase(String name)
	{
		super(name, Material.SPONGE, MapColor.AIR, EA.MODID, false);
		this.setCreativeTab(EA.extraambiance);
		this.setHardness(0.5f);
		this.setResistance(0.3f);
		this.setSoundType(SoundType.CLOTH);
		this.setTickRandomly(true);

	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return Constants.LIGHT_AABB;

	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return Block.NULL_AABB;
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onHover(EntityPlayer player, BlockPos pos, EnumHand hand, boolean editer)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		this.generateViewingParticles(worldIn, stateIn, pos, rand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Particle getViewingParticle(World world, IBlockState state, BlockPos pos, Random rand)
	{
		return null;
	}

	@Override
	public void onShiftRightClick(EntityPlayer player, World world, BlockPos pos, ItemStack stack, EnumHand hand)
	{

	}

}
