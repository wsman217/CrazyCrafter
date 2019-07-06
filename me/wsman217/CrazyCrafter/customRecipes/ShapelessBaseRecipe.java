package me.wsman217.CrazyCrafter.customRecipes;

import java.util.ArrayList;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class ShapelessBaseRecipe {

	private static ArrayList<NamespacedKey> recipes = new ArrayList<NamespacedKey>();
	private ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
	private ItemStack output;
	private NamespacedKey key;
	
	public ShapelessBaseRecipe(ArrayList<ItemStack> inputs, ItemStack output, NamespacedKey key) {
		this.inputs = inputs;
		this.output = output;
		this.key = key;
		recipes.add(key);
	}
	
	public ArrayList<ItemStack> getInputs() {
		return inputs;
	}
	
	public ItemStack getOutput() {
		return output;
	}
	
	public NamespacedKey getKey() {
		return key;
	}
	
	public ArrayList<NamespacedKey> getRecipes() {
		return recipes;
	}
}
