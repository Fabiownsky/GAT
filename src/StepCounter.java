import javax.swing.*;

public class StepCounter implements Observer {
    private int steps; // Numero dei passi
    private JLabel stepsLabel; // Etichetta da aggiornare

    // Costruttore che prende in ingresso l'etichetta stepsLabel
    public StepCounter(JLabel stepsLabel) {
        this.steps = 0;
        this.stepsLabel = stepsLabel;
        updateSteps(); // Aggiorna l'etichetta all'inizio
    }

    // Metodo update chiamato quando Game notifica un cambiamento
    @Override
    public void update() {
        steps++; // Incrementa il contatore dei passi
        updateSteps(); // Aggiorna l'etichetta stepsLabel
    }

    // Metodo per aggiornare l'etichetta dei passi
    private void updateSteps() {
        stepsLabel.setText("Passi: " + steps); // Aggiorna il testo dell'etichetta
    }

    public int getSteps() {
        return steps;
    }

    public void resetSteps() {
        steps = 0; // Resetta il contatore
        updateSteps(); // Aggiorna l'etichetta con il reset
    }
}
