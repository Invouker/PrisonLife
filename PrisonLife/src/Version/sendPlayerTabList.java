// 
// Decompiled by Procyon v0.5.36
// 

package Version;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import java.io.IOException;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_14_R1.PacketDataSerializer;
import io.netty.buffer.ByteBufAllocator;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class sendPlayerTabList
{
    public static void sendPlayerListTab(final Player p, final String header, final String footer) {
        final EntityPlayer player = ((CraftPlayer)Bukkit.getPlayer(p.getUniqueId())).getHandle();
        final ByteBuf byteBuffer = ByteBufAllocator.DEFAULT.buffer();
        final PacketDataSerializer packetDataSerializer = new PacketDataSerializer(byteBuffer);
        final PacketPlayOutPlayerListHeaderFooter packetPlayOutPlayerListHeaderFooter = new PacketPlayOutPlayerListHeaderFooter();
        try {
            packetDataSerializer.a(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}"));
            packetDataSerializer.a(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}"));
            packetPlayOutPlayerListHeaderFooter.a(packetDataSerializer);
            player.playerConnection.sendPacket((Packet)packetPlayOutPlayerListHeaderFooter);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
