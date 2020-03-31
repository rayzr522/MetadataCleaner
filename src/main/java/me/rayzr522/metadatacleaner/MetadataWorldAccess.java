package me.rayzr522.metadatacleaner;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IWorldAccess;
import org.bukkit.scheduler.BukkitRunnable;

public class MetadataWorldAccess implements IWorldAccess {
    private final MetadataCleaner plugin;

    public MetadataWorldAccess(MetadataCleaner plugin) {
        this.plugin = plugin;
    }

    @Override
    public void a(BlockPosition blockPosition) {

    }

    @Override
    public void b(BlockPosition blockPosition) {

    }

    @Override
    public void a(int i, int i1, int i2, int i3, int i4, int i5) {

    }

    @Override
    public void a(String s, double v, double v1, double v2, float v3, float v4) {

    }

    @Override
    public void a(EntityHuman entityHuman, String s, double v, double v1, double v2, float v3, float v4) {

    }

    @Override
    public void a(int i, boolean b, double v, double v1, double v2, double v3, double v4, double v5, int... ints) {

    }

    @Override
    public void a(Entity entity) {

    }

    @Override
    public void b(Entity entity) {
        plugin.getCleanerQueue().addToQueue(entity.getBukkitEntity());
    }

    @Override
    public void a(String s, BlockPosition blockPosition) {

    }

    @Override
    public void a(int i, BlockPosition blockPosition, int i1) {

    }

    @Override
    public void a(EntityHuman entityHuman, int i, BlockPosition blockPosition, int i1) {

    }

    @Override
    public void b(int i, BlockPosition blockPosition, int i1) {

    }
}
