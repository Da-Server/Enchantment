package wyvernenchants.wyvernenchants;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import wyvernenchants.wyvernenchants.commands.AddEnchantment;
import wyvernenchants.wyvernenchants.commands.ApplyEnchant;
import wyvernenchants.wyvernenchants.commands.ScheduleRestart;
import wyvernenchants.wyvernenchants.enchantments.Enchant;
import wyvernenchants.wyvernenchants.events.Events;
import wyvernenchants.wyvernenchants.gui.guis.EnchantUI;

import java.lang.reflect.Field;

public final class WyvernEnchants extends JavaPlugin {


    public static WyvernEnchants instance;
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic


        Enchant.init();


        registerEvents(Enchant.listeners.toArray(new Listener[]{}));
        registerEvents(new EnchantUI());


        Bukkit.getPluginManager().registerEvents(new Events(), this);
        Bukkit.getPluginCommand("AddEnchant").setExecutor(new AddEnchantment());
        Bukkit.getPluginCommand("Enchant").setExecutor(new ApplyEnchant());
        Bukkit.getPluginCommand("ScheduleRestart").setExecutor(new ScheduleRestart());



    }

    public void registerEvents(Listener... l) {
        for(Listener listener : l) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerEnchants() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            /*Enchantment.registerEnchantment(Enchant.telekinesis);
            Enchantment.registerEnchantment(Enchant.thunderlord);
            Enchantment.registerEnchantment(Enchant.perroar);
            Enchantment.registerEnchantment(Enchant.lifesteal);*/

            for(Enchantment ec : Enchant.enchantMap.values()) {
                Enchantment.registerEnchantment(ec);
            }
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
