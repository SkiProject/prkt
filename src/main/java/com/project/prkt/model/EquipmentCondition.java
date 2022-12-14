package com.project.prkt.model;

import java.util.ResourceBundle;

/**
 * @author Nikolai Khriapov
 * @author Sergei Gavrilov
 */

public enum EquipmentCondition {
    UNKNOWN,
    GOOD,
    USABLE,
    BROKEN,
    SERVICE;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("equipment");

    public String toString() {
        return resourceBundle.getString("equipment_condition." + name());
    }
}
