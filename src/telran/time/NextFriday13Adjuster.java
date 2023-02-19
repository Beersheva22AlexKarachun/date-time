package telran.time;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextFriday13Adjuster implements TemporalAdjuster {
	private static final int DAY_OF_WEEK = 5;
	private static final int DAY_OF_MONTH = 13;

	@Override
	public Temporal adjustInto(Temporal temporal) {
		temporal = adjustDay(temporal);

		while (temporal.get(ChronoField.DAY_OF_WEEK) != DAY_OF_WEEK) {
			temporal = temporal.plus(1, ChronoUnit.MONTHS);
		}
		return temporal;
	}

	private Temporal adjustDay(Temporal temporal) {
		if (temporal.get(ChronoField.DAY_OF_MONTH) >= DAY_OF_MONTH) {
			temporal = temporal.plus(1, ChronoUnit.MONTHS);
		}
		return temporal.with(ChronoField.DAY_OF_MONTH, DAY_OF_MONTH);
	}

}
