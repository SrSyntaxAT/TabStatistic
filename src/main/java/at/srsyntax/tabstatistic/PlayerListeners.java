package at.srsyntax.tabstatistic;

import at.srsyntax.tabstatistic.TabStatistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.metadata.FixedMetadataValue;

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
public class PlayerListeners implements Listener {
  
  private final TabStatistic plugin;
  
  public PlayerListeners(TabStatistic plugin) {
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
  
  @EventHandler
  public void onPlayerJoinEvent(PlayerJoinEvent event) {
    final Player player = event.getPlayer();
    final String teamName = plugin.registerScoreboardTeam(player);
    player.setMetadata(TabStatistic.METADATA_KEY, new FixedMetadataValue(plugin, teamName));
    plugin.updatePlayer(player);
  }
  
  @EventHandler
  public void onPlayerQuitEvent(PlayerQuitEvent event) {
    plugin.getTeam(event.getPlayer()).unregister();
  }
  
  @EventHandler
  public void onPlayerStatisticIncrementEvent(PlayerStatisticIncrementEvent event) {
    plugin.updatePlayer(event.getPlayer());
  }
}
