package wyvernenchants.wyvernenchants.enchantments.enchants;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import wyvernenchants.wyvernenchants.enchantments.Enchant;

import java.util.HashSet;
import java.util.Set;

public class TreeCapitator extends Enchantment implements Listener {
    public TreeCapitator(NamespacedKey key) {
        super(key);
    }

    @Override
    public String getName() {
        return "Tree Capitator";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 0;
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
        return item.getType().equals(Material.WOODEN_AXE) ||
                item.getType().equals(Material.STONE_AXE) ||
                item.getType().equals(Material.IRON_AXE) ||
                item.getType().equals(Material.GOLDEN_AXE) ||
                item.getType().equals(Material.DIAMOND_AXE) ||
                item.getType().equals(Material.NETHERITE_AXE);
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null) {
            if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(this)) {
                Material blockType = e.getBlock().getType();
                Player p = (Player) e.getPlayer();
                if(blockType.equals(Material.ACACIA_LOG) ||
                blockType.equals(Material.BIRCH_LOG) ||
                blockType.equals(Material.JUNGLE_LOG) ||
                blockType.equals(Material.DARK_OAK_LOG) ||
                blockType.equals(Material.SPRUCE_LOG) ||
                blockType.equals(Material.OAK_LOG)) {
                    for(Block b : countOres(e.getBlock(), blockType, new HashSet <>())) {
                        if(p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchant.telekinesis)) {
                            for(ItemStack i : b.getDrops(p.getInventory().getItemInMainHand())) {
                                if(p.getInventory().firstEmpty() > -1) {
                                    if(p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchant.autoSmelter)) {
                                        if(AutoSmelter.getResult(i) != null) {
                                            p.getInventory().addItem(AutoSmelter.getResult(i));
                                        } else {
                                            p.getInventory().addItem(i);
                                        }
                                    } else {
                                        p.getInventory().addItem(i);
                                    }
                                } else {
                                    if(AutoSmelter.getResult(i) != null) {
                                        b.getWorld().dropItemNaturally(b.getLocation(), AutoSmelter.getResult(i));
                                    } else {
                                        b.getWorld().dropItemNaturally(b.getLocation(), i);
                                    }
                                }
                            }
                            b.setType(Material.AIR);

                        } else {
                            if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchant.autoSmelter)) {
                                for (ItemStack i: b.getDrops(p.getInventory().getItemInMainHand())) {
                                    if (AutoSmelter.getResult(i) != null) {
                                        b.getWorld().dropItemNaturally(b.getLocation(), AutoSmelter.getResult(i));
                                    }
                                    else {
                                        b.getWorld().dropItemNaturally(b.getLocation(), i);
                                    }
                                }
                                b.setType(Material.AIR);
                            } else{
                                b.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                            }
                        }
                        p.playSound(b.getLocation(), Sound.BLOCK_WOOD_BREAK, 2, 1);
                    }
                }
            }
        }
    }


    BlockFace[] faces = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
    int amount;

    public Set <Block> countOres(Block seed, Material type, Set<Block> counted){
        if(seed.getType() == type && !counted.contains(seed)){
            amount++;
            counted.add(seed);

            for(BlockFace face: faces){
                countOres(seed.getRelative(face), type, counted);
            }
        }
        return counted;
    }
}
