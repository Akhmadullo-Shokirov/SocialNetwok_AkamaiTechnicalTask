package net.ddns.akhmadullo.SocialNetwork.repository;

import net.ddns.akhmadullo.SocialNetwork.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, String> {

    @Query(value = "SELECT * FROM POST " +
            "ORDER BY view_count DESC LIMIT 10", nativeQuery = true)
    List<Post> findTopTenViews();
}
