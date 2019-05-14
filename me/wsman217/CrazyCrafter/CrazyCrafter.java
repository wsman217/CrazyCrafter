package me.wsman217.CrazyCrafter;

import org.bukkit.plugin.java.JavaPlugin;

public class CrazyCrafter extends JavaPlugin {

	private FileManager fileManager = FileManager.getInstance();
	
	@Override 
	public void onEnable() {
		System.out.println("-----------------------");
		System.out.println("CrazyCrafter is ENABLED");
		System.out.println("   Author: wsman217    ");
		System.out.println("-----------------------");
		
		
		fileManager.logInfo(true)
		.registerDefaultGenerateFiles("FurnaceRecipes.yml", "/Recipes")
		.registerDefaultGenerateFiles("ShapedRecipes.yml", "/Recipes")
		.registerDefaultGenerateFiles("ShapelessRecipes.yml", "/Recipes")	
		//Register all files inside the custom folders.
		.registerCustomFilesFolder("/Recipes")
		.setup(this);
		
		new Metrics(this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	
}

