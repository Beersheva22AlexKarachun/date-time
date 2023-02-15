
package telran.time;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateTimeTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void localDateTest() {
		LocalDate birthDateAS = LocalDate.parse("1799-06-06");
		LocalDate barMizvaAS = birthDateAS.plusYears(13);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM,YYYY,d");
		System.out.println(barMizvaAS.format(dtf));
		ChronoUnit unit = ChronoUnit.MONTHS;
		System.out.printf("Number of %s between %s and %s is %d", unit, birthDateAS, barMizvaAS,
				unit.between(birthDateAS, barMizvaAS));
		System.out.println();

	}

	@Test
	void barMizvaTest() {
		LocalDate current = LocalDate.now();
		assertEquals(current.plusYears(13), current.with(new BarMizvaAdjuster()));
	}

	@Test
	void displayCurrentDateTimeCanadaTimeZones() {
		// displaying current local date and time for all Canada time zones
		// displaying should contains time zone name
		ZoneId.getAvailableZoneIds().stream().filter(x -> x.startsWith("Canada")).map(ZoneId::of)
				.forEach(x -> displayTimeZone(ZonedDateTime.now(x)));
	}

	void displayTimeZone(ZonedDateTime dt) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:MM");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		System.out.printf("TimeZone: %-20s Time: %s, Date: %s.\n", dt.getZone(), dt.format(timeFormatter),
				dt.format(dateFormatter));
	}

	@Test
	void nextFriday13Test() {
		LocalDate date1 = LocalDate.of(2023, 2, 14);
		LocalDate date2 = LocalDate.of(2023, 10, 13);
		LocalDate date3 = LocalDate.of(2024, 9, 13);

		assertEquals(LocalDate.of(2023, 10, 13), date1.with(new NextFriday13Adjuster()));
		assertEquals(LocalDate.of(2024, 9, 13), date2.with(new NextFriday13Adjuster()));
		assertEquals(LocalDate.of(2024, 12, 13), date3.with(new NextFriday13Adjuster()));
	}

	@Test
	void workingDaysTest() {
		HashSet<DayOfWeek> dayOffs1 = new HashSet<>();
		dayOffs1.add(DayOfWeek.SUNDAY);
		dayOffs1.add(DayOfWeek.MONDAY);

		HashSet<DayOfWeek> dayOffs2 = new HashSet<>();
		dayOffs2.add(DayOfWeek.SUNDAY);
		dayOffs2.add(DayOfWeek.TUESDAY);
		dayOffs2.add(DayOfWeek.FRIDAY);

		LocalDate date = LocalDate.of(2023, 2, 14);
		assertEquals(LocalDate.of(2023, 2, 26), date.with(new WorkingDaysAdjuster(10, dayOffs1)));
		assertEquals(LocalDate.of(2023, 3, 3), date.with(new WorkingDaysAdjuster(10, dayOffs2)));
	}
}
