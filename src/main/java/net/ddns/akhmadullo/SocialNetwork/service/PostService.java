package net.ddns.akhmadullo.SocialNetwork.service;

import lombok.*;
import net.ddns.akhmadullo.SocialNetwork.entity.Post;
import net.ddns.akhmadullo.SocialNetwork.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class PostWrapper {
        private String author;
        private String content;
    }

    public String save(PostWrapper postWrapper) {
        Post newPost = new Post();
        newPost.setDate(LocalDate.now());
        newPost.setAuthor(postWrapper.getAuthor());
        newPost.setContent(postWrapper.getContent());
        newPost.incrementViewCount(0);
        postRepository.save(newPost);

        return "Saved a new post with the id: " + newPost.getId();
    }

    public Post view(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post postToView = optionalPost.get();
            postToView.incrementViewCount(1);
            postRepository.save(postToView);
            return postToView;
        }
        return null;
    }

    public String update(String postId, PostWrapper postWrapper) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post postToUpdate = optionalPost.get();
            postToUpdate.setAuthor(postWrapper.getAuthor());
            postToUpdate.setContent(postWrapper.getContent());
            postRepository.save(postToUpdate);
            return "Updated successfully.";
        }

        return "Post not found";
    }

    public String delete(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(postId);
            return "Deleted successfully";
        }

        return "Post not found";
    }

    public List<Post> viewTop10() {
        return postRepository.findTop10Views();
    }
}
