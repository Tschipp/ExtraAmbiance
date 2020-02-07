package tschipp.extraambiance.crafting;

import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.OreIngredient;
import tschipp.extraambiance.handler.ConfigHandler;

public class BondingAgentIngredientFactory implements IIngredientFactory
{

	@Override
	public Ingredient parse(JsonContext context, JsonObject json)
	{
		Object bondingIngredient = ConfigHandler.getBondingIngredient();
		if(bondingIngredient instanceof String && bondingIngredient.equals("slimeball"))
			return new OreIngredient((String) bondingIngredient);
		else
			return Ingredient.fromItem((Item) bondingIngredient);
	}

}
