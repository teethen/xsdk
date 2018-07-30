package com.teethen.xsdk.recyvlergroup;

import com.teethen.xsdk.R;
import com.teethen.xsdk.recyvlergroup.bean.WeChatItem;

/**
 * Created by xingq on 2018/3/15.
 */

public class DataSource {

    public static final int VIEW_TYPE_WECHAT_PROFILE = 0x0001;
    public static final int VIEW_TYPE_WECHAT_COMMON = 0x0002;
    public static final int VIEW_TYPE_WECHAT_SWITCH = 0x0003;
    public static final int VIEW_TYPE_WECHAT_TEXT = 0x0004;

    private static WeChatItem HEADER_NULL_ITEM = new WeChatItem(0,0,0,0,null);

    public static WeChatItem[][] WeChatWeItems = new WeChatItem[][]{
            {HEADER_NULL_ITEM, new WeChatItem(R.attr.key_profile, R.mipmap.ic_me_avatar, R.string.wechat_name, R.string.wechat_id, VIEW_TYPE_WECHAT_PROFILE, null)},
            {HEADER_NULL_ITEM, new WeChatItem(R.attr.key_wallet, R.mipmap.ic_me_wallet, R.string.wallet, VIEW_TYPE_WECHAT_COMMON, null)},
            {HEADER_NULL_ITEM,
                    new WeChatItem(R.attr.key_collect, R.mipmap.ic_me_collect, R.string.collect, VIEW_TYPE_WECHAT_COMMON, null),
                    new WeChatItem(R.attr.key_photo, R.mipmap.ic_me_photo, R.string.photo, VIEW_TYPE_WECHAT_COMMON, null),
                    new WeChatItem(R.attr.key_card, R.mipmap.ic_me_card, R.string.card, VIEW_TYPE_WECHAT_COMMON, null),
                    new WeChatItem(R.attr.key_smile, R.mipmap.ic_me_smile, R.string.smile, VIEW_TYPE_WECHAT_COMMON, null)},
            {HEADER_NULL_ITEM, new WeChatItem(R.attr.key_setting, R.mipmap.ic_me_setting, R.string.setting, VIEW_TYPE_WECHAT_COMMON, null)}
    };

    public static WeChatItem NEW_MESSAGE_VOICE = new WeChatItem(R.attr.key_new_message_voice, R.string.new_message_voice, R.string.little_apple, VIEW_TYPE_WECHAT_TEXT, false);
    public static WeChatItem[][] WeChatNewMsgItems = new WeChatItem[][]{
        {HEADER_NULL_ITEM,
                new WeChatItem(R.attr.key_new_message_notify, R.string.new_message_notify, 0, VIEW_TYPE_WECHAT_SWITCH, true),
                new WeChatItem(R.attr.key_voice_video_notify, R.string.voice_video_notify, 0, VIEW_TYPE_WECHAT_SWITCH, true)},
        {HEADER_NULL_ITEM, new WeChatItem(R.attr.key_notify_detail, R.string.notify_detail, R.string.notify_detail_subtitle, VIEW_TYPE_WECHAT_SWITCH, false)},
        {HEADER_NULL_ITEM,
                new WeChatItem(R.attr.key_voice, R.string.voice, 0, VIEW_TYPE_WECHAT_SWITCH, false),
                new WeChatItem(R.attr.key_vibrant, R.string.vibrant, 0, VIEW_TYPE_WECHAT_SWITCH, false)}
    };

    public static WeChatItem[][] WeChatItemMsgNotifyItemsHolder = {
            {HEADER_NULL_ITEM, new WeChatItem(R.attr.key_notify_detail, R.string.notify_detail, R.string.notify_detail_subtitle, VIEW_TYPE_WECHAT_SWITCH, false)},
            {HEADER_NULL_ITEM,
                    new WeChatItem(R.attr.key_voice, R.string.voice, 0, VIEW_TYPE_WECHAT_SWITCH, false),
                    new WeChatItem(R.attr.key_vibrant, R.string.vibrant, 0, VIEW_TYPE_WECHAT_SWITCH, false)}
    };
}
