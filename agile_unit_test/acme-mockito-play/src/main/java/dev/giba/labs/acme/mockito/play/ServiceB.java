package dev.giba.labs.acme.mockito.play;

import java.util.UUID;

public class ServiceB {
    private DummyBankConcreteService dummyBankConcreteService = new DummyBankConcreteService();

    public UUID newTransactionId() {
        return this.dummyBankConcreteService.newTransactionId();
    }
}
