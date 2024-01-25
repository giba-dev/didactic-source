package dev.giba.labs.acme.mockito.play;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class DummyBankTest {
    @Mock
    private DummyBankService mockedDummyBankService;
    @Mock
    private DummyBankConcreteService mockedDummyBankConcreteService;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @BeforeEach
    void beforeEachTest() {
        Mockito.reset(this.mockedDummyBankService, this.mockedDummyBankConcreteService);
    }

    @Test
    @DisplayName("Deve mockar o comportamento do metodo usando correspondência de argumentos")
    void shouldProgramMethodUsingAnyArgumentMatcher() {
        //Given
        Mockito.when(this.mockedDummyBankService.getBalance(Mockito.any(UUID.class))).thenReturn(BigDecimal.ONE);

        //When
        var result = mockedDummyBankService.getBalance(UUID.randomUUID());

        //Then
        Assertions.assertEquals(BigDecimal.ONE, result);
    }

    @Test
    @DisplayName("Deve mockar o comportamento do metodo especificando um argumento")
    void shouldProgramMethodSpecifyingAnArgument() {
        //Given
        var uuid = UUID.fromString("3eef7663-dadb-4cb5-b14a-164d89b79add");
        Mockito.when(this.mockedDummyBankService.getBalance(uuid)).thenReturn(BigDecimal.TEN);

        //When
        var result = mockedDummyBankService.getBalance(uuid);

        //Then
        Assertions.assertEquals(BigDecimal.TEN, result);
    }

    @Test
    @DisplayName("Deve mockar o comportamento do metodo usando correspondência de argumentos")
    void shouldProgramMethodWithoutReturns() {
        //Given
        Mockito.doNothing().when(this.mockedDummyBankService).deposit(Mockito.any(UUID.class),
                Mockito.any(BigDecimal.class));

        //When
        mockedDummyBankService.deposit(UUID.randomUUID(), BigDecimal.ONE);

        //Then
    }

    @Test
    @DisplayName("Deve mockar o comportamento do metodo para levantar uma exceção")
    void shouldProgramMethodToRaiseAnException() {
        //Given
        Mockito.doThrow(RuntimeException.class).when(this.mockedDummyBankConcreteService)
                .transfer(Mockito.any(UUID.class), Mockito.any(UUID.class), Mockito.any(BigDecimal.class));

        //When
        var exception = Assertions.assertThrows(
                RuntimeException.class, () -> this.mockedDummyBankConcreteService.transfer(UUID.randomUUID(),
                        UUID.randomUUID(), BigDecimal.TEN));

        //Then
        Assertions.assertNotNull(exception);
    }

    @Test
    @DisplayName("Deve verificar se o metodo mockado foi chamado")
    void shouldVerifyIfMockedMethodWasCalled() {
        //Given
        var uuid = UUID.randomUUID();

        Mockito.when(this.mockedDummyBankService.getBalance(Mockito.any(UUID.class))).thenReturn(BigDecimal.ONE);

        //When
        var result = mockedDummyBankService.getBalance(uuid);

        //Then
        Mockito.verify(this.mockedDummyBankService, Mockito.times(1)).getBalance(uuid);

        Assertions.assertEquals(BigDecimal.ONE, result);
    }

    @Test
    @DisplayName("Deve capturar o argumento passado para metodo mockado chamado")
    void shouldCaptureArgumentPassedToMockedMethodCalled() {
        //Given
        var uuid = UUID.randomUUID();

        Mockito.when(this.mockedDummyBankService.getBalance(Mockito.any(UUID.class))).thenReturn(BigDecimal.ONE);

        //When
        var result = mockedDummyBankService.getBalance(uuid);

        //Then
        Mockito.verify(this.mockedDummyBankService, Mockito.times(1)).getBalance(
                this.uuidArgumentCaptor.capture());

        Assertions.assertEquals(uuid, this.uuidArgumentCaptor.getValue());
        Assertions.assertEquals(BigDecimal.ONE, result);
    }

    @Test
    @DisplayName("Deve mockar o comportamento do método estático")
    void shouldMockStaticMethod() {
        try (MockedStatic<DummyBankStaticService> mockedDummyBankStaticService
                     = Mockito.mockStatic(DummyBankStaticService.class)) {
            //Given
            var transactionKey = "aRvYuBdE23D7HnAqP";
            mockedDummyBankStaticService.when(DummyBankStaticService::getTransactionKey).thenReturn(transactionKey);

            //When
            var result = DummyBankStaticService.getTransactionKey();

            //Then
            Assertions.assertEquals(transactionKey, result);
        }

    }
}
