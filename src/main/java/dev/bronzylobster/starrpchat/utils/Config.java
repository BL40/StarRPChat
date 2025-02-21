package dev.bronzylobster.starrpchat.utils;

import lombok.Getter;

@Getter
public enum Config {

    DEBUG_ENABLED ("debug"),
    RANGEMODE_ENABLED ("RangeMode.Enabled"),
    RANGE_OVERRIDE_SYMBOL ("RangeMode.RangeOverrideSymbol"),
    RANGEMODE_DISTANCE ("RangeMode.RangeDistance"),
    GLOBAL_CHAT_FORMAT ("RangeMode.GlobalChatFormat"),
    LOCAL_CHAT_FORMAT ("RangeMode.LocalChatFormat"),
    DIMMODE_ENABLED ("DimensionMode.Enabled"),
    DIM_CHAT_SYMBOL ("DimensionMode.DimensionChatSymbol"),
    DIM_CHAT_FORMAT ("DimensionMode.DimensionChatFormat"),
    MUTED ("Messages.muted"),
    MUTE ("Messages.mute"),
    UNMUTE ("Messages.unmute"),
    IS_NOT_MUTED ("Messages.isnotmuted"),
    WT ("Messages.wt.format"),
    HAS_NOT_FREQ ("Messages.wt.hasnotfreq"),
    WT_SET ("Messages.wt.set"),
    WT_GET ("Messages.wt.get"),
    WT_DO ("Messages.wt.do"),
    WT_ME ("Messages.wt.me"),
    WT_TRY ("Messages.wt.try"),
    WT_TRY_WIN ("Messages.wt.tryWin"),
    WT_TRY_LOSE ("Messages.wt.tryLose"),
    WT_ROLL ("Messages.wt.roll"),
    WT_ROLL_MAX ("WT.rollmax"),
    WT_ROLL_MIN ("WT.rollmin"),
    MSG_SEND ("Messages.msg.send"),
    MSG_RECEIVE ("Messages.msg.receive"),
    MSG_OFFLINE ("Messages.msg.playerisoffline"),
    MSG_SPY ("Messages.msg.spy"),
    MSG_SPY_ON ("Messages.msg.spyon"),
    MSG_SPY_OFF ("Messages.msg.spyoff"),
    MSG_NO_REPLY ("Messages.msg.noreply"),
    MSG_DELAY ("Msg.delay"),
    JOIN ("Messages.join"),
    QUIT ("Messages.quit"),
    SOUND_JOIN ("Sound.join"),
    SOUND_QUIT ("Sound.quit"),
    SOUND_MSG ("Sound.msg"),
    SOUND_REPLY ("Sound.reply");


    private String path;

    Config(String path) {
        this.path = path;
    }
}
