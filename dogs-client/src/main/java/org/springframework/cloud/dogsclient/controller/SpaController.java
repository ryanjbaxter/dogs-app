package org.springframework.cloud.dogsclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan Baxter
 */
// Necessary to be able to refresh the web app in the browser to allow react handle the routing
@RestController
public class SpaController {
	@GetMapping({"/", "/{path:[^\\.]*}", "/{path:^(?!api$).*?}/{pathTwo:[^\\.]*}/{pathThree:[^\\.]*}"})
	public String forwardToIndex() {
		return "forward:/index.html";
	}
}
