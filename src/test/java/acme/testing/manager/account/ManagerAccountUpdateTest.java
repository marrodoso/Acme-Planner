package acme.testing.manager.account;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerAccountUpdateTest extends AcmePlannerTest{
	/*
	 * Positive case of updating a manager account
	 * The attributes tested are valid the account is updated
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
	 * Negative case of updating a manager account
	 * Checked that the attributes sector and company cannot be blank when updating
	 * Checked that the attributes String sector and String company cannot have a length > 255 when updating
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