package ECSE428Project.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ECSE428Project.service.AdminConfigService;

@CrossOrigin(origins = "*")
@RestController
public class AdminConfigController {

	@Autowired
	public AdminConfigService adminConfigService;

	@GetMapping(path = { "/configs/banned", "/configs/banned/" })
	public List<String> getBannedEmails() throws ResponseStatusException {
		return adminConfigService.getBannedEmails();
	}

	@GetMapping(path = { "/configs/admins", "/configs/banned/admins/" })
	public List<String> getAdminEmails() throws ResponseStatusException {
		return adminConfigService.getBannedEmails();
	}

	@PostMapping(path = { "/configs/ban/{emailToBan}", "/configs/ban/{emailToBan}/" })
	public List<String> banEmail(@RequestBody Map<String, String> json, @PathVariable String emailToBan)
			throws ResponseStatusException {

		authenticateAdmin(json);
		List<String> bannedEmails = null;
		try {
			bannedEmails = adminConfigService.banEmail(emailToBan);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		if (bannedEmails == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"The server could not accomplish the request");
		}

		return bannedEmails;
	}

	@PostMapping(path = { "/configs/unban/{emailToUnban}", "/configs/unban/{emailToUnban}/" })
	public List<String> unbanEmail(@RequestBody Map<String, String> json, @PathVariable String emailToUnban)
			throws ResponseStatusException {

		authenticateAdmin(json);

		List<String> bannedEmails = null;
		try {
			bannedEmails = adminConfigService.unbanEmail(emailToUnban);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		if (bannedEmails == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"The server could not accomplish the request");
		}

		return bannedEmails;
	}

	private void authenticateAdmin(Map<String, String> json) {
		if (json == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin email and Admin password cannot be null");
		}

		String email = json.get("email");
		String password = json.get("password");
		if (email == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin email cannot be null");
		}

		if (password == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin password cannot be null");
		}

		if (!adminConfigService.isAdmin(email, password)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The credentials are not that of an admin");
		}
	}
}
