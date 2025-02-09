import javax.swing.*;
import java.awt.*;

public class ElementDetails extends JPanel {
    private final Element element;
    private final Layout layout;

    public ElementDetails(Element element, Layout layout) {
        this.element = element;
        this.layout = layout;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Hauptpanel: Kategoriefarbenhintergund links und Details rechts
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Kategoriefarbenhintergund mit Symbol, Name und Ordnungszahl
        mainPanel.add(createCategoryPanel(), BorderLayout.WEST);

        // Panel für die restlichen Details
        mainPanel.add(createDetailsPanel(), BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Erstellt ein Quadrat mit der Farbe der Kategorie und zeigt das Symbol, den Namen und die Ordnungszahl.
     */
    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setPreferredSize(new Dimension(150, 150)); // Quadratgröße festlegen
        categoryPanel.setBackground(layout.getCategoryColor(element.getCategory()));
        categoryPanel.setLayout(new BorderLayout());

        // Symbol (zentriert, groß)
        JLabel symbolLabel = new JLabel(element.getSymbol(), SwingConstants.CENTER);
        symbolLabel.setFont(new Font("Arial", Font.BOLD, 36));
        symbolLabel.setForeground(Color.WHITE);

        // Unterer Bereich: Name und Ordnungszahl
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(layout.getCategoryColor(element.getCategory()));

        JLabel nameLabel = new JLabel(element.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setForeground(Color.WHITE);

        JLabel atomicNumberLabel = new JLabel(String.valueOf(element.getAtomicNumber()), SwingConstants.CENTER);
        atomicNumberLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        atomicNumberLabel.setForeground(Color.WHITE);

        infoPanel.add(nameLabel);
        infoPanel.add(atomicNumberLabel);

        categoryPanel.add(symbolLabel, BorderLayout.CENTER);
        categoryPanel.add(infoPanel, BorderLayout.SOUTH);

        return categoryPanel;
    }

    /**
     * Erstellt das Panel mit den restlichen Details des Elements.
     */
    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Details hinzufügen
        detailsPanel.add(createDetailLabel("Atommasse:"));
        detailsPanel.add(createDetailValueLabel(element.getMolarMass() + " g/mol"));

        detailsPanel.add(createDetailLabel("Dichte:"));
        detailsPanel.add(createDetailValueLabel(element.getDensity() + " g/cm³"));

        detailsPanel.add(createDetailLabel("Schmelzpunkt:"));
        detailsPanel.add(createDetailValueLabel(element.getMeltingPoint() + " °C"));

        detailsPanel.add(createDetailLabel("Siedepunkt:"));
        detailsPanel.add(createDetailValueLabel(element.getBoilingPoint() + " °C"));

        detailsPanel.add(createDetailLabel("Zustand bei Raumtemperatur:"));
        detailsPanel.add(createDetailValueLabel(element.getStateAtRoomTemp()));

        detailsPanel.add(createDetailLabel("Elektronegativität:"));
        detailsPanel.add(createDetailValueLabel(String.valueOf(element.getElectronegativity())));

        detailsPanel.add(createDetailLabel("Oxidationsstufen:"));
        detailsPanel.add(createDetailValueLabel(element.getOxidationStates()));

        detailsPanel.add(createDetailLabel("Löslichkeit:"));
        detailsPanel.add(createDetailValueLabel(element.getSolubility()));

        detailsPanel.add(createDetailLabel("Sicherheitsinformationen:"));
        detailsPanel.add(createDetailValueLabel(element.getSafetyInfo()));

        return detailsPanel;
    }

    /**
     * Erstellt ein JLabel für die Beschriftung eines Parameters.
     */
    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        return label;
    }

    /**
     * Erstellt ein JLabel für den Wert eines Parameters.
     */
    private JLabel createDetailValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }
}
