package wyvernenchants.wyvernenchants.util;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import wyvernenchants.wyvernenchants.enchantments.Enchant;

import java.util.ArrayList;
import java.util.List;

public class EnchantUtils {

    public static ArrayList <String> createLore(List <String> input, Enchantment in, int level) {
        ArrayList <String> lore = new ArrayList <>();

        if (input == null) {
            lore = new ArrayList <>();
        }
        else {
            lore = new ArrayList <>(input);
        }
        int pos = 0;
        int amount = 0;
        ChatColor color = ChatColor.GRAY;

        if (Enchant.colorMap.containsKey(in)) {
            color = Enchant.colorMap.get(in);
        }
        for (String s: lore) {
            if (s.contains(ChatColor.translateAlternateColorCodes('&', color + in.getName() + " "))) {
                amount++;
            }
            else {
                pos++;
            }

        }

        if (amount == 0) {
            lore.add(ChatColor.translateAlternateColorCodes('&', color + in.getName() + " " + new RomanNumeral(level).toString()));
        }
        else {
            lore.set(pos, ChatColor.translateAlternateColorCodes('&', color + in.getName() + " " + new RomanNumeral(level).toString()));
        }
        return lore;
    }

    public static String createCost(ItemStack cost, int amount) {
        return Color.colorize("&6Cost: ") + Color.colorize("&ax") + Color.colorize(amount + " ") + Color.colorize("&6" + cost.getType().name());
    }
}
