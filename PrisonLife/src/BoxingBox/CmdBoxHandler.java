// 
// Decompiled by Procyon v0.5.36
// 

package BoxingBox;

import org.bukkit.Location;
import Main.PlayerFreezing;
import org.bukkit.Bukkit;
import Main.Main;
import Main.Chat;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CmdBoxHandler implements CommandExecutor
{
    BoxHandler bh;
    
    public CmdBoxHandler() {
        this.bh = new BoxHandler();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("box")) {
            final Player p = (Player)sender;
            if (args.length == 0) {
                Chat.info(p, "/box setup <boxID> <1 / 2>");
                Chat.info(p, "/box reset <boxID> <1 / 2>");
                Chat.info(p, "/box left");
                return true;
            }
            if (args[0].equalsIgnoreCase("setup")) {
                final Location loc = p.getLocation();
                if (!args[2].equalsIgnoreCase("1") && !args[2].equalsIgnoreCase("2")) {
                    return false;
                }
                final int id = Integer.parseInt(args[2]);
                if (id == 1) {
                    BoxData.getBox(Integer.parseInt(args[1])).setPort_1(loc);
                }
                else if (id == 2) {
                    BoxData.getBox(Integer.parseInt(args[1])).setPort_2(loc);
                }
                BoxData.saveConfigurationOfBox(id);
            }
            else if (args[0].equals("reload")) {
                BoxData.saveConfiguration();
                Main.getBoxes().reload();
                BoxData.loadConfiguration();
                for (int i = 0; i < BoxData.boxes.size() - 1; ++i) {
                    JoinBoxListener.updateSign(i);
                }
                Chat.info(p, "\u00daspe\u0161ne si obnovil config ");
            }
            else if (args[0].equalsIgnoreCase("left")) {
                final int box = BoxWin.getPlayerBox(p);
                if (box != -1) {
                    final String name1 = BoxData.getBox(box).getName1();
                    final String name2 = BoxData.getBox(box).getName2();
                    final Location loc2 = BoxData.getBox(box).getSignLoc();
                    if (p.getName().equalsIgnoreCase(name1)) {
                        BoxData.getBox(box).setName1("- NONE -");
                        Bukkit.getServer().getPlayer(name1).teleport(loc2);
                    }
                    if (p.getName().equalsIgnoreCase(name2)) {
                        BoxData.getBox(box).setName2("- NONE -");
                        Bukkit.getServer().getPlayer(name2).teleport(loc2);
                    }
                    JoinBoxListener.updateSign(box);
                    PlayerFreezing.removePlayer(p);
                }
                else {
                    Chat.send(p, "box.leave.unsucces", new String[0]);
                }
            }
        }
        return false;
    }
}
