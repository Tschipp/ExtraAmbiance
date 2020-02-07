package tschipp.extraambiance.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tschipp.extraambiance.api.LightData;

public class ItemBlockSoundEmitterAdvanced extends ItemBlockLight
{

	public ItemBlockSoundEmitterAdvanced(Block block)
	{
		super(block);
	}
	
	 @Override
 public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		 super.addInformation(stack, worldIn, tooltip, flagIn);
		 
		 
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
