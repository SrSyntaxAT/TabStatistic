package at.srsyntax.tabstatistic.command;

import at.srsyntax.tabstatistic.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
public abstract class StatisticCommand implements CommandExecutor, TabCompleter, StatisticTabCompleter {

    private final String permission;
    private final boolean modify;
    protected final MessageConfig messages;

    public StatisticCommand(String permission, boolean modify, MessageConfig messages) {
        this.permission = permission;
        this.modify = modify;
        this.messages = messages;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (hasPermission(sender))
            return tabComplete(sender, args, modify);
        return new ArrayList<>();
    }

    protected boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission);
    }

    protected OfflinePlayer getPlayer(String arg) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(arg);
        if (player == null || !player.hasPlayedBefore())
            throw new RuntimeException(messages.getPlayerNotFound());
        return player;
    }

    protected Statistic getStatistic(String arg) {
        try {
            return Statistic.valueOf(arg.toUpperCase());
        } catch (Exception exception) {
            throw new RuntimeException(String.format(messages.getStatisticNotFound(), arg));
        }
    }

    protected EntityType getEntityType(String arg) {
        try {
            return EntityType.valueOf(arg.toUpperCase());
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    protected Material getMaterial(String arg) {
        try {
            return Material.valueOf(arg.toUpperCase());
        } catch (Exception exception) {
            throw new RuntimeException(messages.getMaterialNotFound());
        }
    }
}
