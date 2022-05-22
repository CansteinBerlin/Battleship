package net.quickwrite.miniminigames.commands;

import net.quickwrite.miniminigames.commands.debug.*;
import net.quickwrite.miniminigames.commandsystem.BaseCommand;

public class DebugCommand extends BaseCommand {

    public DebugCommand() {
        super("mdebug", null);

        addSubCommand(new DebugSaveShipConfigCommand(this));
        addSubCommand(new DebugAddShipCommand(this));
        addSubCommand(new DebugSaveItemConfigCommand(this));
        addSubCommand(new DebugAddItemCommand(this));
        addSubCommand(new DebugAddBlockCommand(this));
        addSubCommand(new DebugSaveBlockConfigCommand(this));
        addSubCommand(new DebugEnableSelectionCommand(this));
        addSubCommand(new DebugAddVerticalDisplayCommand(this));
        addSubCommand(new DebugAddPlayerToDisplayCommand(this));
        addSubCommand(new DebugRemovePlayerFromDisplayCommand(this));
        addSubCommand(new DebugAddRandomBlockToDisplayCommand(this));
        addSubCommand(new DebugClearDisplayCommand(this));
        addSubCommand(new DebugAddHorizontalDisplayCommand(this));
    }
}
