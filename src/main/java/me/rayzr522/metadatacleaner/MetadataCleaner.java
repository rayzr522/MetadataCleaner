package me.rayzr522.metadatacleaner;

import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetadataCleaner extends JavaPlugin implements Listener {
    private final Map<World, MetadataWorldAccess> accessMap = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        getServer().getWorlds().forEach(this::linkWorld);
    }

    @Override
    public void onDisable() {
        getServer().getWorlds().forEach(this::unlinkWorld);
    }

    public void linkWorld(World world) {
        MetadataWorldAccess access = new MetadataWorldAccess();
        ((CraftWorld) world).getHandle().addIWorldAccess(access);
        accessMap.put(world, access);

        getLogger().info("Registered world access handler for: " + world.getName());
    }

    public void unlinkWorld(World world) {
        try {
            WorldServer handle = ((CraftWorld) world).getHandle();
            Field iAccessListField = handle.getClass().getDeclaredField("u");
            iAccessListField.setAccessible(true);
            ((List<?>) iAccessListField.get(handle)).remove(accessMap.remove(world));

            getLogger().info("Unregistered world access handler for: " + world.getName());
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e) {
        linkWorld(e.getWorld());
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent e) {
        unlinkWorld(e.getWorld());
    }
}
