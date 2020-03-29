package me.rayzr522.metadatacleaner;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IWorldAccess;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.metadata.EntityMetadataStore;
import org.bukkit.metadata.MetadataStoreBase;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MetadataWorldAccess implements IWorldAccess {
    private static Field metadataMapField = null;

    static {
        try {
            metadataMapField = MetadataStoreBase.class.getDeclaredField("metadataMap");
            metadataMapField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
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
        if (metadataMapField == null) {
            return;
        }

        CraftEntity bukkitEntity = entity.getBukkitEntity();

        EntityMetadataStore entityMetadata = ((CraftServer) Bukkit.getServer()).getEntityMetadata();

        try {
            Map<String, Map<Plugin, MetadataValue>> metadataMap = (Map<String, Map<Plugin, MetadataValue>>) metadataMapField.get(entityMetadata);

            Set<String> collect = metadataMap.keySet().stream()
                    .filter(key -> key.startsWith(bukkitEntity.getUniqueId().toString()))
                    .collect(Collectors.toSet());

            collect.forEach(metadataMap::remove);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
