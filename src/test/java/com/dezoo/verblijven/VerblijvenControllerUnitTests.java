package com.dezoo.verblijven;

import com.dezoo.verblijven.model.Verblijf;
import com.dezoo.verblijven.repository.VerblijfRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VerblijvenControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VerblijfRepository verblijfRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenVerblijf_whenGetVerblijfByPersoneelIDAndDierID_thenReturnJsonVerblijf() throws Exception{
        Verblijf verblijf = new Verblijf(0,"0", "Pride Rock", 4, 1995, false, "1", "1");

        given(verblijfRepository.findVerblijfByPersoneelIDAndDierID("1","1")).willReturn(verblijf);

        mockMvc.perform(get("/verblijven/personeel/{personeelID}/dieren/{dierID}", "1","1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verblijfID", is("0")))
                .andExpect(jsonPath("$.name", is("Pride Rock")))
                .andExpect(jsonPath("$.maxDieren", is(4)))
                .andExpect(jsonPath("$.bouwjaar", is(1995)))
                .andExpect(jsonPath("$.nocturnal", is(false)))
                .andExpect(jsonPath("$.personeelID", is("1")))
                .andExpect(jsonPath("$.dierID", is("1")));
    }

    @Test
    public void givenVerblijf_whenGetVerblijfByPersoneelID_thenReturnJsonVerblijven() throws Exception{
        Verblijf verblijf1 = new Verblijf(0,"0", "Pride Rock", 4, 1995, false, "1", "1");
        Verblijf verblijf2 = new Verblijf(3, "3", "Adelaarsnest", 3, 2000, false, "1","3");

        List<Verblijf> verblijfList = new ArrayList<>();
        verblijfList.add(verblijf1);
        verblijfList.add(verblijf2);

        mockMvc.perform(get("/verblijven/personeel/personeelID", "1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.verblijfID", is("0")))
                .andExpect(jsonPath("$.name", is("Pride Rock")))
                .andExpect(jsonPath("$.maxDieren", is(4)))
                .andExpect(jsonPath("$.bouwjaar", is(1995)))
                .andExpect(jsonPath("$.nocturnal", is(false)))
                .andExpect(jsonPath("$.personeelID", is("1")))
                .andExpect(jsonPath("$.dierID", is("1")))
                .andExpect(jsonPath("$.verblijfID", is("3")))
                .andExpect(jsonPath("$.name", is("Aderlaarsnest")))
                .andExpect(jsonPath("$.maxDieren", is(3)))
                .andExpect(jsonPath("$.bouwjaar", is(2000)))
                .andExpect(jsonPath("$.nocturnal", is(false)))
                .andExpect(jsonPath("$.personeelID", is("1")))
                .andExpect(jsonPath("$.dierID", is("3")));
    }

    @Test
    public void whenGivenVerblijf_whenGetVerblijvenByAnimalID_thenReturnJsonReviews() throws Exception{
        Verblijf verblijf1 = new Verblijf(0,"0", "Pride Rock", 4, 1995, false, "1", "1");
        Verblijf verblijf2 = new Verblijf(4, "4", "Shame Rock", 3, 2000, true, "2","1");

        List<Verblijf> verblijfList = new ArrayList<>();
        verblijfList.add(verblijf1);
        verblijfList.add(verblijf2);

        mockMvc.perform(get("/verblijven/dieren/dierID", "1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.verblijfID", is("0")))
                .andExpect(jsonPath("$.name", is("Pride Rock")))
                .andExpect(jsonPath("$.maxDieren", is(4)))
                .andExpect(jsonPath("$.bouwjaar", is(1995)))
                .andExpect(jsonPath("$.nocturnal", is(false)))
                .andExpect(jsonPath("$.personeelID", is("1")))
                .andExpect(jsonPath("$.dierID", is("1")))
                .andExpect(jsonPath("$.verblijfID", is("4")))
                .andExpect(jsonPath("$.name", is("Shame Rock")))
                .andExpect(jsonPath("$.maxDieren", is(3)))
                .andExpect(jsonPath("$.bouwjaar", is(2000)))
                .andExpect(jsonPath("$.nocturnal", is(true)))
                .andExpect(jsonPath("$.personeelID", is("2")))
                .andExpect(jsonPath("$.dierID", is("1")));
    }

    @Test
    public void whenPostVerblijf_thenReturnJsonVerblijf() throws Exception{
        Verblijf testVerblijf = new Verblijf(16,"16","in progress",1,2021,true,"2","5");

        mockMvc.perform(post("/verblijven")
                .content(mapper.writeValueAsString(testVerblijf))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verblijfID", is("16")))
                .andExpect(jsonPath("$.name", is("in progress")))
                .andExpect(jsonPath("$.maxDieren", is(1)))
                .andExpect(jsonPath("$.bouwjaar", is(2021)))
                .andExpect(jsonPath("$.nocturnal", is(true)))
                .andExpect(jsonPath("$.personeelID", is("2")))
                .andExpect(jsonPath("$.dierID", is("5")));
    }

    @Test
    public void givenVerblijf_whenPutVerblijf_thenReturnJsonReview() throws Exception{
        Verblijf verblijf = new Verblijf(1, "1", "De konijnenpijp", 16, 2008, true, "2","2");

        given(verblijfRepository.findVerblijfByPersoneelIDAndDierID("2","2")).willReturn(verblijf);

        Verblijf updatedVerblijf = new Verblijf(1, "1", "De konijnenpijp", 6, 2018, true, "2","2");

        mockMvc.perform(put("/verblijven")
                .content(mapper.writeValueAsString(updatedVerblijf))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verblijfID", is("1")))
                .andExpect(jsonPath("$.name", is("De konijnenpijp")))
                .andExpect(jsonPath("$.maxDieren", is(6)))
                .andExpect(jsonPath("$.bouwjaar", is(2018)))
                .andExpect(jsonPath("$.nocturnal", is(true)))
                .andExpect(jsonPath("$.personeelID", is("2")))
                .andExpect(jsonPath("$.dierID", is("2")));
    }

    @Test
    public void givenVerblijf_whenDeleteVerblijf_thenStatusOk() throws Exception{

        Verblijf verblijfToBeDeleted = new Verblijf(4, "4", "White House", 1,2021,true, "0","0");

        given(verblijfRepository.findVerblijfByPersoneelIDAndDierID("0","0")).willReturn(verblijfToBeDeleted);

        mockMvc.perform(delete("/verblijven/personeel/{personeelID}/dieren/{dierID}", "0", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoVerblijf_whenDeleteVerblijf_thenStatusNotFound() throws Exception{

        given(verblijfRepository.findVerblijfByPersoneelIDAndDierID("81","81")).willReturn(null);

        mockMvc.perform(delete("/verblijven/personeel/{personeelID}/dieren/{dierID}", "81", "81")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
