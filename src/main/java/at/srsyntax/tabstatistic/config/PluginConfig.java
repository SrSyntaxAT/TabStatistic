package at.srsyntax.tabstatistic.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
public class PluginConfig {

    private final String suffix;
    private final MessageConfig messages;

    public PluginConfig(String suffix, MessageConfig messages) {
        this.suffix = suffix;
        this.messages = messages;
    }

    public static PluginConfig loadConfig(File dataFolder) throws IOException {
        if (!dataFolder.exists())
            dataFolder.mkdirs();

        final File file = new File(dataFolder, "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            file.createNewFile();
            configuration.addDefault("suffix", "&e <MOB_KILLS>");
            configuration.addDefault("message.modified", "&aStatistics have been modified.");
            configuration.addDefault("message.noPermission", "&cYou have no rights to do this.");
            configuration.addDefault("message.useage", "&cUseage&8: &f/modifystatistic <player> <statistic> <value> <qualifier>");
            configuration.addDefault("message.not.found.player", "&cPlayer not found.");
            configuration.addDefault("message.not.found.stastistic", "&cStatistic not found.");
            configuration.addDefault("message.not.found.entityType", "&cEntityType not found.");
            configuration.addDefault("message.not.found.material", "&cMaterial not found.");
            configuration.addDefault("message.needQualifier", "&cYou need a %s qualifier.");
            configuration.addDefault("message.invalidValue", "&cInvalid value. The value must be an integer.");
            configuration.save(file);
        }

        return new PluginConfig(
                configuration.getString("suffix"),
                new MessageConfig(
                        configuration.getString("message.modified"),
                        configuration.getString("message.noPermission"),
                        configuration.getString("message.useage"),
                        configuration.getString("message.not.found.player"),
                        configuration.getString("message.not.found.stastistic"),
                        configuration.getString("message.not.found.entityType"),
                        configuration.getString("message.not.found.material"),
                        configuration.getString("message.needQualifier"),
                        configuration.getString("message.invalidValue")
                )
        );
    }

    public String getSuffix() {
        return suffix;
    }

    public MessageConfig getMessages() {
        return messages;
    }
}
