package plugin;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestStepFinished;

public class ConsoleStatusPlugin implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::onStart);
        publisher.registerHandlerFor(TestStepFinished.class, this::onStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onFinished);
    }

    private void onStart(TestCaseStarted event) {
        String name = event.getTestCase().getName();
        System.out.println("[INICIO] Cenário: " + name);
    }

    private void onStepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            Status status = event.getResult().getStatus();
            String keyword = step.getStep().getKeyword();
            String text = step.getStep().getText();

            String prefix;
            switch (status) {
                case PASSED -> prefix = "[OK]";
                case FAILED -> prefix = "[FALHA]";
                case SKIPPED -> prefix = "[PULADO]";
                case PENDING -> prefix = "[PENDENTE]";
                default -> prefix = "[" + status.name() + "]";
            }
            System.out.println(prefix + " " + keyword + text);

            if (status == Status.FAILED && event.getResult().getError() != null) {
                // Mostra a stacktrace imediatamente no console
                event.getResult().getError().printStackTrace(System.out);
            }
        }
    }

    private void onFinished(TestCaseFinished event) {
        String name = event.getTestCase().getName();
        Status status = event.getResult().getStatus();
        System.out.println("[FIM] Cenário: " + name + " -> " + status.name());
    }
}

