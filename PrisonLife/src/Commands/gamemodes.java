// 
// Decompiled by Procyon v0.5.36
// 

package Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Chat;

public class gamemodes implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("gms")) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.SURVIVAL);
                    Chat.send(p, "admin.gamemodeChange", "Survival");
                }
                else {
                    final Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setGameMode(GameMode.SURVIVAL);
                        Chat.send(p, "admin.gamemodeChange-player", target.getName(), "Survival");
                        Chat.send(target, "admin.gamemodeChange-target", p.getName(), "Survival");
                    }
                }
            }
            if (cmd.getName().equalsIgnoreCase("gmc")) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.CREATIVE);
                    Chat.send(p, "admin.gamemodeChange", "Creative");
                }
                else {
                    final Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setGameMode(GameMode.CREATIVE);
                        Chat.send(p, "admin.gamemodeChange-player", target.getName(), "Creative");
                        Chat.send(target, "admin.gamemodeChange-target", p.getName(), "Creative");
                    }
                }
            }
            if (cmd.getName().equalsIgnoreCase("gmsp")) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.SPECTATOR);
                    Chat.send(p, "admin.gamemodeChange", "Spectator");
                }
                else {
                    final Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setGameMode(GameMode.SPECTATOR);
                        Chat.send(p, "admin.gamemodeChange-player", target.getName(), "Spectator");
                        Chat.send(target, "admin.gamemodeChange-target", p.getName(), "Spectator");
                    }
                }
            }
            if (cmd.getName().equalsIgnoreCase("gma")) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.ADVENTURE);
                    Chat.send(p, "admin.gamemodeChange", "Adventure");
                }
                else {
                    final Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setGameMode(GameMode.ADVENTURE);
                        Chat.send(p, "admin.gamemodeChange-player", target.getName(), "Adventure");
                        Chat.send(target, "admin.gamemodeChange-target", p.getName(), "Adventure");
                    }
                }
            }
        }
        else {
            Chat.print("§cThis command u can only use in-game !");
        }
        return false;
    }
    
    public String getGM(final Player p) {
        String gm = null;
        switch (p.getGameMode()) {
            case SURVIVAL: {
                gm = "Survival";
                break;
            }
            case CREATIVE: {
                gm = "Creative";
                break;
            }
            case SPECTATOR: {
                gm = "Spectator";
                break;
            }
            case ADVENTURE: {
                gm = "Adventure";
                break;
            }
            default: {
                gm = "";
                break;
            }
        }
        return gm;
    }
}
