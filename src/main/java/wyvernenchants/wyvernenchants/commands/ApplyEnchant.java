package wyvernenchants.wyvernenchants.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wyvernenchants.wyvernenchants.enchantments.Enchant;
import wyvernenchants.wyvernenchants.gui.guis.EnchantUI;
import wyvernenchants.wyvernenchants.util.Color;

import java.util.ArrayList;
import java.util.List;

public class ApplyEnchant implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            EnchantUI ui = new EnchantUI(p);
            p.openInventory(ui.getInventory());
            p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK,2,1);
        }
        return false;
    }

    @Override
    public List <String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList <>();
    }
}
