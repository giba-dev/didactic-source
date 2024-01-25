package dev.giba.labs.acme.mockito.play;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ServiceATest {
    @Mock
    private DummyBankService mockedDummyBankService;
    private ServiceA serviceA;

    @BeforeEach
    void beforeEachTest() {
        Mockito.reset(this.mockedDummyBankService);
        this.serviceA = new ServiceA(this.mockedDummyBankService);
    }

    @Test
    @DisplayName("Deve retornar o user id corretamente")
    void shouldReturnUserIdCorrectly() {
        //When
        var uuid = UUID.randomUUID();
        Mockito.when(this.mockedDummyBankService.getUserId()).thenReturn(uuid);

        //Given
        var userId = this.serviceA.getUserId();

        //Then
        Assertions.assertEquals(uuid, userId);
    }
}
