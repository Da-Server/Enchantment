package wyvernenchants.wyvernenchants.enchantments.enchants;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Thunderlord extends Enchantment implements Listener {
    public Thunderlord(NamespacedKey key) {
        super(key);
    }

    @Override
    public String getName() {
        return "Thunderlord";
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
    public void onEntityAttacked(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (!e.isCancelled()) {
                Player p = (Player)e.getDamager();
                if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
                    ItemStack i = p.getInventory().getItemInMainHand();
                    if (i.getItemMeta().hasEnchant(this)) {
                        float defaultChance = 0.25f;
                        int level = i.getEnchantmentLevel(this);

                        float chance = defaultChance / (level / 10f) * 100f;
                        float currentValue = new Random().nextFloat(0, 100);
                        if (currentValue > chance) {
                            e.getEntity().getWorld().strikeLightning(e.getEntity().getLocation());
                        }
                    }
                }
            }
        }
    }


}
