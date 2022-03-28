package wyvernenchants.wyvernenchants.enchantments.enchants;

import net.minecraft.world.item.crafting.RecipeSerializerCooking;
import net.minecraft.world.item.crafting.RecipeSmoking;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import wyvernenchants.wyvernenchants.enchantments.Enchant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AutoSmelter extends Enchantment implements Listener {
    public AutoSmelter(NamespacedKey key) {
        super(key);
    }

    @Override
    public String getName() {
        return "Auto Smelter";
    }

    @Override
    public int getMaxLevel() {
        return 1;
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
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null) {
            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(this)) {
                if (e.isDropItems()) {
                    List <ItemStack> items = new ArrayList <>();
                    for (ItemStack i : e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand())) {
                        if (getResult(i) != null) {
                            items.add(getResult(i));
                        } else {
                            items.add(i);
                        }

                    }
                    e.setDropItems(false);
                    for(ItemStack i : items) {
                        if(!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchant.telekinesis)) {
                            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), i);
                        } else {
                            if(e.getPlayer().getInventory().firstEmpty() > -1) {
                                e.getPlayer().getInventory().addItem(i);
                            } else {
                                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), i);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e) {
        if(e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();
            if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
                if (p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(this)) {
                    if(!e.getDrops().isEmpty()) {
                        List <ItemStack> items = new ArrayList <>();
                        for (ItemStack i : e.getDrops()) {
                            if (getResult(i) != null) {
                                items.add(getResult(i));
                            } else {
                                items.add(i);
                            }

                        }
                        e.getDrops().clear();
                        for (ItemStack i: items) {

                            if (!p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchant.telekinesis)) {
                                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), i);
                            }
                            else {
                                if (p.getInventory().firstEmpty() > -1) {
                                    p.getInventory().addItem(i);
                                }
                                else {
                                    e.getEntity().getWorld().dropItemNaturally(p.getLocation(), i);
                                }
                            }

                        }
                    }
                }
            }
        }

    }

    public static ItemStack getResult(ItemStack i){
        ItemStack result =  null;
        Material m = i.getType();
        switch (m) {
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
            case RAW_GOLD:
                result =  new ItemStack(Material.GOLD_INGOT);
                break;
            case IRON_ORE:
            case RAW_IRON:
            case DEEPSLATE_IRON_ORE:
                result = new ItemStack(Material.IRON_INGOT);
                break;
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
                result = new ItemStack(Material.COAL);
                break;
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
            case RAW_COPPER:
                result = new ItemStack(Material.COPPER_INGOT);
                break;
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                result = new ItemStack(Material.DIAMOND);
                break;
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                result = new ItemStack(Material.EMERALD);
                break;
            case COBBLESTONE:
                result = new ItemStack(Material.STONE);
                break;
            case OAK_LOG:
            case SPRUCE_LOG:
            case BIRCH_LOG:
            case JUNGLE_LOG:
            case DARK_OAK_LOG:
            case ACACIA_LOG:
                result = new ItemStack(Material.CHARCOAL);
                break;
            case SAND:
                result = new ItemStack(Material.GLASS);
                break;
            case ANCIENT_DEBRIS:
                result = new ItemStack(Material.NETHERITE_SCRAP);
                break;
            case KELP:
                result = new ItemStack(Material.DRIED_KELP);
                break;
            case PORKCHOP:
                result = new ItemStack(Material.COOKED_PORKCHOP);
                break;
            case CHICKEN:
                result = new ItemStack(Material.COOKED_CHICKEN);
                break;
            case RABBIT:
                result = new ItemStack(Material.COOKED_RABBIT);
                break;
            case MUTTON:
                result = new ItemStack(Material.COOKED_MUTTON);
                break;
            case BEEF:
                result = new ItemStack(Material.COOKED_BEEF);
            case POTATO:
                result = new ItemStack(Material.BAKED_POTATO);
                break;
            case COD:
                result = new ItemStack(Material.COOKED_COD);
                break;
            case SALMON:
                result = new ItemStack(Material.COOKED_SALMON);
                break;
        }
        if(result != null) {
            result.setAmount(i.getAmount());
        }
        return result;
    }
}
