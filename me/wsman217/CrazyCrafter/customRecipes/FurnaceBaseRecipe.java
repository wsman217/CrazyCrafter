package me.wsman217.CrazyCrafter.customRecipes;

import java.util.ArrayList;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class FurnaceBaseRecipe {

	private static ArrayList<NamespacedKey> recipes = new ArrayList<NamespacedKey>();
	private ItemStack input, output;
	private NamespacedKey key;
	
	public FurnaceBaseRecipe(ItemStack input, ItemStack output, NamespacedKey key) {
		this.input = input;
		this.output = output;
		this.key = key;
		recipes.add(key);
	}
	
	public ItemStack getInput() {
		return input;
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
