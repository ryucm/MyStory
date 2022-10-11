package com.mystory.service;

import com.mystory.domain.Post;
import com.mystory.domain.User;
import com.mystory.dto.PostInfoDto;
import com.mystory.dto.PostRequestDto;
import com.mystory.repository.PostRepository;
import com.mystory.security.UserDetailslmpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> postList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public PostInfoDto postView(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 POST가 존재하지 않습니다."));

        PostInfoDto postInfoDto = new PostInfoDto(post);
        return postInfoDto;
    }

    @Transactional
    public void create(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
    }

    @Transactional
    public void update(Long id, PostRequestDto postRequestDto, UserDetailslmpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("없다 새킫야"));
        if (checkUser(id, userDetails)){
            post.update(postRequestDto);
            postRepository.save(post);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"본인만 수정할 수 있습니다.");
        }
    }
    @Transactional
    public void delete(Long id, UserDetailslmpl userDetailslmpl) {
        if (checkUser(id, userDetailslmpl)) {
            postRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"본인만 수정할 수 있습니다.");
        }
    }

    public boolean checkUser(Long id, UserDetailslmpl userDetailslmpl) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("없다 새킫야"));
        if (post.getUser().getId().equals(userDetailslmpl.getUser().getId())){
            return true;
        } else {
            return false;
        }
    }
}
