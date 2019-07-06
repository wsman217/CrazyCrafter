package me.wsman217.CrazyCrafter.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.wsman217.CrazyCrafter.utils.Tools;

public class MainGUI implements Listener {

	private int page = 0;

	public void createGUI(Player p) {
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.LIGHT_PURPLE + "Edit or Create New Recipes");
		
		gui = makeBottomRow(gui);
		gui = loadRecipes(gui);

		p.openInventory(gui);
	}

	private Inventory makeBottomRow(Inventory gui) {
		if (page != 1)
			gui.setItem(45, createItem(ChatColor.BLUE + "Previous Page", null, Tools.getSkull(
					"http://textures.minecraft.net/texture/dcec807dcc1436334fd4dc9ab349342f6c52c9e7b2bf346712db72a0d6d7a4")));
		else
			gui.setItem(45, createItem(" ", null, Material.GRAY_STAINED_GLASS_PANE));
		gui.setItem(46, createItem(" ", null, Material.GRAY_STAINED_GLASS_PANE));
		gui.setItem(47, createItem(" ", null, Material.GRAY_STAINED_GLASS_PANE));
		gui.setItem(48, createItem(" ", null, Material.GRAY_STAINED_GLASS_PANE));
		gui.setItem(49, createItem(ChatColor.DARK_RED + "Cancel", null, Material.BARRIER));
		gui.setItem(50, createItem(" ", null, Material.GRAY_STAINED_GLASS_PANE));
		gui.setItem(51, createItem(" ", null, Material.GRAY_STAINED_GLASS_PANE));
		gui.setItem(52, createItem(" ", null, Material.GRAY_STAINED_GLASS_PANE));
		if (countPages() != page)
			gui.setItem(53, createItem(ChatColor.BLUE + "Next Page", null, Tools.getSkull(
					"http://textures.minecraft.net/texture/e01c7b5726178974b3b3a01b42a590e54366026fd43808f2a787648843a7f5a")));
		else
			gui.setItem(53, createItem(" ", null, Material.GRAY_STAINED_GLASS_PANE));
		return gui;
	}

	private ItemStack createItem(String name, ArrayList<String> lore, ItemStack item) {
		ItemMeta im = item.getItemMeta();

		im.setDisplayName(name);
		im.setLore(lore);

		item.setItemMeta(im);

		return item;
	}

	private ItemStack createItem(String name, ArrayList<String> lore, Material type) {
		ItemStack item = new ItemStack(type);

		ItemMeta im = item.getItemMeta();

		im.setDisplayName(name);
		im.setLore(lore);

		item.setItemMeta(im);

		return item;
	}

	private Inventory loadRecipes(Inventory gui) {
		return gui;
	}

	private int countPages() {
		return 2;
	}
}
