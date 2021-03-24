package com.glch.base.util;

import com.glch.base.common.BizException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class DateUtils {
    public static final String DEFAULT_FORMAT_DATE_WITHOUT_TIME = "yyyy-MM-dd";
    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    public static final BigDecimal BASETIME = new BigDecimal("60.00");
    public static final Pattern DATE_PATTERN = Pattern.compile("^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])([-/.]?)(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])([-/.]?)(?:29|30)|(?:0?[13578]|1[02])([-/.]?)31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2([-/.]?)29)$");
    public static final String DEFAULT_FORMAT_DATE_WITHOUT_TIME_CN = "yyyy年MM月dd日";
    public static final String DEFAULT_FORMAT_DATE_MD = "MMdd";
    public static final String DEFAULT_FORMAT_DATE_WITHOUT_SPLIT = "yyyyMMddHHmmss";

    /**
     * 获取系统当前时间
     *
     * @param timeFormat
     * @return String
     */
    public static String getCurrentTime(String timeFormat) {
        String format = (timeFormat == null) ? DEFAULT_FORMAT_DATE_WITHOUT_SPLIT : timeFormat;
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String formatDate(Date date, String formatStr) {
        return (new SimpleDateFormat((formatStr == null ? DEFAULT_FORMAT_DATE
                : formatStr))).format(date);
    }

    /*
     * s时间计算
     */
    public static Date addSecondDate(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, seconds);

        return calendar.getTime();
    }

    /**
     * 获取前(num)多少天时间
     */
    public static Date getBeforeDay(int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(cal.DAY_OF_YEAR, -num);
        return cal.getTime();
    }

    /***
     * 将timestamp格式字符串转换为指定格式的timestamp
     * @param timestampStr
     * @param formatStr
     * @return
     * @throws ParseException
     */
    public static Timestamp formatTimestamp(String timestampStr, String formatStr) throws Exception {
        if (null == timestampStr) {
            return null;
        }
        Date date = formatDate(timestampStr, formatStr);
        Timestamp stamp = new Timestamp(date.getTime());
        return stamp;
    }

    /***
     * 将timestamp字符串转换为指定格式的Date
     * @param timestampStr
     * @param formatStr
     * @return
     * @throws ParseException
     */
    public static Date formatDate(String timestampStr, String formatStr) throws ParseException {
        return (new SimpleDateFormat((formatStr == null ? DEFAULT_FORMAT_DATE
                : formatStr))).parse(timestampStr);
    }

    /**
     * 获取前三个月的时间
     *
     * @return
     */
    public static Date getDate() {
        Date now = new Date();
        Date beforeDate = new Date();
        //得到日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        //获取前三个月的时间
        calendar.add(Calendar.MONTH, -3);
        beforeDate = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM:dd HH:mm:ss");
        String defaultDate = format.format(beforeDate);
        Date date = null;
        try {
            date = format.parse(defaultDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间格式yyyy-MM-dd HH:mm:ss转ydb时间格式
     */
    public static String formatToYdbTime(Date dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String date = sdf.format(dateStr);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String lastDate = format.format(dateStr);
        return lastDate;
    }

    /**
     * 将string类型的date转为Date类型的date，在将date类型转为yyyyMMddHHmmss
     */
    public static String formatToYdbTime2(String dateStr) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf1.parse(dateStr);
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date1);
    }

    /***
     * yyyyMMddHHmms格式转为yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatToYdbTime3(String date) {
        String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
        String lastDate = date.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
        return lastDate;
    }

    public static String queryDateByYearMonth(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String arr[] = date.split("-");
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int firstDay = cal.getActualMinimum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        String firstDate = sdf.format(cal.getTime());
        String result1 = (firstDate + " 00:00:00").replaceAll("[[\\s-:punct:]]", "");
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        String lastDate = sdf.format(cal.getTime());
        String result2 = (lastDate + " 23:59:59").replaceAll("[[\\s-:punct:]]", "");
        return result1 + "," + result2;
    }

    /**
     * 计算两个日期间的天数
     */
    public static long daysBetween(Date startTime, Date endTime) {
        Long day = 0L;
        try {
            //将时间转换成毫秒数
            long startTs = startTime.getTime();
            long endTs = endTime.getTime();
            //计算毫秒差，除以一天的总毫秒数，得出天数
            day = (endTs - startTs) / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return day;
    }

    public static String getDayStartTime(Date date) {
        String day = DateUtils.formatDate(date, DateUtils.DEFAULT_FORMAT_DATE_WITHOUT_TIME);
        return day + " 00:00:00";
    }

    public static String getDayEndTime(Date date) {
        String day = DateUtils.formatDate(date, DateUtils.DEFAULT_FORMAT_DATE_WITHOUT_TIME);
        return day + " 23:59:59";
    }

    public static Date getDateBySecond(Long second) {
        Date date = new Date();
        date.setTime(second);
        return date;
    }

    /**
     * 获取固定间隔时刻集合
     *
     * @param start    一个时间段的开始时间
     * @param end      结束时间
     * @param interval 时间间隔(单位：分钟)
     * @return
     */
    public static List<String> getIntervalTimeList(String start, String end, int interval) {
        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);
            while (startDate.getTime() <= endDate.getTime()) {
                list.add(sdf.format(startDate));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.MINUTE, interval);
                if (calendar.getTime().getTime() >= endDate.getTime()) {
                    if (!startDate.equals(endDate)) {
                        list.add(sdf.format(endDate));
                    }
                    startDate = calendar.getTime();
                } else {
                    startDate = calendar.getTime();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取近一个月的所有时间
     *
     * @return
     */
    public static List<String> getDayRange() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> condition = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
         String today = sdf.format(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date m = calendar.getTime();
        String mom = sdf.format(m);

        condition = getBetweenDates(mom, today);
        return condition;
    }

    public static List<String> getBetweenDates(String dateStart, String dateEnd) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dateList = new ArrayList<>();
        try {
            Date dateOne = format.parse(dateStart);
            Date dateTwo = format.parse(dateEnd);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTwo);
            dateList.add(dateEnd);
            while (calendar.getTime().after(dateOne)) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                dateList.add(format.format(calendar.getTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateList;
    }

    /**
     * 获取时间段内所有月份
     *
     * @return
     */
    public static Map<String, List<String>> getMnothBetweenDates(String minDate, String maxDate) throws Exception {
        List<String> result = new ArrayList<>();
        List<String> result1 = new ArrayList<>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1, 0, 0, 0);
        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2, 0, 0, 0);
        Calendar curr = min;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
        while (curr.before(max)) {
            result.add(sdf1.format(curr.getTime()));
            result1.add(sdf2.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        Map<String, List<String>> map = new HashMap<>();
        map.put("data", result);
        map.put("datas", result1);
        return map;
    }

    /**
     * 获取时间段内所有年
     *
     * @return
     */
    public static Map<String, List<String>> getYearBetweenDates(String minDate, String maxDate) throws Exception {
        List<String> result = new ArrayList<>();
        List<String> result1 = new ArrayList<>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), 0, 1, 0, 0, 0);
        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), 1, 1, 0, 0, 0);
        Calendar curr = min;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
        while (curr.before(max)) {
            result.add(sdf1.format(curr.getTime()));
            result1.add(sdf2.format(curr.getTime()));
            curr.add(Calendar.YEAR, 1);
        }
        Map<String, List<String>> map = new HashMap<>();
        map.put("data", result);
        map.put("datas", result1);
        return map;
    }

    /**
     * 获取时间段内所有日
     *
     * @return
     */
    public static Map<String, List<String>> getDayBetweenDates(String minDate, String maxDate) throws Exception {
        List<String> result = new ArrayList<>();
        List<String> result1 = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = null;
        Date end = null;
        begin = sdf.parse(minDate);
        end = sdf.parse(maxDate);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd000000");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        result.add(sdf1.format(begin));
        result1.add(sdf2.format(begin));
        Calendar min = Calendar.getInstance();
        min.setTime(sdf.parse(minDate));
        Calendar max = Calendar.getInstance();
        max.setTime(sdf.parse(maxDate));
        while (max.after(min)) {
            min.add(Calendar.DAY_OF_MONTH, 1);
            String day = sdf1.format(min.getTime());
            String day1 = sdf2.format(min.getTime());
            result.add(day);
            result1.add(day1);
        }
        Map<String, List<String>> map = new HashMap<>();
        map.put("data", result);
        map.put("datas", result1);
        return map;
    }

    /**
     * 获取时间段内所有小时
     *
     * @return
     */
    public static Map<String, List<String>> getHourBetweenDates(String minDate, String maxDate) throws Exception {
        List<String> result = new ArrayList<>();
        List<String> result1 = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = null;
        begin = sdf.parse(minDate);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHH0000");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH");
        result.add(sdf1.format(begin));
        result1.add(sdf2.format(begin));
        Calendar min = Calendar.getInstance();
        min.setTime(sdf.parse(minDate));
        Calendar max = Calendar.getInstance();
        max.setTime(sdf.parse(maxDate));
        while (max.after(min)) {
            min.add(Calendar.HOUR_OF_DAY, 1);
            String day = sdf1.format(min.getTime());
            String day1 = sdf2.format(min.getTime());
            result.add(day);
            result1.add(day1);
        }
        Map<String, List<String>> map = new HashMap<>();
        map.put("data", result);
        map.put("datas", result1);
        return map;
    }

    /**
     * 获取某年某月的第一天的日期字符串(如2013-08-01)
     */
    public static String getFirstDayText(Integer year, Integer month) {
        if (month < 10) {
            return year + "-0" + month + "-01 00:00:00";
        }
        return year + "-" + month + "-01" + " 00:00:00";
    }


    /**
     * 取所在日期的星期第一天，星期日
     *
     * @param date weifengdeng 2015-3-10
     */
    public static Date getFirstWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//本周第一天，星期日
        return cal.getTime();
    }

    /**
     * 取所在日期的星期最后一天，星期六
     *
     * @param date
     */
    public static Date getLastWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);//本周最后一天，星期六
        return cal.getTime();
    }

    /**
     * 获取某年某月的最后一天的日期字符串(如2013-08-31)
     */
    public static String getLastDayText(Integer year, Integer month) {
        return formatDate(getLastDay(year, month), DEFAULT_FORMAT_DATE_WITHOUT_TIME + " 23:59:59");
    }

    public static Date getLastDay(Integer year, Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取同比时间
     *
     * @param time 时间
     * @param n    年数
     * @return
     */
    public static String getYearBefore(String time, int n) {
        String dateStr = "";
        try {
            //日期实例化
            Calendar calendar = Calendar.getInstance();
            Date startTime = DateUtils.formatDate(time, DateUtils.DEFAULT_FORMAT_DATE);
            calendar.setTime(startTime);
            calendar.add(Calendar.YEAR, -n);
            Date date = calendar.getTime();
            if (null != date) {
                dateStr = formatDate(date, DEFAULT_FORMAT_DATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return dateStr;
    }

    /**
     * 获取时间段
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param n     时间段个数
     * @return
     * @throws ParseException
     */
    public static Map<String, Object> getBeforeTimeQuantum(String start, String end, int n) throws ParseException {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("startTime" + 0, start);
        resultMap.put("endTime" + 0, end);
        //时间对象实例化
        Calendar calendar = Calendar.getInstance();
        Date startTime = DateUtils.formatDate(start, DateUtils.DEFAULT_FORMAT_DATE);
        Date endTime = DateUtils.formatDate(end, DateUtils.DEFAULT_FORMAT_DATE);
        //获取起始时间和截止时间的毫秒差
        long between = 0;
        if (end.endsWith("59")) {
            between = endTime.getTime() + 1000 - startTime.getTime();
        } else {
            between = endTime.getTime() - startTime.getTime();
        }
        //初始化起止时间
        Date lastTimeFromDate = null;
        Date lastTimeToDate = null;
        for (int i = 1; i <= n; i++) {
            if (i == 1) {//获取上一时间段的起止时间
                /**
                 * 因使用new Date(mills)存在1s的时间误差，故用Calendar来获取目标时间
                 */
                calendar.setTimeInMillis(startTime.getTime() - between);
                lastTimeFromDate = calendar.getTime();
                calendar.setTimeInMillis(endTime.getTime() - between);
                lastTimeToDate = calendar.getTime();
            } else {
                calendar.setTimeInMillis(lastTimeFromDate.getTime() - between);
                lastTimeFromDate = calendar.getTime();
                calendar.setTimeInMillis(lastTimeToDate.getTime() - between);
                lastTimeToDate = calendar.getTime();
            }
            //将时间转换为字符串
            String from = DateUtils.formatDate(lastTimeFromDate, DateUtils.DEFAULT_FORMAT_DATE);
            String to = DateUtils.formatDate(lastTimeToDate, DateUtils.DEFAULT_FORMAT_DATE);
            //将转换结果放入时间参数
            resultMap.put("startTime" + i, from);
            resultMap.put("endTime" + i, to);
        }
        return resultMap;
    }

    /**
     * 获取时间段列表
     * @param start 开始时间
     * @param end 结束时间
     * @param type 数据类型
     * @return
     */
    public static List<String> getInitList(String start,String end,Integer type){
        List<String> initList = new ArrayList();
        if(StringUtil.isEmpty(start) && StringUtil.isEmpty(end)){
            return null;
        }
        try {
            Date startTime = StringUtil.isEmpty(start) ? new Date() : DateUtils.formatDate(start,DateUtils.DEFAULT_FORMAT_DATE);
            Date endTime = StringUtil.isEmpty(end) ? new Date() : DateUtils.formatDate(end,DateUtils.DEFAULT_FORMAT_DATE);
            //获取开始时间年、月、日
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);
            int startYear = startCalendar.get(Calendar.YEAR);
            int startMonth = startCalendar.get(Calendar.MONTH) + 1;
            int startMonthDay = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
            //获取结束时间年、月、日
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);
            int endYear = endCalendar.get(Calendar.YEAR);
            int endMonth = endCalendar.get(Calendar.MONTH) + 1;
            int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
            if(1 == type){//年
                for(int i = startYear;i <= endYear; i++){
                    if(initList.indexOf(i) == -1){
                        initList.add(String.valueOf(i));
                    }
                }
            }else if(2 == type){//月
                if(startYear == endYear){//开始时间和结束时间在同一年
                    for(int i = startMonth;i <= endMonth; i++){
                        String startMonthStr = i < 10 ? "0" + i : String.valueOf(i);
                        initList.add(startYear + startMonthStr);
                    }
                }else{//开始时间和结束时间不在同一年
                    for(int i = startMonth;i <= 12; i++){
                        String startMonthStr = i < 10 ? "0" + i : String.valueOf(i);
                        initList.add(startYear + startMonthStr);
                    }
                    for(int i = 1;i <= endMonth; i++){
                        String endMonthStr = i < 10 ? "0" + i : String.valueOf(i);
                        initList.add(endYear + endMonthStr);
                    }
                }
            }else if(3 == type){//日
                //TODO 按日分组
                long daysBetween = daysBetween(startTime,endTime);
                if(daysBetween > 30){
                    throw new BizException("获取日期不能大于30天!");
                }
                if(startMonth != endMonth){
                    for(int i = startDay;i <= startMonthDay; i ++){
                        if(i < 10){
                            String startMonthStr = i < 10 ? "0" + i : String.valueOf(i);
                            initList.add(startYear + startMonthStr + "0" + String.valueOf(i));
                        }else{
                            initList.add(startYear + i + String.valueOf(i));
                        }
                    }
                    for(int i = 1;i <= endDay; i ++){
                        if(i < 10){
                            String endMonthStr = i < 10 ? "0" + i : String.valueOf(i);
                            initList.add(endYear + endMonthStr + "0" + String.valueOf(i));
                        }else{
                            initList.add(endYear + i + String.valueOf(i));
                        }
                    }
                }else{
                    String monthStr = startMonth < 10 ? "0" + startMonth : String.valueOf(startMonth);
                    for(int i = startDay;i <= endDay; i ++){
                        if(i < 10){
                            initList.add(startYear + monthStr + "0" + String.valueOf(i));
                        }else{
                            initList.add(startYear + monthStr + String.valueOf(i));
                        }
                    }
                }
            }else if(4 == type){//环比时间段
                //TODO 自定义时间段分组
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return initList;
    }

    public static List<String> getDayList(String start,String end,int n){
        List<String> resultList = new ArrayList<>();
        if(n <= 0){
            return resultList;
        }
        List<String> list = getInitList(start,end,3);
        if(null != list && list.size() > n){
            resultList.addAll(list.subList(list.size() - n,list.size()));
        }
        return resultList;
    }

    /**
     * 获取指定年份的第一天
     * @param year
     * @return
     */
    public static String getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        Date yearFirst = calendar.getTime();
        return DateUtils.formatDate(yearFirst,DateUtils.DEFAULT_FORMAT_DATE_WITHOUT_TIME) + " 00:00:00";
    }

    /**
     * 获取指定年份的最后一天
     * @param year
     * @return
     */
    public static String getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.roll(Calendar.DAY_OF_YEAR,-1);
        Date yearLast = calendar.getTime();
        return DateUtils.formatDate(yearLast,DateUtils.DEFAULT_FORMAT_DATE_WITHOUT_TIME) + " 23:59:59";
    }

    /**
     * 获取当前年份第一天
     * @return
     */
    public static String getCurYearFirst(){
        Calendar calendar = Calendar.getInstance();
        return getYearFirst(calendar.get(Calendar.YEAR));
    }

    /**
     * 获取当前年份最后一天
     * @return
     */
    public static String getCurYearLast(){
        Calendar calendar = Calendar.getInstance();
        return getYearLast(calendar.get(Calendar.YEAR));
    }

    /**
     * 获取指定月份的第一天
     * @param year
     * @return
     */
    public static String getMonthFirst(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        Date yearFirst = calendar.getTime();
        return DateUtils.formatDate(yearFirst,DateUtils.DEFAULT_FORMAT_DATE_WITHOUT_TIME) + " 00:00:00";
    }

    /**
     * 获取指定月份的最后一天
     * @param year
     * @return
     */
    public static String getMonthLast(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.roll(Calendar.DAY_OF_MONTH,-1);
        Date yearLast = calendar.getTime();
        return DateUtils.formatDate(yearLast,DateUtils.DEFAULT_FORMAT_DATE_WITHOUT_TIME) + " 23:59:59";
    }

    /**
     * 获取当前月第一天
     * @return
     */
    public static String getCurMonthFirst(){
        Calendar calendar = Calendar.getInstance();
        return getMonthFirst(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH));
    }

    /**
     * 获取当前月最后一天
     * @return
     */
    public static String getCurMonthLast(){
        Calendar calendar = Calendar.getInstance();
        return getMonthLast(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH));
    }

    public static String stringDateFormate(String datetime){
        LocalDateTime ldt = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String dt = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return dt;
    }
}
