package constant;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER_FOR_DATETIME = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

}