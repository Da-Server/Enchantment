package wyvernenchants.wyvernenchants.enchantments.enchants;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import wyvernenchants.wyvernenchants.WyvernEnchants;
import wyvernenchants.wyvernenchants.util.Color;

public class Perroar extends Enchantment implements Listener {
    public Perroar(NamespacedKey key) {
        super(key);
    }

    @Override
    public String getName() {
        return "Perroar";
    }

    @Override
    public int getMaxLevel() {
        return 3320;
    }

    @Override
    public int getStartLevel() {
        return 233;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return false;
    }

    @EventHandler
    public void onRightCLick(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getItem() != null) {
                if (e.getItem().getItemMeta() != null) {
                    if (e.getItem().getItemMeta().hasEnchant(this)) {
                        for (Entity entity: e.getPlayer().getWorld().getNearbyEntities(e.getPlayer().getLocation(), 15, 15, 15)) {
                            if (entity instanceof Player) {
                                Player p = (Player)entity;
                                Location loc = p.getLocation().clone();
                                p.sendMessage(Color.colorize("&7You, sir/ma'am " + p.getDisplayName() + " &7Are going to &6HEAVEN"));
                                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_HURT, 2, 1);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        p.teleport(p.getLocation().add(0, 1000, 0));
                                        p.setAllowFlight(true);

                                        p.sendMessage(Color.colorize("&6You are in heaven, this is god now u return :)"));

                                        new BukkitRunnable() {

                                            @Override
                                            public void run() {
                                                p.teleport(loc);
                                                p.setAllowFlight(false);
                                                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
                                            }
                                        }.runTaskLater(WyvernEnchants.instance, 50L);
                                    }
                                }.runTaskLater(WyvernEnchants.instance, 40L);


                            }
                        }
                    }
                }
            }
        }
    }
}
