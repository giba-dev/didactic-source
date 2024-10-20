package dev.giba.labs.acme.mockito.play;

import java.math.BigDecimal;
import java.util.UUID;

public interface DummyBankService {
    UUID getUserId();
    BigDecimal getBalance(UUID userId);
    void deposit(UUID userId, BigDecimal value);
}
