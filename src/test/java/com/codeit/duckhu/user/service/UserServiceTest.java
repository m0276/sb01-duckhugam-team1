package com.codeit.duckhu.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl sut;

    @Test
    @DisplayName("회원가입 성공")
    void UserServiceTest() {
        //given
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        UserRegisterRequest request = new UserRegisterRequest(
                "testA@example.com", "testA", "testa1234!"
        );
        User user = new User(id, "testA@example.com", "testA", "testa1234!", now);
        UserDto dto = new UserDto(id, "testA@example.com", "testA", now);
        given(userRepository.existsByEmail("testA@example.com")).willReturn(false);
        given(userRepository.save(any(User.class))).willReturn(user);
        given(userMapper.toDto(any(User.class))).willReturn(dto);

        //when
        UserDto result = sut.register(request);

        //then
        assertThat(result.getEmail()).isEqualTo("testA@example.com");
        assertThat(result.getNickname()).isEqualTo("testA");
        verify(userRepository,times(1)).save(any(User.class));
    }
}
