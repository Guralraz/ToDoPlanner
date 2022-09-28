package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        // Given
        Mockito.when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        Mockito.when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloToken()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloUserName()).thenReturn("testUser");

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "Test Board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/testUser/boards?key=test&token=test&fields=name,id&lists=all");

        Mockito.when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        // When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        // Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("Test Board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        Mockito.when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        Mockito.when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloToken()).thenReturn("test");
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id");

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Test task", "http://test.com");

        Mockito.when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        //Then
        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        //Given
        Mockito.when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        Mockito.when(trelloConfig.getTrelloUserName()).thenReturn("TestUser");
        Mockito.when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloToken()).thenReturn("test");

        URI uri = new URI("http://test.com/members/TestUser/boards?key=test&token=test&fields=name,id&lists=all");

        Mockito.when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);
        //When
        List<TrelloBoardDto> resultList = trelloClient.getTrelloBoards();
        //Then
        assertEquals(new ArrayList<>(), resultList);
    }

}