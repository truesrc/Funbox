package ru.funbox.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import redis.clients.jedis.Tuple;
import ru.funbox.model.VisitedDomains;
import ru.funbox.model.VisitedLinks;
import ru.funbox.repository.RedisRepository;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author truesrc
 * @since 16.04.2019
 */
//@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(WebCtrl.class)
public class WebCtrlTest {
    private static final String KEY = "visitedLinks";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RedisRepository redisRepository;

    @Test
    public void visitedDomainsGetTest() throws Exception {
        VisitedDomains visitedDomains = new VisitedDomains();
        visitedDomains.getDomains().add("test.ru");
        visitedDomains.getDomains().add("test.com");
        visitedDomains.setStatus("ok");
        ObjectMapper mapper = new ObjectMapper();
        Set<Tuple> elements = new HashSet<>();
        Tuple tuple = new Tuple("test.com", 126.);
        Tuple tuple2 = new Tuple("test.ru", 130.);
        elements.add(tuple);
        elements.add(tuple2);
        given(redisRepository.zrevrangeByScoreWithScores(KEY, 140, 125)).willReturn(elements);
        mvc.perform(
                get("/visited_domains?from=125&to=140").accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(mapper.writeValueAsString(visitedDomains))
        );
    }


    @Test
    public void visitedLinksPostTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        VisitedLinks visitedLinks = new VisitedLinks();
        visitedLinks.setLinks(asList("https://stackoverflow.com/questions/11828270/how-to-exit-the-vim-editor", "funbox.ru", "https://ya.ru/?q=123", "https://ya.ru/"));
        mvc.perform(
                post("/visited_links").contentType(MediaType.APPLICATION_JSON_UTF8).content(
                        mapper.writeValueAsString(visitedLinks))
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string("{\"status\": \"ok\"}")
        );
    }

}