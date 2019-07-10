package me.wsman217.CrazyCrafter.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.wsman217.CrazyCrafter.CrazyCrafter;
import me.wsman217.CrazyCrafter.utils.FileManager.Files;

public class CreateFurnaceRecipeGUI implements Listener {

	private CrazyCrafter plugin = CrazyCrafter.getInstance();
	private String title = ChatColor.YELLOW + "Add new recipe (" + ChatColor.GOLD + "Furnace" + ChatColor.YELLOW + ")";
	private String miscTitle = ChatColor.YELLOW + "Set options for the new furnace recipe.";

	public void opneGUI(Player p) {
		Inventory gui = Bukkit.createInventory(null, InventoryType.FURNACE, title);
		p.openInventory(gui);
	}

	private void saveRecipe(ItemStack input, ItemStack output) {
		
		input.setAmount(1);
		output.setAmount(1);
		
		plugin.getFileManager().reloadFile(Files.RECIPES);
		FileConfiguration recipeConfig = plugin.getFileManager().getFile(Files.RECIPES);

		if (recipeConfig.getConfigurationSection("CrazyRecipes") == null)
			recipeConfig.createSection("CrazyRecipes");
		
		int index = recipeConfig.getConfigurationSection("CrazyRecipes").getKeys(false).size();
		
		String path = "CrazyRecipes." + index + ".";
		recipeConfig.set(path + "Type", "Furnace");
		recipeConfig.set(path + "Output", output);
		recipeConfig.set(path + "Recipe.1", input);
		
		//Set cook time and exp
		
		plugin.getFileManager().saveFile(Files.RECIPES);
	}
	
	private void setMiscGUI(Player p) {
		Inventory gui = Bukkit.createInventory(null, 9, miscTitle);
		p.openInventory(gui);
	}

	@EventHandler
	public void onClickEventMain(InventoryClickEvent e) {

		Inventory inv = e.getInventory();
		Player p = (Player) e.getWhoClicked();
		ItemStack output = e.getCursor();
		ItemStack input = null;
		int clicked = e.getRawSlot();
		String title = e.getView().getTitle();
		
		if (!title.equalsIgnoreCase(this.title))
			return;
		
		if (clicked == 1) {
			e.setCancelled(true);
			return;
		}

		if (clicked == 2) {
			e.setCancelled(true);
			if (output.getType() == Material.AIR)
				return;

			if (inv.getItem(0) == null)
				return;

			input = inv.getItem(0);
			Bukkit.getScheduler().runTask(plugin, () -> {
				p.closeInventory();
			});

			//Instead of this open a GUI for setting cook time and exp amount and gui to set icon
			setMiscGUI(p);
			saveRecipe(input, output);
			return;
		}
	}
	
	@EventHandler
	public void onClickEventMisc(InventoryClickEvent e) {
		Inventory inv = e.getClickedInventory();
		Player p = (Player) e.getWhoClicked();
		String title = e.getView().getTitle();
		
		if (!title.equalsIgnoreCase(this.miscTitle))
			return;
	}
}
