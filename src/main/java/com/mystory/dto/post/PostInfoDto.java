package com.mystory.dto.post;

import com.mystory.domain.Comment;
import com.mystory.domain.Post;
import com.mystory.domain.User;
import com.mystory.dto.comment.CommentListDto;
import com.mystory.dto.comment.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoDto {
    private Long postId;
    private String user;
    private String title;

    private List<CommentResponseDto> comments;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<CommentListDto> commentResponseDtoList;


    public PostInfoDto(Post post, List<CommentListDto> comment ) {
        this.postId = post.getPostId();
        this.user = post.getUser().getUsername();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentResponseDtoList = comment;
    }
}