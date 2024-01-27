package at.srsyntax.tabstatistic;

import at.srsyntax.tabstatistic.config.MessageConfig;
import at.srsyntax.tabstatistic.util.Replacer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

/*
 * MIT License
 *
 * Copyright (c) 2022, 2024 Marcel Haberl
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
public class ScoreboardManager {

  private final Plugin plugin;
  private final MessageConfig messageConfig;

  private final String suffix;
  private final Scoreboard scoreboard;

  public ScoreboardManager(Plugin plugin, MessageConfig messageConfig, String suffix) {
    this.plugin = plugin;
    this.messageConfig = messageConfig;
    this.suffix = suffix;
    this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
  }

  public void updatePlayer(Player player) {
    getTeam(player).setSuffix(new Replacer(plugin.getLogger(), player, messageConfig).replace(suffix));
    Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.setScoreboard(scoreboard));
  }

  public void registerPlayer(Player player) {
    final String teamName = registerScoreboardTeam(player);
    player.setMetadata(TabStatistic.METADATA_KEY, new FixedMetadataValue(plugin, teamName));
    updatePlayer(player);
  }

  private String registerScoreboardTeam(Player player) {
    final String name = UUID.randomUUID().toString().split("-")[4];
    scoreboard.registerNewTeam(name).addEntry(player.getName());
    return name;
  }

  public Team getTeam(Player player) {
    return scoreboard.getTeam(player.getMetadata(TabStatistic.METADATA_KEY).get(0).asString());
  }

  public Scoreboard getScoreboard() {
    return scoreboard;
  }

  public String getSuffix() {
    return suffix;
  }
}
