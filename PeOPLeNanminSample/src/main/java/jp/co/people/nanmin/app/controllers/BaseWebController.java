package jp.co.people.nanmin.app.controllers;

import org.springframework.ui.Model;

/**
 * Abstract class of controller
 */
public abstract class BaseWebController {
	/* Display corresponding screen */
	protected abstract String display(Model model);
}
