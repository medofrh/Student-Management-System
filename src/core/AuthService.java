package core;

import domain.Role;

import java.util.Optional;

public class AuthService {
    private final DataStore dataStore;

    public AuthService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Optional<String> authenticate(Role role, String username, String password) {
        return dataStore.findAccount(username)
                .filter(account -> account.role() == role && account.password().equals(password))
                .map(DataStore.Account::referenceId);
    }
}
