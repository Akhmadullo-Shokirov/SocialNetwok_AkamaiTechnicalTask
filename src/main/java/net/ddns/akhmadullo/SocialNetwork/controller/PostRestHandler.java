package net.ddns.akhmadullo.SocialNetwork.controller;

import net.ddns.akhmadullo.SocialNetwork.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin
public class PostRestHandler {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public String save(@RequestBody PostService.PostWrapper newPost) {
        return postService.save(newPost);
    }

    @GetMapping("/view/{postId}")
    public Post view(@PathVariable String postId) {
        return postService.view(postId);
    }

    @PutMapping("/update/{postId}")
    public String update(@PathVariable String postId, @RequestBody PostService.PostWrapper postWrapper) {
        return postService.update(postId, postWrapper);
    }

    @DeleteMapping("/delete/{postId}")
    public String delete(@PathVariable String postId) {
        return postService.delete(postId);
    }

    @GetMapping("/top10views")
    public List<Post> viewTop10() {
        return postService.viewTop10();
    }
}