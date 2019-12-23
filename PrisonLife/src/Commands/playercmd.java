// 
// Decompiled by Procyon v0.5.36
// 

package Commands;

import org.bukkit.World;
import Items.LoadItem;
import Cells.CellAsigner;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import ScoreBoard.Prisoner;
import org.bukkit.inventory.Inventory;
import Items.SaveItem;
import org.bukkit.plugin.java.JavaPlugin;
import Main.ConfigListener;
import BoxingBox.JoinBoxListener;
import BoxingBox.BoxData;
import Main.Chat;
import Solitary.SolitaryControl;
import Main.PrisonType;
import Needs.NeedsUpdater;
import Main.Main;
import org.bukkit.entity.Player;
import Player.Data.PlayerDataHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class playercmd implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final PlayerDataHandler pdh = new PlayerDataHandler();
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("news")) {
            p.sendMessage("NOVINKY, TO DO...");
        }
        if (!cmd.getName().equalsIgnoreCase("changeduty")) {
            if (cmd.getName().equalsIgnoreCase("credit") || cmd.getName().equalsIgnoreCase("credits")) {
                final String version = Main.getInstance().getDescription().getVersion();
                p.sendMessage("");
                p.sendMessage("§e§m]----------§r §f§lPRISON LIFE §e§m----------[");
                p.sendMessage("");
                p.sendMessage("§7Plugin created by §f§lXpresS §7( Owner of this server )");
                p.sendMessage("§7Version of this plugin §f§l" + version);
                p.sendMessage("");
                p.sendMessage("§e§m]----------§r §f§lPRISON LIFE §e§m----------[");
            }
            if (cmd.getName().equalsIgnoreCase("help") || cmd.getName().equalsIgnoreCase("support")) {
                if (args.length == 0) {
                    p.sendMessage("----------HELP PAGE ------------");
                    p.sendMessage("");
                    p.sendMessage("");
                    p.sendMessage("");
                    p.sendMessage("§6Page (§71/10§6), To next page §7/" + cmd.getName() + " 2");
                }
                else if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("2")) {
                        p.sendMessage("----------HELP PAGE ------------");
                        p.sendMessage("");
                        p.sendMessage("");
                        p.sendMessage("");
                        p.sendMessage("§6Page (§7" + args[0] + "/10§6), To next page §7/" + cmd.getName() + " " + (Integer.parseInt(args[0]) + 1));
                    }
                    if (args[0].equalsIgnoreCase("3")) {
                        p.sendMessage("----------HELP PAGE ------------");
                        p.sendMessage("");
                        p.sendMessage("");
                        p.sendMessage("");
                        p.sendMessage("§6Page (§7" + args[0] + "/10§6), To next page §7/" + cmd.getName() + " " + (Integer.parseInt(args[0]) + 1));
                    }
                    if (args[0].equalsIgnoreCase("4")) {
                        p.sendMessage("----------HELP PAGE ------------");
                        p.sendMessage("");
                        p.sendMessage("");
                        p.sendMessage("");
                        p.sendMessage("§6Page (§7" + args[0] + "/10§6), To next page §7/" + cmd.getName() + " " + (Integer.parseInt(args[0]) + 1));
                    }
                }
            }
            return false;
        }
        final NeedsUpdater nu = new NeedsUpdater();
        if (pdh.getData(p).getType() == PrisonType.LOBBY) {
            return true;
        }
        if (pdh.getData(p).getType() == PrisonType.PRISONER) {
            final SolitaryControl scon = new SolitaryControl();
            final int solitary = scon.getPlayerSolitary(p);
            if (solitary > 0) {
                Chat.print("Nem\u00f4\u017ee je na samotke!");
                return true;
            }
            for (int i = 0; i < BoxData.boxes.size() - 1; ++i) {
                if (BoxData.getBox(i).getName1().equalsIgnoreCase(p.getName())) {
                    BoxData.getBox(i).setName1("- NONE -");
                    JoinBoxListener.updateSign(i);
                }
                else if (BoxData.getBox(i).getName2().equalsIgnoreCase(p.getName())) {
                    BoxData.getBox(i).setName2("- NONE -");
                    JoinBoxListener.updateSign(i);
                }
            }
            final SaveItem si = new SaveItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), pdh.getData(p).getType().getName(), (Inventory)p.getInventory());
            si.save();
            si.clear();
            nu.removePlayer(p);
            Prisoner.removePlayer(p);
            pdh.getData(p).setType(PrisonType.LOBBY);
            final World w = Bukkit.getServer().getWorld(Main.mainWorld);
            final Location loc = new Location(w, -475.5, 93.0, 324.5);
            loc.setYaw(180.0f);
            p.teleport(loc);
            CellAsigner.unloadCellByPlayer(p);
            p.getInventory().clear();
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        if (pdh.getData(p).getType() == PrisonType.WARDEN) {
            final SaveItem si2 = new SaveItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), pdh.getData(p).getType().getName(), (Inventory)p.getInventory());
            si2.save();
            si2.clear();
            final World w2 = Bukkit.getServer().getWorld(Main.mainWorld);
            final Location loc2 = new Location(w2, -475.5, 93.0, 324.5);
            loc2.setYaw(180.0f);
            p.teleport(loc2);
            p.getInventory().clear();
            pdh.getData(p).setType(PrisonType.LOBBY);
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        if (pdh.getData(p).getType() == PrisonType.ADMIN) {
            final SaveItem si2 = new SaveItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), pdh.getData(p).getType().getName(), (Inventory)p.getInventory());
            si2.save();
            si2.clear();
            final World w2 = Bukkit.getServer().getWorld(Main.mainWorld);
            final Location loc2 = new Location(w2, -475.5, 93.0, 324.5);
            loc2.setYaw(180.0f);
            p.teleport(loc2);
            pdh.getData(p).setType(PrisonType.LOBBY);
            p.getInventory().clear();
        }
        if (pdh.getData(p).getType() != PrisonType.LOBBY) {
            Chat.info(p, "LOADING ITEMS!");
            final LoadItem li = new LoadItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), pdh.getData(p).getType().getName());
            p.getInventory().setContents(li.getItemsPole());
        }
        return true;
    }
}
