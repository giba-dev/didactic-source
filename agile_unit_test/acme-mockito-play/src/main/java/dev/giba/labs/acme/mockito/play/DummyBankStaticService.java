package dev.giba.labs.acme.mockito.play;


public class DummyBankStaticService {
    private static final String TRANSACTION_KEY = "vuIyAV8I3ftlyCwECxXYR3Vizx3ed4xZ";
    public static String getTransactionKey() {
        return TRANSACTION_KEY;
    }

    private DummyBankStaticService() {

    }
}
