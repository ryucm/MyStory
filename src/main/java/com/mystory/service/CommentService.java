package com.mystory.service;

import com.mystory.domain.Comment;
import com.mystory.domain.Post;
import com.mystory.domain.User;
import com.mystory.dto.comment.CommentRequestDto;
import com.mystory.dto.ResponseDto;
import com.mystory.repository.CommentRepository;
import com.mystory.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public ResponseDto<?> addComment(CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다."));
        Comment comment = new Comment(commentRequestDto, user, post);
        commentRepository.save(comment);
        return ResponseDto.success(comment);
    }

    public ResponseDto<?> getComments(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다."));
        List<Comment> commentList = commentRepository.findAllByPost(post);
        return ResponseDto.success(commentList);
    }

    public ResponseDto<?> updateComment(Long id, CommentRequestDto requestDto ,User user) {

        Post post = postRepository.findById( requestDto.getPostId() )
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));

        Comment comment = commentRepository.findById( id )
                .orElseThrow(() -> new RuntimeException("댓글이 없습니다."));

        if( !comment.getAuthor().equals( user.getUsername() ) ){
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다.");
        }

        comment.update( requestDto );

        commentRepository.save( comment );

        return ResponseDto.success(comment);
    }

    public ResponseDto<?> deleteComment( Long id , User user) {

        Comment comment = commentRepository.findById( id )
                .orElseThrow(() -> new RuntimeException("댓글이 없습니다."));

        if( !comment.getAuthor().equals( user.getUsername() ) ){
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다.");
        }
        commentRepository.deleteById( id );

        return ResponseDto.success("delete success");

    }

}
