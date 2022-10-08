package at.srsyntax.tabstatistic.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

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
public interface StatisticTabCompleter {

    default List<String> tabComplete(String[] args, boolean modify) {
        final int qualifier = modify ? 4 : 3;
        if (args.length == 1)
            return getOnlinePlayers(args[0]);
        else if (args.length == 2)
            return getStatistics(args[1]);
        else if (args.length == qualifier)
            return getQualifiers(args, qualifier);
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

    private List<String> getQualifiers(String[] args, int length) {
        try {
            final Statistic statistic = Statistic.valueOf(args[1].toUpperCase());
            if (statistic.getType() == Statistic.Type.UNTYPED) return new ArrayList<>();

            final String arg = args[length-1];
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
