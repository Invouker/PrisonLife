// 
// Decompiled by Procyon v0.5.36
// 

package Items;

import org.bukkit.inventory.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeBuilder
{
    private ShapedRecipe recipe;
    
    public RecipeBuilder(final ItemStack output) {
        this.recipe = new ShapedRecipe(output);
    }
    
    public RecipeBuilder setShape(final String... shape) {
        this.recipe.shape(shape);
        return this;
    }
    
    public RecipeBuilder setIngredient(final char c, final Material mat) {
        this.recipe.setIngredient(c, mat);
        return this;
    }
    
    public RecipeBuilder build() {
        Bukkit.addRecipe((Recipe)this.recipe);
        return this;
    }
}
