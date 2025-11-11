import app.cli.MenuCLI;
import app.core.AuthService;
import app.core.DataStore;
import app.observer.ConsoleObserver;

public class Main {
    public static void main(String[] args) {
        DataStore dataStore = new DataStore();
        dataStore.registerObserver(new ConsoleObserver());
        AuthService authService = new AuthService(dataStore);
        MenuCLI cli = new MenuCLI(authService, dataStore);
        cli.start();
    }
}
