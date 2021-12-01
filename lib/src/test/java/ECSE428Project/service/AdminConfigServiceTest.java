package ECSE428Project.service;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.dao.AdminConfigRepository;
import ECSE428Project.model.Account;
import ECSE428Project.model.AdminConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("IntegrationTest")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AdminConfigServiceTest {

	private static final String ADMIN_EMAIL = "admin@hotmail.com";
	private static final String ADMIN_PASSWORD = "password1";
	private static final String CONFIG_ID = "default";
	@Autowired
	private AdminConfigService adminConfigService;

	@MockBean
	private AccountRepository accountRepository;

	@MockBean
	private AdminConfigRepository adminConfigRepository;

	@BeforeEach
	public void setMockSaveOutput() {
		Account account = new Account();
		account.setEmail(ADMIN_EMAIL);
		account.setPassword(ADMIN_PASSWORD);
		when(accountRepository.findById(ADMIN_EMAIL)).thenReturn(Optional.of(account));
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
		when(adminConfigRepository.save(any(AdminConfig.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testIsAdmin() {
		AdminConfig defaultConfig = new AdminConfig();
		defaultConfig.addAdminEmail(ADMIN_EMAIL);
		when(adminConfigRepository.findById(CONFIG_ID)).thenReturn(Optional.of(defaultConfig));
		// mock repository call to mock there not being a value in the database with
		// this email in the admin list.
		assertTrue(adminConfigService.isAdmin(ADMIN_EMAIL, ADMIN_PASSWORD));
	}

	@Test
	public void testIsNotAdmin() {
		assertFalse(adminConfigService.isAdmin(ADMIN_EMAIL, ADMIN_PASSWORD));
	}

	@Test
	public void testGetAdminEmails() {
		String email1 = "admin1@hotmail.com", email2 = "admin2@hotmail.com", email3 = "admin3@hotmail.com";
		AdminConfig defaultConfig = new AdminConfig();
		List<String> list = new ArrayList<>();
		list.add(email1);
		list.add(email2);
		list.add(email3);
		defaultConfig.addAdminEmail(email1);
		defaultConfig.addAdminEmail(email2);
		defaultConfig.addAdminEmail(email3);
		when(adminConfigRepository.findById(CONFIG_ID)).thenReturn(Optional.of(defaultConfig));
		assertLinesMatch(adminConfigService.getAdminEmails(), list);
	}

	@Test
	public void testGetBannedEmails() {
		String email1 = "banned1@hotmail.com", email2 = "banned2@hotmail.com", email3 = "banned3@hotmail.com";
		AdminConfig defaultConfig = new AdminConfig();
		List<String> list = new ArrayList<>();
		list.add(email1);
		list.add(email2);
		list.add(email3);
		defaultConfig.addBannedEmail(email1);
		defaultConfig.addBannedEmail(email2);
		defaultConfig.addBannedEmail(email3);
		when(adminConfigRepository.findById(CONFIG_ID)).thenReturn(Optional.of(defaultConfig));
		assertLinesMatch(adminConfigService.getBannedEmails(), list);
	}

	@Test
	public void banUser() {
		AdminConfig defaultConfig = new AdminConfig();
		String bannedEmail = "banned@hotmail.com";
		List<String> list = new ArrayList<>();
		list.add(bannedEmail);
		when(adminConfigRepository.findById(CONFIG_ID)).thenReturn(Optional.of(defaultConfig));
		assertLinesMatch(adminConfigService.banEmail(bannedEmail), list);
		verify(adminConfigRepository, times(1)).save(any(AdminConfig.class));
	}
	
	@Test
	public void banUserAlreadyBanned() {
		AdminConfig defaultConfig = new AdminConfig();
		String bannedEmail = "banned@hotmail.com";
		defaultConfig.addBannedEmail(bannedEmail);
		when(adminConfigRepository.findById(CONFIG_ID)).thenReturn(Optional.of(defaultConfig));
		
		assertThrows(IllegalArgumentException.class,() -> adminConfigService.banEmail(bannedEmail), "This email is already in the banned list.");
		verify(adminConfigRepository, never()).save(any(AdminConfig.class));
	}
	
	@Test
	public void unbanUser() {
		AdminConfig defaultConfig = new AdminConfig();
		String bannedEmail = "banned@hotmail.com";
		defaultConfig.addBannedEmail(bannedEmail);
		List<String> list = new ArrayList<>();
		when(adminConfigRepository.findById(CONFIG_ID)).thenReturn(Optional.of(defaultConfig));
		assertLinesMatch(adminConfigService.unbanEmail(bannedEmail), list);
		verify(adminConfigRepository, times(1)).save(any(AdminConfig.class));
	}
	
	@Test
	public void unbanUserNotBanned() {
		AdminConfig defaultConfig = new AdminConfig();
		String bannedEmail = "banned@hotmail.com";
		when(adminConfigRepository.findById(CONFIG_ID)).thenReturn(Optional.of(defaultConfig));
		
		assertThrows(IllegalArgumentException.class,() -> adminConfigService.unbanEmail(bannedEmail), "This email is not in the banned list.");
		verify(adminConfigRepository, never()).save(any(AdminConfig.class));
	}
}
