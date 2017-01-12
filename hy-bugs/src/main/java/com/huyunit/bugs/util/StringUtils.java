package com.huyunit.bugs.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 工具类StringUtils
 * User: bobo
 * Date: 13-5-13
 * Time: 下午1:38
 */
public class StringUtils {
    //斜杠
    public static final String SPLIT_XG = "/";
    //反斜杠
    public static final String SPLIT_FXG = "\\";
    //分号
    public static final String SPLIT_FH = ";";
    //冒号
    public static final String SPLIT_MH = ":";
    //横杠
    public static final String SPLIT_HG = " -- ";
    //竖杠
    public static final String SPLIT_SG = "\\|";
    //换行符
    public static final String SPLIT_HHF = "\n";
    //逗号
    public static final String SPLIT_COMMA = ",";

    /**
     * 分列字符串
     *
     * @param s
     * @return
     */
    public static String[] split(String s) {
        return s.split(SPLIT_XG);
    }

    /**
     * 分列字符串
     *
     * @param s
     * @return
     */
    public static String[] split(String s, String regular) {
        return s.split(regular);
    }

    /**
     * 字符串为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return !isNotEmpty(s);
    }

    /**
     * 字符串不为空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    /**
     * 判断是否为null或空值
     *
     * @param str String
     * @return true or false
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }


    /**
     * 去除字符中间空格
     *
     * @param s
     * @return
     */
    public static String repalceEmptyStr(String s) {
        return s.replaceAll(" ", "");
    }

    /**
     * 字符串转成List集合
     *
     * @param texts
     * @return
     */
    public static List<String> stringToList(String[] texts) {
        List<String> ss = new ArrayList<String>();
        for (int i = 0; i < texts.length; i++) {
            ss.add(texts[i]);
        }
        return ss;
    }

    /**
     * 拼接拍照后的图片路劲，分号";" 隔开
     *
     * @param filePathList
     * @return
     */
    public static String joinFilesListToString(List<String> filePathList, String splitString) {
        String filesPath = "";
        if (filePathList != null && filePathList.size() > 0) {
            for (String str : filePathList) {
                if (!filesPath.equals("")) {
                    filesPath += splitString + str;
                } else {
                    filesPath = str;
                }
            }
        }
        return filesPath;
    }

    /**
     * 拆开文件（String）并存入到List集合里
     *
     * @param files
     * @param splitString
     * @return
     */
    public static List<String> detachFilesStringToList(String files, String splitString) {
        List<String> sList = new ArrayList<String>();
        if (files != null && !files.equals("")) {
            if (files.indexOf(splitString) != -1) {
                String[] filePaths = files.split(splitString);
                for (String s : filePaths) {
                    sList.add(s);
                }
            } else {
                sList.add(files);
            }
        }
        return sList;
    }

    /**
     * 在原来的数组中添加第一条字符串
     *
     * @param olds
     * @param s
     * @return
     */
    public static String[] addFirstValue(String[] olds, String s) {
        String[] news = new String[olds.length + 1];
        news[0] = s;
        for (int i = 0; i < olds.length; i++) {
            news[i + 1] = olds[i];
        }
        return news;
    }

    /**
     * 使double不用科学计数法显示
     *
     * @param d
     * @return
     */
    public static String double2String(Double d) {
        if (null == d)
            return "";
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(d);
    }

    /**
     * 获取UUID值
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 查找集合里是否存在指定的字符串
     *
     * @param stringList
     * @param str
     * @return
     */
    public static boolean isExistForList(List<String> stringList, String str) {
        for (String s : stringList) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找集合里是否存在指定的字符串
     *
     * @param set
     * @param id
     * @return
     */
    public static boolean isExistForSet(Set<String> set, long id) {
        if (set.isEmpty()) {
            return false;
        }
        for (String s : set) {
            if (Long.parseLong(s) == id) {
                return true;
            }
        }
        return false;
    }


    @SuppressWarnings("unused")
    public static String string2Decimal(String str) {
        String src = str.split("\\.")[0];
        StringBuilder sb = new StringBuilder();
        char[] strChar = src.toCharArray();
        for (int i = strChar.length - 1; i >= 0; i--) {
            sb.append(strChar[i]);
            if ((strChar.length - i) % 3 == 0) {
                sb.append(",");
            }
        }
        return sb.reverse().toString();
    }

    /**
     * 逗号分隔显示数字
     *
     * @param str
     * @return
     */
    public static String split3Number(String str) {
        if (isEmpty(str))
            return "";
        String[] ss = str.split("\\.");
        str = ss[0];
        int i = ss[0].length();
        while (i > 3) {
            str = str.substring(0, i - 3) + "," + str.substring(i - 3);
            i -= 3;
        }
        if (ss.length > 1) {
            str = str + "." + ss[1];
        }
        return str;
    }

    public static String double2Decimal(BigDecimal decimal) {
        NumberFormat nf = new DecimalFormat(",##0.00");
        return nf.format(decimal);
    }

}
