package acme.features.manager.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.validators.SpamFilterService;
import acme.entities.validators.TaskValidator;
import acme.forms.HoursAndMinutes;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class ManagerTaskCreateService implements AbstractCreateService<Manager, Task> {
	@Autowired
	ManagerTaskRepository repository;
	
	@Autowired
	TaskValidator validator;
	
	@Autowired
	SpamFilterService spamService;

	@Override
	public boolean authorise(final Request<Task> request) {
		return true;
	}

	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		request.unbind(entity, model, "title", "description", "workload", "link", "startPeriod", "endPeriod",
				"visibility", "manager");
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		validator.validate(request, entity, errors);

		if (!errors.hasErrors()) {
			spamService.validate(request, "title", entity.getTitle(), errors);
			spamService.validate(request, "description", entity.getDescription(), errors);
			spamService.validate(request, "link", entity.getLink(), errors);
		}
	}

	@Override
	public void create(final Request<Task> request, final Task entity) {
		final Integer managerId = request.getPrincipal().getActiveRoleId();
		final Manager manager = this.repository.findManagerById(managerId);
		entity.setManager(manager);
		
		try {
			entity.setWorkload(HoursAndMinutes.fromFormattedTime(entity.getWorkload()).getDecimalTime());
		} catch (Exception e) {}
		
		this.repository.save(entity);
	}

	@Override
	public Task instantiate(final Request<Task> request) {
		return new Task();
	}
}
