package me.wsman217.CrazyCrafter;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.wsman217.CrazyCrafter.commands.CrazyCrafterEdit;
import me.wsman217.CrazyCrafter.commands.DebugCommand;
import me.wsman217.CrazyCrafter.customRecipes.FurnaceRecipes;
import me.wsman217.CrazyCrafter.gui.CreateFurnaceRecipeGUI;
import me.wsman217.CrazyCrafter.gui.ViewRecipesGUI;
import me.wsman217.CrazyCrafter.utils.FileManager;
import me.wsman217.CrazyCrafter.utils.Tools;

public class CrazyCrafter extends JavaPlugin {

	private FileManager fileManager;
		
	private static CrazyCrafter instance;
	private static Tools tools;

	@Override
	public void onEnable() {
		
		
		instance = this;
		
		tools = new Tools();
		
		System.out.println("-----------------------");
		System.out.println("CrazyCrafter is ENABLED");
		System.out.println("   Author: wsman217    ");
		System.out.println("-----------------------");

		fileManager = FileManager.getInstance().logInfo(true).setup(this);

		new Metrics(this);
		
		//new FurnaceRecipes().init();;
		
		getCommand("ccdebug").setExecutor(new DebugCommand());
		getCommand("ccedit").setExecutor(new CrazyCrafterEdit());
		
		registerEvents();
	}

	@Override
	public void onDisable() {

	}
	
	private void registerEvents() {
		PluginManager pman = getServer().getPluginManager();
		pman.registerEvents(new CreateFurnaceRecipeGUI(), this);
	}

	public static CrazyCrafter getInstance() {
		if (instance == null)
			throw new NullPointerException("CrazyCrafter was not initialized");
		return instance;
	}

	public FileManager getFileManager() {
		if (fileManager == null)
			throw new NullPointerException("File manger for plugin CrazyCrafter was null");
		return fileManager;
	}
	
	public void registerRecipes() {
		new FurnaceRecipes().init();
	}
	
	public static Tools getTools() {
		if (tools == null)
			throw new NullPointerException("The tools class for plugin CrazyCrafter was null");
		return tools;
	}
}
