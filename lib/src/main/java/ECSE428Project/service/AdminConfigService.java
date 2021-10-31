package ECSE428Project.service;

import ECSE428Project.dao.AdminConfigRepository;
import ECSE428Project.model.AdminConfig;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AdminConfigService {

	@Autowired
	private AdminConfigRepository adminConfigRepository;

	@Autowired
	private AccountService accountService;

	/**
	 * Finds the default config if it exists or creates one.
	 * 
	 * @return The default config; will never be null.
	 */
	@Transactional
	public AdminConfig getDefaultConfig() {
		Optional<AdminConfig> opt = adminConfigRepository.findById("default");
		AdminConfig adminConfig;
		if (opt.isEmpty()) {
			adminConfig = createDefaultConfig();
		} else {
			adminConfig = opt.get();
		}
		Hibernate.initialize(adminConfig.getBannedEmails());
		for (String s : adminConfig.getBannedEmails()) {
			Hibernate.initialize(s);
		}
		Hibernate.initialize(adminConfig.getAdminEmails());
		for (String s : adminConfig.getAdminEmails()) {
			Hibernate.initialize(s);
		}

		return adminConfig;
	}

	@Transactional
	public AdminConfig createDefaultConfig() {
		AdminConfig adminConfig = new AdminConfig();
		adminConfig.setName("default");
		adminConfig = adminConfigRepository.save(adminConfig);
		return adminConfig;
	}

	/**
	 * @throws IllegalArgumentException if the email is already banned.
	 * @param email The email to be banned
	 * @return the updated list of banned emails
	 */
	@Transactional
	public List<String> banEmail(String email) {
		AdminConfig adminConfig = getDefaultConfig();

		if (!adminConfig.addBannedEmail(email)) {
			throw new IllegalArgumentException("This email is already in the banned list.");
		}
		adminConfig = adminConfigRepository.save(adminConfig);
		List<String> list = adminConfig.getBannedEmails();
		Hibernate.initialize(list);
		for (String s : list) {
			Hibernate.initialize(s);
		}
		return list;
	}

	/**
	 * @throws IllegalArgumentException if the email is not in the banned list.
	 * @param email The email to be unbanned
	 * @return the updated list of banned emails
	 */
	@Transactional
	public List<String> unbanEmail(String email) {
		AdminConfig adminConfig = getDefaultConfig();
		if (!adminConfig.removeBannedEmail(email)) {
			throw new IllegalArgumentException("This email is not in the banned list.");
		}
		adminConfig = adminConfigRepository.save(adminConfig);
		List<String> list = adminConfig.getBannedEmails();
		Hibernate.initialize(list);
		for (String s : list) {
			Hibernate.initialize(s);
		}
		return list;
	}

	@Transactional
	public List<String> getBannedEmails() {
		AdminConfig adminConfig = getDefaultConfig();
		List<String> list = adminConfig.getBannedEmails();
		Hibernate.initialize(list);
		for (String s : list) {
			Hibernate.initialize(s);
		}
		return list;
	}

	@Transactional
	public List<String> getAdminEmails() {
		AdminConfig adminConfig = getDefaultConfig();
		List<String> list = adminConfig.getAdminEmails();
		Hibernate.initialize(list);
		for (String s : list) {
			Hibernate.initialize(s);
		}
		return list;
	}

	/**
	 * @throws InternalError if the default adminConfig does not exist.
	 * @param email    The supposed admin email
	 * @param password the supposed admin password
	 * @return true if the credentials are that of an admin. False otherwise.
	 */
	@Transactional
	public boolean isAdmin(String email, String password) {
		AdminConfig adminConfig = getDefaultConfig();
		return accountService.validatePassword(email, password) && adminConfig.isAdmin(email);
	}

}
