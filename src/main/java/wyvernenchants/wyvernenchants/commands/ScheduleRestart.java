package wyvernenchants.wyvernenchants.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import wyvernenchants.wyvernenchants.WyvernEnchants;
import wyvernenchants.wyvernenchants.util.Color;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleRestart implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("WyvernEnchants.ScheduleRestart")) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(Color.colorize("&#fb0000R&#fb0909E&#fb1212S&#fc1b1bT&#fc2424A&#fc2e2eR&#fc3737T&#fc4040I&#fd4949N&#fd5252G &#fd5b5bI&#fd6464N "), Color.colorize("&#fd6d6d2&#fe76760 &#fe7f7fS&#fe8989E&#fe9292C&#fe9b9bO&#ffa4a4N&#ffadadD&#ffb6b6S"), 20,20,20);
                p.sendMessage(Color.colorize("&7Restarting in 20 seconds for a &6Game Update" ));
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        restart();
                    }
                }.runTaskLater(WyvernEnchants.instance, 20*20);
            }
        }
        return false;
    }

    @Override
    public List <String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }

    public void restart() {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
    }
}
