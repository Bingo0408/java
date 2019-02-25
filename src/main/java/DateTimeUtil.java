import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: Bin GU
 * @Date: 2019/2/25 15:08
 * @Version Initial Version
 */
public class DateTimeUtil {

    public static String YYYYMMDD_0 = "yyyy-MM-dd";
    public static String YYYYMMDD_1 = "yyyyMMdd";

    /** yyyy-MM-dd => 2019-02-25 */
    public static String today(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /** @param offset : 1
     *  @param pattern: yyyy-MM-dd
     *  @return       : 2019-02-26 */
    public static String todayPlusOffset(long offset, String pattern) {
        return LocalDate.now().plusDays(offset).format(DateTimeFormatter.ofPattern(pattern));
    }

    /** @param offset: 1
     *  @return      : Date(2019-02-26) */
    public static Date todayPlusOffset(long offset) {
        return Date.from(LocalDate.now().plusDays(offset).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /** Date(2019-02-25), 1, yyyy-MM-dd => 2019-02-26 */
    public static String dayPlusOffset(Date date, long offset, String pattern) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(offset).format(DateTimeFormatter.ofPattern(pattern));
    }

    /** Date(2019-02-25), 1 => Date(2019-02-26) */
    public static Date dayPlusOffset(Date date, int offset) {
        return toDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(offset));
    }

    /** Date => LocalDate */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /** LocalDate => Date */
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /** Date(2019-02-25), Date(2019-02-26) => 1 */
    public static int daysBetween(Date date1, Date date2) {
        LocalDate start = toLocalDate(date1);
        LocalDate end = toLocalDate(date2);
        int daysBetween = Period.between(start, end).getDays();
        assert(daysBetween >= 0);
        return daysBetween;
    }
}
