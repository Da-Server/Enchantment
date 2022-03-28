package wyvernenchants.wyvernenchants.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import wyvernenchants.wyvernenchants.util.Color;

import java.util.UUID;

public abstract class GUI implements InventoryHolder {
    private Inventory i;
    private String name;

    public GUI(String name, int size) {
        i = Bukkit.createInventory(this, size, Color.colorize(name));
        this.name = name;
    }

    @Override
    public Inventory getInventory() {
        return i;
    }

    public String getName() {
        return name;
    }


}
