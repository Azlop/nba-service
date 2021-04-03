package com.carta.nbaservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.dtos.CommentDto;
import com.carta.nbaservice.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addCommentToGame(@RequestBody @Validated CommentDto commentDto) {
        return commentService.addCommentToGame(commentDto.getGameId(), commentDto.getText());
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Comment modifyCommentOnGame(@PathVariable(value = "commentId") Integer commentId, @RequestBody @Validated CommentDto commentDto) {
        return commentService.modifyCommentOnGame(commentId, commentDto.getText());
    }
}
