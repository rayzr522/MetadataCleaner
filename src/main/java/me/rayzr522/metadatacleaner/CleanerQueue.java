package me.rayzr522.metadatacleaner;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.metadata.EntityMetadataStore;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataStoreBase;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class CleanerQueue extends Thread {
    private static Field metadataMapField = null;

    static {
        try {
            metadataMapField = MetadataStoreBase.class.getDeclaredField("metadataMap");
            metadataMapField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private final BlockingQueue<UUID> entitiesToClean = new LinkedBlockingQueue<>();
    private final Map<String, Map<Plugin, MetadataValue>> metadataMap;

    private boolean stopping = false;

    @SuppressWarnings("unchecked")
    public CleanerQueue() {
        if (metadataMapField == null) {
            throw new IllegalStateException("Failed to reflect!");
        }

        EntityMetadataStore entityMetadata = ((CraftServer) Bukkit.getServer()).getEntityMetadata();

        Map<String, Map<Plugin, MetadataValue>> metadataMap = null;
        try {
            metadataMap = (Map<String, Map<Plugin, MetadataValue>>) metadataMapField.get(entityMetadata);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.metadataMap = metadataMap;
    }

    public void addToQueue(Entity entity) {
        entitiesToClean.add(entity.getUniqueId());
    }

    public void stopThread() {
        this.stopping = true;
        interrupt();
    }

    @Override
    public void run() {
        while (!stopping) {
            try {
                UUID entityId = entitiesToClean.take();

                Set<String> collect = new ArrayList<>(metadataMap.keySet()).stream()
                        .filter(key -> key.startsWith(entityId.toString()))
                        .collect(Collectors.toSet());

                metadataMap.keySet().removeAll(collect);
            } catch (InterruptedException ignored) {

            }
        }
    }
}
