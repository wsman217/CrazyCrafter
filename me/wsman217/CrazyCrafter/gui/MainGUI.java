package me.wsman217.CrazyCrafter.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.wsman217.CrazyCrafter.CrazyCrafter;
import me.wsman217.CrazyCrafter.utils.Tools;

public class MainGUI implements Listener {

	private int page = 0;

	private ArrayList<ItemStack> bottomRow = new ArrayList<ItemStack>();
	private ArrayList<ItemStack> recipeIcons = new ArrayList<ItemStack>();

	private CrazyCrafter plugin = CrazyCrafter.getInstance();

	private ItemStack previous = createItem(ChatColor.BLUE + "Previous Page", null, Tools.getSkull(
			"http://textures.minecraft.net/texture/dcec807dcc1436334fd4dc9ab349342f6c52c9e7b2bf346712db72a0d6d7a4"));
	private ItemStack placeholder = createItem(" ", null, Material.GRAY_STAINED_GLASS_PANE);
	private ItemStack cancel = createItem(ChatColor.DARK_RED + "Cancel", null, Material.BARRIER);
	private ItemStack next = createItem(ChatColor.BLUE + "Next Page", null, Tools.getSkull(
			"http://textures.minecraft.net/texture/e01c7b5726178974b3b3a01b42a590e54366026fd43808f2a787648843a7f5a"));
	private ItemStack createShaped = createItem(ChatColor.YELLOW + "Create new shaped recipe.", null,
			Material.CRAFTING_TABLE);
	private ItemStack createShapeless = createItem(ChatColor.GOLD + "Create new shapeless recipe.", null,
			Material.OAK_PLANKS);
	private ItemStack createFurnace = createItem(ChatColor.GREEN + "Create new furnace recipe.", null,
			Material.FURNACE);

	private String title = ChatColor.LIGHT_PURPLE + "Edit or Create New Recipes";

	public MainGUI() {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		bottomRow.add(previous);
		bottomRow.add(placeholder);
		bottomRow.add(cancel);
		bottomRow.add(next);
		bottomRow.add(createShaped);
		bottomRow.add(createShapeless);
		bottomRow.add(createFurnace);
	}

	public void openGUI(Player p) {
		Inventory gui = Bukkit.createInventory(null, 54, title);

		gui = makeBottomRow(gui);
		gui = loadRecipes(gui);

		p.openInventory(gui);
	}

	private Inventory makeBottomRow(Inventory gui) {

		if (page != 1)
			gui.setItem(45, previous);
		else
			gui.setItem(45, placeholder);
		gui.setItem(46, createShaped);
		gui.setItem(47, createShapeless);
		gui.setItem(48, createFurnace);
		gui.setItem(49, cancel);
		gui.setItem(50, placeholder);
		gui.setItem(51, placeholder);
		gui.setItem(52, placeholder);
		if (countPages() != page)
			gui.setItem(53, next);
		else
			gui.setItem(53, placeholder);

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

	@EventHandler
	public void onClickEvent(InventoryClickEvent e) {

		Inventory inv = e.getInventory();
		ItemStack clickedItem = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		String title = e.getView().getTitle();

		if (!title.equalsIgnoreCase(this.title))
			return;

		if (inv == null)
			return;

		if (clickedItem == null)
			return;

		if (p == null)
			return;

		for (ItemStack bottomItems : bottomRow) {
			if (bottomItems.isSimilar(clickedItem)) {
				e.setCancelled(true);
				switch (ChatColor.stripColor(bottomItems.getItemMeta().getDisplayName())) {
				case " ":
					break;
				case "Previous Page":
					--page;
					inv.clear();
					inv = makeBottomRow(inv);
					inv = loadRecipes(inv);
					p.updateInventory();
					break;
				case "Next Page":
					++page;
					p.closeInventory();
					openGUI(p);
					break;
				case "Cancel":
					p.closeInventory();
					break;
				case "Create new shaped recipe.":
					// Open new shaped recipe here
					break;
				case "Create new shapeless recipe.":
					// Open new shapeless recipe here
					break;
				case "Create new furnace recipe.":
					new CreateFurnaceRecipeGUI().opneGUI(p);
					break;
				}
			}
		}
	}
}
