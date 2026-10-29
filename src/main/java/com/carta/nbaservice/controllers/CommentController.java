package com.carta.nbaservice.controllers;

import com.carta.nbaservice.domain.Comment;
import com.carta.nbaservice.dtos.CommentDto;
import com.carta.nbaservice.exceptions.CommentNotFoundException;
import com.carta.nbaservice.services.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addCommentToGame(@RequestBody @Validated CommentDto commentDto) {
        log.info("Accessing POST Comment endpoint");
        return commentService.addCommentToGame(commentDto.gameId(), commentDto.text());
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Comment modifyCommentOnGame(@PathVariable(value = "commentId") Integer commentId, @RequestBody @Validated CommentDto commentDto) {
        log.info("Accessing PATCH Comment endpoint for ID: {}" , commentId);
        return commentService.modifyCommentOnGame(commentId, commentDto.text());
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable(value = "commentId") Integer commentId) throws CommentNotFoundException {
        log.info("Accessing DELETE Comment endpoint for ID: {}", commentId);
        commentService.deleteComment(commentId);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCommentNotFoundException(CommentNotFoundException exception) {}
}
