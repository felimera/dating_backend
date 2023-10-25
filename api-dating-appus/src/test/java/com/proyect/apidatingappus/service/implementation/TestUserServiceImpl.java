package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.model.entitytest.UserBuilder;
import com.proyect.apidatingappus.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TestUserServiceImpl {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    User user;

    @BeforeEach
    void setUp() {
        user = UserBuilder.builder().build().toUser();
    }

    @DisplayName("Test JUnit for the PostUser method.")
    @Test
    void when_the_user_is_registered_correctly() {
        given(userRepository.save(Mockito.any())).willReturn(user);
        User postUser = userServiceImpl.postUser(user);
        assertThat(postUser).isNotNull();
    }

    @DisplayName("Test JUnit for the PostUser method.")
    @Test
    void when_there_is_a_repeated_email() {
        given(userRepository.findOneByEmail(anyString())).willReturn(Optional.of(user));
        assertThrows(BusinessException.class, () -> userServiceImpl.postUser(user));
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("Test JUnit for the PutUser method.")
    @Test
    void when_a_record_is_updated_successfully() {
        given(userRepository.save(user)).willReturn(user);
        given(userRepository.findOneByEmail(anyString())).willReturn(Optional.of(user));
        user.setPassword("789654");
        user.setEmail("nombre@nombre.com");
        User putUser = userServiceImpl.putUser(user);
        assertThat(putUser).isNotNull();
        assertThat(putUser.getPassword()).isEqualTo("789654");
        assertThat(putUser.getEmail()).isEqualTo("nombre@nombre.com");
    }

    @DisplayName("Test JUnit for the PutUser method.")
    @Test
    void when_the_user_email_returns_no_results() {
        given(userRepository.findOneByEmail(anyString())).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userServiceImpl.putUser(user));
        verify(userRepository, never()).save(any(User.class));
    }
}