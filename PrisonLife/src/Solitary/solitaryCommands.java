// 
// Decompiled by Procyon v0.5.36
// 

package Solitary;

import org.bukkit.Location;
import Utils.Functions;
import org.bukkit.Bukkit;
import Main.Chat;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class solitaryCommands implements CommandExecutor
{
    SolitaryControl solitary;
    
    public solitaryCommands() {
        this.solitary = new SolitaryControl();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("solitary")) {
                if (args.length <= 0) {
                    Chat.info(p, "Usage: §c/solitary setup spawn <ID>");
                    Chat.info(p, "Usage: §c/solitary add <player> <time> <reason>");
                    return true;
                }
                if (args[0].equalsIgnoreCase("add") && args.length >= 4) {
                    long time = 0L;
                    final String sTime = args[2];
                    if (sTime.endsWith("s")) {
                        time = Long.parseLong(this.getTimeLetter(sTime));
                    }
                    if (sTime.endsWith("m")) {
                        time = Long.parseLong(this.getTimeLetter(sTime)) * 60L;
                    }
                    if (sTime.endsWith("h")) {
                        time = Long.parseLong(this.getTimeLetter(sTime)) * 60L * 60L;
                    }
                    final Player target = Bukkit.getServer().getPlayer(args[1]);
                    if (target == null) {
                        Chat.send(p, "notConnected", new String[0]);
                        return false;
                    }
                    final String reason = Functions.stringBuilder(3, args);
                    this.solitary.addPlayer(this.solitary.firstEmpty(), target, time, reason);
                }
                if (args[0].equalsIgnoreCase("setup") && args[1].equalsIgnoreCase("spawn")) {
                    if (args.length >= 3) {
                        if (!Functions.isNum(args[2])) {
                            Chat.send(p, "noNum", new String[0]);
                            return false;
                        }
                        final int solitary = Integer.parseInt(args[2]);
                        final Location loc = p.getLocation();
                        solitaryCache.setD(solitary, loc.getX(), solitaryCache.pos_X);
                        solitaryCache.setD(solitary, loc.getY(), solitaryCache.pos_Y);
                        solitaryCache.setD(solitary, loc.getZ(), solitaryCache.pos_Z);
                        solitaryCache.setI(solitary, Functions.getYaw(loc.getYaw()), solitaryCache.yaw);
                        solitaryCache.save(solitary);
                        Chat.info(p, "Solitary positions have been saved!");
                    }
                    return true;
                }
            }
        }
        else {
            Chat.print("Error - This commands can be called only ingame !");
        }
        return false;
    }
    
    public String getTimeLetter(final String time) {
        String builder = "";
        for (int i = 0; i < time.length(); ++i) {
            final char a = time.charAt(i);
            if (!Character.isDigit(a)) {
                break;
            }
            builder = String.valueOf(builder) + a;
        }
        return builder;
    }
}
