package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.controller.dto.auth.ChangePassowrdDto;
import com.proyect.apidatingappus.controller.dto.auth.SignUpDto;
import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.exception.NotFoundException;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.model.entitytest.ChangePassowrdDtoBuilder;
import com.proyect.apidatingappus.model.entitytest.SignUpDtoBuider;
import com.proyect.apidatingappus.model.entitytest.UserBuilder;
import com.proyect.apidatingappus.repository.UserRepository;
import com.proyect.apidatingappus.service.AccessPermitsService;
import com.proyect.apidatingappus.service.CustomerService;
import com.proyect.apidatingappus.service.TipoRoleService;
import com.proyect.apidatingappus.util.JwtUtil;
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
import static org.mockito.ArgumentMatchers.*;
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
    JwtUtil jwtUtil;
    @Mock
    CustomerService customerService;
    @Mock
    AccessPermitsService accessPermitsService;
    @Mock
    TipoRoleService tipoRoleService;
    @InjectMocks
    AuthServiceImpl authServiceImpl;

    SignUpDto signUpDto;
    User user;
    ChangePassowrdDto changePassowrdDto;

    @BeforeEach
    void setUp() {
        signUpDto = SignUpDtoBuider.builder().build().toSignUpDto();
        user = UserBuilder.builder().build().toUser();
        changePassowrdDto = ChangePassowrdDtoBuilder.builder().build().toChangePassowrdDto();
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

    @DisplayName("Test JUnit for the UpdatePassword method.")
    @Test
    void when_the_password_change_works() {
        User userOld = UserBuilder.builder().build().toEditPassword("$2a$10$TsunHg5DVZY8HAcnQUH3A.UvQMZqVt9X174utYV6LX8JSCH1/r3Fi");

        given(userRepository.findOneByEmail(anyString())).willReturn(Optional.of(userOld));
        given(passwordEncoder.matches(changePassowrdDto.getPasswordOld(), userOld.getPassword())).willReturn(true);
        given(userRepository.save(any(User.class))).willReturn(user);
        given(userRepository.existsById(anyLong())).willReturn(true);

        boolean resultado = authServiceImpl.updatePassword(changePassowrdDto);
        assertTrue(resultado);
    }

    @DisplayName("Test JUnit for the UpdatePassword method.")
    @Test
    void when_the_query_by_email_does_not_return_anything() {
        given(userRepository.findOneByEmail(anyString())).willReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> authServiceImpl.updatePassword(changePassowrdDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("Test JUnit for the UpdatePassword method.")
    @Test
    void when_the_password_matches_the_original_one() {
        User userOld = UserBuilder.builder().build().toEditPassword("$2a$10$TsunHg5DVZY8HAcnQUH3A.UvQMZqVt9X174utYV6LX8JSCH1/r3Fi");
        given(userRepository.findOneByEmail(anyString())).willReturn(Optional.of(userOld));
        assertThrows(BusinessException.class, () -> authServiceImpl.updatePassword(changePassowrdDto));
        verify(userRepository, never()).save(any(User.class));
    }
}