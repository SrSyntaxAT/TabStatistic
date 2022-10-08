package at.srsyntax.tabstatistic.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

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
public class Replacer {

  private final Logger logger;
  private final Player player;

  public Replacer(Logger logger, Player player) {
    this.logger = logger;
    this.player = player;
  }

  public String replace(String message) {
    return replaceWithPlaceholderAPI(replaceSuffix(message));
  }

  private String replaceSuffix(String message) {
    String result = message.replace("&", "§");

    for (Statistic statistic : Statistic.values()) {
      try {
        final String key = "<" + statistic.name() + ">";
        if (result.contains(key))
          result = result.replace(key, String.valueOf(player.getStatistic(statistic)));
      } catch (Exception exception) {
        logger.severe("Not supported statistic! (" + statistic.name() + ")");
      }
    }

    return result;
  }

  private String replaceWithPlaceholderAPI(String message) {
    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) return message;
    return PlaceholderAPI.setPlaceholders(player, message);
  }
}
