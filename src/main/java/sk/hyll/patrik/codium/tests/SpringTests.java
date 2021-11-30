package sk.hyll.patrik.codium.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;
import sk.hyll.patrik.codium.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
// do not pollute database
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Profile({"local", "localsql"})
class SpringTests {

    @LocalServerPort
    int randomServerPort;

    private static final String[] URLS = {"/api/save", "/api/list"};

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;



    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void addNewEntry0Card() throws Exception {
        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("Test");
        cardOwner.setSurname("Tester");
        BankCard bankCard = new DebitCard();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(cardOwner);

        this.mockMvc.perform(post(URLS[0])
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void addNewEntry1Card() throws Exception {
        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("Test");
        cardOwner.setSurname("Tester1");
        BankCard bankCard = new DebitCard();
        bankCard.setBrand("visa");
        bankCard.setCardNumber(4571_0000_0000_0001L);
        bankCard.setCsv("007");
        bankCard.setState(State.ACTIVE);
        String dateString = "12 01 2010";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.ENGLISH);
        LocalDate localeDate = LocalDate.parse(dateString, formatter);
        bankCard.setValidity(java.sql.Date.valueOf(localeDate));

        cardOwner.addCard(bankCard);


        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(cardOwner);

        this.mockMvc.perform(post(URLS[0])
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }
    @Test
    public void addNewEntry3Cards() throws Exception {
        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("Test");
        cardOwner.setSurname("Tester2");

        BankCard bankCard = new DebitCard();
        bankCard.setBrand("visa");
        bankCard.setCardNumber(4263982640269299L);
        bankCard.setCsv("007");
        bankCard.setState(State.ACTIVE);
        String dateString = "12 10 2010";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.ENGLISH);
        LocalDate localeDate = LocalDate.parse(dateString, formatter);
        bankCard.setValidity(java.sql.Date.valueOf(localeDate));
        System.out.println("first");

        BankCard bankCard1 = new DebitCard();
        bankCard1.setBrand("visa");
        bankCard1.setCardNumber(4111111111111111L);
        bankCard1.setCsv("007");
        bankCard1.setState(State.ACTIVE);
        String dateString1 = "12 02 2010";
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.ENGLISH);
        LocalDate localeDate1 = LocalDate.parse(dateString1, formatter1);
        bankCard1.setValidity(java.sql.Date.valueOf(localeDate1));
        System.out.println("second");


        BankCard bankCard2 = new CreditCard();
        bankCard2.setBrand("visa");
        bankCard2.setCardNumber(4001919257537193L);
        bankCard2.setCsv("007");
        bankCard2.setState(State.ACTIVE);
        String dateString2 = "12 03 2010";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.ENGLISH);
        LocalDate localeDate2 = LocalDate.parse(dateString2, formatter2);
        bankCard2.setValidity(java.sql.Date.valueOf(localeDate2));
        System.out.println("third");


        cardOwner.addCard(bankCard);
        cardOwner.addCard(bankCard1);
        cardOwner.addCard(bankCard2);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cardOwner);

        this.mockMvc.perform(post(URLS[0])
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testInvalidCardNumber() throws Exception {
        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("Test");
        cardOwner.setSurname("Tester3");
        BankCard bankCard = new DebitCard();
        bankCard.setBrand("visa");
        bankCard.setCardNumber(374245455400121L);
        cardOwner.addCard(bankCard);

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(cardOwner);

        this.mockMvc.perform(post(URLS[0])
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void testValidAndInvalidCardNumber() throws Exception {
        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("Test");
        cardOwner.setSurname("Tester4");

        BankCard bankCard = new DebitCard();
        bankCard.setBrand("visa");
        bankCard.setCardNumber(4646_4646_4646_4644L);

        // invalid
        BankCard bankCard2 = new DebitCard();
        bankCard2.setBrand("visa");
        bankCard2.setCardNumber(374245155400126L);

        cardOwner.addCard(bankCard);
        cardOwner.addCard(bankCard2);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cardOwner);

        this.mockMvc.perform(post(URLS[0])
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void addCardOwnerEmptyName() throws Exception {

        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("");
        cardOwner.setSurname("Tester5");

        BankCard bankCard = new DebitCard();
        bankCard.setBrand("visa");
        bankCard.setCardNumber(4646_4646_4646_4644L);

        cardOwner.addCard(bankCard);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cardOwner);

        this.mockMvc.perform(post(URLS[0])
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addCardOwnerEmptySurname() throws Exception {

        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("Test");
        cardOwner.setSurname("");

        BankCard bankCard = new DebitCard();
        bankCard.setBrand("visa");
        bankCard.setCardNumber(4646_4646_4646_4644L);

        cardOwner.addCard(bankCard);


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cardOwner);

        this.mockMvc.perform(post(URLS[0])
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addCardOwnerEmptyNameAndSurname() throws Exception {

        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("");
        cardOwner.setSurname("");

        BankCard bankCard = new DebitCard();
        bankCard.setBrand("visa");
        bankCard.setCardNumber(4646_4646_4646_4644L);

        cardOwner.addCard(bankCard);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cardOwner);

        this.mockMvc.perform(post(URLS[0])
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testListAllEndpoint() throws Exception {
        this.mockMvc
                .perform(get("/api/list", ""))
                .andDo(print()).andExpect(status().isOk());
                    }


    // Custom error handling
    // Work only in production mode
    @Test
    public void whenMethodArgumentMismatch_thenBadRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/api/find").param("surname", ""))
                .andDo(print()).andReturn();
        String content = result.getResponse().getContentAsString();
       assertTrue(content.contains("error"));
    }

    // Mapper
}
