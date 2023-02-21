package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {

	private static final String LANGUAGE_TAG = "en";
	private static final int YEAR_OFFSET = 10;
	private static final int WIDTH_FIELD = 4;
	private static Locale locale = Locale.forLanguageTag(LANGUAGE_TAG);

	private static record Date(int month, int year, int firstDay) {
		Date(int month, int year) {
			this(month, year, 0);
		}
	}

	public static void main(String[] args) {
		try {
			Date date = getDate(args);
			printCalendar(date);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static Date getDate(String[] args) throws Exception {
		return args.length == 0 ? getCurrentDate() : getRequiredDate(args);
	}

	private static Date getRequiredDate(String[] args) throws Exception {
		return new Date(getRequiredMonth(args), getRequierdYear(args), getRequiredFirstDay(args));
	}

	private static int getRequiredFirstDay(String[] args) throws Exception {
		int res = 0;
		try {
			if (args.length > 2) {
				res = DayOfWeek.valueOf(args[2].toUpperCase()).ordinal();
			}
		} catch (Exception e) {
			throw new Exception(
					"First day must be one of [Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday]");
		}
		return res;
	}

	private static int getRequiredMonth(String[] args) throws Exception {
		try {
			int res = Integer.parseInt(args[0]);
			if (res < 1 || res > 12) {
				throw new Exception("Month should be a number in the range [1-12]");
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("Month should be a number");
		}

	}

	private static int getRequierdYear(String[] args) throws Exception {
		int res = LocalDate.now().getYear();
		if (args.length > 1) {
			try {
				res = Integer.parseInt(args[1]);
				if (res < 0) {
					throw new Exception("year must be a positive number");
				}
			} catch (NumberFormatException e) {
				throw new Exception("year must be a number");
			}
		}
		return res;
	}

	private static Date getCurrentDate() {
		LocalDate current = LocalDate.now();
		return new Date(current.getMonthValue(), current.getYear());
	}

	private static void printCalendar(Date date) {
		printTitle(date);
		printWeekDays(date.firstDay());
		printDates(date);
	}

	private static void printDates(Date date) {
		int weekDayNumber = getFirstDay(date);
		int offset = getOffset(weekDayNumber);
		int nDays = YearMonth.of(date.year(), date.month()).lengthOfMonth();

		System.out.printf("%s", " ".repeat(offset));
		for (int day = 1; day <= nDays; day++) {
			System.out.printf("%4d", day);
			if (++weekDayNumber > 7) {
				System.out.println();
				weekDayNumber = 1;
			}
		}
	}

	private static int getOffset(int weekDayNumber) {
		return (weekDayNumber - 1) * WIDTH_FIELD;
	}

	private static int getFirstDay(Date date) {
		return LocalDate.of(date.year(), date.month(), 1).getDayOfWeek().minus(date.firstDay()).getValue();
	}

	private static void printWeekDays(int firstDay) {
		System.out.print("  ");
		Arrays.stream(DayOfWeek.values()).map(day -> day.plus(firstDay))
				.forEach(day -> System.out.printf("%s ", day.getDisplayName(TextStyle.SHORT, locale)));
		System.out.println();
	}

	private static void printTitle(Date date) {
		System.out.printf("%s%d, %s\n", " ".repeat(YEAR_OFFSET), date.year(),
				Month.of(date.month()).getDisplayName(TextStyle.FULL, locale));
	}
}