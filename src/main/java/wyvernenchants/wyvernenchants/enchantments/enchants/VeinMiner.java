package wyvernenchants.wyvernenchants.enchantments.enchants;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_18_R2.CraftSound;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import wyvernenchants.wyvernenchants.WyvernEnchants;
import wyvernenchants.wyvernenchants.enchantments.Enchant;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class VeinMiner extends Enchantment  implements Listener {
    public static ArrayList<Material> veinables = new ArrayList<Material>() {
        {
            add(Material.GOLD_ORE);
            add(Material.DEEPSLATE_GOLD_ORE);
            add(Material.IRON_ORE);
            add(Material.DEEPSLATE_IRON_ORE);
            add(Material.LAPIS_ORE);
            add(Material.DEEPSLATE_LAPIS_ORE);
            add(Material.COAL_ORE);
            add(Material.DEEPSLATE_COAL_ORE);
            add(Material.DIAMOND_ORE);
            add(Material.DEEPSLATE_DIAMOND_ORE);
            add(Material.EMERALD_ORE);
            add(Material.DEEPSLATE_EMERALD_ORE);
            add(Material.COPPER_ORE);
            add(Material.DEEPSLATE_COPPER_ORE);
            add(Material.REDSTONE_ORE);
            add(Material.DEEPSLATE_REDSTONE_ORE);
            add(Material.GRANITE);
            add(Material.ANDESITE);
            add(Material.DIORITE);
        }
    };

    public VeinMiner(NamespacedKey key) {
        super(key);
    }

    @Override
    public String getName() {
        return "Vein Miner";
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
        return item.getType().equals(Material.WOODEN_PICKAXE) ||
                item.getType().equals(Material.STONE_PICKAXE) ||
                item.getType().equals(Material.IRON_PICKAXE) ||
                item.getType().equals(Material.GOLDEN_PICKAXE) ||
                item.getType().equals(Material.DIAMOND_PICKAXE) ||

                item.getType().equals(Material.NETHERITE_PICKAXE);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand().getItemMeta() != null) {
            if(p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(this)) {
                if(veinables.contains(e.getBlock().getType())) {
                    for(Block b : countOres(e.getBlock(), e.getBlock().getType(), new HashSet <>())) {
                        if(p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchant.telekinesis)) {
                            for(ItemStack i : b.getDrops(p.getInventory().getItemInMainHand())) {
                                if(p.getInventory().firstEmpty() > -1) {

                                    if (p.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchant.autoSmelter)) {
                                        if (AutoSmelter.getResult(i) != null) {
                                            p.getInventory().addItem(AutoSmelter.getResult(i));
                                        }
                                        else {
                                            p.getInventory().addItem(i);
                                        }
                                    }
                                    else {
                                        p.getInventory().addItem(i);
                                    }

                                } else {
                                    if(AutoSmelter.getResult(i) != null) {
                                        p.getWorld().dropItemNaturally(b.getLocation(), AutoSmelter.getResult(i));
                                    } else {
                                        p.getWorld().dropItemNaturally(b.getLocation(), i);
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
                        p.playSound(b.getLocation(), Sound.BLOCK_STONE_BREAK, 2, 1);
                    }
                }
            }
        }
    }
    BlockFace[] faces = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
    int amount;

    public Set<Block> countOres(Block seed, Material type, Set<Block> counted){
        if(seed.getType() == type && !counted.contains(seed)){
            amount++;
            counted.add(seed);

            for(BlockFace face: faces){
                countOres(seed.getRelative(face), type, counted);
            }
        }
        return counted;
    }


    private ArrayList<Block> search(Block center) {
        //The maximum amount of blocks to find (the 'cap', our limit)
        final int max = 25;

        ArrayList<Block> blocks = new ArrayList<>();
        Queue <Block> toSearch = new LinkedList <>();

        //Add the center to list, so it has something to start the search
        toSearch.add(center);

        //While we have something to search and have not reached the limit (the 'cap')
        while (toSearch.size() > 0 && blocks.size() < max) {
            Block b = toSearch.remove(); //Get the block on top of the queue, (and remove it)

            blocks.add(b); //Since this block is already of the type we want, we add it the found list (in this case is the 'blocks' var)

            //Find all its neighbours
            for (Block around : findNeighbours(b)) {
                //We do this check here too 'cause findNeighbours() might return up to 26 blocks and it might be too much
                //eg. we have a max of 50 blocks and have already found 45, if findNeighbours find more than five blocks we want to ignore to others
                //that way we stay within our limit, and once this check is made once the whole loop will end
                if (blocks.size() >= max) {
                    break;
                }

                //Only add this block if not yet found/processed/searched
                if (toSearch.contains(around) || blocks.contains(around)) {
                    continue;
                }

                toSearch.add(around);
            }

            //If in our toSearch list we already enough blocks to fill our limit we stop the search and add as much as we need to fill up out limit.
            //This can save some resources when searching for common blocks like dirt and stone, which we might find a lot and not all of them will be added to our list
            if (toSearch.size() + blocks.size() >= max) {
                int remains = max - blocks.size(); //Gets how many more blocks we need to fulfill our goal (the limit)

                for (int i = 0; i < remains; i++) {
                    blocks.add(toSearch.remove());
                }

                break;
            }
        }

        return blocks;
    }

    //Finds all neighbours around a block
    private List <Block> findNeighbours(Block block) {
        //to avoid a bunch of ifs we use these 3 fors to loop over each axis (X, Y, Z)

        ArrayList<Block> blocks = new ArrayList<>();

        //SQUARED 'radius' to search around
        final int searchRadius = 1;

        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int y = -searchRadius; y <= searchRadius; y++) {
                for (int z = -searchRadius; z <= searchRadius; z++) {
                    if (x == 0 && y == 0 && z == 0) {continue;}

                    //Get the block at this location (x,y,z)
                    Block near = block.getLocation().clone().add(x, y, z).getBlock();

                    //Check if the found block is a valid match. eg: is the same type, has the same data/variant
                    if (match(near, block)) {
                        blocks.add(near);
                    }
                }
            }
        }

        return blocks;
    }

    //Checks if a matches to b
    private boolean match(Block a, Block b) {
        //Checks only the block type, ot its variant/data
        //return a.getType() == b.getType();

        //Checks its type and its data/variant (might not work on all bukkit/spigot versions)
        return a.getType() == b.getType() && a.getState().getData().equals(b.getState().getData());
    }


}
