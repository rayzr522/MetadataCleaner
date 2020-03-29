package me.rayzr522.metadatacleaner.command;

import me.rayzr522.metadatacleaner.MetadataCleaner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMetadataCleaner implements CommandExecutor {
    private final MetadataCleaner plugin;

    public CommandMetadataCleaner(MetadataCleaner plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        if (!(sender instanceof Player)) {
//            sender.sendMessage(plugin.tr("command.fail.only-players"));
//            return true;
//        }
//
//        Player player = (Player) sender;

        if (!plugin.checkPermission(sender, "use", true)) {
            return true;
        }

        if (args.length < 1) {
            showUsage(sender);
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "version":
                sender.sendMessage(plugin.tr("command.metadatacleaner.version", plugin.getName(), plugin.getDescription().getVersion()));
                break;
            case "reload":
                plugin.reload();
                sender.sendMessage(plugin.tr("command.metadatacleaner.reloaded"));
                break;
            case "help":
            case "?":
            default:
                showUsage(sender);
        }

        return true;
    }

    private void showUsage(CommandSender sender) {
        sender.sendMessage(plugin.tr("command.metadatacleaner.help.message"));
    }
}