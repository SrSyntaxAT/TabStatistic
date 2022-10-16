package at.srsyntax.tabstatistic.util;

import at.srsyntax.tabstatistic.config.MessageConfig;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

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
public class StatisticUtil {

    protected final MessageConfig messages;

    public StatisticUtil(MessageConfig messages) {
        this.messages = messages;
    }

    public Statistic getStatistic(String arg) {
        try {
            return Statistic.valueOf(arg.toUpperCase());
        } catch (Exception exception) {
            throw new RuntimeException(String.format(messages.getStatisticNotFound(), arg));
        }
    }

    public EntityType getEntityType(String arg) {
        try {
            return EntityType.valueOf(arg.toUpperCase());
        } catch (Exception exception) {
            throw new RuntimeException(messages.getEntityTypeNotFound());
        }
    }

    public Material getMaterial(String arg) {
        try {
            return Material.valueOf(arg.toUpperCase());
        } catch (Exception exception) {
            throw new RuntimeException(messages.getMaterialNotFound());
        }
    }
}
