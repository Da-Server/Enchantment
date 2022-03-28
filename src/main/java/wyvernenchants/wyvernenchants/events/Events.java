package wyvernenchants.wyvernenchants.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wyvernenchants.wyvernenchants.enchantments.Enchant;
import wyvernenchants.wyvernenchants.util.RomanNumeral;

import java.util.List;
import java.util.Random;

public class Events implements Listener {

    @EventHandler
    public void onKillEntity(EntityDeathEvent e) {

        if (e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();
            p.getInventory().getItemInMainHand();
            if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
                if (p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchant.telekinesis)) {
                    for (ItemStack i: e.getDrops()) {
                        if (p.getInventory().firstEmpty() > -1) {
                            p.getInventory().addItem(i);
                        }
                        else {
                            p.getWorld().dropItem(p.getLocation(), i);
                        }
                        if(e.getDroppedExp() > 0) {
                            p.giveExp(e.getDroppedExp());
                            e.setDroppedExp(0);
                        }
                    }
                    e.getDrops().clear();
                }
            }
        }
    }

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null) {
                if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchant.telekinesis)) {
                    if (e.isDropItems()) {
                        Player p = e.getPlayer();
                        for (ItemStack i: e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand())) {
                            if (p.getInventory().firstEmpty() > -1) {
                                p.getInventory().addItem(i);
                            }
                            else {
                                p.getWorld().dropItem(p.getLocation(), i);
                            }
                        }
                        if(e.getExpToDrop() > 0) {
                            p.giveExp(e.getExpToDrop());
                            e.setExpToDrop(0);
                        }
                        e.setDropItems(false);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onAnvilCombine(PrepareAnvilEvent e) {
        ItemStack input = e.getInventory().getItem(0);
        ItemStack input2 = e.getInventory().getItem(1);
        ItemStack result = e.getResult();
        if(input != null) {
            if (input.getItemMeta() != null) {
                List <String> lore = input.getItemMeta().getLore();
                for (Enchantment ec: Enchant.enchantMap.values()) {
                    if (input.getItemMeta().hasEnchant(ec)) {
                        if (result.getItemMeta() != null) {
                            ItemMeta m = result.getItemMeta();
                            m.addEnchant(ec, input.getEnchantmentLevel(ec), true);
                            result.setItemMeta(m);
                        }
                    }
                }
            }
        }
    }


}
