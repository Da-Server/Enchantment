package wyvernenchants.wyvernenchants.enchantments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_18_R2.enchantments.CraftEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import wyvernenchants.wyvernenchants.WyvernEnchants;
import wyvernenchants.wyvernenchants.enchantments.enchants.*;

import java.util.ArrayList;
import java.util.HashMap;

public enum Enchant {;
    public static HashMap<String, Enchantment> enchantMap = new HashMap <String, Enchantment>();
    public static HashMap<Enchantment, ItemStack> applyableEnchants = new HashMap<>();
    public static HashMap<Enchantment, Integer> costMap = new HashMap <>();
    public static HashMap<Enchantment, ChatColor> colorMap = new HashMap <>();
    public static ArrayList<Listener> listeners = new ArrayList<>();
    public static Telekinesis telekinesis = new Telekinesis(createKey("telekinesis"));
    public static Thunderlord thunderlord = new Thunderlord(createKey("thunderlord"));
    public static Perroar perroar = new Perroar(createKey("perroar"));
    public static Syphon syphon = new Syphon(createKey("syphon"));
    public static VeinMiner veinMiner = new VeinMiner(createKey("veinminer"));
    public static TreeCapitator treeCapitator = new TreeCapitator(createKey("treecapitator"));
    public static AutoSmelter autoSmelter = new AutoSmelter(createKey("autosmelter"));
    public static Warp warp = new Warp(createKey("warp"));
    public static Explode explode = new Explode(createKey("explosion"));


    public static ArrayList<Class<? extends Enchantment>> unnasignables = new ArrayList<Class<? extends Enchantment>>() {
        {
            add(EnchantmentWrapper.class);
            add(CraftEnchantment.class);
        }
    };

    public static void init() {
        addKey("Telekinesis", telekinesis, telekinesis);
        addKey("Thunderlord", thunderlord, thunderlord);
        addKey("Syphon", syphon, syphon);
        addKey("Perroar", perroar,perroar);
        addKey("Block_Warp",warp,warp );
        addKey("Vein_Miner",veinMiner,veinMiner);
        addKey("Tree_Capitator", treeCapitator, treeCapitator);
        addKey("Auto_Smelter", autoSmelter, autoSmelter);
        addKey("Explosion", explode, explode);
        addApplyable(syphon, new ItemStack(Material.GOLD_INGOT, 12));
        addApplyable(warp, new ItemStack(Material.DIAMOND, 5));
        addApplyable(autoSmelter, new ItemStack(Material.COAL, 64));
        addApplyable(telekinesis, new ItemStack(Material.LAPIS_LAZULI, 40));
        addApplyable(veinMiner, new ItemStack(Material.EMERALD, 10));
        addApplyable(treeCapitator, new ItemStack(Material.ACACIA_LOG, 64));
        put(warp, ChatColor.LIGHT_PURPLE);
        put(explode, ChatColor.GREEN);
        put(syphon, ChatColor.GOLD);


        WyvernEnchants.instance.registerEnchants();
    }

    public static void put(Enchantment ec, ChatColor color) {
        colorMap.put(ec, color);
    }

    public static void addKey(String name, Enchantment ec, Listener l) {
        enchantMap.put(name, ec);
        listeners.add(l);
    }
    public static void addApplyable(Enchantment ec, ItemStack cost) {
        applyableEnchants.put(ec,cost);
        costMap.put(ec, cost.clone().getAmount());
    }



    public static NamespacedKey createKey(String name) {
        return NamespacedKey.fromString("wyvernenchants:" + name);
    }
}
