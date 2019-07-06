package me.wsman217.CrazyCrafter.customRecipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import me.wsman217.CrazyCrafter.CrazyCrafter;
import me.wsman217.CrazyCrafter.utils.Tools;

public class ShapelessCrafting {

	private final String path = "ShapelessRecipes.";
	private final CrazyCrafter plugin = CrazyCrafter.getInstance();
	private Tools tools = CrazyCrafter.getTools();
	
	public ShapelessCrafting() {
	}
	
	public void init() {
		ShapelessRecipe testR = new ShapelessRecipe(new NamespacedKey(plugin, "test"), new ItemStack(Material.GOLD_BLOCK));
		plugin.getServer().addRecipe(new ShapelessRecipe(null, null));
		ItemStack test = new ItemStack(Material.ACACIA_BOAT);
		test.setAmount(6);
		testR.addIngredient(0, test.getType());
		testR.getIngredientList();
	}
}
