package net.ddns.akhmadullo.SocialNetwork.service;

import net.ddns.akhmadullo.SocialNetwork.entity.Post;
import net.ddns.akhmadullo.SocialNetwork.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private PostService.PostWrapper postWrapper;

    @Autowired
    private Post post;

    @BeforeEach
    public void setup() {
        postWrapper = new PostService.PostWrapper("John Doe", "I had a nice trip in Sydney!");

        post = new Post(postWrapper.getAuthor(), postWrapper.getContent());
    }

    @Test
    public void givenPostObject_whenSavePostObject_thenReturnStringWithId() {
//        Given
        given(postRepository.save(any(Post.class))).willReturn(post);

//        When
        ResponseEntity<String> actualSaveResponse = postService.save(postWrapper);
        String expected = "Saved a new post with the id: " + post.getId();

//        Then
        Assertions.assertThat(actualSaveResponse).isNotNull();
        Assertions.assertThat(actualSaveResponse).isEqualTo(new ResponseEntity<>(expected, HttpStatus.OK));
    }

    @Test
    public void givenPostId_whenView_thenReturnPostObject() {
//        Given
        post.setId("42a347c8-350a-4aa0-b330-b5abf3696721");

//        When
        when(postRepository.findById(anyString())).thenReturn(Optional.of(post));

//        Then
        ResponseEntity<Post> actualViewResponse = postService.view(post.getId());
        Assertions.assertThat(actualViewResponse).isEqualTo(new ResponseEntity<>(post, HttpStatus.OK));

    }

    @Test
    public void givenPostDetails_whenUpdate_thenReturnHttpStatus() {

        post.setId("42a347c8-350a-4aa0-b330-b5abf3696721");

//        When
        when(postRepository.findById(anyString())).thenReturn(Optional.of(post));
        when(postRepository.save(any())).thenReturn(post);

        postWrapper.setContent("Krakow is a beautiful city in Poland");

//        Then
        ResponseEntity<String> actualUpdateResponse = postService.update(post.getId(), postWrapper);
        Assertions.assertThat(actualUpdateResponse).isEqualTo(
                new ResponseEntity<>("Updated successfully.", HttpStatus.OK));
    }

    @Test
    public void givenPostId_whenDelete_thenReturnHttpStatus() {
//        Given
        String postId = "42a347c8-350a-4aa0-b330-b5abf3696721";


        when(postRepository.findById(anyString())).thenReturn(Optional.of(post));

//        Then
        ResponseEntity<String> actualDeleteResponse = postService.delete(postId);
        Assertions.assertThat(actualDeleteResponse).isEqualTo(
                new ResponseEntity<>("Deleted successfully", HttpStatus.OK));
    }
}
