package me.wsman217.CrazyCrafter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.wsman217.CrazyCrafter.gui.ViewRecipesGUI;

public class DebugCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		new ViewRecipesGUI().openGUI((Player)sender);
		return true;
	}
}
