import javax.swing.*;

public class StepCounter implements Observer {
    private static StepCounter instance;
    private int steps;
    private JLabel stepsLabel;

    // Costruttore privato per il Singleton
    private StepCounter() {
        this.steps = 0;
    }

    // Metodo statico per ottenere l'istanza di StepCounter
    public static StepCounter getInstance() {
        if (instance == null) {
            instance = new StepCounter();
        }
        return instance;
    }

    //Imposta il Label su cui aggiornare i passi
    public void setStepsLabel(JLabel stepsLabel) {
        this.stepsLabel = stepsLabel;
        updateSteps();
    }

    @Override
    public void update() {
        steps++;
        updateSteps();
    }

    private void updateSteps() {
        if (stepsLabel != null) {
            stepsLabel.setText("Passi: " + steps);
        }
    }

    public int getSteps() {
        return steps;
    }

    public void resetSteps() {
        steps = 0;
        updateSteps();
    }
}
