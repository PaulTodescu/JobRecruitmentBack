package com.wt.jrs.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path = "/job/{jobId}")
    public ResponseEntity<CommentEntity> addComment(
            @PathVariable("jobId") Long jobId,
            @RequestBody String content) {
        this.commentService.addComment(jobId, content);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/job/{jobId}/all")
    public ResponseEntity<List<CommentEntity>> getCommentsForJob(
            @PathVariable("jobId") Long jobId) {
        List<CommentEntity> comments = this.commentService.getComments(jobId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
