package me.wsman217.CrazyCrafter.listeners;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

import me.wsman217.CrazyCrafter.customRecipes.FurnaceBaseRecipe;
import me.wsman217.CrazyCrafter.customRecipes.FurnaceRecipes;

public class ListenerFurnaceEvent implements Listener {

	ArrayList<FurnaceBaseRecipe> recipes = FurnaceRecipes.recipes;
	
	@EventHandler
	public void onFurnaceEvent(FurnaceSmeltEvent e) {
		for (FurnaceBaseRecipe fbr : recipes) {
			if (fbr.getOutput().isSimilar(e.getResult())) {
				if (!fbr.getInput().isSimilar(e.getSource())) {
					e.setCancelled(true);
				}
			}
		}
	}
}
