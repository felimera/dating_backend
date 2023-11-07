package com.proyect.apidatingappus.service.jwt;

import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.model.entitytest.UserBuilder;
import com.proyect.apidatingappus.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TestUserJwtServiceImpl {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserJwtServiceImpl userJwtService;

    User user;
    @BeforeEach
    void setUp() {
        user = UserBuilder.builder().build().toUser();
    }
@Test
    void when_the_data_is_passed_successfully()
    {
        given(userRepository.findOneByEmail(anyString())).willReturn(Optional.of(user));
        UserDetails userDetails=userJwtService.loadUserByUsername(anyString());
        assertThat(userDetails).isNotNull();
    }
    @Test
    void when_the_record_is_not_found(){
        given(userRepository.findOneByEmail(anyString())).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userJwtService.loadUserByUsername(anyString()));
        verify(userRepository, never()).findById(any());
    }
}