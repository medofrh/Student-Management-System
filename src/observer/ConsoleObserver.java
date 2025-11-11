package observer;

public class ConsoleObserver implements Observer {
    @Override
    public void onEvent(String event, String details) {
        System.out.println("[Observer] " + event + " :: " + details);
    }
}
