package tschipp.extraambiance.block;

import java.util.List;
import java.util.Random;

import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.ILightViewer;
import tschipp.tschipplib.block.IMetaBlockName;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockParticleEmitterSimple extends BlockLightBase implements IMetaBlockName {

	private boolean light;
	public static final PropertyEnum<BlockParticleEmitterSimple.EnumType> PARTICLE = PropertyEnum.<BlockParticleEmitterSimple.EnumType>create("particle", BlockParticleEmitterSimple.EnumType.class);
	
	
	public BlockParticleEmitterSimple(boolean light) {
		super("particle_emitter" + (light ? "_light" : ""));
		this.setDefaultState(this.blockState.getBaseState().withProperty(PARTICLE, EnumType.FLAME));
		this.light = light;

	}
	
	@Override
	public int getLightValue(IBlockState state) {
		return light ? 15 : 0;

	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		if(itemIn == EA.extraambiance)
		for (EnumType type : EnumType.values())
		{
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PARTICLE, EnumType.byMetadata(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumType)state.getValue(PARTICLE)).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {PARTICLE});
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, this.getMetaFromState(state));
    }
	
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		int meta = stack.getMetadata();
		
		worldIn.setBlockState(pos, getStateFromMeta(meta));
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	@Override
	public void randomDisplayTick( IBlockState state, World world, BlockPos pos, Random rand) {
		world.spawnParticle(state.getValue(PARTICLE).particle, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
		
		super.randomDisplayTick(state, world, pos, rand);
	}
	
	@Override
	public Particle getViewingParticle(World world, IBlockState state, BlockPos pos, Random rand)
	{
		return EA.proxy.getLightParticle(world, pos, 0.065f, 0.2627f, 0.75f, 0f);

	}
		
	public static enum EnumType implements IStringSerializable
	{
		FLAME(0, EnumParticleTypes.FLAME),
		SMOKE_LARGE(1, EnumParticleTypes.SMOKE_LARGE),
		CLOUD(2, EnumParticleTypes.CLOUD),
		ENCHANTMENT_TABLE(3, EnumParticleTypes.ENCHANTMENT_TABLE),
		CRIT_MAGIC(4, EnumParticleTypes.CRIT_MAGIC),
		END_ROD(5, EnumParticleTypes.END_ROD),
		EXPLOSION_LARGE(6, EnumParticleTypes.EXPLOSION_LARGE),
		FIREWORKS_SPARK(7, EnumParticleTypes.FIREWORKS_SPARK),
		HEART(8, EnumParticleTypes.HEART),
		LAVA(9, EnumParticleTypes.LAVA),
		NOTE(10, EnumParticleTypes.NOTE),
		PORTAL(11, EnumParticleTypes.PORTAL),
		REDSTONE(12, EnumParticleTypes.REDSTONE),
		TOTEM(13, EnumParticleTypes.TOTEM),
		VILLAGER_HAPPY(14, EnumParticleTypes.VILLAGER_HAPPY),
		DRAGON_BREATH(15, EnumParticleTypes.DRAGON_BREATH);


		/** Array of the Block's BlockStates */
		private static final BlockParticleEmitterSimple.EnumType[] META = new BlockParticleEmitterSimple.EnumType[values().length];
		/** The BlockState's metadata. */
		private final int meta;
		private final EnumParticleTypes particle;
		
		private EnumType(int meta, EnumParticleTypes particle)
		{
			this.meta = meta;
			this.particle = particle;	
		}

		/**
		 * Returns the EnumType's metadata value.
		 */
		public int getMetadata()
		{
			return this.meta;
		}

		public EnumParticleTypes getParticle()
		{
			return this.particle;
		}

		/**
		 * Returns an EnumType for the BlockState from a metadata value.
		 */
		public static BlockParticleEmitterSimple.EnumType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META.length)
			{
				meta = 0;
			}

			return META[meta];
		}

		static
		{
			for (BlockParticleEmitterSimple.EnumType type : values())
			{
				META[type.getMetadata()] = type;
			}
		}

		@Override
		public String getName() {
			return this.particle.getParticleName().toLowerCase();
		}


	}

	@Override
	public String getSpecialName(ItemStack stack) 
	{
		
		return this.getStateFromMeta(stack.getMetadata()).getValue(PARTICLE).getName();
	
	}
	
}
