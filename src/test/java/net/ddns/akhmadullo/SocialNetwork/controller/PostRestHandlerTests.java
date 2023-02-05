package net.ddns.akhmadullo.SocialNetwork.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.ddns.akhmadullo.SocialNetwork.entity.Post;
import net.ddns.akhmadullo.SocialNetwork.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PostRestHandlerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private Post post;

    private PostService.PostWrapper postWrapper;

    @BeforeEach
    public void setup() {
        postWrapper = new PostService.PostWrapper("John Doe", "I had a nice trip in Sydney!");
        post = new Post(postWrapper.getAuthor(), postWrapper.getContent());
        post.setId("42a347c8-350a-4aa0-b330-b5abf3696721");
    }

    @Test
    public void shouldCreateNewPost() throws Exception {
        when(postService.save(any(PostService.PostWrapper.class)))
                .thenReturn(ResponseEntity.ok("Saved a new post with the id: " + post.getId()));

        this.mockMvc.perform(post("/social-network-post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postWrapper)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFetchPostById() throws Exception {
        when(postService.view(anyString())).thenReturn(ResponseEntity.ok(post));

        this.mockMvc.perform(
                get("/social-network-post/view/{postId}",
                        "42a347c8-350a-4aa0-b330-b5abf3696721"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author", is(post.getAuthor())))
                .andExpect(jsonPath("$.content", is(post.getContent())))
                .andExpect(jsonPath("$.id", is(post.getId())));
    }

    @Test
    public void shouldUpdatePost() throws Exception {
        when(postService.update(anyString(), any(PostService.PostWrapper.class)))
                .thenReturn(ResponseEntity.ok("Updated successfully."));

        this.mockMvc.perform(put("/social-network-post/update/{postId}",
                        "42a347c8-350a-4aa0-b330-b5abf3696721")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postWrapper)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeletePost() throws Exception {
        when(postService.delete(anyString()))
                .thenReturn(ResponseEntity.ok("Deleted successfully"));

        this.mockMvc.perform(delete("/social-network-post/delete/{postId}",
                        "42a347c8-350a-4aa0-b330-b5abf3696721"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFetchTopTenViews() throws Exception {
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        when(postService.viewTopTen()).thenReturn(ResponseEntity.ok(postList));

        this.mockMvc.perform(get("/social-network-post/top-ten-views"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(postList.size())));
    }
}
