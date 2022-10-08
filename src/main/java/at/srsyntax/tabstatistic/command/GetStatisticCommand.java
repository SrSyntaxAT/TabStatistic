package at.srsyntax.tabstatistic.command;

import at.srsyntax.tabstatistic.config.MessageConfig;
import at.srsyntax.tabstatistic.util.Replacer;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/*
 * CONFIDENTIAL
 *  Unpublished Copyright (c) 2022 Marcel Haberl, All Rights Reserved.
 *
 * NOTICE:
 * All information contained herein is, and remains the property of Marcel Haberl. The intellectual and
 * technical concepts contained herein are proprietary to Marcel Haberl and may be covered by U.S. and Foreign
 * Patents, patents in process, and are protected by trade secret or copyright law. Dissemination of this
 * information or reproduction of this material is strictly forbidden unless prior written permission is obtained
 * from Marcel Haberl.  Access to the source code contained herein is hereby forbidden to anyone without written
 * permission Confidentiality and Non-disclosure agreements explicitly covering such access.
 *
 * The copyright notice above does not evidence any actual or intended publication or disclosure  of  this source code,
 * which includes information that is confidential and/or proprietary, and is a trade secret, of Marcel Haberl.
 * ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC  PERFORMANCE, OR PUBLIC DISPLAY OF OR THROUGH USE  OF THIS
 * SOURCE CODE  WITHOUT  THE EXPRESS WRITTEN CONSENT OF Marcel Haberl IS STRICTLY PROHIBITED, AND IN VIOLATION OF
 * APPLICABLE LAWS AND INTERNATIONAL TREATIES.  THE RECEIPT OR POSSESSION OF  THIS SOURCE CODE AND/OR RELATED
 * INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO
 * MANUFACTURE, USE, OR SELL ANYTHING THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.
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
