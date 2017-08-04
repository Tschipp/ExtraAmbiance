package tschipp.extraambiance.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tschipp.extraambiance.api.LightData;

public class ItemBlockParticleEmitterAdvanced extends ItemBlockLight
{

	public ItemBlockParticleEmitterAdvanced(Block block)
	{
		super(block);
	}
	
	 @Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		 super.addInformation(stack, playerIn, tooltip, advanced);
		 
		 
		 LightData lightData = LightData.getLightDataFromStack(stack);
		 if(lightData != null)
		 {
			NBTTagCompound nbt = lightData.getNbt();
			String particle = nbt.getString("particleName");
			int count = nbt.getInteger("spawnCount");
			int spawnTimer = nbt.getInteger("spawnTimer");
			String particleTexture = nbt.getString("particleTexture");
			
			if(particle.equals("customTexture"))
				tooltip.add("Texture: " + particleTexture);
			else
				tooltip.add("Particle: " + particle);
			tooltip.add("Count: " + count);
			tooltip.add("Spawn Timer: " + spawnTimer);
		 }
		 
	}

}
