package wyvernenchants.wyvernenchants.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wyvernenchants.wyvernenchants.enchantments.Enchant;
import wyvernenchants.wyvernenchants.util.RomanNumeral;

import java.util.ArrayList;
import java.util.List;

public class AddEnchantment implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("WyvernEnchants.AddEnchant")) {
                if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
                    ItemStack i = p.getInventory().getItemInMainHand();
                    ItemMeta m = p.getInventory().getItemInMainHand().getItemMeta();
                    List <String> lore;
                    if (m.hasLore()) {
                        lore = m.getLore();
                    }
                    else {
                        lore = new ArrayList <>();
                    }

                    if (args.length == 1) {
                        if (Enchant.enchantMap.containsKey(args[0])) {
                            ChatColor color = ChatColor.GRAY;

                            if (Enchant.colorMap.containsKey(Enchant.enchantMap.get(args[0]))) {
                                color = Enchant.colorMap.get(Enchant.enchantMap.get(args[0]));
                            }
                            if (!lore.contains(ChatColor.translateAlternateColorCodes('&', color + Enchant.enchantMap.get(args[0]).getName() + " I"))) {
                                lore.add(ChatColor.translateAlternateColorCodes('&', color + Enchant.enchantMap.get(args[0]).getName() + " I"));
                            }
                            m.addEnchant(Enchant.enchantMap.get(args[0]), 1, false);
                        }
                    }
                    else if (args.length >= 2) {
                        String id = args[0];
                        int value = Integer.parseInt(args[1]);
                        int amount = 0;
                        int pos = 0;
                        ChatColor color = ChatColor.GRAY;

                        if (Enchant.colorMap.containsKey(Enchant.enchantMap.get(args[0]))) {
                            color = Enchant.colorMap.get(Enchant.enchantMap.get(args[0]));
                        }
                        try {
                            if (Enchant.enchantMap.containsKey(id)) {
                                for (String s: lore) {

                                    if (s.contains(ChatColor.translateAlternateColorCodes('&', color + Enchant.enchantMap.get(id).getName() + " "))) {
                                        amount++;
                                    }
                                    else {
                                        pos++;
                                    }

                                }
                                if (amount == 0) {
                                    lore.add(ChatColor.translateAlternateColorCodes('&', color + Enchant.enchantMap.get(id).getName() + " " + new RomanNumeral(value).toString()));
                                }
                                else {
                                    lore.set(pos, ChatColor.translateAlternateColorCodes('&', color + Enchant.enchantMap.get(id).getName() + " " + new RomanNumeral(value).toString()));
                                }

                                m.addEnchant(Enchant.enchantMap.get(id), value, true);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    m.setLore(lore);
                    i.setItemMeta(m);
                    p.getInventory().setItemInMainHand(i);
                }
            }
        }
        return false;
    }


    @Override
    public List <String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList <>(Enchant.enchantMap.keySet());
    }
}
