// 
// Decompiled by Procyon v0.5.36
// 

package Commands;

import Main.Chat;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class PlayerCommands extends Command
{
    public PlayerCommands(final String name) {
        super(name);
    }
    
    public boolean execute(final CommandSender sender, final String label, final String[] args) {
        if (sender instanceof Player) {
            Chat.print("SENDER: " + sender.getName());
            Chat.print("LABEL: " + label);
            Chat.print("ARGS: " + args.toString());
            return true;
        }
        Chat.print("noPlayer");
        return false;
    }
}
