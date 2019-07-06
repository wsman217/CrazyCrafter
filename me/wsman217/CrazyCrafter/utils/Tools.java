package me.wsman217.CrazyCrafter.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import me.wsman217.CrazyCrafter.CrazyCrafter;

public class Tools {

	CrazyCrafter plugin = CrazyCrafter.getInstance();
	Logger logger = plugin.getLogger();;

	public ItemStack loadMeta(ItemStack item, String path, String type, FileConfiguration recipeConfig) {
		switch (type.toUpperCase()) {
		case "FURNACE":
			return loadFurnaceMeta(item, path, recipeConfig);

		case "SHAPELESS":
			return loadShapelessMeta(item, path, recipeConfig);

		case "SHAPED":
			// return loadShapedMeta(path);
		}

		return null;
	}

	private ItemStack loadFurnaceMeta(ItemStack item, String path, FileConfiguration recipeConfig) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(recipeConfig.getString(path + ".Name"));

		ArrayList<String> lore = new ArrayList<String>();
		for (String s : recipeConfig.getStringList(path + ".Lore"))
			lore.add(s);

		meta.setLore(lore);

		for (String s : recipeConfig.getStringList(path + ".Enchants")) {
			if (getEnchantFromString(s) == null) {
				logger.log(Level.WARNING, "Enchantment is null for path " + path + ".Enchants");
				continue;
			}
			meta.addEnchant(getEnchantFromString(s), getLevelFromString(s), true);
		}
		item.setItemMeta(meta);

		return item;
	}

	/*
	 * public ItemMeta loadShapedMeta(String path) {
	 * 
	 * }
	 */
	public ItemStack loadShapelessMeta(ItemStack item, String path, FileConfiguration recipeConfig) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(recipeConfig.getString(path + ".Name"));
		
		ArrayList<String> lore = new ArrayList<String>();
		for (String s : recipeConfig.getStringList(path + ".Lore"))
			lore.add(s);
		
		
		item.setItemMeta(meta);
		
		return item;
	}

	@SuppressWarnings("deprecation")
	private Enchantment getEnchantFromString(String list) {
		Enchantment enchant = Enchantment.getByName(list.substring(0, list.indexOf(":")).toUpperCase());

		return enchant == null ? null : enchant;
	}

	private int getLevelFromString(String list) {
		int level = Integer.parseInt(list.substring(list.indexOf(":") + 1, list.length()));

		return level == 0 ? null : level;
	}
	
	public static ItemStack getSkull(String url) {
        @SuppressWarnings("deprecation")
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        if (url == null || url.isEmpty())
            return skull;
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }
}
