package com.mystory.controller;


import com.mystory.dto.comment.CommentRequestDto;
import com.mystory.dto.ResponseDto;
import com.mystory.security.UserDetailsImpl;
import com.mystory.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/comment")
    public ResponseDto<?> addComment(
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return commentService.addComment(requestDto, userDetails.getUser());
    }

    @GetMapping("/comments/{id}")
    public ResponseDto<?> getComments( @PathVariable Long id ){
        return commentService.getComments( id );
    }

    @PutMapping("/auth/comments/{id}")
    public ResponseDto<?> updateComment(@PathVariable Long id,
                                        @RequestBody CommentRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment( id , requestDto , userDetails.getUser() );
    }

    @DeleteMapping("/auth/comments/{id}")
    public ResponseDto<?> deleteComment(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment( id , userDetails.getUser() );
    }
}
