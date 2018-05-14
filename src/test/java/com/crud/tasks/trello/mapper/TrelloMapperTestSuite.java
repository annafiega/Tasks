package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TrelloMapperTestSuite {

    private TrelloMapper trelloMapper=new TrelloMapper();
    private List<TrelloListDto>trelloListDtoStub;
    private List<TrelloList>trelloListStub;
    private List<TrelloBoardDto> trelloBoardsDtoStub;
    private List<TrelloBoard>trelloBoardsStub;

    @Before
    public  void prepareTestData(){

        List<TrelloListDto>trelloListDto=new ArrayList<>();
        List<TrelloList>trelloList =new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1","dto list 1", false));
        trelloListDto.add(new TrelloListDto("2","dto list 2", true));
        trelloList.add(new TrelloList("1", "domain list 1",false));
        trelloList.add(new TrelloList("2", "domain list 2", true));


        List<TrelloBoardDto> trelloBoardsDto =new ArrayList<>();
        List<TrelloBoard>trelloBoards=new ArrayList<>();
        trelloBoardsDto.add(new TrelloBoardDto("1","dto board 1", trelloListDto));
        trelloBoardsDto.add(new TrelloBoardDto("2","dto board 2", trelloListDto));
        trelloBoards.add(new TrelloBoard("1", "domain board 1", trelloList));
        trelloBoards.add(new TrelloBoard("2", "domain board 2",  trelloList));

        trelloListDtoStub=trelloListDto;
        trelloListStub=trelloList;
        trelloBoardsDtoStub=trelloBoardsDto;
        trelloBoardsStub=trelloBoards;
    }

    @Test
    public void testMapToBoards(){
        //given
        //when
        List<TrelloBoard> resultList=trelloMapper.mapToBoards(trelloBoardsDtoStub);

        //then
        assertEquals(2,resultList.size());
        assertEquals("dto board 1", resultList.get(0).getName());
        assertEquals("dto list 1", resultList.get(0).getLists().get(0).getName());
    }

    @Test
    public void testMapToBoardsDto(){
        //given
        //when
        List<TrelloBoardDto>resultList=trelloMapper.mapToBoardsDto(trelloBoardsStub);

        //then
        assertEquals(2, resultList.size());
        assertEquals("domain board 1", resultList.get(0).getName());
        assertEquals("domain list 1", resultList.get(0).getLists().get(0).getName());
    }

    @Test
    public void testMapToList(){
        //given
        //when
        List<TrelloList>resultList=trelloMapper.mapToList(trelloListDtoStub);

        //then
        assertEquals(2, resultList.size());
        assertEquals("dto list 1", resultList.get(0).getName());
    }

    @Test
    public void testMapToListDto(){
        //given
        //when
        List<TrelloListDto> resultList=trelloMapper.mapToListDto(trelloListStub);

        //then
        assertEquals(2,resultList.size());
        assertEquals("domain list 1", resultList.get(0).getName());
    }

    @Test
    public void testMapToCardDto(){
        //given
        TrelloCard trelloCard=new TrelloCard("domain card", "testing card","top","1");

        //when
        TrelloCardDto result= trelloMapper.mapToCardDto(trelloCard);

        //then
        assertEquals("domain card", result.getName());
    }

    @Test
    public void testMapToCard(){
        //given
        TrelloCardDto trelloCardDto=new TrelloCardDto("dto card", "testing card","top","1");

        //when
        TrelloCard result =trelloMapper.mapToCard(trelloCardDto);

        //then
        assertEquals("dto card", result.getName());
    }
}
