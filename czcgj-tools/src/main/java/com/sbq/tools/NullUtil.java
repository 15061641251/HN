package com.sbq.tools;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author wuyy
 * @version 1.0 2010-11-12
 */
public class NullUtil {
    private static Logger logger = LoggerFactory.getLogger(NullUtil.class);


    /**
     * 判断是否为空,可变参数,如果其中一个为空,则返回true
     *
     * @return
     */
    public static boolean isBlank(String... strs) {

        boolean flag = false;

        for (String str : strs) {
            if (StringUtils.isBlank(str)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断对象是否为null。
     *
     * 集合和数组如果大小为0，也返回true
     * 字符串如果是空字符串或者"null",返回true
     *
     * @param o
     * @return
     */
    public static boolean isNull(Object o) {
        if (o == null) {
            return true;
        }

        if (o instanceof String) {
            String s = (String) o;
            if (s.trim().equals("")) {
                return true;
            }
            if (s.trim().equals("null")) {
                return true;
            }
            return false;
        } else if (o instanceof Object[]) {
            return ((Object[]) o).length == 0;
        } else if (o instanceof Collection<?>) {
            return ((Collection<?>) o).isEmpty();
        } else if (o instanceof Map<?, ?>) {
            return ((Map<?, ?>) o).isEmpty();
        }
        return false;
    }

    /**
     * 判断两个对象是否相等
     *
     * @param o1
     * @param o2
     * @return
     */
    public static boolean equal(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 != null && o1.equals(o2)) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字符串是否为数字
     *
     * @param s
     * @return
     */
    public static boolean isNumber(String s) {
        if (isNull(s)) {
            return false;
        }

        Pattern p = Pattern.compile("\\d+\\.?\\d*|\\d*\\.?\\d+");
        Matcher m = p.matcher(s);
        if (!m.matches()) {
            return false;
        }

        return true;
    }

    /**
     * 验证一个字符串能否被转换成相应的整数、浮点数
     *
     * @param s
     * @param type
     * @return
     */
    public static boolean isInvalidateNumber(String s, Class<?> type) {
        if (isNull(s)) {
            return false;
        }

        try {
            if (type == byte.class) {
                Byte.parseByte(s);
            } else if (type == short.class) {
                Short.parseShort(s);
            } else if (type == int.class) {
                Integer.parseInt(s);
            } else if (type == long.class) {
                Long.parseLong(s);
            } else if (type == float.class) {
                Float.parseFloat(s);
            } else if (type == double.class) {
                Double.parseDouble(s);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 字符串分隔为set
     *
     * @param s
     * @param split
     * @return
     */
    public static Set<String> string2Set(String s, String split) {
        Set<String> set = new HashSet<String>();
        String arr[] = s.split(split);
        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }
        return set;
    }

    /**
     * 将数组、集合、map的value连接成字符串 <功能详细描述>
     *
     * @param array
     * @param prefix
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static String JoinArray(Object array, String prefix) {
        StringBuilder sb = new StringBuilder();
        if (!isNull(array)) {
            String fix = prefix == null ? "" : prefix;
            if (array instanceof Object[]) {
                for (Object o : (Object[]) array) {
                    sb.append(String.valueOf(o)).append(fix);
                }
            } else if (array instanceof Collection) {
                for (Object o : (Collection<?>) array) {
                    sb.append(String.valueOf(o)).append(fix);
                }
            } else if (array instanceof Map) {
                for (Map.Entry<Object, Object> e : ((Map<Object, Object>) array).entrySet()) {
                    sb.append(String.valueOf(e.getValue())).append(prefix);
                }
            }

            // 去掉最后一个分隔符
            if (sb.length() != 0) {
                sb = sb.delete(sb.length() - prefix.length(), sb.length());
            }
        }
        return sb.toString();
    }

    /**
     * 将null转为空字符串
     *
     * @param source
     * @return
     */
    public static String nullToEmpty(String source) {
        if (isNull(source)) {
            source = "";
        }
        return source;
    }

    public static String toString(Object o) {
        if (o == null) {
            logger.warn("the object is null");
            return "";
        }
        if (o.getClass().getName().startsWith("java") || o.getClass().getName().startsWith("javax")) {
            return o.toString();
        }
        StringBuffer result = new StringBuffer();
        try {
            Class<? extends Object> entityClass = o.getClass();
            result.append(entityClass.getName()).append("[");
            PropertyDescriptor[] props = Introspector.getBeanInfo(o.getClass(), Object.class).getPropertyDescriptors();
            for (int i = 0; i < props.length; i++) {
                Object value = props[i].getReadMethod().invoke(o);
                if (i > 0) {
                    result.append(",");
                }
                result.append(props[i].getName()).append("=").append(value);
            }
            result.append("]");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 字符串一去除字符串二的内容，两字符串都是以逗号分隔
     *
     * @param s1
     * @param s2
     * @return 去除后的字符串，并且顺序保持不变，以逗号分隔
     */
    public static String cutString(String s1, String s2) {
        String s1s[] = s1.split(",");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < s1s.length; i++) {
            list.add(s1s[i]);
        }
        String s2s[] = s2.split(",");
        for (int i = 0; i < s2s.length; i++) {
            list.remove(s2s[i]);
        }
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            if (result.equals("")) {
                result += list.get(i);
            } else {
                result += "," + list.get(i);
            }
        }
        return result;
    }

    /**
     * 判断一个值处于数组中的位置
     *
     * @param s1
     * @param s2
     * @return
     */
    public static int getArrayIndex(String s1[], String s2) {
        for (int i = 0; i < s1.length; i++) {
            if (s1[i].equals(s2)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 验证日期是否 “yyyy-MM-dd HH:mm:ss”格式的
     *
     * @param date
     * @return
     */
    public static boolean validateDate(String date) {
        Pattern pattern = Pattern.compile("\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static void main(String main[]) {
        System.out.println(isNumber("34-34"));
    }

    /**
     * 随机生成6位验证码
     */
    public static String makeCode() throws Exception {
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        return vcode;
    }


    /**
     * 字符串List通过分隔符,转换成字符串
     *
     * @param a
     * @return
     */
    public static String listToString(List<String> a, String split) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.size(); i++) {
            if (i + 1 == a.size()) {
                result.append(a.get(i).toString());
            } else {
                result.append(a.get(i).toString()).append(split);
            }
        }
        return result.toString();
    }
}
