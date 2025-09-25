package com.api.testing.tests;

import com.api.testing.core.BaseTest;
import com.api.testing.models.User;
import com.api.testing.utils.ApiClient;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты для управления пользователями
 * Покрывает основные операции с пользователями
 */
@DisplayName("Управление пользователями")
@Owner("API Testing Team")
@Feature("User Management")
@Story("CRUD Operations")
public class UserManagementTests extends BaseTest {
    
    private final ApiClient apiClient = new ApiClient(requestSpec);
    
    @Test
    @DisplayName("Получение существующего пользователя по ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем корректность получения данных пользователя по валидному ID")
    void shouldGetExistingUserById() {
        int userId = 1;

        User user = step("Получаем пользователя по ID " + userId, () ->
                apiClient.get("/users/" + userId)
                        .then()
                        .spec(successResponseSpec)
                        .extract().as(User.class));

        step("Проверяем корректность данных пользователя", () -> {
            assertThat(user).isNotNull();
            assertThat(user.getId()).isEqualTo(userId);
            assertThat(user.getName()).isNotEmpty();
            assertThat(user.getEmail()).isNotEmpty();
            assertThat(user.getUsername()).isNotEmpty();

            assertThat(user.getEmail()).contains("@");
            assertThat(user.isValidEmail()).isTrue();

            if (user.getAddress() != null) {
                assertThat(user.getAddress().getCity()).isNotEmpty();
                assertThat(user.getAddress().getStreet()).isNotEmpty();
            }
        });
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("Получение различных пользователей по ID")
    @Severity(SeverityLevel.NORMAL)
    void shouldGetDifferentUsersById(int userId) {
        step("Получаем пользователя с ID " + userId, () -> {
            User user = apiClient.get("/users/" + userId)
                    .then()
                    .spec(successResponseSpec)
                    .extract().as(User.class);
            
            assertThat(user.getId()).isEqualTo(userId);
            assertThat(user.getEmail()).isNotEmpty();
            assertThat(user.getName()).isNotEmpty();
        });
    }
    
    @Test
    @DisplayName("Получение несуществующего пользователя")
    @Severity(SeverityLevel.MINOR)
    void shouldReturn404ForNonExistentUser() {
        int nonExistentUserId = 999;

        step("Пытаемся получить несуществующего пользователя", () ->
                apiClient.get("/users/" + nonExistentUserId)
                        .then()
                        .spec(errorResponseSpec)
                        .statusCode(404));
    }
    
    @Test
    @DisplayName("Создание нового пользователя")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем создание нового пользователя через POST запрос")
    void shouldCreateNewUser() {
        User newUser = User.builder()
                .name("John Doe")
                .username("johndoe")
                .email("john.doe@example.com")
                .phone("123-456-7890")
                .website("johndoe.com")
                .build();

        User createdUser = step("Создаем нового пользователя", () ->
                apiClient.post("/users", newUser)
                        .then()
                        .spec(createdResponseSpec)
                        .extract().as(User.class));

        step("Проверяем созданного пользователя", () -> {
            assertThat(createdUser).isNotNull();
            assertThat(createdUser.getId()).isNotNull();
            assertThat(createdUser.getName()).isEqualTo(newUser.getName());
            assertThat(createdUser.getUsername()).isEqualTo(newUser.getUsername());
            assertThat(createdUser.getEmail()).isEqualTo(newUser.getEmail());
            assertThat(createdUser.getPhone()).isEqualTo(newUser.getPhone());
            assertThat(createdUser.getWebsite()).isEqualTo(newUser.getWebsite());
        });
    }
    
    @Test
    @DisplayName("Удаление пользователя")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем удаление пользователя через DELETE запрос")
    void shouldDeleteUser() {
        int userIdToDelete = 1;

        step("Удаляем пользователя с ID " + userIdToDelete, () ->
                apiClient.delete("/users/" + userIdToDelete)
                        .then()
                        .spec(successResponseSpec)
                        .statusCode(200));
    }
}
