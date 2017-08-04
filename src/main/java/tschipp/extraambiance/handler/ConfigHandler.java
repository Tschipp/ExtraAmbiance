package tschipp.extraambiance.handler;

import java.io.File;

import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	
	public static Configuration config;
	
	public static String bondingIngredient;
	
	public static int lightParticleViewDistance;
	
	
	public static int maxSpawnCount;
	public static int maxParticleSize;
	public static int maxParticleMaxAge;

	
	public static void preInit(FMLPreInitializationEvent event)
	{
		config = new Configuration(new File(event.getModConfigurationDirectory(), "ExtraAmbiance.cfg"));
		syncConfig();
	}
	
	
	
	public static void syncConfig() {
		
		try {
			config.load();
			
			bondingIngredient = config.getString("bondingIngredient", "crafting", "minecraft:slimeball", "The Ingredient that holds stuff together. Used in most recipes. Change to your liking");		
			
			lightParticleViewDistance = config.getInt("lightParticleViewDistance", "lights", 16, 0, 128, "The maximum distance from where the Lights will render particles when a Light is held");
			
			
			maxSpawnCount = config.getInt("maxSpawnCount", "particles", 1000, 0, 10000, "The maximum Particle Spawn Count");
			maxParticleSize = config.getInt("maxParticleSize", "particles", 50, 0, 100, "The maximum Particle Spawn Size");
			maxParticleMaxAge = config.getInt("maxParticleMaxAge", "particles", 1000, 0, 10000, "The maximum Particle Max Age");

		} catch (Exception e) {

		} finally {

	    	if (config.hasChanged()) config.save();
	    }
		
		
	}
	
	
	public static Object getBondingIngredient()
	{
		if(bondingIngredient.equals("minecraft:slimeball") || bondingIngredient.equals("slimeball"))
			return "slimeball";
		
		Item item = Item.getByNameOrId(bondingIngredient);
		if(item == null) return "slimeball";
		
		return item;
	}
	
	

}
