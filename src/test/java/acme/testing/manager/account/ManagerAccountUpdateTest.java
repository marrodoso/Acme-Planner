package acme.testing.manager.account;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerAccountUpdateTest extends AcmePlannerTest{
	/*
	 * Principal: Manager
	 * Entity: Manager
	 * Action: update (positive)
	 * Cases: We test whether a manager principal is able to update his
	 * manager data successfully.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/account/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String companyUpdate, final String sectorUpdate) {
		super.signIn("manager2", "manager2");
		super.clickOnMenu("Account", "Manager data");
		
		super.fillInputBoxIn("company", companyUpdate);
		super.fillInputBoxIn("sector", sectorUpdate);
		
		super.clickOnSubmitButton("Update");
		super.clickOnMenu("Account", "Manager data");
		
		super.checkInputBoxHasValue("company", companyUpdate);
		super.checkInputBoxHasValue("sector", sectorUpdate);
		
		super.signOut();
	}

	/*
	 * Principal: Manager
	 * Entity: Manager
	 * Action: update (negative)
	 * Cases: We test whether a manager principal is unable to update his
	 * manager data whenever either:
	 *   - The sector or company attributes are blank
	 *   - The sector or company attributes have more than 255 characters
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/account/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updateNegative(final int recordIndex, final String companyUpdate, final String sectorUpdate) {
		super.signIn("manager2", "manager2");
		super.clickOnMenu("Account", "Manager data");
		
		super.fillInputBoxIn("company", companyUpdate);
		super.fillInputBoxIn("sector", sectorUpdate);
		
		super.clickOnSubmitButton("Update");
		super.checkErrorsExist();
		
		super.signOut();
	}
}
