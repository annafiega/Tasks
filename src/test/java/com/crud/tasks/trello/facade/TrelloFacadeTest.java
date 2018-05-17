package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    TrelloService trelloService;

    @Mock
    TrelloValidator trelloValidator;

    @Mock
    TrelloMapper trelloMapper;


    @Test
    public void shouldFetchEmptyList (){
        //given
        List<TrelloListDto>trelloList =new ArrayList<>();
        trelloList.add(new TrelloListDto("1","test_list", false));

        List<TrelloBoardDto>trelloBoards=new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1","test", trelloList));

        List<TrelloList>mappedTrelloLists=new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));

        List<TrelloBoard>mappedTrelloBoards=new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1","test_board", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList<>());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());

        //when
        List<TrelloBoardDto>trelloBoardDtos=trelloFacade.fetchTrelloBoards();

        //then
        assertNotNull(trelloBoardDtos);
        assertEquals(0,trelloBoardDtos.size());

    }

    @Test

    public void shouldFetchTrelloBoards(){
        //given
        List<TrelloListDto>trelloList =new ArrayList<>();
        trelloList.add(new TrelloListDto("1","my_list", false));

        List<TrelloBoardDto>trelloBoards=new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1","my_task", trelloList));

        List<TrelloList>mappedTrelloLists=new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        List<TrelloBoard>mappedTrelloBoards=new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1","my_task", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        //when
        List<TrelloBoardDto>trelloBoardDtos=trelloFacade.fetchTrelloBoards();

        //then
        assertNotNull(trelloBoardDtos);
        assertEquals(1,trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1",trelloBoardDto.getId() );
            assertEquals("my_task",trelloBoardDto.getName() );

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1",trelloListDto.getId() );
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });

    }

    @Test
    public void testCreateCard (){
        //given
        TrelloCardDto trelloCardDto =new TrelloCardDto("test_card","testing card", "top","1");
        TrelloCard mappedTrelloCard= new TrelloCard("test_card","testing card", "top","1");
        CreatedTrelloCardDto createdTrelloCardDto= new CreatedTrelloCardDto("1","test_card1","http://test.cards/1");

        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(mappedTrelloCard);
        when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(trelloMapper.mapToCardDto(mappedTrelloCard)).thenReturn(trelloCardDto);

        //when
        CreatedTrelloCardDto result = trelloFacade.createCard(trelloCardDto);

        //then
        assertEquals("1",result.getId() );
        assertEquals("test_card1", result.getName());
        assertEquals("http://test.cards/1", result.getShortUrl());

    }
}
