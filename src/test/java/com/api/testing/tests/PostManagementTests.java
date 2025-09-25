package com.api.testing.tests;

import com.api.testing.core.BaseTest;
import com.api.testing.models.Post;
import com.api.testing.utils.ApiClient;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Управление постами")
@Owner("API Testing Team")
@Feature("Post Management")
@Story("CRUD Operations for Posts")
public class PostManagementTests extends BaseTest {
    
    private final ApiClient apiClient = new ApiClient(requestSpec);
    
    @Test
    @DisplayName("Получение всех постов")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем получение списка всех постов")
    void shouldGetAllPosts() {
        List<Post> posts = step("Получаем все посты", () ->
                apiClient.get("/posts")
                        .then()
                        .spec(successResponseSpec)
                        .extract().jsonPath().getList("", Post.class));

        step("Проверяем список постов", () -> {
            assertThat(posts).isNotEmpty();
            assertThat(posts.size()).isGreaterThan(0);

            Post firstPost = posts.get(0);
            assertThat(firstPost.getId()).isNotNull();
            assertThat(firstPost.getTitle()).isNotEmpty();
            assertThat(firstPost.getBody()).isNotEmpty();
            assertThat(firstPost.getUserId()).isNotNull();
        });
    }
    
    @Test
    @DisplayName("Получение поста по ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем получение конкретного поста по ID")
    void shouldGetPostById() {
        int postId = 1;

        Post post = step("Получаем пост по ID " + postId, () ->
                apiClient.get("/posts/" + postId)
                        .then()
                        .spec(successResponseSpec)
                        .extract().as(Post.class));

        step("Проверяем данные поста", () -> {
            assertThat(post).isNotNull();
            assertThat(post.getId()).isEqualTo(postId);
            assertThat(post.getTitle()).isNotEmpty();
            assertThat(post.getBody()).isNotEmpty();
            assertThat(post.getUserId()).isNotNull();
            assertThat(post.isValid()).isTrue();
        });
    }
    
    @Test
    @DisplayName("Создание нового поста")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем создание нового поста через POST запрос")
    void shouldCreateNewPost() {
        Post newPost = Post.builder()
                .userId(1)
                .title("Новый пост для тестирования")
                .body("Это тело нового поста, созданного для проверки POST запроса")
                .build();

        Post createdPost = step("Создаем новый пост", () ->
                apiClient.post("/posts", newPost)
                        .then()
                        .spec(createdResponseSpec)
                        .extract().as(Post.class));

        step("Проверяем созданный пост", () -> {
            assertThat(createdPost).isNotNull();
            assertThat(createdPost.getId()).isNotNull();
            assertThat(createdPost.getUserId()).isEqualTo(newPost.getUserId());
            assertThat(createdPost.getTitle()).isEqualTo(newPost.getTitle());
            assertThat(createdPost.getBody()).isEqualTo(newPost.getBody());
            assertThat(createdPost.isValid()).isTrue();
        });
    }
    
    @Test
    @DisplayName("Удаление поста")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем удаление поста через DELETE запрос")
    void shouldDeletePost() {
        int postIdToDelete = 1;

        step("Удаляем пост с ID " + postIdToDelete, () ->
                apiClient.delete("/posts/" + postIdToDelete)
                        .then()
                        .spec(successResponseSpec)
                        .statusCode(200));
    }
}
