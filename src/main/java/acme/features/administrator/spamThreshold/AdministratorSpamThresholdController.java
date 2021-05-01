package acme.features.administrator.spamThreshold;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.spamfilter.SpamThreshold;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/spam-threshold/")
public class AdministratorSpamThresholdController extends AbstractController<Administrator, SpamThreshold> {
	@Autowired
	private AdministratorSpamThresholdShowService showService;

	@Autowired
	private AdministratorSpamThresholdUpdateService updateService;

	@PostConstruct
	protected void initialise() {
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}
}
