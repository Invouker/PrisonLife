// 
// Decompiled by Procyon v0.5.36
// 

package Main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ChunkGenerate
{
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;
    private int chunkX;
    private int chunkZ;
    private World world;
    private ArrayList<Chunk> chunks;
    
    public ChunkGenerate(final World w, final int minX, final int minZ, final int maxX, final int maxZ) {
        this.chunks = new ArrayList<Chunk>();
        this.world = w;
        this.minX = minX / 16;
        this.minZ = minZ / 16;
        this.maxX = maxX / 16;
        this.maxZ = maxZ / 16;
        this.generate();
    }
    
    private void generate() {
        new BukkitRunnable() {
            int x = ChunkGenerate.this.minZ;
            int z = ChunkGenerate.this.minZ;
            
            public void run() {
                ChunkGenerate.access$1(ChunkGenerate.this, this.x);
                ChunkGenerate.access$2(ChunkGenerate.this, this.z);
                final Chunk chunk = ChunkGenerate.this.world.getChunkAt(ChunkGenerate.this.chunkX, ChunkGenerate.this.chunkZ);
                Bukkit.getConsoleSender().sendMessage("§cCHUNK DATA: X: " + chunk.getX() + " Z: " + chunk.getZ());
                chunk.load(true);
                if (chunk.isLoaded()) {
                    chunk.unload();
                }
                ChunkGenerate.this.chunks.add(chunk);
                ++this.x;
                if (this.x >= ChunkGenerate.this.maxX) {
                    this.x = ChunkGenerate.this.minX;
                    ++this.z;
                }
                if (this.x >= ChunkGenerate.this.maxX && this.z >= ChunkGenerate.this.maxZ) {
                    this.cancel();
                }
                Bukkit.getConsoleSender().sendMessage("§6Po\u010det vygenerovna\u00fdch chunkov: " + ChunkGenerate.this.chunks.size());
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 20L, 3L);
    }
    
    static /* synthetic */ void access$1(final ChunkGenerate chunkGenerate, final int chunkX) {
        chunkGenerate.chunkX = chunkX;
    }
    
    static /* synthetic */ void access$2(final ChunkGenerate chunkGenerate, final int chunkZ) {
        chunkGenerate.chunkZ = chunkZ;
    }
}
