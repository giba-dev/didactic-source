package dev.giba.labs.acme.mockito.play;

import java.util.UUID;

public class ServiceA {
    private final DummyBankService dummyBankService;

    public ServiceA(final DummyBankService dummyBankService) {
        this.dummyBankService = dummyBankService;
    }

    public UUID getUserId() {
        return this.dummyBankService.getUserId();
    }

}
