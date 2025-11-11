import app.cli.MenuCLI;
import app.core.AuthService;
import app.core.DataStore;

public class Main {
    public static void main(String[] args) {
        DataStore dataStore = new DataStore();
        AuthService authService = new AuthService(dataStore);
        MenuCLI cli = new MenuCLI(authService, dataStore);
        cli.start();
    }
}
