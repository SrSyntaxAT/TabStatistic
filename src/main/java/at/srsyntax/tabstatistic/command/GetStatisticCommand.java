package at.srsyntax.tabstatistic.command;

import at.srsyntax.tabstatistic.config.MessageConfig;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

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
public class GetStatisticCommand extends StatisticCommand {

    public GetStatisticCommand(MessageConfig messageConfig) {
        super("tabstatistic.get", false, messageConfig);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // getstatistic <player> <statistic> <qualifier>
        try {
            checkRequirements(sender, args);

            final OfflinePlayer player = getPlayer(args[0]);
            final Statistic statistic = getStatistic(args[1]);
            int value;

            if (statistic.getType() != Statistic.Type.UNTYPED) {
                if (args.length < 3)
                    throw new RuntimeException(String.format(messages.getNeedQualifier(), statistic.getType().name()));

                if (statistic.getType() == Statistic.Type.ENTITY) {
                    value = player.getStatistic(statistic, getEntityType(args[2]));
                } else {
                    value = player.getStatistic(statistic, getMaterial(args[2]));
                }
            } else {
                value = player.getStatistic(statistic);
            }

            final String message = messages.getGet()
                    .replace("&", "§")
                    .replace("§player", player.getName())
                    .replace("§statistic", statistic.name())
                    .replace("§value", String.valueOf(value));
            sender.sendMessage(message);
        } catch (Exception exception) {
            sender.sendMessage(exception.getMessage().replace("&", "§"));
            return false;
        }
        return true;
    }

    private void checkRequirements(CommandSender sender, String[] args) {
        if (!hasPermission(sender))
            throw new RuntimeException(messages.getNoPermission());
        if (args.length < 2)
            throw new RuntimeException(messages.getUseageGet());
    }
}
