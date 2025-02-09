import javax.swing.*;
import java.awt.*;

public class Rechner extends JFrame {
    private JFrame frame;
    private JComboBox<String> elementDropdown;
    private JTextField amountInput;
    private JComboBox<String> unitDropdown;
    private JLabel resultLabel;
    private Element selectedElement;
    private JLabel molarMassLabel;

    public Rechner() {
        // Frame setup
        frame = new JFrame("Mol-Rechner");
        frame.setLayout(new GridLayout(4, 2, 10, 10));
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Element-Dropdown erstellen und füllen
        JLabel elementLabel = new JLabel("Stoff:");
        String[] elements = createElementArray();
        elementDropdown = new JComboBox<>(elements);

        // Molmassenanzeige
        JLabel molarMassTextLabel = new JLabel("Molare Masse:");
        molarMassLabel = new JLabel("");

        // Input-Feld
        JLabel amountLabel = new JLabel("Menge/Masse/Volumen:");
        JPanel inputPanel = new JPanel(new BorderLayout());
        amountInput = new JTextField();
        String[] units = {"mol", "g"};
        unitDropdown = new JComboBox<>(units);
        inputPanel.add(amountInput, BorderLayout.CENTER);
        inputPanel.add(unitDropdown, BorderLayout.EAST);

        // Resultat
        JLabel resultTextLabel = new JLabel("Masse:");
        resultLabel = new JLabel("");
        resultLabel.setOpaque(true);
        resultLabel.setBackground(new Color(144, 238, 144));

        // Komponenten zum frame hinzufügen
        frame.add(elementLabel);
        frame.add(elementDropdown);
        frame.add(molarMassTextLabel);
        frame.add(molarMassLabel);
        frame.add(amountLabel);
        frame.add(inputPanel);
        frame.add(resultTextLabel);
        frame.add(resultLabel);

        // Fügt ActionListener zu den Dropdowns und dem Textfeld hinzu, um die Berechnungen zu aktualisieren, wenn der Benutzer Eingaben macht
        elementDropdown.addActionListener(e -> updateMolarMass());
        amountInput.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculate(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculate(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculate(); }
        });
        unitDropdown.addActionListener(e -> calculate());

        // Final frame setup
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private String[] createElementArray() { // Erstellt ein Array von Strings, das die chemischen Elemente für das Dropdown-Menü enthält
        Periodensystem ps = new Periodensystem();
        String[] elements = new String[ps.elements.length];
        for (int i = 0; i < ps.elements.length; i++) {
            elements[i] = ps.elements[i].getSymbol() + " - " + ps.elements[i].getName();
        }
        return elements;
    }

    private void updateMolarMass() { // Aktualisiert die angezeigte molare Masse des ausgewählten Elements im Dropdown-Menü
        Periodensystem ps = new Periodensystem();
        String selectedItem = (String) elementDropdown.getSelectedItem();
        String symbol = selectedItem.split(" - ")[0]; // Holt den aktuell ausgewählten Eintrag aus dem Dropdown-Menü und extrahiert das chemische Symbol

        for (Element element : ps.elements) { // Durchläuft die Elemente im Periodensystem und sucht nach dem Element, dessen Symbol mit dem ausgewählten übereinstimmt
            if (element.getSymbol().equals(symbol)) {
                selectedElement = element;
                molarMassLabel.setText(String.format("%.2f g/mol", element.getMolarMass()));
                break;
            }
        }
        calculate();
    }

    private void calculate() { // Berechnet die Masse oder die Menge eines Stoffes basierend auf der Eingabe des Benutzers und der ausgewählten Einheit
        if (selectedElement == null || amountInput.getText().isEmpty()) { // Überprüft, ob ein Element ausgewählt wurde und ob das Eingabefeld nicht leer ist. Wenn nicht, wird das Ergebnis-Label geleert und die Methode beendet.
            resultLabel.setText("");
            return;
        }

        try {
            double inputValue = Double.parseDouble(amountInput.getText()); // Konvertiert eingegebenen Wert in eine double-Zahl → wenn keine Zahl, dann Fehlermeldung
            String unit = (String) unitDropdown.getSelectedItem(); // Holt die ausgewählte Einheit (mol oder g)
            double result;

            if (unit.equals("mol")) { // Masse wird berechnet
                result = inputValue * selectedElement.getMolarMass();
                resultLabel.setText(String.format("%.2f g", result));
            } else { // Anzahl der Mole berechnet
                result = inputValue / selectedElement.getMolarMass();
                resultLabel.setText(String.format("%.2f mol", result));
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Ungültige Eingabe");
        }
    }
}