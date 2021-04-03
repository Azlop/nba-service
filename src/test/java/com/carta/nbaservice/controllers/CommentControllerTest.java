package com.carta.nbaservice.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.carta.nbaservice.services.CommentService;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    public static final int GAME_ID = 1;
    public static final int COMMENT_ID = 1;
    public static final String COMMENT_TEXT = "some comment";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    void givenCommentOnExistingGame_whenPostingComment_thenCommentIsCreated() throws Exception {
        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"gameId\":\"" + GAME_ID + "\",\"text\":\"" + COMMENT_TEXT + "\"}"))
                .andExpect(status().isCreated());

        verify(commentService, times(1)).addCommentToGame(GAME_ID, COMMENT_TEXT);
    }

    @Test
    void givenComment_whenUpdatingComment_thenCommentIsUpdated() throws Exception {
        mockMvc.perform(patch("/comments/" + COMMENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\":\"" + COMMENT_TEXT + "\"}"))
                .andExpect(status().isOk());

        verify(commentService, times(1)).modifyCommentOnGame(COMMENT_ID, COMMENT_TEXT);
    }
}
