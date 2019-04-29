package me.jadc.itemauthorize;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class IA extends JavaPlugin {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("authorize") || cmd.getName().equalsIgnoreCase("auth")){
			if(!(sender instanceof Player)) { 
				sender.sendMessage("Ingame players only."); 
				return true;
			}
			
			Player p = (Player) sender;
			
			// Determine if holding valid item stack
			if(p == null || p.getInventory() == null || p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR) {
				p.sendMessage(ChatColor.RED + "Invalid item in main hand.");
				return true;
			}
			
			ItemStack item = p.getInventory().getItemInMainHand();
			
			// Check for item meta and lore
			ItemMeta meta = item.getItemMeta();
			if(meta.hasLore()) {
				p.sendMessage(ChatColor.RED + "Item is already authorized.");
				return true;
			}
			
			// Set Lore
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat format = new SimpleDateFormat("M/dd/yy h:mm a");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.DARK_GRAY + "Authorized by " + p.getDisplayName());
			lore.add(ChatColor.DARK_GRAY + "on " + format.format(date));
			
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			// Send response and logs it to console
			sender.sendMessage("Authorized item in main hand to your name");
			Bukkit.getLogger().log(Level.INFO, p.getDisplayName() + " authorized [" + item.getType() + " x" + item.getAmount() + "]");
		}
		
		return true;
	}
}
