package at.srsyntax.tabstatistic.command;

import at.srsyntax.tabstatistic.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/*
 * MIT License
 *
 * Copyright (c) 2022 Marcel Haberl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class ModifyStatisticCommand implements CommandExecutor, TabExecutor, ModifyTabCompleter {

    private final MessageConfig messages;

    public ModifyStatisticCommand(MessageConfig messages) {
        this.messages = messages;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // modifystats <player> <statistic> <value> <extra>
        try {
            checkRequirements(sender, args);

            final OfflinePlayer player = getPlayer(args[0]);
            final Statistic statistic = getStatistic(args[1]);
            final int value = getValue(args[2]);

            if (statistic.getType() != Statistic.Type.UNTYPED) {
                if (args.length < 4)
                    throw new RuntimeException(String.format(messages.getNeedQualifier(), statistic.getType().name()));

                if (statistic.getType() == Statistic.Type.ENTITY) {
                    player.setStatistic(statistic, getEntityType(args[3]), value);
                } else {
                    player.setStatistic(statistic, getMaterial(args[3]), value);
                }
            } else {
                player.setStatistic(statistic, value);
            }

            sender.sendMessage(messages.getModified().replace("&", "§"));
        } catch (Exception exception) {
            sender.sendMessage(exception.getMessage().replace("&", "§"));
            return false;
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return onTabComplete(sender, args);
    }

    private void checkRequirements(CommandSender sender, String[] args) {
        if (!hasPermission(sender))
            throw new RuntimeException(messages.getNoPermission());
        if (args.length < 3)
            sendUseage();
    }

    private void sendUseage() {
        throw new RuntimeException(messages.getUseage());
    }

    private OfflinePlayer getPlayer(String arg) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(arg);
        if (player == null || !player.hasPlayedBefore())
            throw new RuntimeException(messages.getPlayerNotFound());
        return player;
    }

    private Statistic getStatistic(String arg) {
        try {
            return Statistic.valueOf(arg.toUpperCase());
        } catch (Exception exception) {
            throw new RuntimeException(String.format(messages.getStatisticNotFound(), arg));
        }
    }

    private int getValue(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (Exception exception) {
            throw new RuntimeException(messages.getInvalidValue());
        }
    }

    private EntityType getEntityType(String arg) {
        try {
            return EntityType.valueOf(arg.toUpperCase());
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    private Material getMaterial(String arg) {
        try {
            return Material.valueOf(arg.toUpperCase());
        } catch (Exception exception) {
            throw new RuntimeException(messages.getMaterialNotFound());
        }
    }
}
