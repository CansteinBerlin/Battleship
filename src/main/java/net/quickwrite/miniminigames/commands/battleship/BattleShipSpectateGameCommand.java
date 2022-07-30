package net.quickwrite.miniminigames.commands.battleship;

import net.quickwrite.miniminigames.commandsystem.BaseCommand;
import net.quickwrite.miniminigames.commandsystem.SubCommand;
import org.bukkit.command.CommandSender;

public class BattleShipSpectateGameCommand extends SubCommand {

    public BattleShipSpectateGameCommand(BaseCommand parent) {
        super(parent, "spectateGame", "battleship.spectateGame");
    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        return super.performCommand(sender, args);
    }
}
