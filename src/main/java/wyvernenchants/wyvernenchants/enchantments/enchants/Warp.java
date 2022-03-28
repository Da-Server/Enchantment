package wyvernenchants.wyvernenchants.enchantments.enchants;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import wyvernenchants.wyvernenchants.WyvernEnchants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Warp extends Enchantment implements Listener {

    public static ArrayList<UUID> delay = new ArrayList <>();
    
    public static final Set <Material> emptyBlocks = new HashSet<Material>() {
        {
            for(Material m : Material.values())  {
                if(m.isBlock()) {
                    if(m.isAir() || !m.isSolid()) {
                        add(m);
                    }
                }
            }
        }
    };

    public Warp(NamespacedKey key) {
        super(key);
    }

    @Override
    public String getName() {
        return "Block Warp";
    }

    @Override
    public int getMaxLevel() {
        return 8;
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
    void onRightClick(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player p = e.getPlayer();
            if(p.getInventory().getItemInMainHand().getItemMeta() != null) {
                if(p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(this)) {
                    int level = p.getInventory().getItemInMainHand().getEnchantmentLevel(this);
                    int distance = 1;
                    for(int i = 1; i <= level; i++) {
                        distance += 2;
                    }
                    if (!delay.contains(p.getUniqueId())) {
                        delay.add(p.getUniqueId());
                        if(p.getLocation().getY() < 299) {
                            Teleport(p, distance);
                        }
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 1);
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                delay.remove(p.getUniqueId());
                            }
                        }.runTaskLater(WyvernEnchants.instance, 2L);
                    }
                }
            }
        }
    }

    public void Teleport(Player player, int distance)
    {
        try
        {
            int f_ = distance;
            for(int range = 1; range < distance; range++) {
                Location location = player.getTargetBlock(emptyBlocks, range).getLocation();
                if (!emptyBlocks.contains(location.getBlock().getType()))
                {
                    f_ = range;
                    break;
                }
            }
            Location location = player.getTargetBlock(emptyBlocks, f_ - 1).getLocation();
            location.setYaw(player.getLocation().getYaw());
            location.setPitch(player.getLocation().getPitch());
            location.add(0.5, 0, 0.5);
            if (f_ != distance)
            {

            }
            if (f_ > 1) {
                player.teleport(location);
            }
            else player.teleport(player.getLocation());
        }
        catch (IllegalStateException ex) {} // suppress bullshit errors thrown by Player#getTargetBlock
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3f, 1f);
    }
}
