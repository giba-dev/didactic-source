package dev.giba.labs.acme.mockito.play;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ServiceBTest {
    @Mock
    private DummyBankConcreteService mockedDummyBankConcreteService;
    @InjectMocks
    private ServiceB serviceB;

    @Test
    @DisplayName("Deve retornar um novo id de transação")
    void shouldReturnANewTransactionIdCorrectly() {
        //When
        var uuid = UUID.randomUUID();
        Mockito.when(this.mockedDummyBankConcreteService.newTransactionId()).thenReturn(uuid);

        //Given
        var transactionId = this.serviceB.newTransactionId();

        //Then
        Assertions.assertEquals(uuid, transactionId);
    }
}
