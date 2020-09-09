package ch.noseryoung.uk.businessObject.auction.unittest;

import org.assertj.core.api.Assertions;
import ch.noseryoung.uk.businessObject.auction.Auction;
import ch.noseryoung.uk.businessObject.auction.AuctionService;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuctionControllerTest {
    private Auction auctionToBeTestedAgainst = new Auction();
    private List<Auction> listOfAuctionsToBeTestedAgainst = new ArrayList<>();

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuctionService auctionService;

    @BeforeEach
    void setUp() {
        listOfAuctionsToBeTestedAgainst.add(
                new Auction().setId(1).setName("tmn").setPrice(320)
        );
        auctionToBeTestedAgainst.setId(1).setName("Testtt").setPrice(200);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void findById_requestAuctionById_returnAuction() throws Exception {
        given(auctionService.findById(anyInt())).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0))) throw new Exception();
            return (auctionToBeTestedAgainst);
        });
        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/{id}", auctionToBeTestedAgainst.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(auctionToBeTestedAgainst.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(auctionToBeTestedAgainst.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(auctionToBeTestedAgainst.getPrice()));
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(auctionService, times(1)).findById(integerArgumentCaptor.capture());
        Assertions.assertThat(integerArgumentCaptor.getAllValues().equals(auctionToBeTestedAgainst.getId()));
    }

    @Test
    public void getAll_requestAllAuctions_returnAllAuctions() throws Exception {
        listOfAuctionsToBeTestedAgainst.add(
                new Auction().setId(1).setName("tmn").setPrice(320)
        );
        auctionToBeTestedAgainst.setId(1).setName("Testtt").setPrice(200);
        given(auctionService.findAll()).willReturn(listOfAuctionsToBeTestedAgainst);
        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/getAll")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("tmn"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].price").value(320));
        verify(auctionService, times(1)).findAll();
    }

    @Test
    public void getAuctionByRange_requestAllAuctions_returnAllAuctions() throws Exception {
        listOfAuctionsToBeTestedAgainst.add(
                new Auction().setId(1).setName("tmn").setPrice(320)
        );
        auctionToBeTestedAgainst.setId(1).setName("Testtt").setPrice(200);

        given(auctionService.getAuctionByRange(700,300)).willReturn(listOfAuctionsToBeTestedAgainst);

        mvc.perform(
                MockMvcRequestBuilders.get("/auctions/between/{maxPrice}/{minPrice}", 700, 300)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].price").value(listOfAuctionsToBeTestedAgainst.get(0).getPrice()));
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> integerArgumentCaptor2 = ArgumentCaptor.forClass(Integer.class);
        verify(auctionService, times(1)).getAuctionByRange(integerArgumentCaptor.capture(), integerArgumentCaptor2.capture());
        Assertions.assertThat(integerArgumentCaptor.getValue()).isEqualTo(700);
        Assertions.assertThat(integerArgumentCaptor2.getValue()).isEqualTo(300);
    }
}
