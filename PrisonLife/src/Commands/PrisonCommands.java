// 
// Decompiled by Procyon v0.5.36
// 

package Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Bed;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import Basketball.EntitiyBall;
import BoxingBox.BoxData;
import BoxingBox.JoinBoxListener;
import Cells.CellAsigner;
import Cells.CellDataProvider;
import DayLightCycle.DayLight;
import DayLightCycle.DoorControler;
import Items.ITEMS;
import Items.ItemBuilder;
import Items.ItemRarity;
import Items.LoadItem;
import Items.SaveItem;
import Main.Chat;
import Main.ConfigListener;
import Main.Main;
import Main.Maintance;
import Main.PrisonType;
import Needs.NeedsUpdater;
import Player.Data.PlayerDataHandler;
import Player.Premium.PremiumType;
import ScoreBoard.Prisoner;
import Utils.Functions;
import Version.ActionBar;

public class PrisonCommands implements CommandExecutor
{
    PlayerDataHandler pdh;
    private List<MerchantRecipe> recipes;
    
    public PrisonCommands() {
        this.pdh = new PlayerDataHandler();
        this.recipes = new ArrayList<MerchantRecipe>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("prison")) {
            final Player p = (Player)sender;
            if (args.length < 1) {
                p.sendMessage("-----Setup Help-----");
                p.sendMessage("§c/" + cmd.getName() + " setup spawnCell <Cell ID>");
                p.sendMessage("§c/" + cmd.getName() + " setup setWc <Cell ID>");
                p.sendMessage("§c/" + cmd.getName() + " setup setChest <Cell ID>");
                p.sendMessage("§c/" + cmd.getName() + " setup setArea <Cell ID>");
                p.sendMessage("§c/" + cmd.getName() + " setup setBed <Cell ID>");
                p.sendMessage("§c/" + cmd.getName() + " setup setdoor <Cell ID>");
                p.sendMessage("2");
            }
            else {
                final CellDataProvider cd = new CellDataProvider();
                if (args[0].equalsIgnoreCase("setup")) {
                    if (args.length < 3) {
                        Chat.info(p, "/prison setup <args>");
                        return true;
                    }
                    if (p.hasPermission("prison.admin.setup")) {
                        if (args[1].equalsIgnoreCase("spawnCell") && Functions.isNum(args[2])) {
                            final double x = p.getLocation().getX();
                            final double y = p.getLocation().getY();
                            final double z = p.getLocation().getZ();
                            final float yaw = p.getLocation().getYaw();
                            final int cell = Integer.parseInt(args[2]);
                            cd.setDCell(cell, "Spawn.X", x);
                            cd.setDCell(cell, "Spawn.Y", y);
                            cd.setDCell(cell, "Spawn.Z", z);
                            cd.setDCell(cell, "Spawn.Pitch", 0.0);
                            cd.setDCell(cell, "Spawn.Yaw", yaw);
                            Chat.info(p, "X " + x + " Y " + y + " Z " + z);
                        }
                        if (args[1].equalsIgnoreCase("setwc")) {
                            final Block block = p.getTargetBlock((Set)null, 5);
                            if (block.getType() == null || block.getType() != Material.HOPPER) {
                                Chat.send(p, "prison.setup.wc", new String[0]);
                                return false;
                            }
                            final Location loc = block.getLocation();
                            final int cell2 = Integer.parseInt(args[2]);
                            final int x2 = loc.getBlockX();
                            final int y2 = loc.getBlockY();
                            final int z2 = loc.getBlockZ();
                            cd.setICell(cell2, "Wc.X", x2);
                            cd.setICell(cell2, "Wc.Y", y2);
                            cd.setICell(cell2, "Wc.Z", z2);
                            cd.setSCell(cell2, "Wc.World", loc.getWorld().getName());
                            final Location asLoc = block.getLocation();
                            asLoc.setX(asLoc.getX() + 0.5);
                            asLoc.setY(asLoc.getY() - 0.75);
                            asLoc.setZ(asLoc.getZ() + 0.5);
                            final ArmorStand as = (ArmorStand)Bukkit.getWorld(p.getLocation().getWorld().getName()).spawnEntity(asLoc, EntityType.ARMOR_STAND);
                            as.setGravity(false);
                            as.setInvulnerable(true);
                            as.setVisible(false);
                            Chat.info(p, "X " + x2 + " Y " + y2 + " Z " + z2);
                        }
                        if (args[1].equalsIgnoreCase("setbed")) {
                            final Block block = p.getTargetBlock((Set)null, 5);
                            if (block.getType() == Material.WHITE_BED) {
                                final Bed bed = (Bed)block.getState().getData();
                                int x3 = 0;
                                int y3 = 0;
                                int z3 = 0;
                                String facing = null;
                                final Location loc2 = block.getLocation();
                                final int cell = Integer.parseInt(args[2]);
                                if (!bed.isHeadOfBed()) {
                                    Chat.print("LOC:" + block.getLocation());
                                    x3 = loc2.getBlockX();
                                    y3 = loc2.getBlockY();
                                    z3 = loc2.getBlockZ();
                                    facing = bed.getFacing().toString();
                                }
                                else {
                                    final Block b = block.getRelative(bed.getFacing().getOppositeFace());
                                    Chat.print("BED: " + b.getLocation());
                                    if (b.getType() == Material.WHITE_BED) {
                                        x3 = b.getLocation().getBlockX();
                                        y3 = b.getLocation().getBlockY();
                                        z3 = b.getLocation().getBlockZ();
                                        switch (bed.getFacing()) {
                                            case WEST: {
                                                --x3;
                                            }
                                            case EAST: {
                                                ++x3;
                                            }
                                            case NORTH: {
                                                --z3;
                                            }
                                            case SOUTH: {
                                                ++z3;
                                                break;
                                            }
                                        }
                                        facing = bed.getFacing().toString();
                                    }
                                }
                                cd.setICell(cell, "Bed.X", x3);
                                cd.setICell(cell, "Bed.Y", y3);
                                cd.setICell(cell, "Bed.Z", z3);
                                cd.setSCell(cell, "Bed.Face", facing);
                            }
                            Chat.info(p, "Successfully saved a bed !");
                        }
                        if (args[1].equalsIgnoreCase("setdoor")) {
                            final Block block = p.getTargetBlock((Set)null, 5);
                            if (block.getType() == null && block.getType() != Material.IRON_DOOR) {
                                Chat.send(p, "prison.setup.door.look-at", new String[0]);
                                Chat.info(p, "Block: " + block.getType().toString());
                                return false;
                            }
                            final Location loc = block.getLocation();
                            final int cell2 = Integer.parseInt(args[2]);
                            final int x2 = loc.getBlockX();
                            final int y2 = loc.getBlockY();
                            final int z2 = loc.getBlockZ();
                            cd.setICell(cell2, "Door.X", x2);
                            cd.setICell(cell2, "Door.Y", y2);
                            cd.setICell(cell2, "Door.Z", z2);
                            Chat.info(p, "X " + x2 + " Y " + y2 + " Z " + z2);
                        }
                        if (args[1].equalsIgnoreCase("setChest")) {
                            final Block block = p.getTargetBlock((Set)null, 5);
                            if (block.getType() == null || block.getType() != Material.CHEST) {
                                Chat.send(p, "prison.setup.chest", new String[0]);
                                return false;
                            }
                            final Location loc = block.getLocation();
                            final int cell2 = Integer.parseInt(args[2]);
                            final int x2 = loc.getBlockX();
                            final int y2 = loc.getBlockY();
                            final int z2 = loc.getBlockZ();
                            cd.setICell(cell2, "Chest.X", x2);
                            cd.setICell(cell2, "Chest.Y", y2);
                            cd.setICell(cell2, "Chest.Z", z2);
                            cd.setSCell(cell2, "Chest.World", loc.getWorld().getName());
                            Chat.info(p, "X " + x2 + " Y " + y2 + " Z " + z2);
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("player")) {
                    if (args.length < 3) {
                        Chat.info(p, "/prison player §f<Variable>§7 <Player> <Amount>");
                        Chat.send(p, "prison.player.variable", new String[0]);
                        Chat.info(p, "Variables list: §fwc§7, §fhygiene§7, §fsleep§7, §frespect§7, §fmoney§7, §fcoins§7, §fthirst§7, §fstamina§7, §fstrength§7, §fcrafting§7, §fpoints§7, §fintellect");
                        return false;
                    }
                    if (p.hasPermission("prison.admin.player")) {
                        final String player = args[2];
                        final Player op = Bukkit.getServer().getPlayer(player);
                        final int vault = Integer.parseInt(args[3]);
                        if (!Functions.isNum(args[3])) {
                            return false;
                        }
                        if (args[1].equalsIgnoreCase("wc")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 100) {
                                    this.pdh.getData(op).setWC(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "100");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("hygiene")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 100) {
                                    this.pdh.getData(op).setHygiene(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "100");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("sleep")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 100) {
                                    this.pdh.getData(op).setSleep(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "100");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("respect")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 100) {
                                    this.pdh.getData(op).setRespect(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "100");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("money")) {
                            if (op != null && !player.isEmpty()) {
                                final double amount = Double.parseDouble(args[3]);
                                this.pdh.getData(op).setMoney(vault);
                                Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(amount)).toString(), op.getName());
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("coins")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 100) {
                                    this.pdh.getData(op).setCoins(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "100");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("thirst")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 100) {
                                    this.pdh.getData(op).setThirst(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "100");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("stamina")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 100) {
                                    this.pdh.getData(op).setStamina(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "100");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("strength")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 100) {
                                    this.pdh.getData(op).setStrength(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "100");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("crafting")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 100) {
                                    this.pdh.getData(op).setCraftingSkill(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "100");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("points")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 50000) {
                                    this.pdh.getData(op).setPoints(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "50000");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                        if (args[1].equalsIgnoreCase("intellect")) {
                            if (op != null && !player.isEmpty()) {
                                if (vault >= 0 && vault <= 100) {
                                    this.pdh.getData(op).setIntellect(vault);
                                    Chat.send(p, "prison.player.set-Stats", args[1], new StringBuilder(String.valueOf(vault)).toString(), op.getName());
                                }
                                else {
                                    Chat.send(p, "prison.player.between", "100");
                                }
                            }
                            else {
                                Chat.send(p, "notConnected", new String[0]);
                            }
                        }
                    }
                    else {
                        Chat.noPerm(p);
                    }
                }
                if (args[0].equalsIgnoreCase("whitelist")) {
                    if (p.hasPermission("prison.admin.whitelist")) {
                        if (args.length <= 1) {
                            Chat.info(p, "/prison whitelist <§fvariable§7> <Player>");
                            Chat.info(p, "Variable List: §fadd§7, §fremove§7, §freload§7, §fsave§7, §fon§7, §foff§7, §flist");
                            return false;
                        }
                        if (args[1].equalsIgnoreCase("add")) {
                            Maintance.add(args[2]);
                            Chat.sendAll("prison.whitelist.add", p.getName(), args[2]);
                            Maintance.saveWhiteList();
                        }
                        else if (args[1].equalsIgnoreCase("remove")) {
                            Maintance.remove(args[2]);
                            Chat.sendAll("prison.whitelist.remove", p.getName(), args[2]);
                            Maintance.saveWhiteList();
                        }
                        else if (args[1].equalsIgnoreCase("reload")) {
                            Maintance.saveWhiteList();
                            Maintance.loadWhiteList();
                            Chat.send(p, "prison.whitelist.reload", new String[0]);
                        }
                        else if (args[1].equalsIgnoreCase("save")) {
                            Maintance.saveWhiteList();
                            Chat.send(p, "prison.whitelist.save", new String[0]);
                        }
                        else if (args[1].equalsIgnoreCase("on")) {
                            Maintance.setStatus(true);
                            Chat.send(p, "prison.whitelist.enable", new String[0]);
                        }
                        else if (args[1].equalsIgnoreCase("off")) {
                            Maintance.setStatus(false);
                            Chat.send(p, "prison.whitelist.disable", new String[0]);
                        }
                        else if (args[1].equalsIgnoreCase("list")) {
                            String list = "";
                            for (final String s : Maintance.whitelist) {
                                list = String.valueOf(list) + "§f" + s + "§7, §f";
                            }
                            p.sendMessage("§cWhite-Listed players: §f" + list);
                        }
                    }
                    else {
                        Chat.noPerm(p);
                    }
                }
                if (args[0].equalsIgnoreCase("itemc")) {
                    p.getInventory().addItem(new ItemStack[] { ITEMS.COIN.getItem() });
                    return true;
                }
                if (args[0].equalsIgnoreCase("setTime")) {
                    DayLight.setTime(Long.parseLong(args[1]));
                    Chat.info(p, "Nastavujem \u010das..");
                    return true;
                }
                if (args[0].equalsIgnoreCase("ball")) {
                    final EntitiyBall eb = new EntitiyBall();
                    eb.createBall(-414, 77, 161);
                    Chat.info(p, "Lopta bola vytvoren\u00e1 !");
                    return true;
                }
                if (args[0].equalsIgnoreCase("killballs")) {
                    for (final Entity en : p.getWorld().getEntities()) {
                        if (en.getName().equalsIgnoreCase("ball") && en instanceof Slime) {
                            final Slime s2 = (Slime)en;
                            s2.setHealth(0.0);
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("rare")) {
                    final ItemStack is = new ItemBuilder(Material.SHEARS, 1).setName("&bBoxovacie Rukavice").setDamage(1.6f, 2.6f).addFlag(ItemFlag.HIDE_ENCHANTS).setUnbreakable(true).setRare(ItemRarity.RARE).setLoreLine("Nie\u010do nap\u00edsane na prvom riadku", 1).setLoreLine("nie\u010do na druhom riadku", 2).setLoreLine("a nie\u010do na poslednom riadku !?", 3).toItemStack();
                    p.getInventory().addItem(new ItemStack[] { is });
                }
                if (args[0].equalsIgnoreCase("irare")) {
                    Chat.info(p, ItemRarity.BASIC.getColor(ItemRarity.BASIC) + ":§l" + ItemRarity.BASIC);
                    Chat.info(p, ItemRarity.UNCOMMON.getColor(ItemRarity.UNCOMMON) + ":§l" + ItemRarity.UNCOMMON);
                    Chat.info(p, ItemRarity.COMMON.getColor(ItemRarity.COMMON) + ":§l" + ItemRarity.COMMON);
                    Chat.info(p, ItemRarity.RARE.getColor(ItemRarity.RARE) + ":§l" + ItemRarity.RARE);
                    Chat.info(p, ItemRarity.LEGENDARY.getColor(ItemRarity.LEGENDARY) + ":§l" + ItemRarity.LEGENDARY);
                    ItemRarity.LEGENDARY.getOrder(ItemRarity.LEGENDARY);
                }
                if (args[0].equalsIgnoreCase("lang")) {
                    if (args[1].equalsIgnoreCase("get")) {
                        Chat.info(p, "LANG: " + this.pdh.getData(p).getLang().getName());
                    }
                    else if (args[1].equalsIgnoreCase("set")) {
                        this.pdh.getData(p).setLangInt(Integer.parseInt(args[2]));
                    }
                }
                if (args[0].equalsIgnoreCase("bar")) {
                    final ActionBar ab = new ActionBar();
                    ab.ActionBarUpdater();
                }
                if (args[0].equalsIgnoreCase("yaw")) {
                    Chat.print("YAW: " + p.getLocation().getYaw());
                }
                if (args[0].equalsIgnoreCase("ser")) {
                    final SaveItem saveItem = new SaveItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName()), "", (Inventory)p.getInventory());
                }
                if (args[0].equalsIgnoreCase("rename")) {
                    if (args[1] != null) {
                        p.getInventory().setItemInMainHand(new ItemBuilder(p.getInventory().getItemInMainHand()).setName(Functions.stringBuilder(1, args)).toItemStack());
                    }
                    else {
                        Chat.info(p, "N\u00e1zov nem\u00f4\u017ee by\u0165 pr\u00e1zdny!");
                    }
                }
                if (args[0].equalsIgnoreCase("postel")) {
                    for (int i = 0; i < 9; ++i) {
                        final Block b2 = p.getTargetBlock((Set)null, 4);
                        if (b2.getType() == Material.WHITE_BED) {
                            final Bed bed2 = (Bed)b2.getState().getData();
                            Chat.info(p, "bed.getFacing(): " + bed2.getFacing());
                        }
                        else {
                            Chat.info(p, "TYPE:" + b2.getType());
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("bed")) {
                    final Block b3 = p.getTargetBlock((Set)null, 3);
                    if (b3 != null && b3.getType() == Material.WHITE_BED) {
                        final org.bukkit.block.Bed bed3 = (org.bukkit.block.Bed)b3;
                        if (this.pdh.getData(p).getPremium() != PremiumType.NOVIP) {
                            bed3.setColor(DyeColor.getByDyeData((byte)this.pdh.getData(p).getBedColor()));
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("opencell")) {
                    final DoorControler dc = new DoorControler(false);
                    dc.controlAllGate(true);
                }
                if (args[0].equalsIgnoreCase("closecell")) {
                    final DoorControler dc = new DoorControler(false);
                    dc.controlAllGate(false);
                }
                if (args[0].equalsIgnoreCase("villager")) {
                    final Villager v = (Villager)p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
                    this.addRecipe(new ItemBuilder(Material.STICK, 5).toItemStack(), null, new ItemBuilder(Material.STONE, 8).setName("test").setRare(ItemRarity.RARE).toItemStack());
                    this.addRecipe(new ItemBuilder(Material.YELLOW_GLAZED_TERRACOTTA, 5).toItemStack(), new ItemBuilder(Material.APPLE, 32).toItemStack(), new ItemBuilder(Material.ACTIVATOR_RAIL, 8).setName("lol").setRare(ItemRarity.LEGENDARY).toItemStack());
                    v.setRecipes((List)this.getRecipes());
                    v.setAI(false);
                    v.setProfession(Villager.Profession.LIBRARIAN);
                    v.setCustomName("§f§lR\u00f3bert");
                    v.setCustomNameVisible(true);
                    v.setCanPickupItems(false);
                    v.setMaxHealth(2048.0);
                    v.setHealth(2048.0);
                    v.setInvulnerable(true);
                }
                if (args[0].equalsIgnoreCase("sound")) {
                    if (args.length >= 2) {
                        if (args[1].equalsIgnoreCase("start")) {
                            p.playEffect(p.getLocation(), Effect.RECORD_PLAY, 10);
                            p.playSound(p.getLocation(), Sound.MUSIC_DISC_WARD, 10.0f, 1.0f);
                            Chat.info(p, "START MUSIC!");
                        }
                        else if (args[1].equalsIgnoreCase("stop")) {
                            p.stopSound(Sound.MUSIC_DISC_WARD);
                            Chat.info(p, "STOP MUSIC!");
                        }
                    }
                    else {
                        Chat.print("/prison sound start/stop");
                    }
                }
                if (args[0].equalsIgnoreCase("savecc") && args.length > 1) {
                    final int cell3 = Integer.parseInt(args[1]);
                    final int x4 = cd.getICell(cell3, "Chest.X");
                    final int y4 = cd.getICell(cell3, "Chest.Y");
                    final int z4 = cd.getICell(cell3, "Chest.Z");
                    final String world = cd.getSCell(cell3, "Chest.World");
                    final World w = Bukkit.getServer().getWorld(world);
                    if (w == null) {
                        return false;
                    }
                    final Location loc2 = new Location(w, (double)x4, (double)y4, (double)z4);
                    final Block b4 = w.getBlockAt(loc2);
                    if (b4.getState() instanceof Chest) {
                        final Chest chest = (Chest)b4.getState();
                        new SaveItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), "Vezen.Chest", chest.getInventory()).save();
                    }
                }
                if (args[0].equalsIgnoreCase("team")) {
                    final Prisoner pr = new Prisoner();
                    Scoreboard board = pr.getScoreBoard();
                    board = Bukkit.getScoreboardManager().getMainScoreboard();
                    Team team = board.getTeam("yellow_pr");
                    if (team != null) {
                        team.unregister();
                    }
                    team = board.registerNewTeam("yellow_pr");
                    team.setColor(ChatColor.YELLOW);
                    for (final Player P : Bukkit.getOnlinePlayers()) {
                        team.addEntry(P.getName());
                    }
                    p.setScoreboard(board);
                    for (final Team t : board.getTeams()) {
                        Bukkit.broadcastMessage("TEAM: " + t.getName() + " COLOR: " + t.getColor() + "Colored");
                    }
                    Bukkit.broadcastMessage("Registered new yellow_pr team");
                }
                if (args[0].equalsIgnoreCase("bottle")) {
                    p.getInventory().addItem(new ItemStack[] { ITEMS.BOTTLE_03L.getItem() });
                }
                if (args[0].equalsIgnoreCase("getPItem")) {
                    if (p.hasPermission("prison.admin.items")) {
                        final ItemStack is = new ItemStack(Material.NAME_TAG, 1);
                        final ItemMeta im = is.getItemMeta();
                        im.setDisplayName("§c§l§nBlockLocator");
                        im.setUnbreakable(true);
                        is.setItemMeta(im);
                        p.getInventory().addItem(new ItemStack[] { is });
                        Chat.info(p, "Bol ti pridan\u00fd item na z\u00edskavanie poz\u00edcie !");
                        Chat.info(p, "Klikni s itemom na zem pre z\u00edskanie poz\u00edcie !");
                    }
                    else {
                        Chat.noPerm(p);
                    }
                }
                final NeedsUpdater nu = new NeedsUpdater();
                if (args[0].equalsIgnoreCase("makeme")) {
                    if (p.hasPermission("prison.admin.makeme")) {
                        if (args[1].equalsIgnoreCase("prisoner")) {
                            if (this.pdh.getData(p).getType() == PrisonType.PRISONER) {
                                return true;
                            }
                            this.pdh.getData(p).setType(PrisonType.PRISONER);
                            p.setPlayerListName("§e" + p.getName());
                            final Prisoner psb = new Prisoner();
                            psb.addPrisonScoreBoardToPlayer(p);
                            CellAsigner.assignCell(p);
                            nu.addPlayer(p);
                            p.setPlayerListName("§e" + p.getName());
                            final LoadItem li = new LoadItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), this.pdh.getData(p).getType().getName());
                            p.getInventory().setContents(li.getItemsPole());
                        }
                        if (args[1].equalsIgnoreCase("warden")) {
                            if (this.pdh.getData(p).getType() == PrisonType.WARDEN) {
                                return true;
                            }
                            if (this.pdh.getData(p).getType() == PrisonType.PRISONER) {
                                CellAsigner.unloadCellByPlayer(p);
                            }
                            for (int j = 0; j < 10; ++j) {
                                if (BoxData.getBox(j).getName1().equalsIgnoreCase(p.getName())) {
                                    BoxData.getBox(j).setName1("- NONE -");
                                    JoinBoxListener.updateSign(j);
                                }
                                else if (BoxData.getBox(j).getName2().equalsIgnoreCase(p.getName())) {
                                    BoxData.getBox(j).setName2("- NONE -");
                                    JoinBoxListener.updateSign(j);
                                }
                            }
                            this.pdh.getData(p).setType(PrisonType.WARDEN);
                            p.setPlayerListName("§9" + p.getName());
                            final World w2 = Bukkit.getServer().getWorld(Main.mainWorld);
                            p.teleport(new Location(w2, -476.0, 77.0, 294.0));
                            nu.removePlayer(p);
                            final LoadItem li = new LoadItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), this.pdh.getData(p).getType().getName());
                            p.getInventory().setContents(li.getItemsPole());
                        }
                        if (args[1].equalsIgnoreCase("admin")) {
                            if (this.pdh.getData(p).getType() == PrisonType.ADMIN) {
                                return true;
                            }
                            if (this.pdh.getData(p).getType() == PrisonType.PRISONER) {
                                nu.removePlayer(p);
                                Prisoner.removePlayer(p);
                                CellAsigner.unloadCellByPlayer(p);
                            }
                            this.pdh.getData(p).setType(PrisonType.ADMIN);
                            p.setPlayerListName("§c§lAdmin §c" + p.getName());
                            for (int j = 0; j < 10; ++j) {
                                if (BoxData.getBox(j).getName1().equalsIgnoreCase(p.getName())) {
                                    BoxData.getBox(j).setName1("- NONE -");
                                }
                                else if (BoxData.getBox(j).getName2().equalsIgnoreCase(p.getName())) {
                                    BoxData.getBox(j).setName2("- NONE -");
                                }
                            }
                            final World w2 = Bukkit.getServer().getWorld(Main.mainWorld);
                            p.teleport(new Location(w2, -476.0, 82.0, 282.0));
                            nu.removePlayer(p);
                            final LoadItem li = new LoadItem(new ConfigListener(Main.getInstance(), "PlayerData/" + p.getName() + ".yml"), this.pdh.getData(p).getType().getName());
                            p.getInventory().setContents(li.getItemsPole());
                        }
                    }
                    else {
                        Chat.noPerm(p);
                    }
                }
            }
        }
        return false;
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
