// 
// Decompiled by Procyon v0.5.36
// 

package Gangs;

import java.util.Iterator;
import Gangs.Ranks.GangRanks;
import java.util.HashMap;
import java.util.List;
import Utils.Functions;
import Main.PrisonType;
import Player.Data.PlayerDataHandler;
import org.bukkit.Bukkit;
import Main.Chat;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class GangCommands implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("gang") && sender instanceof Player) {
            final Player p = (Player)sender;
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("accept")) {
                    if (GangManager.playerIsInGang(p)) {
                        if (GangManager.playerOwnGang(p)) {
                            if (GangManager.hasInvite(p)) {
                                final String gangName = GangManager.getGangInvites().get(p);
                                final Gang gang = GangManager.getGangFromName(gangName);
                                if (gang == null) {
                                    Chat.info(p, "Tento gang neexistuje, pros\u00edm nahl\u00e1ste to administr\u00e1torovy!");
                                    return true;
                                }
                                Chat.sendGangMessage(GangManager.getGangFromName(gangName), "Hr\u00e1\u010d " + p.getName() + " sa stal nov\u00fdm \u010dlenom n\u00e1\u0161ho gangu!");
                                GangManager.getGangInvites().remove(p);
                                gang.getPlayers().add(p.getName());
                                return true;
                            }
                        }
                        else {
                            Chat.info(p, "Ty si vlastn\u00edkom gangu!");
                        }
                    }
                    else {
                        Chat.info(p, "Nem\u00e1\u0161 \u010do potvrdzova\u0165, u\u017e si v gangu!");
                    }
                }
                if (GangManager.hasInvite(p)) {
                    final String gangName = GangManager.getGangInvites().get(p);
                    final Gang gang = GangManager.getGangFromName(gangName);
                    if (gang == null) {
                        Chat.info(p, "Tento gang neexistuje, pros\u00edm nahl\u00e1ste to administr\u00e1torovy!");
                        return true;
                    }
                    Chat.sendGangMessage(GangManager.getGangFromName(gangName), "Hr\u00e1\u010d " + p.getName() + " sa stal nov\u00fdm \u010dlenom n\u00e1\u0161ho gangu!");
                    GangManager.getGangInvites().remove(p);
                    gang.getPlayers().add(p.getName());
                    return true;
                }
            }
            if (args.length >= 2) {
                if (args[0].equalsIgnoreCase("invite")) {
                    if (!GangManager.playerOwnGang(p)) {
                        Chat.info(p, "Nevlastn\u00ed\u0161 \u017eiadn\u00fd gang alebo nem\u00e1\u0161 dostato\u010dn\u00e9 opravnenie na pozvanie!");
                        return true;
                    }
                    final Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        Chat.info(p, "Hr\u00e1\u010d moment\u00e1lne nieje pripojen\u00fd!");
                        return true;
                    }
                    if (target.getName().equalsIgnoreCase(target.getName())) {
                        Chat.info(p, "Nem\u00f4\u017ee\u0161 pozva\u0165 sam\u00e9ho seba!");
                        return true;
                    }
                    if (GangManager.playerOwnGang(p)) {
                        Chat.info(p, "Nem\u00f4\u017ee\u0161 pozva\u0165 majitela gangu!");
                        return true;
                    }
                    final PlayerDataHandler pdh = new PlayerDataHandler();
                    if (pdh.getData(target).getType() != PrisonType.PRISONER) {
                        Chat.info(p, "Hr\u00e1\u010d moment\u00e1lne nehr\u00e1 za v\u00e4z\u0148a!");
                        return true;
                    }
                    if (GangManager.playerIsInGang(target)) {
                        Chat.info(p, "Hr\u00e1\u010d u\u017e je v nejakom Gangu a nem\u00f4\u017ee\u0161 ho pozva\u0165 do in\u00e9ho!");
                        return true;
                    }
                    if (!GangManager.hasInvite(target)) {
                        final String gangName2 = GangDataManager.getgData().get(GangManager.getGangNumber(p)).getName();
                        GangManager.getGangInvites().put(target, gangName2);
                        Chat.info(target, "Bol si pozvan\u00fd do gangu " + gangName2);
                        Chat.info(target, "Pre prijatie pozvanky nap\u00ed\u0161 /gang invite accept");
                        Chat.info(target, "Pre zamietnutie pozvanky nap\u00ed\u0161 /gang invite deny");
                        Chat.info(p, "Odoslal si pozv\u00e1nku hr\u00e1\u010dovy " + target.getName() + " aby sa pripojil do gangu!");
                        return true;
                    }
                    Chat.info(p, "Hr\u00e1\u010da u\u017e niekto pozval do gangu!");
                }
            }
            else {
                Chat.info(p, "§cUse: /gang invite <player>");
            }
        }
        final GangDataManager gdm = new GangDataManager();
        final Functions func = new Functions();
        if (!cmd.getName().equalsIgnoreCase("gangs")) {
            if (cmd.getName().equalsIgnoreCase("gadmin") && sender instanceof Player) {
                final Player p2 = (Player)sender;
                if (!p2.hasPermission("prison.admin.gang") || !p2.isOp()) {
                    return true;
                }
                if (args.length <= 0) {
                    this.sendHelp(p2);
                    return true;
                }
                if (args[0].equalsIgnoreCase("restart")) {
                    GangManager.removeHolograms();
                    Chat.info(p2, "V\u0161etk\u00fd hologramy boly vymazan\u00e9!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    gdm.config.reload();
                    Chat.info(p2, "Config bol obnoven\u00fd!");
                    return true;
                }
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("create")) {
                        if (args[0].length() >= 3) {
                            if (!GangDataManager.gangExist(args[1])) {
                                final Gang data = new Gang(args[1]);
                                data.setFounder("NONE");
                                data.setDeaths(0);
                                data.setKills(0);
                                data.setRespects(0);
                                data.setLoc(p2.getLocation());
                                data.setTag(args[1].substring(0, 3));
                                data.setPlayers(null);
                                data.setPlayersRanks(null);
                                GangDataManager.getgData().add(data);
                                final GangManager gm = new GangManager();
                                gm.createGangHologram(data);
                                gdm.saveData();
                                Chat.info(p2, "Gang bol \u00faspe\u0161ne vytvoren\u00fd s n\u00e1zvom " + args[1]);
                                return true;
                            }
                            Chat.info(p2, "Gang s podobn\u00fdm n\u00e1zvom u\u017e existuje!");
                        }
                        else {
                            Chat.info(p2, "N\u00e1zov mus\u00ed ma\u0165 minim\u00e1lne 3 znaky!");
                        }
                    }
                    else if (!args[0].equalsIgnoreCase("settings") && args[0].equalsIgnoreCase("remove") && GangDataManager.gangExist(args[1])) {
                        for (int i = 0; i < GangDataManager.getgData().size(); ++i) {
                            if (GangDataManager.getgData().get(i).getName().equalsIgnoreCase(args[1])) {
                                GangDataManager.getgData().remove(i);
                                Chat.info(p2, "Gang bol \u00faspe\u0161ne vymazan\u00fd! (N\u00e1zov: " + args[1] + ")");
                                gdm.getGangConfig().set("Gangs." + args[1], (Object)null);
                                gdm.saveGangConfig();
                                GangManager.removeHolograms();
                                final GangManager gm = new GangManager();
                                gm.spawnGangHolograms();
                                return true;
                            }
                        }
                    }
                    if (args.length >= 3) {
                        args[0].equalsIgnoreCase("invite");
                    }
                }
            }
            return false;
        }
        if (GangDataManager.getgData() == null || GangDataManager.getgData().isEmpty()) {
            Chat.infoSender(sender, "Moment\u00e1lne nen\u00ed vytvoren\u00fd \u017eiadny gang!");
            return true;
        }
        String gangy = "§eGangy:§f ";
        for (final Gang s : GangDataManager.getgData()) {
            gangy = String.valueOf(gangy) + s.getName() + ",";
        }
        Chat.infoSender(sender, Functions.substring(gangy.length() - 1, 0, gangy));
        return true;
    }
    
    public void sendHelp(final Player p) {
        Chat.info(p, "§cHelp commands for admins");
        Chat.info(p, "§cUse: /gadmin create <gangName> - Vytvor\u00ed gang");
        Chat.info(p, "§cUse: /gadmin remove <gangName> - Zma\u017ee gang");
        Chat.info(p, "§cUse: /gadmin invite <gangName> <player> - pozve hr\u00e1\u010da do gangu");
        Chat.info(p, "§cUse: /gadmin kick <gangName> <player>  - vyhod\u00ed hr\u00e1\u010da z gangu");
        Chat.info(p, "§cUse: /gadmin settings <gangName> - otvor\u00ed nastavenia gangu");
        Chat.info(p, "§cUse: /gadmin restart - Obnov\u00ed v\u0161etk\u00fd gangy");
        Chat.info(p, "§cUse: /gadmin reload - Obnov\u00ed config gangov");
    }
}
