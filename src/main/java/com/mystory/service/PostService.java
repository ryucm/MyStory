package com.mystory.service;

import com.mystory.domain.Comment;
import com.mystory.domain.Post;
import com.mystory.domain.User;
import com.mystory.dto.ResponseDto;
import com.mystory.dto.comment.CommentListDto;
import com.mystory.dto.comment.CommentResponseDto;
import com.mystory.dto.post.PostInfoDto;
import com.mystory.dto.user.LoginRequestDto;
import com.mystory.dto.post.PostRequestDto;
import com.mystory.repository.CommentRepository;
import com.mystory.repository.PostRepository;
import com.mystory.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Page<Post> postList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public ResponseDto<?> postView(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 POST가 존재하지 않습니다."));
        List<Comment> commentList = commentRepository.findAllByPost(post);
        List<CommentListDto> commentListDtos = new ArrayList<>();
        for (Comment comment : commentList) {
            commentListDtos.add(new CommentListDto(comment));
        }
        PostInfoDto postInfoDto = new PostInfoDto(post, commentListDtos);
        return ResponseDto.success(postInfoDto);
    }

    @Transactional
    public void create(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
    }

    @Transactional
    public void update(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("없다 새킫야"));
        if (checkUser(id, userDetails)){
            post.update(postRequestDto);
            postRepository.save(post);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"본인만 수정할 수 있습니다.");
        }
    }
    @Transactional
    public void delete(Long id, UserDetailsImpl userDetailslmpl) {
        if (checkUser(id, userDetailslmpl)) {
            postRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"본인만 수정할 수 있습니다.");
        }
    }

    public boolean checkUser(Long id, UserDetailsImpl userDetailslmpl) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("없다 새킫야"));
        if (post.getUser().getId().equals(userDetailslmpl.getUser().getId())){
            return true;
        } else {
            return false;
        }
    }
}
