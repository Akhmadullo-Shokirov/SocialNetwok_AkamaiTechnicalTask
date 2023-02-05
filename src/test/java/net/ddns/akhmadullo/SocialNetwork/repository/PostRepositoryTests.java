package net.ddns.akhmadullo.SocialNetwork.repository;

import net.ddns.akhmadullo.SocialNetwork.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class PostRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    public void setup() {
        post = new Post("John", "I managed to complete it");
        entityManager.persist(post);
        entityManager.flush();
    }
    @Test
    public void whenSave_thenReturnString() {

//        Then
        String foundId = post.getId();
        Assertions.assertThat(foundId).isNotNull();
    }

    @Test
    public void whenFindById_thenReturnPostObject() {
//        When
        Optional<Post> found = postRepository.findById(post.getId());

//        Then
        Assertions.assertThat(found.isPresent()).isTrue();

        String expectedAuthor = post.getAuthor();
        String actualAuthor = found.get().getAuthor();
        Assertions.assertThat(actualAuthor).isEqualTo(expectedAuthor);

        String expectedContent = post.getContent();
        String actualContent = found.get().getContent();
        Assertions.assertThat(actualContent).isEqualTo(expectedContent);
    }

    @Test
    public void whenFindTopTenViews_thenReturnList() {
//        When
        List<Post> topTen = postRepository.findTopTenViews();

//        Then
        int expected = 1;
        int actual = topTen.size();
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
