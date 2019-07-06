package me.wsman217.CrazyCrafter.customRecipes;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import me.wsman217.CrazyCrafter.CrazyCrafter;
import me.wsman217.CrazyCrafter.utils.FileManager;
import me.wsman217.CrazyCrafter.utils.Tools;

public class FurnaceRecipes {

	private final String path = "FurnaceRecipes.";
	private final CrazyCrafter plugin = CrazyCrafter.getInstance();
	private final FileConfiguration furnaceConfig = plugin.getFileManager().getFile(FileManager.Files.FURNACE);

	private int amountOfRecipes;

	private Logger logger = plugin.getLogger();
	

	public static ArrayList<FurnaceBaseRecipe> recipes = new ArrayList<FurnaceBaseRecipe>();

	private Tools tools = CrazyCrafter.getTools();

	public FurnaceRecipes() {
	}

	public void init() {
		amountOfRecipes = furnaceConfig.getInt(path + "TotalRecipes");

		for (int i = 1; i <= amountOfRecipes; i++) {

			logger.log(Level.INFO, "Loading furnace recipe #" + i);

			if (furnaceConfig.getString(path + i + ".Input.Material") == null) {
				logger.log(Level.WARNING, "Path to input material is null.");
				continue;
			}

			if (furnaceConfig.getString(path + i + ".Output.Material") == null) {
				logger.log(Level.WARNING, "Path to output material is null.");
				continue;
			}

			if (Material.matchMaterial(furnaceConfig.getString(path + i + ".Input.Material")) == null) {
				logger.log(Level.WARNING, 
						"Failed to load input material. Input material is either null or malformed. Continuing to the next recipe.");
				continue;
			}
			ItemStack input = new ItemStack(
					Material.matchMaterial(furnaceConfig.getString(path + i + ".Input.Material")));

			if (Material.matchMaterial(furnaceConfig.getString(path + i + ".Output.Material")) == null) {
				logger.log(Level.WARNING, 
						"Failed to load output material. Output material is either null or malformed. Continuing to the next recipe.");
				continue;
			}
			ItemStack output = new ItemStack(
					Material.matchMaterial(furnaceConfig.getString(path + i + ".Output.Material")));

			String recipePath = path + i;

			input = tools.loadMeta(input, recipePath + ".Input", "furnace", furnaceConfig);
			output = tools.loadMeta(output, recipePath + ".Output", "furnace", furnaceConfig);

			output.setAmount(furnaceConfig.getInt(recipePath + ".Output.Amount") <= 0 ? 1
					: furnaceConfig.getInt(recipePath + ".Output.Amount"));

			NamespacedKey key = new NamespacedKey(plugin, "furnace" + Integer.toString(i));

			recipes.add(new FurnaceBaseRecipe(input, output, key));

			if (plugin.getServer()
					.addRecipe(new FurnaceRecipe(key, output, input.getType(),
							(float) furnaceConfig.getDouble(recipePath + ".Experience"),
							furnaceConfig.getInt(recipePath + ".CookTime"))))
				logger.log(Level.INFO, "Furnace recipe #" + i + " loaded successfully.");
			else
				logger.log(Level.INFO, "Something went wrong loading furnace recipe #" + i);
		}
	}
}
