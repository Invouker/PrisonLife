// 
// Decompiled by Procyon v0.5.36
// 

package Basketball;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import Main.Main;
import org.bukkit.entity.Slime;

public class EntitiyBall
{
    Slime slime;
    int maximumBalls;
    
    public EntitiyBall() {
        this.maximumBalls = 3;
    }
    
    public void createBall(final int x, final int y, final int z) {
        final World world = Bukkit.getWorld(Main.mainWorld);
        final Location loc = new Location(world, x + 0.5, (double)y, z + 0.5);
        this.slime = (Slime)world.spawnEntity(loc, EntityType.SLIME);
        this.setupSlime();
    }
    
    public void setupSlime() {
        this.slime.setCustomName("Ball");
        this.slime.setCustomNameVisible(true);
        this.slime.setSize(1);
        this.slime.setAI(true);
        this.slime.setRemoveWhenFarAway(false);
        this.slime.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000000, 9999));
    }
}
