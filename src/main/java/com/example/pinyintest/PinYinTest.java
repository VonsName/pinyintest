package com.example.pinyintest;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinTest {


    // 简体中文的编码范围从B0A1（45217）->到F7FE（63486）
    private static int START = 45217;
    private static int END = 63486;

    // 按照声母表示，这个表是在GB2312中的出现的第一个汉字,“啊”是代表首字母a的第一个汉字。
    // i, u, v都不做声母, 自定规则跟随前面的字母
    private static char[] charTable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', };

    // 二十六个字母区间对应二十七个端点
    // GB2312码汉字区间十进制表示
    private static int[] table = new int[27];

    // 对应首字母区间表
    private static char[] initTable = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 't', 't', 'w', 'x', 'y', 'z', };

    // 初始化列表
    static {
        for (int i = 0; i < 26; i++) {
            table[i] = gbValue(charTable[i]);// 得到GB2312码的首字母区间端点表，十进制。
        }
        table[26] = END;// 区间表结尾
    }


    public static void main(String[] args) {
//
//        String s = getCnASCII("你好");
//        System.out.println(s);
//        String str="nihao";
//        byte[] bytes = str.getBytes();
//        System.out.println();
//        StringBuffer buf = new StringBuffer();
//        for (int i = 0; i <bytes.length ; i++) {
//            //System.out.println(bytes[i]);
//            buf.append(Integer.toHexString(bytes[i] & 0xff));
//        }
//        System.out.println(buf.toString());

        String nihao = getFirstLetters("",HanyuPinyinCaseType.LOWERCASE);

        System.out.println(nihao);
        System.out.println(toHanyuPinyin("脆皮肠（高技）-包"));
        System.out.println(getFirstLetter("脆皮肠（高技）-包"));
    }


    /**
     * 根据中文汉字返回拼音全拼
     * @param chinese
     * @return
     */
    public static String getFullSpell(String chinese) {
        StringBuffer buffer = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    buffer.append(PinyinHelper.toHanyuPinyinStringArray(arr[i],
                            defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                buffer.append(arr[i]);
            }
        }
        return buffer.toString();
    }


    /**
     * 返回汉字的首字母
     * @param chinese
     * @return
     */
    public static String cn2py(String chinese) {
        String Result = "";
        int StrLength = chinese.length();
        int i;
        try {
            for (i = 0; i < StrLength; i++) {
                Result += Char2Initial(chinese.charAt(i));
            }
        } catch (Exception e) {
            Result = "";
            e.printStackTrace();
        }
        return Result;
    }


    /**
     * 返回ASCII码
     * @param str
     * @return
     */
    public static String getCnASCII(String str){
        StringBuffer buf = new StringBuffer();
        //将字符串转换成字节序列
        byte[] bGBK = str.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            //System.out.println(bGBK[i]);
            //将每个字符转换成ASCII码
            buf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return buf.toString();
    }

    /**
     * 输入字符,得到他的声母,英文字母返回对应的大写字母,其他非简体汉字返回 '0' 　　* 　　
     */
    private static char Char2Initial(char ch) {
        // 对英文字母的处理：小写字母转换为大写，大写的直接返回
        if (ch >= 'a' && ch <= 'z') {
            return (char) (ch - 'a' + 'A');
        }
        if (ch >= 'A' && ch <= 'Z') {
            return ch;
        }
        // 对非英文字母的处理：转化为首字母，然后判断是否在码表范围内，
        // 若不是，则直接返回。
        // 若是，则在码表内的进行判断。
        int gb = gbValue(ch);// 汉字转换首字母
        if ((gb < START) || (gb > END))// 在码表区间之前，直接返回
        {
            return ch;
        }
        int i;
        for (i = 0; i < 26; i++) {// 判断匹配码表区间，匹配到就break,判断区间形如“[,)”
            if ((gb >= table[i]) && (gb < table[i + 1])) {
                break;
            }
        }
        if (gb == END) {// 补上GB2312区间最右端
            i = 25;
        }
        return initTable[i]; // 在码表区间中，返回首字母
    }

    /**
     * 取出汉字的编码　　
     */
    private static int gbValue(char ch) {// 将一个汉字（GB2312）转换为十进制表示。
        String str = new String();
        str += ch;
        try {
            byte[] bytes = str.getBytes("GB2312");
            if (bytes.length < 2) {
                return 0;
            }
            return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将文字转为汉语拼音 全拼
     * @param ChineseLanguage
     * @return
     */
    public static String toHanyuPinyin(String ChineseLanguage){
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部小写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V) ;
        try {
            for (int i=0; i<cl_chars.length; i++){
                if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
                }    /*} else {// 如果字符不是中文,则不转换
                        //hanyupinyin += cl_chars[i];
                    }*/
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
                System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyin;
    }


    /**
     * 每个汉字的首字母
     * @param ChineseLanguage
     * @param caseType
     * @return
     */
    public static String getFirstLetters(String ChineseLanguage,HanyuPinyinCaseType caseType) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(caseType);// 输出拼音全部大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        try {
            for (int i = 0; i < cl_chars.length; i++) {
                String str = String.valueOf(cl_chars[i]);
                if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0].substring(0, 1);
                } else if (str.matches("[0-9]+")) {// 如果字符是数字,取数字
                    hanyupinyin += cl_chars[i];
                } else if (str.matches("[a-zA-Z]+")) {// 如果字符是字母,取字母
                    hanyupinyin += cl_chars[i];
                } else {// 否则不转换
                    //hanyupinyin += cl_chars[i];//如果是标点符号的话，带着
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
                System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyin;
    }

    /**
     94      * 取第一个汉字的第一个字符
     95     * @Title: getFirstLetter
     96     * @Description: TODO
     97     * @return String
     98     * @throws
     99      */
    public static String getFirstLetter(String ChineseLanguage){
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 输出拼音全部大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        try {
            String str = String.valueOf(cl_chars[0]);
            if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                hanyupinyin = PinyinHelper.toHanyuPinyinStringArray(
                        cl_chars[0], defaultFormat)[0].substring(0, 1);;
                } else if (str.matches("[0-9]+")) {// 如果字符是数字,取数字
                hanyupinyin += cl_chars[0];
                } else if (str.matches("[a-zA-Z]+")) {// 如果字符是字母,取字母
                hanyupinyin += cl_chars[0];
                } else {// 否则不转换
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
            }
        return hanyupinyin;
    }
}
