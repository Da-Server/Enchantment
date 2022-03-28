package wyvernenchants.wyvernenchants.enchantments.enchants;

import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import wyvernenchants.wyvernenchants.WyvernEnchants;
import wyvernenchants.wyvernenchants.util.Color;

public class Explode extends Enchantment implements Listener {
    public Explode(NamespacedKey key) {
        super(key);
    }

    @Override
    public String getName() {
        return "Explosion";
    }

    @Override
    public int getMaxLevel() {
        return 6;
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
        return false;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player p = e.getPlayer();
            if(p.getInventory().getItemInMainHand().getItemMeta() != null) {
                if(p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(this)) {
                    int level = p.getInventory().getItemInMainHand().getEnchantmentLevel(this);
                    int radius = 1;
                    for (int i = 1;i <= level;i++) {
                        radius += 1;
                    }
                    if (p.getLocation().getY() < 299) {

                    }
                    int entitycount = 0;
                    for (Entity entity: p.getNearbyEntities(radius, radius, radius)) {
                        if(!(entity instanceof Player)) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity ent = (LivingEntity)entity;
                                ent.damage(10000);
                                entitycount++;
                            }
                        }
                    }
                    p.sendMessage(Color.colorize("&7Your explosion killed &6" + entitycount + " &7mobs" ));
                    p.spawnParticle(Particle.EXPLOSION_LARGE, p.getLocation(), 0, 0, 0, 0, 0.0001);
                    p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
                }
            }
        }
    }
}
