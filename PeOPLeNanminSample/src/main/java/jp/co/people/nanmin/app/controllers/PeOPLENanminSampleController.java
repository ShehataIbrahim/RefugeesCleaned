package jp.co.people.nanmin.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.people.nanmin.app.service.NanminSampleService;
import jp.co.people.nanmin.app.service.model.ResultSearch;
import jp.co.people.nanmin.app.service.model.form.SampleForm;

/**
 * Sample screen controller
 */
@Controller
public class PeOPLENanminSampleController extends BaseWebController {

	/** Service class of sample screen */
	@Autowired
	private NanminSampleService nanminSampleService;

	/**
	 * Screen display @ param model
	 * 
	 * @return main screen
	 */
	@RequestMapping("/test")
	public String display(Model model) {
		String strUrl = "nanminMain"; // JSP file name
		return strUrl;
	}

	/**
	 * Processing when register button is pressed @ param form Information from
	 * screen form (for one person) @ param model @ param redirectAttributes An
	 * object for saving messages when redirecting
	 * 
	 * @return JSP file name
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "addBtn")
	public String addBtn(@ModelAttribute SampleForm form, Model model, RedirectAttributes redirectAttributes) {
		String strUrl = ""; // JSP file name
		String msg = ""; // Message to display
		strUrl = "redirect:/test";
		// Registration process call
		msg = nanminSampleService.addService(form);
		redirectAttributes.addFlashAttribute("msg", msg);
		return strUrl;
	}

	/**
	 * Processing when the acquisition button is pressed @ param form Information
	 * from screen form (for one person) @ param model @ param redirectAttributes An
	 * object for saving messages when redirecting
	 * 
	 * @return JSP file name
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "searchBtn")
	public String searchBtn(@ModelAttribute SampleForm form, Model model, RedirectAttributes redirectAttributes) {
		String strUrl = ""; // JSP file name
		ResultSearch result = null; // Processing result
		result = nanminSampleService.findService(form);
		model.addAttribute("result", result);
		strUrl = "nanminResult";
		return strUrl;
	}
}
