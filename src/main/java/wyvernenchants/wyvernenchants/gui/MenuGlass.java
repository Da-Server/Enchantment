package wyvernenchants.wyvernenchants.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum MenuGlass {

    black {
        @Override
        public ItemStack createNew() {
            ItemStack i = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta m = i.getItemMeta();
            m.setDisplayName(" ");
            i.setItemMeta(m);
            return i;
        }
    };
    public ItemStack createNew() {
        return null;
    }
}
