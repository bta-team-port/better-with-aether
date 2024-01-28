package bta.aether.command;

import bta.aether.accessory.API.HealthHelper;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

import static java.lang.Integer.parseInt;

public class CommandExtraHP extends Command {

    public CommandExtraHP() {
        super("extraHP","extrahp","extraHp");
    }

    @Override
    public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {

        if (strings.length == 0) {
            commandSender.sendMessage("Extra HEALTH: " + HealthHelper.getExtraHealth(commandSender.getPlayer()));
            return true;
        }

        if (strings.length != 3 || (!strings[1].equals("set") && !strings[1].equals("add")))
            return false;

        EntityPlayer target = commandHandler.getPlayer(strings[0]);

        if (target == null) {
            commandSender.sendMessage("Couldn't find player with username '" + strings[0] + "'");
            return false;
        }
        int amount;
        try {
            amount = parseInt(strings[2]);

        } catch (NumberFormatException e) {
            return false;
        }

        if (strings[1].equals("set")) {
            HealthHelper.setExtraHealth(target, amount);
        } else {
            HealthHelper.addExtraHealth(target, amount);
        }
        return true;
    }

    @Override
    public boolean opRequired(String[] strings) {
        return true;
    }

    @Override
    public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
        commandSender.sendMessage("/HP <username> set|add <amount>");
    }
}
