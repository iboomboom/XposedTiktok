package com.evayinfo.xposedtt.hook;

public class Config {
    /**
     * TikTok包名
     */
    public static final String TIK_PACKAGE = "com.zhiliaoapp.musically";

    /**
     * 点击事件所在类
     */
    public static final String CLASS_COMMENT_ITEM = "com.ss.android.ugc.aweme.comment.adapter.CommentViewHolder$b";


    /**
     * 点击事件方法名
     */
    public static final String METHOD_COMMENT_ITEM_LONG_CLICK = "onLongClick";

    /**
     * 系统手机号方法名
     */
    public static final String METHOD_MODIFY_PHONE = "getLine1Number";

    /**
     * sim卡地区获取方法名
     */
    public static final String METHOD_MODIFY_SIM_COUNTRY = "getSimCountryIso";

    /**
     * tik tok 目标application
     */
    public static final String CLASS_APPLICATION = "com.ss.android.ugc.trill.app.TrillApplication";
}
