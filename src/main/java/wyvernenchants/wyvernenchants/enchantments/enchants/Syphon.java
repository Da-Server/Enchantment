package wyvernenchants.wyvernenchants.enchantments.enchants;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Syphon extends Enchantment implements Listener {
    public Syphon(NamespacedKey key) {
        super(key);
    }

    @Override
    public String getName() {
        return "Syphon";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getStartLevel() {
        return 1;
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
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    @EventHandler
    public void onDamage(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player damager = e.getEntity().getKiller();
            if (damager.getInventory().getItemInMainHand().getItemMeta() != null) {
                if (damager.getInventory().getItemInMainHand().getItemMeta().hasEnchant(this)) {
                    double damage = e.getEntity().getHealth()/2;
                    double heal = ((damager.getInventory().getItemInMainHand().getEnchantmentLevel(this) / 0.08) / 150) * damage;
                    damager.setHealth(Math.min(20, damager.getHealth() + heal));
                }
            }
        }
    }
}
