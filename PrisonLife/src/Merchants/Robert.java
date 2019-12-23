// 
// Decompiled by Procyon v0.5.36
// 

package Merchants;

import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import java.util.ArrayList;
import org.bukkit.inventory.MerchantRecipe;
import java.util.List;
import org.bukkit.event.Listener;

public class Robert implements Listener
{
    private List<MerchantRecipe> recipes;
    
    public Robert() {
        this.recipes = new ArrayList<MerchantRecipe>();
    }
    
    @EventHandler
    public void onPlayerInteractEntityEvent(final PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Villager && e.getRightClicked().getName().contains("R\u00f3bert")) {
            e.getRightClicked().setInvulnerable(true);
        }
    }
    
    public void addRecipe(final ItemStack is1, final ItemStack is2, final ItemStack result) {
        final MerchantRecipe recipe = new MerchantRecipe(result, 10000);
        recipe.addIngredient(is1);
        recipe.setExperienceReward(false);
        if (is2 != null) {
            recipe.addIngredient(is2);
        }
        this.recipes.add(recipe);
    }
    
    public List<MerchantRecipe> getRecipes() {
        return this.recipes;
    }
}
