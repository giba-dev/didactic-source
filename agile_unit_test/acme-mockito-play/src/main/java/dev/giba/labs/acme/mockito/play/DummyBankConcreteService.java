package dev.giba.labs.acme.mockito.play;
import java.math.BigDecimal;
import java.util.UUID;

public class DummyBankConcreteService {

    public UUID newTransactionId() {
        return UUID.randomUUID();
    }

    public void transfer(UUID from, UUID to, BigDecimal value) {
        if (BigDecimal.ZERO.compareTo(value) >= 0) {
            throw new RuntimeException(String.format("Unable to transfer value [%s] from [%s] to [%s]",
                    value, from, to));
        }
    }
}
