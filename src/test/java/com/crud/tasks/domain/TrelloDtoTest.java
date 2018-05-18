package com.crud.tasks.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrelloDtoTest {

    @Test
    public void testGettersAfterNoArgsConstructor() {
        //given
        TrelloDto trelloDto =new TrelloDto();

        // When and Then
        assertNull(trelloDto.getBoard());
        assertNull(trelloDto.getCard());
    }

    @Test
    public void testGettersAfterAllArgsConstructor() {
        TrelloDto trelloDto =new TrelloDto(1,3);

        // When and Then
       assertEquals(1,trelloDto.getBoard().intValue());
       assertEquals(3,trelloDto.getCard().intValue());
    }
}