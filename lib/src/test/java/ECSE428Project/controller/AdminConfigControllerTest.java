package ECSE428Project.controller;

import ECSE428Project.dao.AccountRepository;
import ECSE428Project.dao.AdminConfigRepository;
import ECSE428Project.model.Account;
import ECSE428Project.model.AdminConfig;
import ECSE428Project.service.AdminConfigService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("IntegrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminConfigControllerTest {

    //Variables used to create dummy account entities in the AccountRepository
    private static final String ACCOUNT_EMAIL = "couldBeBanned@mail.ca";
    //private static final String ACCOUNT_PASSWORD = "accountPassword";
    private static final String ADMIN_EMAIL = "admin@mail.ca";
    private static final String ADMIN_PASSWORD = "adminPassword";
    private static final String ADMIN_CONFIG_ID = "default";

    @Autowired
    private AdminConfigService adminConfigService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AdminConfigRepository adminConfigRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setupRepositoriesForTests() {

        //Empty the Account and AdminConfig Repositories
        accountRepository.deleteAll();
        adminConfigRepository.deleteAll();
        assertEquals(0, accountRepository.count());
        assertEquals(0, adminConfigRepository.count());

        // Create and save a dummy Admin account in the Account Repository
        Account adminAccount = new Account("dummy", ADMIN_EMAIL, ADMIN_PASSWORD, false, false, 0, 0);
        accountRepository.save(adminAccount);

        //Validate the accountRepository is not empty
        if(accountRepository.count() == 0) {
            fail("Test Aborted: The Account Repository is empty. Entity Count = "+ accountRepository.count());
        }
    }
    @AfterEach
    public void clearRepositories() {
        //Empty the Account and AdminConfig Repositories
        accountRepository.deleteAll();
        adminConfigRepository.deleteAll();
        assertEquals(0, accountRepository.count());
        assertEquals(0, adminConfigRepository.count());
    }

    //Test GET all banned emails
    @Test
    public void testGetBannedEmails() throws Exception {

        String email1 = "banned1@mail.ca", email2 = "banned2@mail.ca", email3 = "banned3@mail.ca";
        AdminConfig defaultConfig = new AdminConfig();
        List<String> bannedList = new ArrayList<>();
        bannedList.add(email1);
        bannedList.add(email2);
        bannedList.add(email3);
        defaultConfig.addBannedEmail(email1);
        defaultConfig.addBannedEmail(email2);
        defaultConfig.addBannedEmail(email3);
        defaultConfig.setName(ADMIN_CONFIG_ID);
        adminConfigRepository.save(defaultConfig);

        mockMvc.perform(get("/configs/banned")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(bannedList.toString()));
    }

    //Test GET all Admin emails
    @Test
    public void testGetAdminEmails() throws Exception {

        String admin1 = "admin1@mail.ca", admin2 = "admin2@mail.ca", admin3 = "admin3@mail.ca";
        AdminConfig defaultConfig = new AdminConfig();
        List<String> adminList = new ArrayList<>();
        adminList.add(admin1);
        adminList.add(admin2);
        adminList.add(admin3);
        defaultConfig.addAdminEmail(admin1);
        defaultConfig.addAdminEmail(admin2);
        defaultConfig.addAdminEmail(admin3);
        defaultConfig.setName(ADMIN_CONFIG_ID);
        adminConfigRepository.save(defaultConfig);

        mockMvc.perform(get("/configs/admins")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                .andExpect(content().json(adminList.toString()));
    }

    @Test
    public void testRequestToBan() throws Exception {

        Account adminProfile = accountRepository.findAccountByEmail(ADMIN_EMAIL);
        //Validate that the admin that will ban an email exists
        Assertions.assertNotNull(adminProfile,
                "Test Aborted: The dummy Admin Account was not created successfully.");

        //Add a new AdminConfig to the adminConfig Repository
        AdminConfig defaultConfig = new AdminConfig();
        defaultConfig.addAdminEmail(ADMIN_EMAIL);
        defaultConfig.setName(ADMIN_CONFIG_ID);
        adminConfigRepository.save(defaultConfig);

        //Validate that the admin account isAdmin
        Assertions.assertTrue(adminConfigService.isAdmin(ADMIN_EMAIL,ADMIN_PASSWORD));
        //Validate that the Account email is not banned
        assertFalse(adminConfigService.getBannedEmails().toString().contains(ACCOUNT_EMAIL));

        mockMvc.perform(post("/configs/ban/"+ACCOUNT_EMAIL).content("{\n" +
                        "\"email\": \"" +ADMIN_EMAIL+ "\",\n" +
                        "\"password\": \"" +ADMIN_PASSWORD+ "\"\n" +
                        "}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        //Validate that the Account email is banned
        assertTrue(adminConfigService.getBannedEmails().toString().contains(ACCOUNT_EMAIL));
    }
    @Test
    public void testRequestToUnban() throws Exception {

        Account adminProfile = accountRepository.findAccountByEmail(ADMIN_EMAIL);
        //Validate that the admin that will ban an email exists
        Assertions.assertNotNull(adminProfile,
                "Test Aborted: The dummy Admin Account was not created successfully.");

        //Add a new AdminConfig to the adminConfig Repository
        AdminConfig defaultConfig = new AdminConfig();
        defaultConfig.addAdminEmail(ADMIN_EMAIL);
        defaultConfig.addBannedEmail(ACCOUNT_EMAIL);
        defaultConfig.setName(ADMIN_CONFIG_ID);
        adminConfigRepository.save(defaultConfig);

        //Validate that the dummy Account email is banned
        assertTrue(adminConfigService.getBannedEmails().toString().contains(ACCOUNT_EMAIL));
        //Validate that the admin account isAdmin
        Assertions.assertTrue(adminConfigService.isAdmin(ADMIN_EMAIL,ADMIN_PASSWORD));


        mockMvc.perform(post("/configs/unban/"+ACCOUNT_EMAIL).content("{\n" +
                                "\"email\": \"" +ADMIN_EMAIL+ "\",\n" +
                                "\"password\": \"" +ADMIN_PASSWORD+ "\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        //Validate that the Account email is not banned
        assertFalse(adminConfigService.getBannedEmails().toString().contains(ACCOUNT_EMAIL));
    }
}
