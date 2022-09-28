package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    private List<TrelloListDto> prepareListOfTrelloListDtoToTest() {
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        TrelloListDto trelloListDto1 = new TrelloListDto("IdDto1", "TestDto1", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("IdDto2", "TestDto2", true);
        TrelloListDto trelloListDto3 = new TrelloListDto("IdDto3", "TestDto3", false);
        trelloListDtoList.add(trelloListDto1);
        trelloListDtoList.add(trelloListDto2);
        trelloListDtoList.add(trelloListDto3);
        return trelloListDtoList;
    }

    private List<TrelloList> prepareListOfTrelloListToTest() {
        List<TrelloList> trelloLists = new ArrayList<>();
        TrelloList trelloList1 = new TrelloList("Id1", "Test1", false);
        TrelloList trelloList2 = new TrelloList("Id2", "Test2", true);
        TrelloList trelloList3 = new TrelloList("Id3", "Test3", false);
        trelloLists.add(trelloList1);
        trelloLists.add(trelloList2);
        trelloLists.add(trelloList3);
        return trelloLists;
    }

    @Test
    void mapToListTest() {
        //Given
        List<TrelloListDto> trelloListDtos = prepareListOfTrelloListDtoToTest();
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        List<TrelloList> activeTrelloLists = trelloLists.stream()
                .filter(trelloList -> trelloList.isClosed()==false)
                .collect(Collectors.toList());
        //Then
        assertEquals(3, trelloLists.size());
        assertEquals(2, activeTrelloLists.size());
        assertEquals("IdDto1", trelloLists.get(0).getId());
    }

    @Test
    void mapToListDtoTest() {
        //Given
        List<TrelloList> trelloLists = prepareListOfTrelloListToTest();
        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        List<TrelloListDto> closedTrelloListDtos = trelloListDtos.stream()
                .filter(trelloListDto -> trelloListDto.isClosed()==true)
                        .collect(Collectors.toList());
        //Then
        assertEquals(3, trelloListDtos.size());
        assertEquals(1, closedTrelloListDtos.size());
        assertEquals("Id1", trelloListDtos.get(0).getId());
    }

    @Test
    void mapToBoardsTest() {
        //Given
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("Board1", "TestBoard1", prepareListOfTrelloListDtoToTest());
        trelloBoardDtoList.add(trelloBoardDto);
        //When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);
        //Then
        assertEquals(1, trelloBoardList.size());
        assertEquals("Board1", trelloBoardList.get(0).getId());
        assertEquals(3, trelloBoardList.get(0).getLists().size());
    }

    @Test
    void mapToBoardsDtoTest() {
        //Given
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("Board1", "TestBoard1", prepareListOfTrelloListToTest());
        trelloBoardList.add(trelloBoard);
        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);
        //Then
        assertEquals(1, trelloBoardDtoList.size());
        assertEquals("Board1", trelloBoardDtoList.get(0).getId());
        assertEquals(3, trelloBoardDtoList.get(0).getLists().size());
    }

    @Test
    void mapToCardDtoTest() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Card", "Trello Card Test", "PosTest", "1");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("Card", trelloCardDto.getName());
        assertEquals("1", trelloCardDto.getListId());
    }

    @Test
    void mapToCardTest() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("CardDto", "Trello CardDto Test", "PosTest", "2");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("CardDto", trelloCard.getName());
        assertEquals("2", trelloCard.getListId());
    }
}
