package com.mystory.controller;


import com.mystory.domain.Post;
import com.mystory.domain.User;
import com.mystory.dto.ResponseDto;
import com.mystory.dto.post.PostInfoDto;
import com.mystory.dto.user.LoginRequestDto;
import com.mystory.dto.post.PostRequestDto;
import com.mystory.security.UserDetailsImpl;
import com.mystory.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public ModelAndView index(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
                        Pageable pageable, Model model) {
        Page<Post> list = postService.postList(pageable);
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("list", list);
        modelAndView.addObject("nowPage", nowPage);
        modelAndView.addObject("startPage", startPage);
        modelAndView.addObject("endPage", endPage);

        return modelAndView;
    }

    @GetMapping("/post/{id}")
    public ResponseDto<?> view(@PathVariable("id") Long id) {
        ResponseDto postInfoDto = postService.postView(id);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("post");
//        modelAndView.addObject("post", postInfoDto);
        return postInfoDto;
    }

    @GetMapping("/post/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("postModify");
        modelAndView.addObject("post", postService.postView(id));
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @PostMapping("/post/update/{id}")
    public String update(@PathVariable("id") Long id, PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.update(id, postRequestDto, userDetails);
        return "redirect:/";
    }

    @GetMapping("/post/write")
    public ModelAndView write(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("postWrite");
        System.out.println(httpServletRequest.getHeader("Refresh-Token"));
        return modelAndView;
    }

    @PostMapping("/post/create")
    public ModelAndView create(PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        User user = userDetailsImpl.getUser();
        postService.create(postRequestDto, user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("message");
        modelAndView.addObject("message", "글 작성이 완료되었습니다.");
        modelAndView.addObject("searchUrl", "/");
        return modelAndView;
    }

    @GetMapping("/post/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetailslmpl) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        postService.delete(id,userDetailslmpl);
        return modelAndView;
    }
}
