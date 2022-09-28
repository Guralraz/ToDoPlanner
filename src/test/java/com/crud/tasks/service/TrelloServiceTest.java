package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto =
                new TrelloCardDto("Test Name", "Description", "Test Pos", "1234");

        CreatedTrelloCardDto createdTrelloCardDto =
                new CreatedTrelloCardDto("Id_1", "To_Do", "UrlTest");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(trelloCardDto);
        //Then
        assertNotNull(result);
        assertEquals("Id_1", result.getId());
    }
}
