package tschipp.extraambiance.helper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumParticleTypes;

public class ParticleHelper
{

	public static List<String> PARTICLE_NAMES = new ArrayList<String>();

	static
	{
		for (EnumParticleTypes particle : EnumParticleTypes.values())
		{
			PARTICLE_NAMES.add(particle.getParticleName());
		}
		PARTICLE_NAMES.add("customTexture");
	}

	public static String getNext(String name)
	{
		int index = PARTICLE_NAMES.indexOf(name);
		if (index == -1)
			return "";
		if (index == PARTICLE_NAMES.size() - 1)
			index = 0;
		else
			index++;
		return PARTICLE_NAMES.get(index);
	}

	public static String getPrevious(String name)
	{
		int index = PARTICLE_NAMES.indexOf(name);
		if (index == -1)
			return "";
		if (index == 0)
			index = PARTICLE_NAMES.size() - 1;
		else
			index--;
		return PARTICLE_NAMES.get(index);
	}

}
