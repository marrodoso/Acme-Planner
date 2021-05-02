
package acme.entities.validators;

import java.util.Date;

import org.springframework.stereotype.Component;


import acme.entities.tasks.Task;
import acme.forms.HoursAndMinutes;
import acme.framework.components.Errors;
import acme.framework.components.Request;

@Component
public class TaskValidator {
	static final long HOUR_TO_MILLIS = 60 * 60 * 1000;

	public void validate(final Request<Task> request, final Task target, final Errors errors) {
		final Date startPeriod = target.getStartPeriod();
		final Date endPeriod = target.getEndPeriod();

		errors.state(request, !startPeriod.after(endPeriod), "startPeriod", "acme.validation.start-after-end");

		final long periodDuration = endPeriod.getTime() - startPeriod.getTime();

		double workLoad = 0.0;
		try {
			workLoad = HoursAndMinutes.fromFormattedTime(target.getWorkload()).getDecimalTime();
		} catch (Exception e) {
			errors.state(request, false, "workload", "acme.validation.invalid-time-format");
		}
		
		final long workLoadMilis = (long) (workLoad * TaskValidator.HOUR_TO_MILLIS);

		errors.state(request, workLoadMilis <= periodDuration, "workload", "acme.validation.too-big-workload");
	}
}
