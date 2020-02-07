package tschipp.extraambiance;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import tschipp.extraambiance.handler.ItemHandler;
import tschipp.tschipplib.network.CTileEntitySyncPacket;
import tschipp.tschipplib.network.CTileEntitySyncPacketHandler;
import tschipp.tschipplib.network.STileEntitySyncPacket;
import tschipp.tschipplib.network.STileEntitySyncPacketHandler;

@Mod(modid = EA.MODID, name = EA.NAME, version = EA.VERSION, acceptedMinecraftVersions = EA.ACCEPTED_VERSIONS, dependencies = "required-after:tschipplib@[1.1,);after:albedo")

public class EA {
	
	public final static String MODID = "extraambiance";
	public final static String NAME = "Extra Ambiance";
	public final static String VERSION = "1.1";
	public final static String ACCEPTED_VERSIONS = "[1.12.2,1.13)";


	@Instance(value = MODID)
	public static EA instance;

	public static final String COMMON_PROXY = "tschipp.extraambiance.CommonProxy";
	public static final String CLIENT_PROXY = "tschipp.extraambiance.ClientProxy";

	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
	public static CommonProxy proxy;
	
	public static SimpleNetworkWrapper network;


	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);

		network = NetworkRegistry.INSTANCE.newSimpleChannel("ExtraAmbiance");

	}
	
	
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}


	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);

	}


	public static CreativeTabs extraambiance = new CreativeTabs("extraambiance"){
		@Override
		public ItemStack getTabIconItem(){
			return new ItemStack(ItemHandler.simpleLight);
		}
	};
	
	
}
