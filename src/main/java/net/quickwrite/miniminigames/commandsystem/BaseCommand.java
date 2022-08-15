package net.quickwrite.miniminigames.commandsystem;

import net.quickwrite.miniminigames.Battleship;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseCommand {

    protected String name, permission;
    protected String noPermissionMessage = Battleship.PREFIX + "§cYou don't have permission to use this command";
    protected ArrayList<SubCommand> subCommands;

    public BaseCommand(String name, String permission) {
        this.name = name;
        this.permission = permission;
        subCommands = new ArrayList<>();
    }

    public boolean performCommand(CommandSender sender, String[] args){
        if (args.length > 0) {
            for (SubCommand cmd : subCommands) {
                if (cmd.getName().equalsIgnoreCase(args[0])) {
                    if (cmd.getPermission() != null) {
                        if (sender.hasPermission(cmd.getPermission())) {
                            return cmd.performCommand(sender, Arrays.copyOfRange(args, 1, args.length));
                        } else {
                            sender.sendMessage(cmd.getNoPermissionMessage());
                            return true;
                        }
                    } else {
                        return cmd.performCommand(sender, Arrays.copyOfRange(args, 1, args.length));
                    }
                }
            }
        }
        sender.sendMessage(Battleship.PREFIX + "§cPlease use /" + name + " <" + getValidCommands() + "> ");
        return true;
    }

    public void addSubCommand(SubCommand cmd){
        subCommands.add(cmd);
    }

    public List<String> tabComplete(CommandSender sender, String[] args){
        List<String> out = new ArrayList<>();
        //Bingo.LOGGER.info(name + "; " + args[0].length());
        if (args.length == 1){
            for (BaseCommand cmd : subCommands) {
                if(cmd.getPermission() == null || sender.hasPermission(cmd.getPermission())) {
                    if (args[0].length() != 0) {
                        if (cmd.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                            out.add(cmd.getName());
                            //Bingo.LOGGER.info("Contains " + cmd.getName());
                        }
                    } else {
                        out.add(cmd.getName());
                    }
                }
            }
        }else{
            for(BaseCommand cmd : subCommands){
                if (cmd.getName().equalsIgnoreCase(args[0])){
                    return cmd.tabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }
        //Bingo.LOGGER.info(args.length);
        //TODO: Add Logic
        return out;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public String getValidCommands(){
        if (subCommands.size() > 0) {
            StringBuilder sb = new StringBuilder(subCommands.get(0).getName());
            for (int i = 1; i < subCommands.size(); i++){
                sb.append("/").append(subCommands.get(i).getName());
            }
            return sb.toString();
        }
        return "";
    }
}
