package at.srsyntax.tabstatistic.config;

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
public class MessageConfig {

    private final String noPermission, useageModify, useageGet;
    private final String playerNotFound, statisticNotFound, invalidValue;
    private final String needQualifier, entityTypeNotFound, materialNotFound;
    private final String modified, get;

    public MessageConfig(String noPermission, String useageModify, String useageGet, String playerNotFound, String statisticNotFound, String invalidValue, String needQualifier, String entityTypeNotFound, String materialNotFound, String modified, String get) {
        this.noPermission = noPermission;
        this.useageModify = useageModify;
        this.useageGet = useageGet;
        this.playerNotFound = playerNotFound;
        this.statisticNotFound = statisticNotFound;
        this.invalidValue = invalidValue;
        this.needQualifier = needQualifier;
        this.entityTypeNotFound = entityTypeNotFound;
        this.materialNotFound = materialNotFound;
        this.modified = modified;
        this.get = get;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getPlayerNotFound() {
        return playerNotFound;
    }

    public String getStatisticNotFound() {
        return statisticNotFound;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    public String getNeedQualifier() {
        return needQualifier;
    }

    public String getEntityTypeNotFound() {
        return entityTypeNotFound;
    }

    public String getMaterialNotFound() {
        return materialNotFound;
    }

    public String getModified() {
        return modified;
    }

    public String getUseageModify() {
        return useageModify;
    }

    public String getUseageGet() {
        return useageGet;
    }

    public String getGet() {
        return get;
    }
}
