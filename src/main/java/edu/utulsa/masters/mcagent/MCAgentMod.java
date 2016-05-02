package edu.utulsa.masters.mcagent;
import edu.utulsa.masters.mcagent.actuator.PlayerController;
import edu.utulsa.masters.mcagent.actuator.inventory.PlayerInventory;
import edu.utulsa.masters.mcagent.overrides.OverrideMouseHelper;
import edu.utulsa.masters.shop2.AgentPlanner;
import edu.utulsa.masters.shop2.Operator;
import edu.utulsa.masters.shop2.Task;
import edu.utulsa.masters.shop2.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;


/**
 * Created by Chad on 5/24/2015.
 *
 * Top-level class handles all agent behavior.
 */


@SideOnly(Side.CLIENT)
@Mod(modid = MCAgentMod.MODID, version = MCAgentMod.VERSION)
public class MCAgentMod {
    public static final String MODID = "Minecraft Agent";
    public static final String VERSION = "0.1";
    private Debugger debug = new Debugger(this);
    private MCAgent activeAgent = null;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        PlayerController.setKeyBindings();
        PlayerInventory.loadRecipes();
        Minecraft.getMinecraft().mouseHelper = new OverrideMouseHelper();
        AgentPlanner.initialize();

        AgentPlanner planner = new AgentPlanner();
        // create a workbench
        Task goal = new Task("get-item", new Variable.Item(Item.getItemById(54)), new Variable.Integer(1));
        LinkedList<Operator> plan = planner.plan(goal);

        if(plan != null && !plan.isEmpty()) {
            for (Operator o : plan) {
                System.out.println(o);
            }
        }
        else {
            System.out.println("Darn");
        }
        MCAgentMod mc = new MCAgentMod();
        FMLCommonHandler.instance().bus().register(mc);
        MinecraftForge.EVENT_BUS.register(mc);
    }

    @SubscribeEvent
    public void onPlayerUpdate(TickEvent.PlayerTickEvent e) {
        Minecraft.getMinecraft().gameSettings.pauseOnLostFocus = false;
        Minecraft.getMinecraft().inGameHasFocus = false;
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        //System.out.println(player);
        if(player == null) {
            return;
        }
        MCAgent agent = MCAgent.getAgent(player);
        if(!agent.isAlive()) {
            System.out.println(agent.id);
            agent.start();
        }
        else {
            System.out.println("This sucks!");
        }
        activeAgent = agent;
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e) {
        if(activeAgent == null) return;

        activeAgent.renderEvent();
    }

//    @SubscribeEvent
//    public void onWorldLoad(WorldEvent.Load e) {
//
//    }

    @Override
    public String toString() {
        return "MCAgentMod";
    }
}
