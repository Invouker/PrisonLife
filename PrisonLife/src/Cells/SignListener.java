// 
// Decompiled by Procyon v0.5.36
// 

package Cells;

import fr.minuskube.inv.ClickableItem;
import Items.ItemBuilder;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.inventory.InventoryHolder;
import fr.minuskube.inv.SmartInvsPlugin;
import org.bukkit.inventory.Inventory;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.block.BlockState;
import org.bukkit.block.Block;
import org.bukkit.World;
import org.bukkit.block.Sign;
import Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import Utils.Functions;
import org.bukkit.event.block.SignChangeEvent;
import Main.Chat;
import Player.Data.PlayerDataHandler;
import fr.minuskube.inv.opener.InventoryOpener;
import org.bukkit.event.Listener;

public class SignListener implements Listener, InventoryOpener
{
    PlayerDataHandler pdh;
    CellDataProvider cd;
    Chat c;
    
    public SignListener() {
        this.pdh = new PlayerDataHandler();
        this.cd = new CellDataProvider();
        this.c = new Chat();
    }
    
    @EventHandler
    public void signChange(final SignChangeEvent e) {
        final Player p = e.getPlayer();
        if (p.hasPermission("prison.setup.cell.sign")) {
            if (e.getLine(0).equalsIgnoreCase("%cell%")) {
                final String strCell = e.getLine(1);
                if (Functions.isNum(strCell)) {
                    final Location loc = e.getBlock().getLocation();
                    final int x = loc.getBlockX();
                    final int y = loc.getBlockY();
                    final int z = loc.getBlockZ();
                    final int cell = Integer.parseInt(strCell);
                    this.cd.setICell(cell, "Sign.X", x);
                    this.cd.setICell(cell, "Sign.Y", y);
                    this.cd.setICell(cell, "Sign.Z", z);
                    this.cd.setSCell(cell, "Sign.World", loc.getWorld().getName());
                    this.cd.setSCell(cell, "Sign.Owner", "None");
                    this.cd.setBCell(cell, "isOwned", false);
                    e.setLine(0, "§8Cell §l" + cell + " §8Sector §l" + this.getSector(cell));
                    e.setLine(1, "");
                    e.setLine(2, "Nobody");
                    e.setLine(3, "");
                }
                else {
                    Chat.send(p, "prison.cell.no-create", new String[0]);
                }
            }
        }
        else {
            Chat.noPerm(p);
        }
    }
    
    public void updateCellSign(final int cell) {
        final int x = this.cd.getICell(cell, "Sign.X");
        final int y = this.cd.getICell(cell, "Sign.Y");
        final int z = this.cd.getICell(cell, "Sign.Z");
        final World w = Bukkit.getServer().getWorld(Main.mainWorld);
        final Block block = w.getBlockAt(x, y, z);
        final BlockState state = block.getState();
        if (!(state instanceof Sign)) {
            return;
        }
        final Sign s = (Sign)state;
        if (this.cd.getBCell(cell, "isOwned")) {
            final String owner = this.cd.getSCell(cell, "Sign.Owner");
            final Player p = Bukkit.getServer().getPlayer(owner);
            final int prisoner = this.pdh.getData(p).getPrisoner();
            s.setLine(0, "§8Cell §l" + cell + " §8Sector §l" + this.getSector(cell));
            s.setLine(1, "§8Prisoner §l" + prisoner);
            s.setLine(2, "§8" + owner);
            s.setLine(3, "§8" + Functions.timestampToDate(p.getFirstPlayed()));
            s.update();
        }
        else {
            s.setLine(0, "§8Cell §l" + cell + " §8Sector §l" + this.getSector(cell));
            s.setLine(1, "");
            s.setLine(2, "Nobody");
            s.setLine(3, "");
            s.update();
        }
    }
    
    public Inventory open(final SmartInventory inv, final Player player) {
        final InventoryManager manager = SmartInvsPlugin.manager();
        final Inventory handle = Bukkit.createInventory((InventoryHolder)player, inv.getRows() * inv.getColumns(), inv.getTitle());
        this.fill(handle, (InventoryContents)manager.getContents(player).get());
        player.openInventory(handle);
        return handle;
    }
    
    public boolean supports(final InventoryType type) {
        return type == InventoryType.CHEST;
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        final Action a = e.getAction();
        if (a.equals((Object)Action.RIGHT_CLICK_BLOCK)) {
            final Block b = e.getClickedBlock();
            if (b.getType() == Material.OAK_SIGN || b.getType() == Material.OAK_WALL_SIGN) {
                final int cell = this.getCell(b.getX(), b.getY(), b.getZ());
                if (cell == -1) {
                    return;
                }
                final String owner = this.cd.getSCell(cell, "Sign.Owner");
                if (owner.equalsIgnoreCase("None")) {
                    Chat.send(e.getPlayer(), "prison.cell.player-isnot-online", new String[0]);
                    e.setCancelled(true);
                    return;
                }
                SmartInventory inv = SmartInventory.builder().id("playerCellInfo").provider((InventoryProvider)new InvProvider()).size(1, 9).title("§7Player Info " + owner).closeable(true).build();
                inv.open(e.getPlayer());
            }
        }
    }
    
    public int getCell(final int x, final int y, final int z) {
        for (int maxPlayer = Bukkit.getServer().getMaxPlayers(), i = 1; i < maxPlayer; ++i) {
            final int posX = this.cd.getICell(i, "Sign.X");
            final int posY = this.cd.getICell(i, "Sign.Y");
            final int posZ = this.cd.getICell(i, "Sign.Z");
            if (posX == x && posY == y && posZ == z) {
                return i;
            }
        }
        return -1;
    }
    
    public String getSector(final int c) {
        String sector = "null";
        if (c >= 1 && c <= 15) {
            sector = "A";
        }
        if (c >= 16 && c <= 31) {
            sector = "B";
        }
        else if (c >= 32 && c <= 47) {
            sector = "C";
        }
        else if (c >= 48 && c <= 64) {
            sector = "D";
        }
        return sector;
    }
    
    private class InvProvider implements InventoryProvider
    {
        public void init(final Player p, final InventoryContents contents) {
            contents.set(1, 4, ClickableItem.empty(new ItemBuilder(Material.ARMOR_STAND).setName("\u0160tatistiky").toItemStack()));
            contents.set(1, 5, ClickableItem.empty(new ItemBuilder(Material.LILY_PAD).setName("BODY a sra\u010dky...").toItemStack()));
        }
        
        public void update(final Player p, final InventoryContents contents) {
        }
    }
}
