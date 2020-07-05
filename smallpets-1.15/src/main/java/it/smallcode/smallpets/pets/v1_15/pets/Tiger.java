package it.smallcode.smallpets.pets.v1_15.pets;
/*

Class created by SmallCode
02.07.2020 14:57

*/

import it.smallcode.smallpets.pets.v1_15.SamplePet;
import it.smallcode.smallpets.pets.v1_15.SkullCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Tiger extends SamplePet {

    public Tiger(Player owner, Long xp) {
        super(owner, xp);
    }

    public Tiger(Player owner) {
        this(owner, 0L);
    }

    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM0MjYzODc0NDkyMmI1ZmNmNjJjZDliZjI3ZWVhYjkxYjJlNzJkNmM3MGU4NmNjNWFhMzg4Mzk5M2U5ZDg0In19fQ==");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

    }

    @Override
    public String getName() {

        return "tiger";

    }

}