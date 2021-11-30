package at.srsyntax.tabstatistic;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/*
 * MIT License
 *
 * Copyright (c) 2021 Marcel Haberl
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
  
  public static final String METADATA_KEY = "sts:sbt";
  
  private Scoreboard scoreboard;
  private String suffix;
  private Metrics metrics;
  
  @Override
  public void onEnable() {
    try {
      loadConfig();
      scoreboard = getServer().getScoreboardManager().getNewScoreboard();
      new PlayerListeners(this);
      metrics = new Metrics(this, 13458);
    } catch (IOException exception) {
      getLogger().severe("Configuration could not be loaded!");
      exception.printStackTrace();
    }
  }
  
  private void loadConfig() throws IOException {
    if (!getDataFolder().exists())
      getDataFolder().mkdirs();
    
    final File file = new File(getDataFolder(), "config.yml");
    final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
    
    if (!file.exists()) {
      file.createNewFile();
      configuration.set("suffix", "&e <MOB_KILLS>");
      configuration.save(file);
    }
    
    suffix = configuration.getString("suffix");
  }
  
  public void updatePlayer(Player player) {
    getTeam(player).setSuffix(replaceSuffix(player));
    Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.setScoreboard(scoreboard));
  }
  
  public String registerScoreboardTeam(Player player) {
    final String name = UUID.randomUUID().toString().split("-")[4];
    scoreboard.registerNewTeam(name).addEntry(player.getName());
    return name;
  }
  
  public Team getTeam(Player player) {
    return scoreboard.getTeam(player.getMetadata(METADATA_KEY).get(0).asString());
  }
  
  private String replaceSuffix(Player player) {
    String result = suffix.replace("&", "§");
    
    for (Statistic statistic : Statistic.values()) {
      try {
        final String key = "<" + statistic.name() + ">";
        if (result.contains(key))
          result = result.replace(key, String.valueOf(player.getStatistic(statistic)));
      } catch (Exception exception) {
        this.getLogger().severe("Not supported statistic! (" + statistic.name() + ")");
      }
    }
    
    return result;
  }
}
