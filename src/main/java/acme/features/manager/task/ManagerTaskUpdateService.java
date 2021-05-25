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
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerTaskUpdateService implements AbstractUpdateService<Manager, Task> {
	@Autowired
	ManagerTaskRepository repository;
	
	@Autowired
	TaskValidator validator;
	
	@Autowired
	SpamFilterService spamService;
	
	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;

		final Integer managerId = request.getPrincipal().getActiveRoleId();
		final Integer taskId = request.getModel().getInteger("id");

		assert managerId != null;
		assert taskId != null;

		final Task task = this.repository.findTaskById(taskId);
		
		assert task != null;
		
		return task.getManager().getId() == managerId.intValue();
	}

	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		// NOT USED
	}

	@Override
	public Task findOne(final Request<Task> request) {
		assert request != null;

		final Integer taskId = request.getModel().getInteger("id");
		return this.repository.findTaskById(taskId);
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		this.validator.validate(request, entity, errors);
		
		if (!errors.hasErrors()) {
			this.spamService.validate(request, "title", entity.getTitle(), errors);
			this.spamService.validate(request, "description", entity.getDescription(), errors);
			this.spamService.validate(request, "link", entity.getLink(), errors);

		}
		
		assert request.getPrincipal().getActiveRoleId() == entity.getManager().getId();
	}

	@Override
	public void update(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;
		
		try {
			entity.setWorkload(HoursAndMinutes.fromFormattedTime(entity.getWorkload()).getDecimalTime());
		} catch (final Exception e) {}
		
		this.repository.save(entity);
	}
}
