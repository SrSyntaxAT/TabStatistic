package at.srsyntax.tabstatistic;

import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/*
 * MIT License
 *
 * Copyright (c) 2021, 2022 Marcel Haberl
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
public class TabStatistic extends JavaPlugin {
  
  public static final String METADATA_KEY;
  private static final int BSTATS_ID, RESOURCE_ID;

  static {
    METADATA_KEY = "sts:sbt";

    BSTATS_ID = 13458;
    RESOURCE_ID = 98022;
  }

  @Override
  public void onEnable() {
    try {
      checkVersion();
      new PlayerListeners(this, new ScoreboardManager(getLogger(), loadSuffixFromConfig()));
      new Metrics(this, BSTATS_ID);
    } catch (IOException exception) {
      getLogger().severe("Configuration could not be loaded!");
      exception.printStackTrace();
    }
  }

  private void checkVersion() {
    try {
      final VersionCheck check = new VersionCheck(getDescription().getVersion(), RESOURCE_ID);
      if (check.check()) return;
      getLogger().warning("The plugin is no longer up to date, please update the plugin.");
    } catch (Exception ignored) {}
  }

  private String loadSuffixFromConfig() throws IOException {
    if (!getDataFolder().exists())
      getDataFolder().mkdirs();
    
    final File file = new File(getDataFolder(), "config.yml");
    final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
    
    if (!file.exists()) {
      file.createNewFile();
      configuration.set("suffix", "&e <MOB_KILLS>");
      configuration.save(file);
    }
    
    return configuration.getString("suffix");
  }
}
