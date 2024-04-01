package com.example.demo.Utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.dictionary.py.PinyinDictionary;

import java.util.List;

public class PinyinUtil {

    /**
     * 获取中文完整拼音
     *
     * @param chineseStr
     * @return
     */
    public static String getPinyin(String chineseStr) {
        List<Pinyin> pinyins = PinyinDictionary.convertToPinyin(chineseStr);
        StringBuilder stringBuilder = new StringBuilder();
        for (Pinyin pinyin : pinyins) {
            stringBuilder.append(pinyin.getPinyinWithoutTone());
        }
        return stringBuilder.toString();
    }


    /**
     * 获取中文拼音首字母
     *
     * @param chineseStr
     * @return
     */
    public static String getInitial(String chineseStr) {
        List<Pinyin> pinyins = PinyinDictionary.convertToPinyin(chineseStr);
        if (CollectionUtils.isEmpty(pinyins)) {
            return "";
        }
        return String.valueOf(pinyins.get(0).getPinyinWithoutTone().charAt(0));
    }
}
