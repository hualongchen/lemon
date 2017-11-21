package com.lemon.chen.lemon.tools;

import org.apache.commons.lang.StringUtils;

/**
 * Created by chenhualong on 16/6/27.
 * 过滤emoji等表情
 */
public class EmojiUtil {


    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isEmpty(source)) {
            return false;
        }

        return isEmojiCharacter(source);
        //do nothing，判断到了这里表明，确认有表情字符
    }

    //    "(\ud83c[\udc00-\udfff])|(\ud83d[\udc00-\udfff])|[\u2600-\u27ff]",
    private static boolean isEmojiCharacter(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (codePoint == '\ud83c' || codePoint == '\ud83d') {
                if (i + 1 < len) {
                    char nextCodePoint = source.charAt(i + 1);
                    if (nextCodePoint >= '\udc00' && nextCodePoint <= '\udfff') {
                        return true;
                    }
                }
            } else if (codePoint >= '\u2600' && codePoint <= '\u27ff') {
                return true;
            }
        }
        return false;

    }

}
