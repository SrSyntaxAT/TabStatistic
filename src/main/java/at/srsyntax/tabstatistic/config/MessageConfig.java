package at.srsyntax.tabstatistic.config;

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
public class MessageConfig {

    private final String noPermission, useage;
    private final String playerNotFound, statisticNotFound, invalidValue;
    private final String needQualifier, entityTypeNotFound, materialNotFound;
    private final String modified;

    public MessageConfig(String noPermission, String useage, String playerNotFound, String statisticNotFound, String invalidValue, String needQualifier, String entityTypeNotFound, String materialNotFound, String modified) {
        this.noPermission = noPermission;
        this.useage = useage;
        this.playerNotFound = playerNotFound;
        this.statisticNotFound = statisticNotFound;
        this.invalidValue = invalidValue;
        this.needQualifier = needQualifier;
        this.entityTypeNotFound = entityTypeNotFound;
        this.materialNotFound = materialNotFound;
        this.modified = modified;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getUseage() {
        return useage;
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
}
