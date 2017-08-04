package tschipp.extraambiance.item;

import java.util.List;

import tschipp.extraambiance.api.LightData;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBlockSoundEmitterAdvanced extends ItemBlockLight
{

	public ItemBlockSoundEmitterAdvanced(Block block)
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
			String sound = nbt.getString("soundName");
			float volume = nbt.getFloat("volume");
			float pitch = nbt.getFloat("pitch");
			
			
			tooltip.add("Sound: " + sound);
			tooltip.add("Volume: " + volume);
			tooltip.add("Pitch: " + pitch);

		 }
		 
	}
	
}
