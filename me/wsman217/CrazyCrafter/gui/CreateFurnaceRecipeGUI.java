package me.wsman217.CrazyCrafter.gui;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.wsman217.CrazyCrafter.CrazyCrafter;
import me.wsman217.CrazyCrafter.utils.FileManager.Files;
import me.wsman217.CrazyCrafter.utils.Tools;

public class CreateFurnaceRecipeGUI implements Listener {

	private CrazyCrafter plugin = CrazyCrafter.getInstance();
	private String title = ChatColor.YELLOW + "Add new recipe (" + ChatColor.GOLD + "Furnace" + ChatColor.YELLOW + ")";
	private String miscTitle = ChatColor.YELLOW + "Finish the new furnace recipe.";

	private ArrayList<ItemStack> buttons = new ArrayList<ItemStack>();

	private ItemStack cookTime = Tools.createItem(ChatColor.DARK_RED + "Cook Time: 60", new ArrayList<String>(Arrays
			.asList(ChatColor.GOLD + "Left Click to Increase Time.", ChatColor.GRAY + "Right Click to Decrease Time.")),
			Material.FURNACE);
	private ItemStack exp = Tools.createItem(ChatColor.YELLOW + "EXP Gain: 0.0", new ArrayList<String>(Arrays
			.asList(ChatColor.GOLD + "Left Click to Increase EXP.", ChatColor.GRAY + "Right Click to Decrease EXP.")),
			Material.EXPERIENCE_BOTTLE);
	private ItemStack placeholder = Tools.createItem(" ", null, Material.GRAY_STAINED_GLASS_PANE);
	private ItemStack continueButton = Tools.createItem(ChatColor.GREEN + "Continue", null, Material.GREEN_WOOL);
	private ItemStack cancelButton = Tools.createItem(ChatColor.RED + "Cancel", null, Material.RED_WOOL);

	private ItemStack output;
	private ItemStack input;
	private ItemStack icon;
	private int cook;
	private double xp;

	public void opneGUI(Player p) {
		Inventory gui = Bukkit.createInventory(null, InventoryType.FURNACE, title);
		p.openInventory(gui);
	}

	private void saveRecipe() {

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
		recipeConfig.set(path + "Recipe.0", input);

		recipeConfig.set(path + "CookTime", cook);
		recipeConfig.set(path + "Exp", xp);
		recipeConfig.set(path + "Icon", icon);

		plugin.getFileManager().saveFile(Files.RECIPES);
	}

	private void setMiscGUI(Player p) {
		Inventory gui = Bukkit.createInventory(null, 9, miscTitle);

		gui.setItem(0, continueButton);
		gui.setItem(1, placeholder);
		gui.setItem(2, cookTime);
		gui.setItem(3, placeholder);

		gui.setItem(5, placeholder);
		gui.setItem(6, exp);
		gui.setItem(7, placeholder);
		gui.setItem(8, cancelButton);

		p.openInventory(gui);
	}

	@SuppressWarnings("deprecation")
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
			this.output = output;
			this.input = input;

			e.setCursor(null);

			Bukkit.getScheduler().runTask(plugin, () -> {
				p.closeInventory();
				setMiscGUI(p);
			});
			return;
		}
	}

	@EventHandler
	public void onClickEventMisc(InventoryClickEvent e) {
		Inventory inv = e.getClickedInventory();
		Player p = (Player) e.getWhoClicked();
		String title = e.getView().getTitle();
		ItemStack clicked = e.getCurrentItem();
		ClickType click = e.getClick();

		buttons.clear();
		buttons.add(cookTime);
		buttons.add(exp);
		buttons.add(placeholder);
		buttons.add(continueButton);
		buttons.add(cancelButton);

		if (!title.equalsIgnoreCase(this.miscTitle))
			return;

		if (clicked == null)
			return;

		if (clicked.getType() == Material.AIR)
			return;

		for (ItemStack button : buttons) {
			if (!clicked.isSimilar(button)
					&& !clicked.getItemMeta().getDisplayName().startsWith(ChatColor.DARK_RED + "Cook Time:")
					&& !clicked.getItemMeta().getDisplayName().startsWith(ChatColor.YELLOW + "EXP Gain:"))
				continue;

			e.setCancelled(true);

			if (clicked.getItemMeta().getDisplayName().startsWith(ChatColor.YELLOW + "EXP Gain:")) {
				String name = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());
				Double xp = Double.parseDouble(name.substring(name.indexOf(":") + 2));

				if (click.isLeftClick() && !click.isShiftClick())
					xp += .01;
				if (click.isRightClick() && !click.isShiftClick())
					xp -= .01;
				if (click.isLeftClick() && click.isShiftClick())
					xp += .1;
				if (click.isRightClick() && click.isShiftClick())
					xp -= .1;

				if (xp < 0)
					xp = 0.0;

				if (xp > 10)
					xp = 10.0;

				ItemStack exp = Tools.createItem(ChatColor.YELLOW + "EXP Gain: " + xp,
						new ArrayList<String>(Arrays.asList(ChatColor.GOLD + "Left Click to Increase EXP.",
								ChatColor.GRAY + "Right Click to Decrease EXP.")),
						Material.EXPERIENCE_BOTTLE);

				inv.setItem(6, exp);
				p.updateInventory();
			}

			if (clicked.getItemMeta().getDisplayName().startsWith(ChatColor.DARK_RED + "Cook Time:")) {
				String name = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());
				int cookTime = Integer.parseInt(name.substring(name.indexOf(":") + 2));

				if (click.isLeftClick() && !click.isShiftClick())
					cookTime += 1;
				if (click.isRightClick() && !click.isShiftClick())
					cookTime -= 1;
				if (click.isLeftClick() && click.isShiftClick())
					cookTime += 10;
				if (click.isRightClick() && click.isShiftClick())
					cookTime -= 10;

				if (cookTime < 1)
					cookTime = 1;

				ItemStack cookTimeItem = Tools.createItem(ChatColor.DARK_RED + "Cook Time: " + cookTime,
						new ArrayList<String>(Arrays.asList(ChatColor.GOLD + "Left Click to Increase Time.",
								ChatColor.GRAY + "Right Click to Decrease Time.")),
						Material.FURNACE);

				inv.setItem(2, cookTimeItem);
				p.updateInventory();
			}

			if (clicked.isSimilar(cancelButton)) {
				Bukkit.getScheduler().runTask(plugin, () -> {
					p.closeInventory();
				});
			}

			if (clicked.isSimilar(continueButton)) {
				if (inv.getItem(4) == null || inv.getItem(4).getType() == Material.AIR)
					return;

				String name = ChatColor.stripColor(inv.getItem(6).getItemMeta().getDisplayName());
				Double xp = Double.parseDouble(name.substring(name.indexOf(":") + 2));
				this.xp = xp;

				String name2 = ChatColor.stripColor(inv.getItem(2).getItemMeta().getDisplayName());
				int cookTime = Integer.parseInt(name2.substring(name2.indexOf(":") + 2));
				this.cook = cookTime;

				icon = inv.getItem(4);

				saveRecipe();
				Bukkit.getScheduler().runTask(plugin, () -> {
					p.closeInventory();
				});
			}
		}
	}
}
