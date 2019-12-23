// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PacketDataSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerListHeaderFooter;

public class TabHandler
{
    public static void sendPlayerListTabs(final Player p, final String header, final String footer) {
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
