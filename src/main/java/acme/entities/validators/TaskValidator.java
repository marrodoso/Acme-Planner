package acme.entities.validators;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import acme.entities.tasks.Task;

@Component
public class TaskValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return TaskValidator.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		 final Task task = (Task) target;
		 
		 final Date startPeriod = task.getStartPeriod();
		 final Date endPeriod = task.getEndPeriod();

		 if(startPeriod.after(endPeriod)) {
			 errors.rejectValue("startPeriod", "Start period cannot be after end period.");
		 }
		 
		 final long periodDuration = endPeriod.getTime() - startPeriod.getTime();
		 
		 final long workLoadMilis = (long) (task.getWorkload() * 60);
		 
		 if(workLoadMilis > periodDuration) {
			 errors.rejectValue("workload", "Workload cannot be greater than the task period.");
		 }
		
	}

}