package tschipp.extraambiance.block;

import java.util.Random;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschipp.extraambiance.EA;
import tschipp.extraambiance.api.LightData;
import tschipp.tschipplib.block.IMetaBlockName;

public class BlockSoundEmitterSimple extends BlockLightBase implements IMetaBlockName
{

	private boolean light;
	public static final PropertyEnum<BlockSoundEmitterSimple.EnumType> SOUND = PropertyEnum.<BlockSoundEmitterSimple.EnumType> create("sound", BlockSoundEmitterSimple.EnumType.class);

	public BlockSoundEmitterSimple(boolean light)
	{
		super("sound_emitter" + (light ? "_light" : ""));
		this.setDefaultState(this.blockState.getBaseState().withProperty(SOUND, EnumType.BLOCK_FURNACE_FIRE_CRACKLE));
		this.light = light;
	}

	@Override
	public int getLightValue(IBlockState state)
	{
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
		return this.getDefaultState().withProperty(SOUND, EnumType.byMetadata(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumType) state.getValue(SOUND)).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { SOUND });
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(this, 1, this.getMetaFromState(state));
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		int meta = stack.getMetadata();

		worldIn.setBlockState(pos, getStateFromMeta(meta));
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}

	@Override
	public Particle getViewingParticle(World world, IBlockState state, BlockPos pos, Random rand)
	{
		return EA.proxy.getLightParticle(world, pos, 0.065f, 0.035f, 0.3725f, 1f);

	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (rand.nextFloat() < 0.2)
			worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), stateIn.getValue(SOUND).getSound(), SoundCategory.BLOCKS, (float) (1.0f + (rand.nextGaussian() * 0.2)), (float) (1.0f + (rand.nextGaussian() * 0.2)), true);
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}

	public static enum EnumType implements IStringSerializable
	{
		BLOCK_FURNACE_FIRE_CRACKLE(0, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE), BLOCK_LAVA_POP(1, SoundEvents.BLOCK_LAVA_POP), ENTITY_ARROW_HIT_PLAYER(2, SoundEvents.ENTITY_ARROW_HIT_PLAYER), ENTITY_CREEPER_PRIMED(3, SoundEvents.ENTITY_CREEPER_PRIMED), ENTITY_ENDERDRAGON_GROWL(4, SoundEvents.ENTITY_ENDERDRAGON_GROWL), ENTITY_ENDERMEN_DEATH(5, SoundEvents.ENTITY_ENDERMEN_DEATH), ENTITY_ENDERMEN_STARE(6, SoundEvents.ENTITY_ENDERMEN_STARE), ENTITY_GENERIC_EXPLODE(7, SoundEvents.ENTITY_GENERIC_EXPLODE), ENTITY_GHAST_SCREAM(8, SoundEvents.ENTITY_GHAST_SCREAM), ENTITY_LLAMA_SPIT(9, SoundEvents.ENTITY_LLAMA_SPIT), ENTITY_LIGHTNING_THUNDER(10, SoundEvents.ENTITY_LIGHTNING_THUNDER), ENTITY_PLAYER_LEVELUP(11, SoundEvents.ENTITY_PLAYER_LEVELUP), ENTITY_SHULKER_AMBIENT(12, SoundEvents.ENTITY_SHULKER_AMBIENT), ENTITY_SKELETON_AMBIENT(13, SoundEvents.ENTITY_SKELETON_AMBIENT), ENTITY_WITHER_AMBIENT(14, SoundEvents.ENTITY_WITHER_AMBIENT), EVOCATION_ILLAGER_PREPARE_WOLOLO(15, SoundEvents.EVOCATION_ILLAGER_PREPARE_WOLOLO);

		/** Array of the Block's BlockStates */
		private static final BlockSoundEmitterSimple.EnumType[] META = new BlockSoundEmitterSimple.EnumType[values().length];
		/** The BlockState's metadata. */
		private final int meta;
		private final SoundEvent sound;

		private EnumType(int meta, SoundEvent sound)
		{
			this.meta = meta;
			this.sound = sound;

		}

		/**
		 * Returns the EnumType's metadata value.
		 */
		public int getMetadata()
		{
			return this.meta;
		}

		public SoundEvent getSound()
		{
			return this.sound;
		}

		/**
		 * Returns an EnumType for the BlockState from a metadata value.
		 */
		public static BlockSoundEmitterSimple.EnumType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META.length)
			{
				meta = 0;
			}

			return META[meta];
		}

		static
		{
			for (BlockSoundEmitterSimple.EnumType type : values())
			{
				META[type.getMetadata()] = type;
			}
		}

		@Override
		public String getName()
		{
			return ForgeRegistries.SOUND_EVENTS.getKey(sound).toString().replace(".", "_").replace("minecraft:", "");
			
		}

	}

	@Override
	public String getSpecialName(ItemStack stack)
	{

		return this.getStateFromMeta(stack.getMetadata()).getValue(SOUND).getName();

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
					player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + I18n.translateToLocal("text.lighteditor.compatible") + TextFormatting.RESET + " Sound: " + I18n.translateToLocal("sound." + player.world.getBlockState(pos).getValue(SOUND).getName())), true);
				else
					player.sendStatusMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("text.lighteditor.incompatible") + TextFormatting.RESET + " Sound: " + I18n.translateToLocal("sound." + player.world.getBlockState(pos).getValue(SOUND).getName())), true);
			}
			else
			{
				player.sendStatusMessage(new TextComponentString("Sound: " + I18n.translateToLocal("sound." + player.world.getBlockState(pos).getValue(SOUND).getName())), true);
			}
		}
	}

}
