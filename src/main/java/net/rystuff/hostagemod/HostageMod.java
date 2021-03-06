package net.rystuff.hostagemod;

import net.ilexiconn.llibrary.common.update.UpdateHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.rystuff.hostagemod.common.block.BlockChair;
import net.rystuff.hostagemod.common.event.EventChairRope;
import net.rystuff.hostagemod.common.event.EventGag;
import net.rystuff.hostagemod.common.event.EventHandcuff;
import net.rystuff.hostagemod.common.event.EventHandler;
import net.rystuff.hostagemod.common.item.ItemGag;
import net.rystuff.hostagemod.common.item.ItemHandcuff;
import net.rystuff.hostagemod.common.item.ItemKey;
import net.rystuff.hostagemod.common.item.ItemRope;
import net.rystuff.hostagemod.common.proxy.ServerProxy;

import java.io.IOException;

@Mod(modid = "hostagemod", name = "Hostage Mod", version = "0.1.3", dependencies = "required-after:llibrary@[0.3.0-1.8,)")
public class HostageMod
{
    @SidedProxy(clientSide = "net.rystuff.hostagemod.client.proxy.ClientProxy", serverSide = "net.rystuff.hostagemod.common.proxy.ServerProxy")
    public static ServerProxy proxy;
    @Mod.Instance("hostagemod")
    public static HostageMod instance;

    public static Item gag = new ItemGag();
    public static Item handcuffs = new ItemHandcuff();
    public static Item rope = new ItemRope();
    public static Item key = new ItemKey();

    public static Block chair = new BlockChair();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new EventGag());
        MinecraftForge.EVENT_BUS.register(new EventHandcuff());
        MinecraftForge.EVENT_BUS.register(new EventChairRope());
        GameRegistry.registerItem(gag, "gag");
        GameRegistry.registerItem(handcuffs, "handcuffs");
        GameRegistry.registerItem(rope, "rope");
        GameRegistry.registerItem(key, "key");
        //GameRegistry.registerItem(lock, "lock");
        GameRegistry.addShapelessRecipe(new ItemStack(key, 1), new ItemStack(key));
        GameRegistry.addShapelessRecipe(new ItemStack(handcuffs, 1), new ItemStack(handcuffs));
        GameRegistry.addShapelessRecipe(new ItemStack(handcuffs, 1), new ItemStack(key), new ItemStack(handcuffs));
        GameRegistry.registerBlock(chair, "chair");
        int entityId = EntityRegistry.findGlobalUniqueEntityId();

        try
        {
            UpdateHelper.registerUpdateChecker(instance, "https://raw.githubusercontent.com/Rydog101/HostageMod/master/update.json");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
