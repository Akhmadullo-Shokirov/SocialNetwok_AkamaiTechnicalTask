package net.ddns.akhmadullo.SocialNetwork.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.ddns.akhmadullo.SocialNetwork.entity.Post;
import net.ddns.akhmadullo.SocialNetwork.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<String> save(PostWrapper postWrapper) {
        if (postWrapper.getAuthor().equals("") || postWrapper.getContent().equals("")) {
            return new ResponseEntity<>("Author or content field is empty", HttpStatus.BAD_REQUEST);
        }
        Post newPost = new Post(postWrapper.getAuthor(), postWrapper.getContent());
        postRepository.save(newPost);
        return new ResponseEntity<>("Saved a new post with the id: " + newPost.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Post> view(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post postToView = optionalPost.get();
            postToView.incrementViewCount(1);
            postRepository.save(postToView);
            return new ResponseEntity<>(postToView, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> update(String postId, PostWrapper postWrapper) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post postToUpdate = optionalPost.get();
            if (postToUpdate.getAuthor().equals(postWrapper.getAuthor())) {
                postToUpdate.setContent(postWrapper.getContent());
                postRepository.save(postToUpdate);
                return new ResponseEntity<>("Updated successfully.", HttpStatus.OK);
            }
            return new ResponseEntity<>("The content author did not match", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Post not found", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> delete(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(postId);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Post not found", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Post>> viewTopTen() {
        return new ResponseEntity<>(postRepository.findTopTenViews(), HttpStatus.OK);
    }
}