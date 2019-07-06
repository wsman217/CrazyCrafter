package me.wsman217.CrazyCrafter.customRecipes;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

import me.wsman217.CrazyCrafter.CrazyCrafter;
import me.wsman217.CrazyCrafter.utils.FileManager;
import me.wsman217.CrazyCrafter.utils.Tools;

public class ShapedCrafting {

	private final String path = "ShapedRecipes.";
	
	private CrazyCrafter plugin = CrazyCrafter.getInstance();
	private FileConfiguration shapedConfig = plugin.getFileManager().getFile(FileManager.Files.SHAPED);
	private Logger logger = plugin.getLogger();
	
	private int amountOfRecipes;
	
	private static ArrayList<ShapedBaseRecipe> recipes = new ArrayList<ShapedBaseRecipe>();
	
	private Tools tools = CrazyCrafter.getTools();
	
	public ShapedCrafting() {
	}
	
	public void init() {
		amountOfRecipes = shapedConfig.getInt(path + "TotalRecipes");
		
		for (int i = 1; i <= amountOfRecipes; i++) {
			logger.log(Level.INFO, "Loading Shaped Recipes");
			
			
		}
	}
}
