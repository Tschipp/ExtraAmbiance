package tschipp.extraambiance.handler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingHandler {
	
	public static void init()
	{
		
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.simpleLight, 4), new Object[] {"dustGlowstone", ConfigHandler.getBondingIngredient(), Items.COAL}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.redstoneLight, 4), new Object[] {"dustGlowstone", "dustRedstone", ConfigHandler.getBondingIngredient(), Items.COAL}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.redstoneLightInverted, 1), new Object[] {Blocks.REDSTONE_TORCH, ItemHandler.redstoneLight}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.dimmableLight, 1), new Object[] {Items.COMPARATOR, ItemHandler.simpleLight}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.dimmableLightRedstone, 1), new Object[] {Items.COMPARATOR, ItemHandler.redstoneLight}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.dimmableLightRedstoneInverted, 1), new Object[] {Items.COMPARATOR, ItemHandler.redstoneLightInverted}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.dimmableLightRedstoneInverted, 1), new Object[] {Blocks.REDSTONE_TORCH, ItemHandler.dimmableLightRedstone}));
//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandler.lighteditor), new Object[] {" IL", " II", "I  ", 'I', "ingotIron", 'L', ItemHandler.simpleLight}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.particleEmitterAdvanced, 1), new Object[]{"dyeRed", "dyeGreen", "dyeBlue", ConfigHandler.getBondingIngredient(), "gemDiamond", new ItemStack(ItemHandler.particleEmitterSimple, 1, OreDictionary.WILDCARD_VALUE), ItemHandler.dimmableLight}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.comparatorLight, 1), new Object[]{Items.COMPARATOR, "dustRedstone", ItemHandler.dimmableLightRedstone}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.comparatorLightInverted, 1), new Object[]{Items.COMPARATOR, "dustRedstone", ItemHandler.dimmableLightRedstoneInverted}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.comparatorLightInverted, 1), new Object[]{Items.COMPARATOR, "dustRedstone", ItemHandler.dimmableLightRedstone, Blocks.REDSTONE_TORCH}));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.soundEmitterAdvanced, 1), new Object[]{Blocks.NOTEBLOCK, Blocks.HOPPER, ConfigHandler.getBondingIngredient(), "gemDiamond", new ItemStack(ItemHandler.soundEmitterSimple, 1, OreDictionary.WILDCARD_VALUE), ItemHandler.dimmableLight}));
//
//		makeParticleEmitterRecipes();
//		makeSoundEmitterRecipes();
	}
	
	
	private static void makeParticleEmitterRecipes()
	{
//		Object[] ingredients = new Object[] {Items.BLAZE_POWDER, new ItemStack(Items.COAL, 1, 1), Blocks.WOOL, Items.BOOK, "gemQuartz", Blocks.END_ROD, "gunpowder", Items.FIREWORKS, Blocks.RED_FLOWER, Items.LAVA_BUCKET, Blocks.NOTEBLOCK, "obsidian", "dustRedstone", "blockEmerald", "gemEmerald", Items.DRAGON_BREATH};
//		for(int i = 0; i < 16; i++)
//		{
//			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.particleEmitterSimple, 4, i), new Object[]{"dyeRed", "dyeGreen", "dyeBlue", ConfigHandler.getBondingIngredient(), ingredients[i]}));
//			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.particleEmitterSimpleLight, 1, i), new Object[]{new ItemStack(ItemHandler.particleEmitterSimple, 1, i), ItemHandler.simpleLight}));
//		}
	}
	
	
	private static void makeSoundEmitterRecipes()
	{
//		Object[] ingredients = new Object[] {Blocks.FURNACE, Items.LAVA_BUCKET, Items.ARROW, "gunpowder", Items.DRAGON_BREATH, Items.ENDER_PEARL, Items.ENDER_EYE, Blocks.TNT, Items.GHAST_TEAR, new ItemStack(Blocks.WOOL, 1 ,12), Items.WATER_BUCKET, Items.EXPERIENCE_BOTTLE, Items.SHULKER_SHELL, Items.BONE, new ItemStack(Items.SKULL, 1, 1), new ItemStack(Blocks.WOOL, 1 ,11)};
//		for(int i = 0; i < 16; i++)
//		{
//			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.soundEmitterSimple, 4, i), new Object[]{Blocks.NOTEBLOCK, Blocks.HOPPER, ConfigHandler.getBondingIngredient(), ingredients[i]}));
//			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.soundEmitterSimpleLight, 1, i), new Object[]{new ItemStack(ItemHandler.soundEmitterSimple, 1, i), ItemHandler.simpleLight}));
//		}
	}
	
	

}
