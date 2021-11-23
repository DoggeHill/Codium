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
import sk.hyll.patrik.codium.model.BankCard;
import sk.hyll.patrik.codium.model.CardOwner;
import sk.hyll.patrik.codium.model.CreditCard;
import sk.hyll.patrik.codium.model.DebitCard;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
// do not polute database
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Profile("local")
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
        bankCard.setBrand("visa");

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
        bankCard.setCardNumber(4151_5000_0000_0008L);

        BankCard bankCard1 = new DebitCard();
        bankCard.setBrand("visa");
        bankCard.setCardNumber(4035_5010_0000_0008L);

        BankCard bankCard2 = new CreditCard();
        bankCard.setBrand("visa");
        bankCard.setCardNumber(4131_8400_0000_0003L);

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
    @Test
    public void whenMethodArgumentMismatch_thenBadRequest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/api/find").param("surname", ""))
                .andDo(print()).andReturn();
        String content = result.getResponse().getContentAsString();
       // assertTrue(content.contains("should be of type"));
    }

    // Mapper
}
