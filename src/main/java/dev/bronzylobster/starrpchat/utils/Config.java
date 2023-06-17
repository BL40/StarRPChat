package dev.bronzylobster.starrpchat.utils;

import lombok.Getter;

public enum Config {

    DEBUG_ENABLED ("debug"),
    RANGEMODE_ENABLED ("RangeMode.Enabled"),
    RANGE_OVERRIDE_SYMBOL ("RangeMode.RangeOverrideSymbol"),
    RANGEMODE_DISTANCE ("RangeMode.RangeDistance"),
    GLOBAL_CHAT_FORMAT ("RangeMode.GlobalChatFormat"),
    LOCAL_CHAT_FORMAT ("RangeMode.LocalChatFormat"),
    DIMMODE_ENABLED ("DimensionMode.Enabled"),
    DIM_CHAT_SYMBOL ("DimensionMode.DimensionChatSymbol"),
    DIM_CHAT_FORMAT ("DimensionMode.DimensionChatFormat");

    @Getter
    private String path;

    Config(String path) {
        this.path = path;
    }
}
