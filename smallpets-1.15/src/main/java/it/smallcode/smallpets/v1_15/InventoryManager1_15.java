package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
03.07.2020 16:40

*/

import it.smallcode.smallpets.languages.Language;
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.InventoryCache;
import it.smallcode.smallpets.manager.InventoryManager;
import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.text.CenteredText;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InventoryManager1_15 extends InventoryManager {

    private final ArrayList<String> colors = new ArrayList<>();

    public InventoryManager1_15(InventoryCache inventoryCache, LanguageManager languageManager, double xpMultiplier) {

        super(inventoryCache, languageManager, xpMultiplier);

        colors.add("§4");
        colors.add("§c");
        colors.add("§6");
        colors.add("§e");
        colors.add("§2");
        colors.add("§a");
        colors.add("§b");
        colors.add("§3");
        colors.add("§1");
        colors.add("§9");
        colors.add("§d");
        colors.add("§5");

    }

    @Override
    public void openPetsMenu(List<Pet> pets, Player p) {

        Inventory inventory = inventoryCache.getInventory(p);

        inventory.clear();

        inventory = makeEdges(inventory);

        for(Pet pet : pets){

            ItemStack itemStack = makePetItem(pet, p);

            if(itemStack != null) {

                inventory.addItem(itemStack);

            }

        }

        ItemStack stats = new ItemStack(Material.REDSTONE_TORCH);

        ItemMeta itemMeta = stats.getItemMeta();

        itemMeta.setDisplayName("§6" + languageManager.getLanguage().getStringFormatted("stats"));

        List<String> lore = new LinkedList<>();

        double experienceMultiplier = (int) (xpMultiplier * 100D) /100D;

        lore.add("");
        lore.add("§e" + languageManager.getLanguage().getStringFormatted("experienceMultiplier") + "§8: §7" + experienceMultiplier);
        lore.add("§e" + languageManager.getLanguage().getStringFormatted("experienceBooster") + "§8: §7" + "none");
        lore.add("");

        itemMeta.setLore(lore);

        stats.setItemMeta(itemMeta);

        inventory.setItem(40, stats);

        p.openInventory(inventory);

    }

    private ItemStack makePetItem(Pet pet, Player p){

        ItemStack itemStack = pet.getItem();

        if(itemStack != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(pet.getCustomeNameWithoutPlayername());

            ArrayList<String> lore = new ArrayList();

            if(pet.getPetType() != null) {

                lore.add("§8" + pet.getPetType().getName(languageManager));

            }

            lore.add("");

            lore.add(pet.getAbility());

            lore.add("");

            String progressBar = CenteredText.sendCenteredMessage(generateFinishedProgressbar(pet), ChatColor.stripColor(pet.getAbility()).length());

            if(pet.getLevel() != 100) {

                lore.add("  " + CenteredText.sendCenteredMessage(pet.getLevelColor() + pet.getLevel(), ChatColor.stripColor(progressBar).length()));
                lore.add(progressBar);

                String expB = pet.getLevelColor() + (pet.getXp() - pet.getExpForLevel(pet.getLevel())) + "§8/" + pet.getLevelColor() + (pet.getExpForNextLevel() - pet.getExpForLevel(pet.getLevel()));

                lore.add("  " + CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

            }else{

                lore.add("§8" + languageManager.getLanguage().getStringFormatted("maxLevel"));

            }

            lore.add("");

            if(pet.isActivated())
                lore.add(languageManager.getLanguage().getStringFormatted("clickToDeselect"));
            else
                lore.add(languageManager.getLanguage().getStringFormatted("clickToSelect"));

            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);

        }

        return itemStack;

    }

    private String generateFinishedProgressbar(Pet pet){

        if(pet.getLevel() == 100)
            return generateProgressBar(pet);

        return pet.getLevelColor() + pet.getLevel() + " " + generateProgressBar(pet) + " " + pet.getLevelColor() + (pet.getLevel() +1);

    }

    private String generateProgressBar(Pet pet){

        String bar = "";

        int bars = 35;

        long lastExp = pet.getExpForLevel(pet.getLevel());
        long nextExp = pet.getExpForNextLevel();

        if(pet.getLevel() == 100){

            int color = (int) (Math.random() * colors.size()-1);

            for(int i = 0; i < bars; i++) {

                bar += colors.get(color) + "|";

                color++;

                if(color == colors.size())
                    color = 0;

            }

            return bar;

        }

        long oneBar = (nextExp - lastExp) / bars;

        long nextBar = 0;

        while(nextBar <= (pet.getXp() - lastExp) && bar.length() < (bars*3)){

            nextBar += oneBar;

            bar += pet.getLevelColor() + "|";

        }

        while(bar.length() < (bars*3)){

            bar += "§8|";

        }

        return bar;

    }

    private Inventory makeEdges(Inventory inventory){

        for(int i = 0; i < 9; i++){

            inventory.setItem(i, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        }

        for(int i = 36; i < 45; i++){

            inventory.setItem(i, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        }

        inventory.setItem(9, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(9 + 8, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        inventory.setItem(9 * 2, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(9 * 2 + 8, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        inventory.setItem(9 * 3, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(9 * 3 + 8, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        return inventory;

    }

    private ItemStack createItem(String name, Material material){

        ItemStack item = new ItemStack(material);

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);

        item.setItemMeta(itemMeta);

        return item;

    }

}
