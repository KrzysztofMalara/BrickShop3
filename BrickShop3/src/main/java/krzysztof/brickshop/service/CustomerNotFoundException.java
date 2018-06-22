package krzysztof.brickshop.service;

public class CustomerNotFoundException extends RuntimeException {
    private final String username;

    public CustomerNotFoundException(String username) {
        super(String.format("Customer %s not found", username));
        this.username = username;
    }
}
