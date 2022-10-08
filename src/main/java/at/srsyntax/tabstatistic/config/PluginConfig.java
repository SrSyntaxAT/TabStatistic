package at.srsyntax.tabstatistic.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
            configuration.addDefault("message.not.found.statistic", "&cStatistic not found.");
            configuration.addDefault("message.not.found.entityType", "&cEntityType not found.");
            configuration.addDefault("message.not.found.material", "&cMaterial not found.");
            configuration.addDefault("message.needQualifier", "&cYou need a %s qualifier.");
            configuration.addDefault("message.invalidValue", "&cInvalid value. The value must be an integer.");
            configuration.options().copyDefaults(true);
            configuration.save(file);
        }

        return new PluginConfig(
                configuration.getString("suffix"),
                new MessageConfig(
                        configuration.getString("message.noPermission"),
                        configuration.getString("message.useage"),
                        configuration.getString("message.not.found.player"),
                        configuration.getString("message.not.found.statistic"),
                        configuration.getString("message.invalidValue"),
                        configuration.getString("message.needQualifier"),
                        configuration.getString("message.not.found.entityType"),
                        configuration.getString("message.not.found.material"),
                        configuration.getString("message.modified")
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
