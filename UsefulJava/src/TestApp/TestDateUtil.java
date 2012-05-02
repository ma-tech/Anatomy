package TestApp;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Calendar;

import Utility.DateUtil;
import Utility.CalendarUtil;

public class TestDateUtil {

	public static void main(String args[]){  

		long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	
    	try {
    	   	Date parsed = format.parse(dateString);
            System.out.println("=========   -------------------");
            System.out.println("EXECUTING - TestDateUtil.java on " + parsed.toString());
            System.out.println("=========   -------------------");
            System.out.println("");

            System.out.println("Set Up Date Strings");
            System.out.println("-------------------");
            String dateString1 = "20120229";
            String dateString2 = "29-02-2012";
            String dateString3 = "2012-02-29";
            String dateString4 = "02/29/2012";
            String dateString5 = "2012/02/29";
            String dateString6 = "29 Feb 2012";
            String dateString7 = "29 February 2012";
            String dateString8 = "201202291200";
            String dateString9 = "20120229 1200";
            String dateString10 = "29-02-2012 12:00";
            String dateString11 = "2012-02-29 12:00";
            String dateString12 = "02/29/2012 12:00";
            String dateString13 = "2012/02/29 12:00";
            String dateString14 = "29 Feb 2012 12:00";
            String dateString15 = "29 February 2012 12:00";
            String dateString16 = "20120229120055";
            String dateString17 = "20120229 120055";
            String dateString18 = "29-02-2012 12:00:55";
            String dateString19 = "2012-02-29 12:00:55";
            String dateString20 = "02/29/2012 12:00:55";
            String dateString21 = "2012/02/29 12:00:55";
            String dateString22 = "29 Feb 2012 12:00:55";
            String dateString23 = "29 February 2012 12:00:55";

            System.out.println("");
            System.out.println("Set Up Date Formats");
            System.out.println("-------------------");
    	   	SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format4 = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat format5 = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat format6 = new SimpleDateFormat("dd MMM yyyy");
            SimpleDateFormat format7 = new SimpleDateFormat("dd MMMM yyyy");
            SimpleDateFormat format8 = new SimpleDateFormat("yyyyMMddHHmm");
            SimpleDateFormat format9 = new SimpleDateFormat("yyyyMMdd HHmm");
            SimpleDateFormat format10 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            SimpleDateFormat format11 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat format12 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            SimpleDateFormat format13 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            SimpleDateFormat format14 = new SimpleDateFormat("dd MMM yyyy HH:mm");
            SimpleDateFormat format15 = new SimpleDateFormat("dd MMMM yyyy HH:mm");
            SimpleDateFormat format16 = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat format17 = new SimpleDateFormat("yyyyMMdd HHmmss");
            SimpleDateFormat format18 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat format19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format20 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            SimpleDateFormat format21 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            SimpleDateFormat format22 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            SimpleDateFormat format23 = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

            System.out.println("");
            System.out.println("Parse Date Strings Using Date Formats");
            System.out.println("-------------------------------------");
            Date parsed1 = format1.parse(dateString1);
    	   	Date parsed2 = format2.parse(dateString2);
    	   	Date parsed3 = format3.parse(dateString3);
    	   	Date parsed4 = format4.parse(dateString4);
    	   	Date parsed5 = format5.parse(dateString5);
    	   	Date parsed6 = format6.parse(dateString6);
    	   	Date parsed7 = format7.parse(dateString7);
    	   	Date parsed8 = format8.parse(dateString8);
    	   	Date parsed9 = format9.parse(dateString9);
    	   	Date parsed10 = format10.parse(dateString10);
    	   	Date parsed11 = format11.parse(dateString11);
    	   	Date parsed12 = format12.parse(dateString12);
    	   	Date parsed13 = format13.parse(dateString13);
    	   	Date parsed14 = format14.parse(dateString14);
    	   	Date parsed15 = format15.parse(dateString15);
    	   	Date parsed16 = format16.parse(dateString16);
    	   	Date parsed17 = format17.parse(dateString17);
    	   	Date parsed18 = format18.parse(dateString18);
    	   	Date parsed19 = format19.parse(dateString19);
    	   	Date parsed20 = format20.parse(dateString20);
    	   	Date parsed21 = format21.parse(dateString21);
    	   	Date parsed22 = format22.parse(dateString22);
    	   	Date parsed23 = format23.parse(dateString23);

            System.out.println("");
            System.out.println("Print Out Date Strings Parsed Using Date Formats");
            System.out.println("------------------------------------------------");
            System.out.println("format1.parse(dateString1)   " + parsed1.toString());
            System.out.println("format2.parse(dateString2)   " + parsed2.toString());
            System.out.println("format3.parse(dateString3)   " + parsed3.toString());
            System.out.println("format4.parse(dateString4)   " + parsed4.toString());
            System.out.println("format5.parse(dateString5)   " + parsed5.toString());
            System.out.println("format6.parse(dateString6)   " + parsed6.toString());
            System.out.println("format7.parse(dateString7)   " + parsed7.toString());
            System.out.println("format8.parse(dateString8)   " + parsed8.toString());
            System.out.println("format9.parse(dateString9)   " + parsed9.toString());
            System.out.println("format10.parse(dateString10) " + parsed10.toString());
            System.out.println("format11.parse(dateString11) " + parsed11.toString());
            System.out.println("format12.parse(dateString12) " + parsed12.toString());
            System.out.println("format13.parse(dateString13) " + parsed13.toString());
            System.out.println("format14.parse(dateString14) " + parsed14.toString());
            System.out.println("format15.parse(dateString15) " + parsed15.toString());
            System.out.println("format16.parse(dateString16) " + parsed16.toString());
            System.out.println("format17.parse(dateString17) " + parsed17.toString());
            System.out.println("format18.parse(dateString18) " + parsed18.toString());
            System.out.println("format19.parse(dateString19) " + parsed19.toString());
            System.out.println("format20.parse(dateString20) " + parsed20.toString());
            System.out.println("format21.parse(dateString21) " + parsed21.toString());
            System.out.println("format22.parse(dateString22) " + parsed22.toString());
            System.out.println("format23.parse(dateString23) " + parsed23.toString());

            System.out.println("");
            System.out.println("Parse Date Strings Using DateUtil package");
            System.out.println("-----------------------------------------");
            Date date1 = DateUtil.parse(dateString1);
            Date date2 = DateUtil.parse(dateString2); 
            Date date3 = DateUtil.parse(dateString3); 
            Date date4 = DateUtil.parse(dateString4);
            Date date5 = DateUtil.parse(dateString5);
            Date date6 = DateUtil.parse(dateString6); 
            Date date7 = DateUtil.parse(dateString7);
            Date date8 = DateUtil.parse(dateString8);
            Date date9 = DateUtil.parse(dateString9);
            Date date10 = DateUtil.parse(dateString10);
            Date date11 = DateUtil.parse(dateString11);
            Date date12 = DateUtil.parse(dateString12);
            Date date13 = DateUtil.parse(dateString13);
            Date date14 = DateUtil.parse(dateString14);
            Date date15 = DateUtil.parse(dateString15);
            Date date16 = DateUtil.parse(dateString16);
            Date date17 = DateUtil.parse(dateString17);
            Date date18 = DateUtil.parse(dateString18);
            Date date19 = DateUtil.parse(dateString19);
            Date date20 = DateUtil.parse(dateString20);
            Date date21 = DateUtil.parse(dateString21);
            Date date22 = DateUtil.parse(dateString22);
            Date date23 = DateUtil.parse(dateString23);

            System.out.println("");
            System.out.println("Print Out Date Strings Parsed Using DateUtil package");
            System.out.println("----------------------------------------------------");
            System.out.println("date1.toString()  " + date1.toString());
            System.out.println("date2.toString()  " + date2.toString());
            System.out.println("date3.toString()  " + date3.toString());
            System.out.println("date4.toString()  " + date4.toString());
            System.out.println("date5.toString()  " + date5.toString());
            System.out.println("date6.toString()  " + date6.toString());
            System.out.println("date7.toString()  " + date7.toString());
            System.out.println("date8.toString()  " + date8.toString());
            System.out.println("date9.toString()  " + date9.toString());
            System.out.println("date10.toString() " + date10.toString());
            System.out.println("date11.toString() " + date11.toString());
            System.out.println("date12.toString() " + date12.toString());
            System.out.println("date13.toString() " + date13.toString());
            System.out.println("date14.toString() " + date14.toString());
            System.out.println("date15.toString() " + date15.toString());
            System.out.println("date16.toString() " + date16.toString());
            System.out.println("date17.toString() " + date17.toString());
            System.out.println("date18.toString() " + date18.toString());
            System.out.println("date19.toString() " + date19.toString());
            System.out.println("date20.toString() " + date20.toString());
            System.out.println("date21.toString() " + date21.toString());
            System.out.println("date22.toString() " + date22.toString());
            System.out.println("date23.toString() " + date23.toString());

            System.out.println("");
            System.out.println("Convert Dates to Calendars using DateUtil package");
            System.out.println("-------------------------------------------------");
            Calendar calendar1 = DateUtil.toCalendar(date1);
            Calendar calendar2 = DateUtil.toCalendar(date2); 
            Calendar calendar3 = DateUtil.toCalendar(date3); 
            Calendar calendar4 = DateUtil.toCalendar(date4);
            Calendar calendar5 = DateUtil.toCalendar(date5);
            Calendar calendar6 = DateUtil.toCalendar(date6); 
            Calendar calendar7 = DateUtil.toCalendar(date7);
            Calendar calendar8 = DateUtil.toCalendar(date8);
            Calendar calendar9 = DateUtil.toCalendar(date9);
            Calendar calendar10 = DateUtil.toCalendar(date10);
            Calendar calendar11 = DateUtil.toCalendar(date11);
            Calendar calendar12 = DateUtil.toCalendar(date12);
            Calendar calendar13 = DateUtil.toCalendar(date13);
            Calendar calendar14 = DateUtil.toCalendar(date14);
            Calendar calendar15 = DateUtil.toCalendar(date15);
            Calendar calendar16 = DateUtil.toCalendar(date16);
            Calendar calendar17 = DateUtil.toCalendar(date17);
            Calendar calendar18 = DateUtil.toCalendar(date18);
            Calendar calendar19 = DateUtil.toCalendar(date19);
            Calendar calendar20 = DateUtil.toCalendar(date20);
            Calendar calendar21 = DateUtil.toCalendar(date21);
            Calendar calendar22 = DateUtil.toCalendar(date22);
            Calendar calendar23 = DateUtil.toCalendar(date23);

            System.out.println("");
            System.out.println("Print Out Converted Calendars");
            System.out.println("-----------------------------");
            System.out.println("calendar1.getTime()  " + calendar1.getTime());
            System.out.println("calendar2.getTime()  " + calendar2.getTime());
            System.out.println("calendar3.getTime()  " + calendar3.getTime());
            System.out.println("calendar4.getTime()  " + calendar4.getTime());
            System.out.println("calendar5.getTime()  " + calendar5.getTime());
            System.out.println("calendar6.getTime()  " + calendar6.getTime());
            System.out.println("calendar7.getTime()  " + calendar7.getTime());
            System.out.println("calendar8.getTime()  " + calendar8.getTime());
            System.out.println("calendar9.getTime()  " + calendar9.getTime());
            System.out.println("calendar10.getTime() " + calendar10.getTime());
            System.out.println("calendar11.getTime() " + calendar11.getTime());
            System.out.println("calendar12.getTime() " + calendar12.getTime());
            System.out.println("calendar13.getTime() " + calendar13.getTime());
            System.out.println("calendar14.getTime() " + calendar14.getTime());
            System.out.println("calendar15.getTime() " + calendar15.getTime());
            System.out.println("calendar16.getTime() " + calendar16.getTime());
            System.out.println("calendar17.getTime() " + calendar17.getTime());
            System.out.println("calendar18.getTime() " + calendar18.getTime());
            System.out.println("calendar19.getTime() " + calendar19.getTime());
            System.out.println("calendar20.getTime() " + calendar20.getTime());
            System.out.println("calendar21.getTime() " + calendar21.getTime());
            System.out.println("calendar22.getTime() " + calendar22.getTime());
            System.out.println("calendar23.getTime() " + calendar23.getTime());
            
            System.out.println("");
            System.out.println("Date Arithmetic using DateUtil package");
            System.out.println("--------------------------------------");
            Date date24 = DateUtil.addYears(date1, 1);
            Date date25 = DateUtil.addMonths(date2, 1); 
            Date date26 = DateUtil.addDays(date3, 1); 
            Date date27 = DateUtil.addHours(date4, 1);
            Date date28 = DateUtil.addMinutes(date5, 1);
            Date date29 = DateUtil.addSeconds(date6, 1); 
            Date date30 = DateUtil.addMillis(date7, 1);
            
            System.out.println("date24.toString() " + date24.toString());
            System.out.println("date25.toString() " + date25.toString());
            System.out.println("date26.toString() " + date26.toString());
            System.out.println("date27.toString() " + date27.toString());
            System.out.println("date28.toString() " + date28.toString());
            System.out.println("date29.toString() " + date29.toString());
            System.out.println("date30.toString() " + date30.toString());
            
            Calendar calendar24 = DateUtil.toCalendar(date24);
            Calendar calendar25 = DateUtil.toCalendar(date25);
            Calendar calendar26 = DateUtil.toCalendar(date26);
            Calendar calendar27 = DateUtil.toCalendar(date27);
            Calendar calendar28 = DateUtil.toCalendar(date28);
            Calendar calendar29 = DateUtil.toCalendar(date29);
            Calendar calendar30 = DateUtil.toCalendar(date30);

            System.out.println("calendar24.getTime() " + calendar24.getTime());
            System.out.println("calendar25.getTime() " + calendar25.getTime());
            System.out.println("calendar26.getTime() " + calendar26.getTime());
            System.out.println("calendar27.getTime() " + calendar27.getTime());
            System.out.println("calendar28.getTime() " + calendar28.getTime());
            System.out.println("calendar29.getTime() " + calendar29.getTime());
            System.out.println("calendar30.getTime() " + calendar30.getTime());

            System.out.println("");
            System.out.println("Calendar Arithmetic using CalendarUtil package");
            System.out.println("----------------------------------------------");
            CalendarUtil.addYears(calendar1, 1);
            CalendarUtil.addMonths(calendar2, 1); 
            CalendarUtil.addDays(calendar3, 1); 
            CalendarUtil.addHours(calendar4, 1);
            CalendarUtil.addMinutes(calendar5, 1);
            CalendarUtil.addSeconds(calendar6, 1); 
            CalendarUtil.addMillis(calendar7, 1);

            System.out.println("calendar1.getTime() " + calendar1.getTime());
            System.out.println("calendar2.getTime() " + calendar2.getTime());
            System.out.println("calendar3.getTime() " + calendar3.getTime());
            System.out.println("calendar4.getTime() " + calendar4.getTime());
            System.out.println("calendar5.getTime() " + calendar5.getTime());
            System.out.println("calendar6.getTime() " + calendar6.getTime());
            System.out.println("calendar7.getTime() " + calendar7.getTime());

            System.out.println("");
            System.out.println("Date Comparison using DateUtil package");
            System.out.println("--------------------------------------");
            boolean b1 = DateUtil.sameYear(date1, date2);
            boolean b2 = DateUtil.sameMonth(date3, date4);
            boolean b3 = DateUtil.sameDay(date5, date6);
            boolean b4 = DateUtil.sameHour(date7, date8);
            boolean b5 = DateUtil.sameMinute(date9, date10);
            boolean b6 = DateUtil.sameSecond(date11, date12);

            if (b1) {
            	System.out.println("DateUtil.sameYear(date1, date2) is TRUE");
                System.out.println("calendar1  " + calendar1.get(Calendar.YEAR));
                System.out.println("calendar2  " + calendar2.get(Calendar.YEAR));
            }
            else {
            	System.out.println("DateUtil.sameYear(date1, date2) is FALSE");
                System.out.println("calendar1  " + calendar1.get(Calendar.YEAR));
                System.out.println("calendar2  " + calendar2.get(Calendar.YEAR));
            }

            if (b2) {
            	System.out.println("DateUtil.sameMonth(date3, date4) is TRUE");
                System.out.println("calendar3  " + calendar3.get(Calendar.MONTH));
                System.out.println("calendar4  " + calendar4.get(Calendar.MONTH));
            }
            else {
            	System.out.println("DateUtil.sameMonth(date3, date4) is FALSE");
                System.out.println("calendar3  " + calendar3.get(Calendar.MONTH));
                System.out.println("calendar4  " + calendar4.get(Calendar.MONTH));
            }

            if (b3) {
            	System.out.println("DateUtil.sameDay(date5, date6) is TRUE");
                System.out.println("calendar5  " + calendar5.get(Calendar.DAY_OF_MONTH));
                System.out.println("calendar6  " + calendar6.get(Calendar.DAY_OF_MONTH));
            }
            else {
            	System.out.println("DateUtil.sameDay(date5, date6) is FALSE");
                System.out.println("calendar5  " + calendar5.get(Calendar.DAY_OF_MONTH));
                System.out.println("calendar6  " + calendar6.get(Calendar.DAY_OF_MONTH));
            }

            if (b4) {
            	System.out.println("DateUtil.sameHour(date7, date8) is TRUE");
                System.out.println("calendar7  " + calendar7.get(Calendar.HOUR));
                System.out.println("calendar8  " + calendar8.get(Calendar.HOUR));
            }
            else {
            	System.out.println("DateUtil.sameHour(date7, date8) is FALSE");
                System.out.println("calendar7  " + calendar7.get(Calendar.HOUR));
                System.out.println("calendar8  " + calendar8.get(Calendar.HOUR));
            }

            if (b5) {
            	System.out.println("DateUtil.sameMinute(date9, date10) is TRUE");
                System.out.println("calendar9  " + calendar9.get(Calendar.MINUTE));
                System.out.println("calendar10  " + calendar10.get(Calendar.MINUTE));
            }
            else {
            	System.out.println("DateUtil.sameMinute(date9, date10) is FALSE");
                System.out.println("calendar9  " + calendar9.get(Calendar.MINUTE));
                System.out.println("calendar10 " + calendar10.get(Calendar.MINUTE));
            }

            if (b6) {
            	System.out.println("DateUtil.sameSecond(date11, date12) is TRUE");
                System.out.println("calendar11 " + calendar11.get(Calendar.SECOND));
                System.out.println("calendar12 " + calendar12.get(Calendar.SECOND));
            }
            else {
            	System.out.println("DateUtil.sameSecond(date11, date12) is FALSE");
                System.out.println("calendar11  " + calendar11.get(Calendar.SECOND));
                System.out.println("calendar12  " + calendar12.get(Calendar.SECOND));
            }

            System.out.println("");
            System.out.println("Calendar Comparison using CalendarUtil package");
            System.out.println("----------------------------------------------");
            boolean b7 = CalendarUtil.sameYear(calendar1, calendar2);
            boolean b8 = CalendarUtil.sameMonth(calendar3, calendar4);
            boolean b9 = CalendarUtil.sameDay(calendar5, calendar6);
            boolean b10 = CalendarUtil.sameHour(calendar7, calendar8);
            boolean b11 = CalendarUtil.sameMinute(calendar9, calendar10);
            boolean b12 = CalendarUtil.sameSecond(calendar11, calendar12);

            if (b7) {
            	System.out.println("CalendarUtil.sameYear(calendar1, calendar2) is TRUE");
                System.out.println("calendar1  " + calendar1.get(Calendar.YEAR));
                System.out.println("calendar2  " + calendar2.get(Calendar.YEAR));
            }
            else {
            	System.out.println("CalendarUtil.sameYear(calendar1, calendar2) is FALSE");
                System.out.println("calendar1  " + calendar1.get(Calendar.YEAR));
                System.out.println("calendar2  " + calendar2.get(Calendar.YEAR));
            }

            if (b8) {
            	System.out.println("CalendarUtil.sameMonth(calendar3, calendar4) is TRUE");
                System.out.println("calendar3  " + calendar3.get(Calendar.MONTH));
                System.out.println("calendar4  " + calendar4.get(Calendar.MONTH));
            }
            else {
            	System.out.println("CalendarUtil.sameMonth(calendar3, calendar4) is FALSE");
                System.out.println("calendar3  " + calendar3.get(Calendar.MONTH));
                System.out.println("calendar4  " + calendar4.get(Calendar.MONTH));
            }

            if (b9) {
            	System.out.println("CalendarUtil.sameDay(calendar5, calendar6) is TRUE");
                System.out.println("calendar5  " + calendar5.get(Calendar.DAY_OF_MONTH));
                System.out.println("calendar6  " + calendar6.get(Calendar.DAY_OF_MONTH));
            }
            else {
            	System.out.println("CalendarUtil.sameDay(calendar5, calendar6) is FALSE");
                System.out.println("calendar5  " + calendar5.get(Calendar.DAY_OF_MONTH));
                System.out.println("calendar6  " + calendar6.get(Calendar.DAY_OF_MONTH));
            }

            if (b10) {
            	System.out.println("CalendarUtil.sameHour(calendar7, calendar8) is TRUE");
                System.out.println("calendar7  " + calendar7.get(Calendar.HOUR));
                System.out.println("calendar8  " + calendar8.get(Calendar.HOUR));
            }
            else {
            	System.out.println("CalendarUtil.sameHour(calendar7, calendar8) is FALSE");
                System.out.println("calendar7  " + calendar7.get(Calendar.HOUR));
                System.out.println("calendar8  " + calendar8.get(Calendar.HOUR));
            }

            if (b11) {
            	System.out.println("CalendarUtil.sameMinute(calendar9, calendar10) is TRUE");
                System.out.println("calendar9  " + calendar9.get(Calendar.MINUTE));
                System.out.println("calendar10  " + calendar10.get(Calendar.MINUTE));
            }
            else {
            	System.out.println("CalendarUtil.sameMinute(calendar9, calendar10) is FALSE");
                System.out.println("calendar9  " + calendar9.get(Calendar.MINUTE));
                System.out.println("calendar10 " + calendar10.get(Calendar.MINUTE));
            }

            if (b12) {
            	System.out.println("CalendarUtil.sameSecond(calendar11, calendar12) is TRUE");
                System.out.println("calendar11 " + calendar11.get(Calendar.SECOND));
                System.out.println("calendar12 " + calendar12.get(Calendar.SECOND));
            }
            else {
            	System.out.println("CalendarUtil.sameSecond(calendar11, calendar12) is FALSE");
                System.out.println("calendar11  " + calendar11.get(Calendar.SECOND));
                System.out.println("calendar12  " + calendar12.get(Calendar.SECOND));
            }

            System.out.println("");
            System.out.println("More Date Comparisons using DateUtil package");
            System.out.println("--------------------------------------------");
            int i1 = DateUtil.elapsedYears(date1, date2);
            int i2 = DateUtil.elapsedMonths(date3, date4);
            int i3 = DateUtil.elapsedDays(date5, date6);
            int i4 = DateUtil.elapsedHours(date7, date8);
            int i5 = DateUtil.elapsedMinutes(date9, date10);
            int i6 = DateUtil.elapsedSeconds(date11, date12);
            long l1 = DateUtil.elapsedMillis(date13, date14);
            int[] intArray = DateUtil.elapsedTime(date15, date16);

            System.out.println("DateUtil.elapsedYears(date1, date2) = " + i1 + " years");
            System.out.println("DateUtil.elapsedMonths(date3, date4) = " + i2 + " months");
            System.out.println("DateUtil.elapsedDays(date5, date6) = " + i3 + " days");
            System.out.println("DateUtil.elapsedHours(date7, date8) = " + i4 + " hours");
            System.out.println("DateUtil.elapsedMinutes(date9, date10) = " + i5 + " minutes");
            System.out.println("DateUtil.elapsedSeconds(date11, date12) = " + i6 + " seconds");
            System.out.println("DateUtil.elapsedMillis(date13, date14) = " + l1 + " milliseconds");
            
            String appendStr = "";
            
            for (int i = 0; i < intArray.length; i++) {
            	if (i == 0) { appendStr = " Years"; }
            	if (i == 1) { appendStr = " Months"; }
            	if (i == 2) { appendStr = " Days"; }
            	if (i == 3) { appendStr = " Hours"; }
            	if (i == 4) { appendStr = " Minutes"; }
            	if (i == 5) { appendStr = " Seconds"; }
                System.out.println("DateUtil.elapsedTime(date15, date16) = " + intArray[i] + appendStr);
            }
            
            System.out.println("");
            System.out.println("More Calendar Comparisons using CalendarUtil package");
            System.out.println("----------------------------------------------------");
            int i7 = CalendarUtil.elapsedYears(calendar2, calendar1);
            int i8 = CalendarUtil.elapsedMonths(calendar4, calendar3);
            int i9 = CalendarUtil.elapsedDays(calendar6, calendar5);
            int i10 = CalendarUtil.elapsedHours(calendar7, calendar8);
            int i11 = CalendarUtil.elapsedMinutes(calendar9, calendar10);
            int i12 = CalendarUtil.elapsedSeconds(calendar11, calendar12);
            long l2 = CalendarUtil.elapsedMillis(calendar13, calendar14);
            int[] intArray2 = CalendarUtil.elapsedTime(calendar15, calendar16);

            System.out.println("CalendarUtil.elapsedYears(calendar2, calendar1) = " + i7 + " years");
            System.out.println("CalendarUtil.elapsedMonths(calendar4, calendar3) = " + i8 + " months");
            System.out.println("CalendarUtil.elapsedDays(calendar6, calendar5) = " + i9 + " days");
            System.out.println("CalendarUtil.elapsedHours(calendar7, calendar8) = " + i10 + " hours");
            System.out.println("CalendarUtil.elapsedMinutes(calendar9, calendar10) = " + i11 + " minutes");
            System.out.println("CalendarUtil.elapsedSeconds(calendar11, calendar12) = " + i12 + " seconds");
            System.out.println("CalendarUtil.elapsedMillis(calendar13, calendar14) = " + l2 + " milliseconds");
            
            String appendStr2 = "";
            
            for (int i = 0; i < intArray2.length; i++) {
            	if (i == 0) { appendStr2 = " Years"; }
            	if (i == 1) { appendStr2 = " Months"; }
            	if (i == 2) { appendStr2 = " Days"; }
            	if (i == 3) { appendStr2 = " Hours"; }
            	if (i == 4) { appendStr2 = " Minutes"; }
            	if (i == 5) { appendStr2 = " Seconds"; }
                System.out.println("CalendarUtil.elapsedTime(calendar15, calendar16) = " + intArray2[i] + appendStr2);
            }

            long endTime = System.currentTimeMillis();
        	long duration = endTime - startTime;

            System.out.println("");
            System.out.println("====       ------------------");
            System.out.println("DONE ----- TestDateUtil.java took " + duration + " milliseconds");
            System.out.println("====       ------------------");

    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

	}  
	
}
