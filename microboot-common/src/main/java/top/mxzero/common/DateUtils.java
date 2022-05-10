package top.mxzero.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @Author zero
 * @Date 2022/2/24
 * @Description 日期时间格式化处理
 */
public class DateUtils {
    private static final String FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DateUtils(){}

    public static String format(Date date){
        return new SimpleDateFormat(FORMAT_PATTERN).format(date);
    }

    public static String format(Date date, String formatPattern){
        return new SimpleDateFormat(formatPattern).format(date);
    }

    public static Date parse(String dateString) {
        Date parse;
        try {
            parse = new SimpleDateFormat(FORMAT_PATTERN).parse(dateString);
        }catch (ParseException e){
            parse = null;
        }
        return parse;
    }

}
