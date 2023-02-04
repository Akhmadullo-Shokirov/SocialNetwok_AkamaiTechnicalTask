package net.ddns.akhmadullo.SocialNetwork.controller;

import net.ddns.akhmadullo.SocialNetwork.entity.Post;
import net.ddns.akhmadullo.SocialNetwork.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/social-network-post")
@CrossOrigin
public class PostRestHandler {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<String> save(@RequestBody PostService.PostWrapper newPost) {
        return postService.save(newPost);
    }

    @GetMapping("/view/{postId}")
    public ResponseEntity<Post> view(@PathVariable String postId) {
        return postService.view(postId);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<String> update(@PathVariable String postId,
                                         @RequestBody PostService.PostWrapper postWrapper) {
        return postService.update(postId, postWrapper);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> delete(@PathVariable String postId) {
        return postService.delete(postId);
    }

    @GetMapping("/top-ten-views")
    public ResponseEntity<List<Post>> viewTopTen() {
        return postService.viewTopTen();
    }
}