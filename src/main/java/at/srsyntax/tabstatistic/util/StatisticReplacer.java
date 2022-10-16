package at.srsyntax.tabstatistic.util;

import at.srsyntax.tabstatistic.config.MessageConfig;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.logging.Logger;

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
public class StatisticReplacer extends StatisticUtil {

    private final Logger logger;
    private final Player player;
    private final Statistic statistic;
    private String result;

    public StatisticReplacer(Logger logger, Player player, Statistic statistic, String result, MessageConfig messages) {
        super(messages);
        this.logger = logger;
        this.player = player;
        this.statistic = statistic;
        this.result = result;
    }

    public String replace() {
        if (statistic.isSubstatistic())
            result = replaceQualifiers();
        else {
            final String key = "%<" + statistic.name() + ">";
            if (result.contains(key))
                result = result.replace(key, String.valueOf(player.getStatistic(statistic)));
        }
        return result;
    }

    private String replaceAll(String result, Statistic statistic) {
        int total = 0;

        if (needMaterial()) {
            for (Material material : Material.values()) {
                try {
                    total += player.getStatistic(statistic, material);
                } catch (Exception ignored) {}
            }
        } else if (statistic.getType() == Statistic.Type.ENTITY) {
            for (EntityType type : EntityType.values()) {
                try {
                    total += player.getStatistic(statistic, type);
                } catch (Exception ignored) {}
            }
        }

        return result.replace("%<" + statistic.name() + ">", String.valueOf(total));
    }

    private boolean shouldReplaceAll() {
        return result.contains("%<" + statistic.name()  +">");
    }

    private boolean needMaterial() {
        final Statistic.Type type = statistic.getType();
        return type == Statistic.Type.BLOCK || type == Statistic.Type.ITEM;
    }

    private String replaceQualifiers() {
        if (shouldReplaceAll())
            return replaceAll(result, statistic);
        if (!result.contains(statistic.name())) return result;

        final String statisticKey = getKeyFromSuffix();
        if (statisticKey == null) return result;

        final String rawQualifiers = getQualifiersFromKey(statisticKey);
        if (rawQualifiers == null) return result;
        final String[] qualifiers = rawQualifiers.split("&");
        if (qualifiers.length == 0) return result;

        return replace(statisticKey, getTotal(statisticKey, qualifiers));
    }

    private String getQualifiersFromKey(String key) {
        if (!key.contains("&")) return null;
        int indexStart = key.indexOf("&")+1;
        int indexEnd = key.indexOf(">");
        return key.substring(indexStart, indexEnd);
    }

    private int getTotal(String key, String... qualifiers) {
        int total = 0;
        final boolean material = needMaterial();

        for (String qualifier : qualifiers) {
            try {
                if (material)
                    total += player.getStatistic(statistic, getMaterial(qualifier));
                else
                    total += player.getStatistic(statistic, getEntityType(qualifier));
            } catch (Exception exception) {
                logger.severe(String.format("Qualifier '%s' for %s not found!", qualifier, key));
            }
        }

        return total;
    }

    private String getKeyFromSuffix() {
        for (String splited : result.split(" ")) {
            if (splited.contains("%<" + statistic.name())) {
                int indexStart = splited.indexOf("%<");
                int indexEnd = splited.indexOf(">")+1;
                return splited.substring(indexStart, indexEnd);
            }
        }
        return null;
    }

    private String replace(String key, Object value) {
        if (result.contains(key))
            return result.replace(key, String.valueOf(value));
        return result;
    }
}
