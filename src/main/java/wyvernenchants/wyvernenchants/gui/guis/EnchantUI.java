package wyvernenchants.wyvernenchants.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import wyvernenchants.wyvernenchants.enchantments.Enchant;
import wyvernenchants.wyvernenchants.gui.GUI;
import wyvernenchants.wyvernenchants.gui.MenuGlass;
import wyvernenchants.wyvernenchants.util.Color;
import wyvernenchants.wyvernenchants.util.EnchantUtils;
import wyvernenchants.wyvernenchants.util.RomanNumeral;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnchantUI extends GUI implements Listener {

    public EnchantUI() {
        super("&#084cfbE&#1a5ffbN&#2d71fbC&#3f84fcH&#5196fcA&#64a9fcN&#76bbfcT&#88cefdI&#9be0fdN&#adf3fdG", 54);

    }
    int inputSlot = 49;

    int maxLevel = 0;
    int amount = 0;
    int value = 1;
    int level = 0;
    int pos = 0;

    ItemStack cost = null;
    public EnchantUI(Player p) {
        super("&#084cfbE&#1a5ffbN&#2d71fbC&#3f84fcH&#5196fcA&#64a9fcN&#76bbfcT&#88cefdI&#9be0fdN&#adf3fdG", 54);
        Inventory inv = this.getInventory();
        MenuGlass glass = MenuGlass.black;



        ItemStack book = null;
        ItemMeta meta = null;
        List <String> lore = new ArrayList <>();

        for (int i = 0; i  < 54;i++) {
            inv.setItem(i, glass.createNew());
        }
        inv.setItem(inputSlot, new ItemStack(Material.AIR));

        for(Enchantment enchantment : Enchant.applyableEnchants.keySet()) {

            book = new ItemStack(Material.ENCHANTED_BOOK);
            meta = book.getItemMeta();

            assert meta != null;


            cost = Enchant.applyableEnchants.get(enchantment).clone();

            amount = Enchant.costMap.get(enchantment);
            if(amount == 0) {
                amount = Enchant.costMap.get(enchantment);
            }


            if(enchantment.getMaxLevel() > 1) {
                maxLevel = enchantment.getMaxLevel();
                for(int i = 1; i <= enchantment.getMaxLevel(); i++) {
                    if(p.getInventory().containsAtLeast(cost, amount * i)) {
                        value = i;
                    } else {
                        break;
                    }
                }
                level = value;
                meta.addEnchant(enchantment, level, true);

                lore = EnchantUtils.createLore(meta.getLore(), enchantment, level);
                lore.add("");
                lore.add(EnchantUtils.createCost(cost, amount * level));


            } else {
                level = 1;
                meta.addEnchant(enchantment, 1, true);
                lore = EnchantUtils.createLore(meta.getLore(), enchantment, 1);
                lore.add("");
                lore.add(EnchantUtils.createCost(cost, amount));
            }
            meta.setLore(lore);
            book.setItemMeta(meta);
            inv.setItem(pos, book);
            pos++;
        }

    }
    int toAdd = 0;
    int currentCount = 0;
    int maxEnchantLevel = 0;
    int costAmount = 0;

    ItemStack normalCost = null;
    int normalCostAmount = 0;
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        InventoryView view = e.getView();
        Inventory clickedInventory = e.getClickedInventory();
        HumanEntity whoClicked = e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        Player player = (Player) whoClicked;
        PlayerInventory playerInv = player.getInventory();

        boolean isLeftClick = e.isLeftClick();
        boolean isRightClick = e.isRightClick();
        boolean isShift = e.isShiftClick();
        boolean isApplyable = false;


        int slot = e.getRawSlot();

        boolean isTopInventory = clickedInventory == view.getTopInventory();
        boolean isBottomInventory = clickedInventory == view.getBottomInventory();

        boolean isInput = slot == inputSlot;

        boolean invNull = clickedInventory == null;
        boolean hasItemMeta = false;
        boolean isInputNull =  false;


        boolean isEnchantedBook = false;
        boolean hasEnchant = false;
        boolean isMenuGlass = clickedItem.equals(MenuGlass.black.createNew());
        boolean isLowerLevel = false;

        boolean playerHasItems = false;

        ItemStack input = null;
        ItemMeta meta = null;


        Enchantment enchantment = null;


        if(clickedItem == null) return;
        isEnchantedBook = clickedItem.getType().equals(Material.ENCHANTED_BOOK);

        if(isBottomInventory) return;

        if(isInput) return;

        if(invNull) return;

        isInputNull = clickedInventory.getItem(slot) == null;

        if(isInputNull) return;

        if(!view.getTitle().equals(Color.colorize(getName()))) return;

        e.setCancelled(true);
        hasItemMeta =  clickedInventory.getItem(slot).getItemMeta() != null;

        if(!hasItemMeta) return;


        input = clickedInventory.getItem(inputSlot);

        assert input != null;

        if(input == null) return;

        meta = input.getItemMeta();

        assert meta != null;

        if(isEnchantedBook) {
            for(Enchantment enchant : Enchant.applyableEnchants.keySet()) {
                if(Objects.requireNonNull(clickedItem.getItemMeta()).hasEnchant(enchant)) {
                    enchantment = enchant;
                    level = clickedItem.getEnchantmentLevel(enchant);
                    hasEnchant = meta.hasEnchant(enchant);
                    isApplyable = enchant.canEnchantItem(input);
                    cost = Enchant.applyableEnchants.get(enchantment);
                    costAmount = Enchant.costMap.get(enchantment);
                    break;
                }
            }
        }
        if(cost == null) {
            cost = new ItemStack(Material.AIR);
        }

        assert enchantment != null;
        if(hasEnchant) {
            if (input.getEnchantmentLevel(enchantment) <= level) {
                isLowerLevel = true;
                player.sendMessage(Color.colorize("&7Cannot add a lower level enchantment"));
            }
        }

        if(!isLowerLevel) {
            cost = Enchant.applyableEnchants.get(enchantment);

        }

        if(player.getInventory().containsAtLeast(cost, costAmount)) playerHasItems = true;


        if(!playerHasItems) {
            player.sendMessage(Color.colorize("&7You do not have &6x" + costAmount + " &a" + cost.getType().name()));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 0.1f);
        }
        else {
            if(isApplyable) {
                meta.addEnchant(enchantment, level, true);
                meta.setLore(EnchantUtils.createLore(meta.getLore(), enchantment, level));
                input.setItemMeta(meta);


                cost.setAmount(costAmount * level);
                playerInv.removeItem(cost);


                player.sendMessage(Color.colorize("&6Enchanted"));
                player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 2, 1);
            } else {
                player.sendMessage(Color.colorize("&7Enchantment can not be applied to this item"));
            }
        }
    }

    public void update(int slot, int amount, ItemStack cost, Enchantment enchantment, Player p) {
        int maxLevel = 0;
        int value = 0;
        int level = 0;
        ItemStack it = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = it.getItemMeta();
        List<String> lore = new ArrayList<>();
        if(enchantment.getMaxLevel() > 1) {
            maxLevel = enchantment.getMaxLevel();
            for(int i = 1; i <= enchantment.getMaxLevel(); i++) {
                if(p.getInventory().containsAtLeast(cost, amount * i)) {
                    value++;
                }
            }
            if(value == 0) value++;
            int finalValue = value;
            int finalAmount = amount;
            if(amount * value > finalValue * finalAmount) {
                amount = finalAmount;
                value = finalValue;
            }
            level = value;
            meta.addEnchant(enchantment, level, true);
            lore = EnchantUtils.createLore(meta.getLore(), enchantment, level);
            lore.add("");
            lore.add(EnchantUtils.createCost(cost, amount * level));


        } else {
            level = 1;
            meta.addEnchant(enchantment, 1, true);
            lore = EnchantUtils.createLore(meta.getLore(), enchantment, 1);
            lore.add("");
            lore.add(EnchantUtils.createCost(cost, amount));
        }
        meta.setLore(lore);
        it.setItemMeta(meta);
        this.getInventory().setItem(slot, it);

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(e.getView().getTopInventory() == e.getInventory()) {
            if(e.getView().getTitle().equals(Color.colorize(getName()))) {
                ItemStack i = e.getInventory().getItem(49);
                e.getPlayer().getInventory().addItem(i);
                
            }
        }
    }


}
