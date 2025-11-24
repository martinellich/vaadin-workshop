package ch.martinelli.vaadin.workshop.ui.views;

import com.microsoft.playwright.Page;

public class LoginPO {

	private final Page page;

	public LoginPO(Page page) {
		this.page = page;
	}

	public void login(String username, String password) {
		// Wait for the login form to be visible
		page.locator("vaadin-login-form").waitFor();

		// Fill username - Vaadin LoginForm uses specific input selectors
		var usernameField = page.locator("vaadin-login-form input[name='username']")
			.or(page.locator("vaadin-login-form vaadin-text-field input"))
			.or(page.locator("input[name='username']"));
		usernameField.waitFor();
		usernameField.fill(username);

		// Fill password - Vaadin LoginForm uses specific input selectors
		var passwordField = page.locator("vaadin-login-form input[name='password']")
			.or(page.locator("vaadin-login-form vaadin-password-field input"))
			.or(page.locator("input[name='password']"));
		passwordField.waitFor();
		passwordField.fill(password);

		// Click login button - Vaadin LoginForm has a specific submit button
		var loginButton = page.locator("vaadin-login-form vaadin-button[slot='submit']")
			.or(page.locator("vaadin-login-form vaadin-button[theme~='primary']").first())
			.or(page.locator("vaadin-button[type='submit']"));
		loginButton.waitFor();
		loginButton.click();

		// Wait for navigation after login
		page.waitForLoadState();
	}

}
