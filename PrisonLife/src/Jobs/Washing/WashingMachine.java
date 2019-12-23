// 
// Decompiled by Procyon v0.5.36
// 

package Jobs.Washing;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import Items.ItemBuilder;
import Main.Chat;
import Main.Main;

public class WashingMachine implements Listener
{
    Washing wash;
    BukkitTask washingMachine;
    
    public WashingMachine() {
        this.wash = new Washing();
    }
    
    @EventHandler
    public void on(final PlayerInteractEvent e) {
        final Action a = e.getAction();
        final Player p = e.getPlayer();
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (a == Action.RIGHT_CLICK_BLOCK) {
            final Block b = e.getClickedBlock();
            if (b != null && b.getType() == Material.IRON_BLOCK && this.wash.isWasher(p)) {
                final Block top = b.getRelative(BlockFace.UP);
                if (top.getType().toString().endsWith("_CARPET")) {
                    for (final Entity ent : p.getNearbyEntities(3.0, 2.0, 3.0)) {
                        if (ent.getType() == EntityType.ITEM_FRAME) {
                            if (top.getType() == Material.LIME_CARPET) {
                                if (p.getInventory().getItemInMainHand() == null || !p.getInventory().getItemInMainHand().hasItemMeta() || !p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Dirty")) {
                                    continue;
                                }
                                final ItemFrame frame = (ItemFrame)ent;
                                final BlockFace face = frame.getAttachedFace();
                                final Block attached = frame.getLocation().getBlock().getRelative(face);
                                if (!attached.getLocation().equals((Object)b.getLocation())) {
                                    continue;
                                }
                                this.updaterItemFrame(frame);
                                this.updator(p, b);
                                final ItemStack is = p.getInventory().getItemInMainHand();
                                final ItemStack isSave = p.getInventory().getItemInMainHand();
                                is.setAmount(is.getAmount() - 1);
                                p.getInventory().setItemInMainHand(is);
                                isSave.setAmount(1);
                                Washing.addWashingMachine(p, b, isSave);
                                Washing.setWashingMachineTime(p, b, 21);
                                Chat.info(p, "Pra\u010dka za\u010dala pra\u0165! Po\u010dkaj p\u00e1r sekund!");
                            }
                            else if (top.getType() == Material.ORANGE_CARPET) {
                                Chat.info(p, "§cDURABILITA = Oran\u017eov\u00e1 !");
                                if (Washing.getWashingMachine().get(b) == null) {
                                    return;
                                }
                                final ItemStack is2 = Washing.getWashingMachine().get(b).getItem();
                                if (is2 == null) {
                                    return;
                                }
                                if (p.getName().equals(Washing.getWashingMachine().get(b).getPlayer().getName())) {
                                    Chat.print("Sp\u00fa\u0161tam premenu hr\u00e1\u010d vlo\u017eil pr\u00e1dlo !");
                                    final String name = is2.getItemMeta().getDisplayName().replaceFirst("Dirty", "Clean");
                                    final ItemStack rebuildIS = new ItemBuilder(is2).setName(name).setLeatherArmorColor(Color.fromRGB(242, 242, 242)).toItemStack();
                                    Chat.print("MENO: " + name);
                                    p.getInventory().addItem(new ItemStack[] { rebuildIS });
                                    top.setType(Material.LIME_CARPET);
                                    Washing.removeWashingMachine(b);
                                }
                                else {
                                    Chat.info(p, "Toto pr\u00e1dlo si tam nevlo\u017eil ty!");
                                }
                            }
                            else {
                                if (top.getType() != Material.ORANGE_CARPET) {
                                    continue;
                                }
                                Chat.info(p, "Pra\u010dka e\u0161te nedoprala! Po\u010dkaj si e\u0161te: " + Washing.getWashingMachineTime(p, b) + " sekund.");
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void updator(final Player p, final Block b) {
        new BukkitRunnable() {
            Block carpet = b.getRelative(BlockFace.UP);
            int time = 10;
            
            public void run() {
                if (this.time >= 1) {
                    this.carpet.setType(Material.RED_CARPET);
                }
                else if (this.time <= 0) {
                    this.carpet.setType(Material.ORANGE_CARPET);
                }
                Washing.setWashingMachineTime(p, b, this.time);
                if (this.time <= 0) {
                    WashingMachine.this.washingMachine.cancel();
                    this.cancel();
                }
                --this.time;
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 20L);
    }
    
    public void updaterItemFrame(final ItemFrame f) {
        this.washingMachine = new BukkitRunnable() {
            int i = 0;
            
            public void run() {
                if (f != null) {
                    if (WashingMachine.this.washingMachine.isCancelled()) {
                        this.cancel();
                    }
                    f.setRotation(WashingMachine.this.getRotation(this.i));
                    f.setItem(new ItemStack(Material.ENDER_PEARL, 1));
                    ++this.i;
                    if (this.i >= 6) {
                        this.i = 0;
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 20L);
    }
    
    public Rotation getRotation(final int i) {
        Chat.print("I: " + i);
        switch (i) {
            case 0: {
                return Rotation.CLOCKWISE;
            }
            case 1: {
                return Rotation.CLOCKWISE_135;
            }
            case 2: {
                return Rotation.CLOCKWISE_45;
            }
            case 3: {
                return Rotation.COUNTER_CLOCKWISE_45;
            }
            case 4: {
                return Rotation.COUNTER_CLOCKWISE;
            }
            case 5: {
                return Rotation.FLIPPED_45;
            }
            case 6: {
                return Rotation.FLIPPED;
            }
            default: {
                return Rotation.NONE;
            }
        }
    }
}
