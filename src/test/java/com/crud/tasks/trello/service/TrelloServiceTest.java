package com.crud.tasks.trello.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    TrelloService trelloService;

    @Mock
    TrelloClient trelloClient;

    @Mock
    AdminConfig adminConfig;

    @Mock
    SimpleEmailService emailService;

    @Test
    public void shouldFetchTrelloBoards(){
        //given
        List<TrelloBoardDto> trelloBoardDto=new ArrayList<>();
        List<TrelloListDto> lists =new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1","test_board", lists));
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDto);

        //when
        List<TrelloBoardDto> result=trelloService.fetchTrelloBoards();

        //then
        assertEquals("test_board", result.get(0).getName());

    }

    @Test
    public void testCreateTrelloCard(){
        //given
        TrelloCardDto trelloCard =new TrelloCardDto("test name", "test description","test pos","1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1","test name","test url");
        String adminMail="test@test";

        when(trelloClient.createNewCard(trelloCard)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn(adminMail);

        //when
        CreatedTrelloCardDto result =trelloService.createTrelloCard(trelloCard);

        assertEquals(createdTrelloCardDto, result);
        assertEquals("test url", result.getShortUrl());
    }

}
