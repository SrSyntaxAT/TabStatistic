package at.srsyntax.tabstatistic.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

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
public interface StatisticTabCompleter {

    default List<String> tabComplete(CommandSender sender, String[] args, boolean modify) {
        final int qualifier = modify ? 4 : 3;
        if (args.length == 1)
            return getOnlinePlayers(args[0]);
        else if (args.length == 2)
            return getStatistics(args[1]);
        else if (args.length == qualifier)
            return getQualifiers(args);
        return new ArrayList<>();
    }

    private List<String> getOnlinePlayers(String arg) {
        final List<String> result = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getName().toLowerCase().startsWith(arg.toLowerCase()))
                result.add(player.getName());
        });
        return result;
    }

    private List<String> getStatistics(String arg) {
        final List<String> result = new ArrayList<>();

        for (Statistic statistic : Statistic.values()) {
            if (statistic.name().startsWith(arg.toUpperCase()))
                result.add(statistic.name());
        }

        return result;
    }

    private List<String> getQualifiers(String[] args) {
        try {
            final Statistic statistic = Statistic.valueOf(args[1].toUpperCase());
            if (statistic.getType() == Statistic.Type.UNTYPED) return new ArrayList<>();

            final String arg = args[3];
            if (statistic.getType() == Statistic.Type.ENTITY)
                return getEntityTypes(arg);
            else
                return getMaterials(arg);
        } catch (Exception exception) {
            return new ArrayList<>();
        }
    }

    private List<String> getEntityTypes(String arg) {
        final List<String> result = new ArrayList<>();

        for (EntityType type : EntityType.values()) {
            if (type.name().startsWith(arg.toUpperCase()))
                result.add(type.name());
        }

        return result;
    }

    private List<String> getMaterials(String arg) {
        final List<String> result = new ArrayList<>();

        for (Material material : Material.values()) {
            if (material.name().startsWith(arg.toUpperCase()))
                result.add(material.name());
        }

        return result;
    }
}
