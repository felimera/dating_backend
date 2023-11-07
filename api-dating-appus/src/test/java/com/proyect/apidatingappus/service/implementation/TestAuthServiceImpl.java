package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.controller.dto.auth.SignUpDto;
import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.model.entitytest.SignUpDtoBuider;
import com.proyect.apidatingappus.model.entitytest.UserBuilder;
import com.proyect.apidatingappus.repository.UserRepository;
import com.proyect.apidatingappus.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TestAuthServiceImpl {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    CustomerService customerService;
    @InjectMocks
    AuthServiceImpl authServiceImpl;

    SignUpDto signUpDto;
    User user;

    @BeforeEach
    void setUp() {
        signUpDto = SignUpDtoBuider.builder().build().toSignUpDto();
        user = UserBuilder.builder().build().toUser();
    }

    @DisplayName("Test JUnit for the CreateUser method.")
    @Test
    void when_the_record_is_created_correctly() {
        given(userRepository.findOneByEmail(anyString())).willReturn(Optional.empty());
        given(userRepository.save(any())).willReturn(user);
        boolean resultado = authServiceImpl.createUser(signUpDto);
        assertTrue(resultado);
    }

    @DisplayName("Test JUnit for the CreateUser method.")
    @Test
    void when_the_user_is_not_found() {
        given(userRepository.findOneByEmail(anyString())).willReturn(Optional.of(user));
        assertThrows(BusinessException.class, () -> authServiceImpl.createUser(signUpDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("Test JUnit for the CreateUser method.")
    @Test
    void when_the_password_has_the_required_length() {
        signUpDto = SignUpDtoBuider.builder().build().toEditPassword("password");
        assertTrue(signUpDto.getPassword().length() >= 8);
    }
}