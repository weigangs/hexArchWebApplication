package com.swg1024.hexarch.domain.createUser;

import com.swg1024.hexarch.port.persist.createUser.CreateUserOutPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserDomainTest {

    @Mock
    private CreateUserOutPort createUserOutPort;

    @BeforeEach
    public void given() {
        when(createUserOutPort.createUser(any())).thenReturn(1);
    }

    @Test
    public void whenRun() {
        CreateUserDomain createUserDomain = new CreateUserDomain("nicholas", "email@172.com", createUserOutPort);

        createUserDomain.createUser();

    }
}
