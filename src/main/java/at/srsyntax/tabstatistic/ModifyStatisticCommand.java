package at.srsyntax.tabstatistic;

import at.srsyntax.tabstatistic.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
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
public class ModifyStatisticCommand implements CommandExecutor {

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
            sender.sendMessage(exception.getMessage());
            return false;
        }
        return true;
    }

    private void checkRequirements(CommandSender sender, String[] args) {
        if (!sender.hasPermission("tabstatistic.modify"))
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
