package me.wsman217.CrazyCrafter.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
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
	private FileConfiguration furnaceConfig = CrazyCrafter.getInstance().getFileManager().getFile(Files.FURNACE);

	public CreateFurnaceRecipeGUI(Player p) {
		p.openInventory(createGUI());
	}

	public CreateFurnaceRecipeGUI() {
	}

	public Inventory createGUI() {
		Inventory gui = Bukkit.createInventory(null, InventoryType.FURNACE, "PUT TITLE FROM CONFIG BOI");
		gui.setItem(2, new ItemStack(Material.EMERALD));
		return gui;
	}

	// THIS DOESNT FREAKING WORK
	@EventHandler
	public void onClickEvent(InventoryClickEvent e) {

		Inventory inv = e.getInventory();
		Player p = (Player) e.getWhoClicked();
		ItemStack cursor = e.getCursor();
		int clicked = e.getRawSlot();

		if (!inv.getName().equalsIgnoreCase("PUT TITLE FROM CONFIG BOI"))
			return;
		
		if (cursor == null)
			return;

		if (clicked == 2) {
			inv.setItem(2, cursor);
			p.updateInventory();
			return;
		}
	}
}
