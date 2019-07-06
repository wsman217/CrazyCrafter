package me.wsman217.CrazyCrafter.customRecipes;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class ShapedBaseRecipe {
	
	private ItemStack output;
	private ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
	
	public ShapedBaseRecipe(ItemStack output) {
		this.output = output;
	}
	
	public void addInput(ItemStack input) {
		this.inputs.add(input);
	}
	
	public ItemStack getOutput() {
		return output;
	}
	
	public ArrayList<ItemStack> getInputs() {
		return inputs;
	}
}
