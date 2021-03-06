package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
09.07.2020 18:40

*/

import it.smallcode.smallpets.languages.Language;
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.InventoryCache;
import it.smallcode.smallpets.manager.InventoryManager;
import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.text.CenteredText;
import it.smallcode.smallpets.utils.ProgressbarUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InventoryManager1_12 extends InventoryManager {

    private final ArrayList<String> colors = new ArrayList<>();

    /**
     * Creates a inventory manager object
     *
     * @param inventoryCache - the inventoryCache
     */
    public InventoryManager1_12(InventoryCache inventoryCache, LanguageManager languageManager, double xpMultiplier) {

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

        ItemStack stats = new ItemStack(Material.REDSTONE_TORCH_ON);

        ItemMeta itemMeta = stats.getItemMeta();

        itemMeta.setDisplayName("§6" + languageManager.getLanguage().getStringFormatted("stats"));

        List<String> lore = new LinkedList<>();

        lore.add("");
        lore.add("§e" + languageManager.getLanguage().getStringFormatted("experienceMultiplier") + "§8: §7" + ((int) (xpMultiplier *100)) / 100);
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

            itemMeta.setDisplayName(pet.getCustomeName());

            ArrayList<String> lore = new ArrayList();

            lore.add("");

            lore.add(pet.getAbility());

            lore.add("");

            String progressBar = CenteredText.sendCenteredMessage(generateFinishedProgressbar(pet), ChatColor.stripColor(pet.getAbility()).length());

            if(pet.getLevel() != 100)
                lore.add("  " + CenteredText.sendCenteredMessage(pet.getLevelColor() + pet.getLevel(), ChatColor.stripColor(progressBar).length()));
            else
                lore.add("§d" + CenteredText.sendCenteredMessage("" + pet.getLevel(), ChatColor.stripColor(progressBar).length()));

            lore.add(progressBar);

            String expB = languageManager.getLanguage().getStringFormatted("maxLevel");

            if(pet.getLevel() != 100) {
                expB = pet.getLevelColor() + (pet.getXp() - pet.getExpForLevel(pet.getLevel())) + "§8/" + pet.getLevelColor() + (pet.getExpForNextLevel() - pet.getExpForLevel(pet.getLevel()));

                lore.add("  " + CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

            }else
                lore.add(CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

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
            return ProgressbarUtils.generateProgressBar(pet);

        return pet.getLevelColor() + pet.getLevel() + " " + ProgressbarUtils.generateProgressBar(pet) + " " + pet.getLevelColor() + (pet.getLevel() +1);

    }

    private Inventory makeEdges(Inventory inventory){

        for(int i = 0; i < 9; i++){

            inventory.setItem(i, createItem("§8", 160, (short) 15));

        }

        for(int i = 36; i < 45; i++){

            inventory.setItem(i, createItem("§8", 160, (short) 15));

        }

        inventory.setItem(9, createItem("§8", 160, (short) 15));
        inventory.setItem(9 + 8, createItem("§8", 160, (short) 15));

        inventory.setItem(9 * 2, createItem("§8", 160, (short) 15));
        inventory.setItem(9 * 2 + 8, createItem("§8", 160, (short) 15));

        inventory.setItem(9 * 3, createItem("§8", 160, (short) 15));
        inventory.setItem(9 * 3 + 8, createItem("§8", 160, (short) 15));

        return inventory;

    }

    private ItemStack createItem(String name, int id, short subid){

        ItemStack item = new ItemStack(id, 1, subid);

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);

        item.setItemMeta(itemMeta);

        return item;

    }

}
