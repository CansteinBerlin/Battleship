package net.quickwrite.miniminigames.commands.debug;

import net.quickwrite.miniminigames.MiniMinigames;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DebugTestMovementCommand extends SubCommand {

    public DebugTestMovementCommand(BaseCommand parent) {
        super(parent, "movingArmorstand", null);
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {

        ArrayList<ArmorStand> stands = new ArrayList<>();
        for(Entity e : Bukkit.getWorld("world").getEntities().stream().filter((e) -> e instanceof ArmorStand).collect(Collectors.toList())){
            stands.add((ArmorStand) e);
        }

        final int[] duration = {20*5};
        new BukkitRunnable(){

            @Override
            public void run() {
                duration[0]--;

                for(int i = 0; i < 2; i++) {
                    for (ArmorStand stand : stands) {
                        stand.teleport(stand.getLocation().add(0.5 / 2, 0, 0));
                    }
                }
                if(duration[0] < 0) cancel();
            }
        }.runTaskTimer(MiniMinigames.getInstance(), 0, 1);

        return true;
    }
}
