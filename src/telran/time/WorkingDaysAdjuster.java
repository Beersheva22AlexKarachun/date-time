package telran.time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Set;

public class WorkingDaysAdjuster implements TemporalAdjuster {
	private int amountOfDays;
	private Set<DayOfWeek> dayOffs;

	@Override
	public Temporal adjustInto(Temporal temporal) {
		int count = 0;
		Temporal res = temporal;
		while (count != amountOfDays) {
			DayOfWeek currentDayOfWeek = DayOfWeek.of(res.get(ChronoField.DAY_OF_WEEK));
			if (!dayOffs.contains(currentDayOfWeek)) {
				count++;
			}
			res = res.plus(1, ChronoUnit.DAYS);
		}
		return res;
	}

	public WorkingDaysAdjuster(int amountOfDays, Set<DayOfWeek> dayOffs) {
		this.amountOfDays = amountOfDays;
		this.dayOffs = dayOffs;
	}

}
