import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class Layout extends JFrame implements Runnable  {
    public JFrame mainFrame = new JFrame("Periodensystem-Explorer | By Ilias Mayer"); // Das Hauptfenster der Anwendung
    public JButton aboutHelp = new JButton("Über & Hilfe");
    public JButton molCalculator = new JButton("Mol-Rechner");
    public JTextField searchField = new JTextField();
    public JButton clearButton = new JButton("X");
    public JPanel periodicTablePanel = new JPanel(new GridLayout(9, 18)); // Periodensystem in einem Gridlayout darstellen
    public JButton[] elementButtons = new JButton[118]; // Ein Array, das alle Elemente als Buttons enthält
    public Element[] elementData; // Ein Array, das alle Element-Objekte speichert

    public void setupLayout() {
        // Hauptfenster (mainFrame) wird erstellt
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Erstellt die obere Leiste mit Buttons und Suchfeld (header)
        createHeader();

        // Periodensystem-Panel mit Daten
        periodicTablePanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        mainFrame.add(new JScrollPane(periodicTablePanel), BorderLayout.CENTER);
        Periodensystem periodicTable = new Periodensystem();
        elementData = periodicTable.elements;
        createPeriodicTable(); // Erstellt das Periodensystem und platziert die Buttons an den richtigen Positionen.

        // Erzeugt die Legende zur Erklärung der Kategorien.
        createLegend();

        // ActionListener für Über&Hilfe, Mol-Rechner, Suchfeld (Enter) und X-Button
        aboutHelp.addActionListener(e -> showAboutDialog());
        molCalculator.addActionListener(e -> openMolCalculator());
        searchField.addActionListener(e -> searchElement());
        clearButton.addActionListener(e -> clearSearch());

        mainFrame.setVisible(true); // Das Fenster wird sichtbar gemacht
    }

    public void createHeader() {
        // Top Panel für Über&Hilfe, Mol-Rechner, Suchfeld und Clear-Button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchField.setPreferredSize(new Dimension(200, 30));
        topPanel.add(aboutHelp);
        topPanel.add(molCalculator);
        topPanel.add(searchField);
        topPanel.add(clearButton);
        mainFrame.add(topPanel, BorderLayout.NORTH);
    }

    public void createPeriodicTable() {
        int rows = 9;
        int columns = 18;

        // Positionen für reguläre Elemente ohne Lanthanoide und Actinoide
        int[][] elementPositions = {
                {0, 0}, {0, 17}, // 1. Reihe
                {1, 0}, {1, 1}, {1, 12}, {1, 13}, {1, 14}, {1, 15}, {1, 16}, {1, 17}, // 2. Reihe
                {2, 0}, {2, 1}, {2, 12}, {2, 13}, {2, 14}, {2, 15}, {2, 16}, {2, 17}, // 3. Reihe
                {3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7}, {3, 8}, {3, 9}, {3, 10}, {3, 11}, {3, 12}, {3, 13}, {3, 14}, {3, 15}, {3, 16}, {3, 17}, // 4. Reihe
                {4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8}, {4, 9}, {4, 10}, {4, 11}, {4, 12}, {4, 13}, {4, 14}, {4, 15}, {4, 16}, {4, 17}, // 5. Reihe
                {5, 0}, {5, 1}, {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10}, {5, 11}, {5, 12}, {5, 13}, {5, 14}, {5, 15}, {5, 16}, {5, 17}, // 6. Reihe ohne La bis Lu
                {6, 0}, {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7}, {6, 8}, {6, 9}, {6, 10}, {6, 11}, {6, 12}, {6, 13}, {6, 14}, {6, 15}, {6, 16}, {6, 17}, // 7. Reihe ohne Ac bis Lr
        };

        int lanthanoideStart = 56;
        int actinoideStart = 88;

        int elementIndex = 0; // Index für das aktuelle Element

        // Hauptreihen des Periodensystems ohne Lanthanoide und Actinoide
        for (int row = 0; row < rows - 2; row++) { // -2, da die letzten beiden Reihen für Lanthanoide und Actinoide reserviert sind
            for (int col = 0; col < columns; col++) {
                boolean elementAdded = false;
                for (int[] position : elementPositions) { // Elemente in regulären Positionen einfügen
                    if (position[0] == row && position[1] == col) {
                        if (elementIndex == 56) {
                            // Platzhalter für Lanthanoide
                            JButton lanthanoidePlaceholder = new JButton("La-Lu");
                            lanthanoidePlaceholder.setBackground(getCategoryColor("Lanthanoid"));
                            lanthanoidePlaceholder.setEnabled(true);
                            lanthanoidePlaceholder.addActionListener(e -> highlightLanthanoides());
                            periodicTablePanel.add(lanthanoidePlaceholder);
                            elementIndex++;
                            elementAdded = true;
                            break;
                        } else if (elementIndex == 88) {
                            // Platzhalter für Actinoide
                            JButton actinoidePlaceholder = new JButton("Ac-Lr");
                            actinoidePlaceholder.setBackground(getCategoryColor("Actinoid"));
                            actinoidePlaceholder.setEnabled(true);
                            actinoidePlaceholder.addActionListener(e -> highlightActinoides());
                            periodicTablePanel.add(actinoidePlaceholder);
                            elementIndex++;
                            elementAdded = true;
                            break;
                        }
                        // Überspringe Lanthanoide und Actinoide
                        else if (elementIndex >= 57 && elementIndex <= 71) {
                            elementIndex = 71; // Springe über La bis Lu
                        }
                        else if (elementIndex >= 89 && elementIndex <= 103) {
                            elementIndex = 103; // Springe über Ac bis Lr
                        }
                        // Füge reguläres Element hinzu
                        if (elementIndex < elementData.length) {
                            final int currentIndex = elementIndex;
                            JButton elementButton = new JButton(elementData[elementIndex].getSymbol());
                            elementButton.setToolTipText(
                                    "<html>Name: " + elementData[elementIndex].getName() +
                                            "<br>Kategorie: " + elementData[elementIndex].getCategory() + "</html>"
                            );
                            elementButton.setBackground(getCategoryColor(elementData[elementIndex].getCategory()));
                            elementButton.addActionListener(e -> showElementDetails(elementData[currentIndex])); // Beim Klick auf ein Element wird showElementDetails(elementData[currentIndex]) aufgerufen
                            periodicTablePanel.add(elementButton);
                            elementButtons[elementIndex] = elementButton;
                            elementAdded = true;
                            elementIndex++;
                        }
                        break;
                    }
                }

                // Platzhalter hinzufügen, falls kein Element vorhanden ist
                if (!elementAdded) {
                    periodicTablePanel.add(new JLabel());
                }
            }
        }
        // Lanthanoide (8. Reihe, ab Spalte 3)
        for (int col = 0; col <= 17; col++) {
            if (col < 2) { // Platzhalter für Spalten 0, 1
                periodicTablePanel.add(new JLabel());
            } else if (lanthanoideStart < 71) {
                final int currentIndex = lanthanoideStart;
                JButton elementButton = new JButton(elementData[lanthanoideStart].getSymbol());
                elementButton.setToolTipText(
                        "<html>Name: " + elementData[lanthanoideStart].getName() +
                                "<br>Kategorie: " + elementData[lanthanoideStart].getCategory() + "</html>"
                );
                elementButton.setBackground(getCategoryColor(elementData[lanthanoideStart].getCategory()));
                elementButton.addActionListener(e -> showElementDetails(elementData[currentIndex])); // Beim Klick auf ein Element wird showElementDetails(elementData[currentIndex]) aufgerufen
                periodicTablePanel.add(elementButton);
                elementButtons[lanthanoideStart] = elementButton;
                lanthanoideStart++;
            } else {
                periodicTablePanel.add(new JLabel()); // Platzhalter für verbleibende Spalten
            }
        }

        // Actinoide (9. Reihe, ab Spalte 3)
        for (int col = 0; col <= 17; col++) {
            if (col < 2) { // Platzhalter für Spalten 0, 1
                periodicTablePanel.add(new JLabel());
            } else if (actinoideStart < 103) {
                final int currentIndex = actinoideStart;
                JButton elementButton = new JButton(elementData[actinoideStart].getSymbol());
                elementButton.setToolTipText(
                        "<html>Name: " + elementData[actinoideStart].getName() +
                                "<br>Kategorie: " + elementData[actinoideStart].getCategory() + "</html>"
                );
                elementButton.setBackground(getCategoryColor(elementData[actinoideStart].getCategory()));
                elementButton.addActionListener(e -> showElementDetails(elementData[currentIndex])); // Beim Klick auf ein Element wird showElementDetails(elementData[currentIndex]) aufgerufen
                periodicTablePanel.add(elementButton);
                elementButtons[actinoideStart] = elementButton;
                actinoideStart++;
            } else {
                periodicTablePanel.add(new JLabel()); // Platzhalter für verbleibende Spalten
            }
        }
    }

    public void createLegend() {
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        legendPanel.setBorder(BorderFactory.createTitledBorder("Elementkategorien"));

        String[] categories = {
                "Alkalimetalle",
                "Erdalkalimetalle",
                "Übergangsmetalle",
                "Metalle",
                "Halbmetalle",
                "Nichtmetalle",
                "Halogene",
                "Edelgase",
                "Lanthanoide",
                "Actinoide"
        };
        for (String category : categories) {
            JPanel colorPanel = new JPanel();
            colorPanel.setBackground(getCategoryColor(category.substring(0, category.length() - 1)));
            colorPanel.setPreferredSize(new Dimension(20, 20));

            JLabel label = new JLabel(category);
            JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            categoryPanel.add(colorPanel);
            categoryPanel.add(label);
            legendPanel.add(categoryPanel);
        }
        mainFrame.add(legendPanel, BorderLayout.SOUTH);
    }

    public void showAboutDialog() {
        JDialog aboutDialog = new JDialog(mainFrame, "Über & Hilfe", true);
        aboutDialog.setSize(700, 500);
        aboutDialog.setLocationRelativeTo(null);
        aboutDialog.setLayout(new BorderLayout());

        // Textbereich für Informationen (HTML-Format)
        JTextPane infoPane = new JTextPane();
        infoPane.setContentType("text/html");
        infoPane.setText("<html><body style='font-family: Arial; font-size: 14px;'>" +
                "<b>Projektbeschreibung:</b><br>" +
                "Dieses Projekt ist ein interaktiver Periodensystem-Explorer, der es Benutzern ermöglicht, " +
                "Informationen über chemische Elemente zu durchsuchen und anzuzeigen.<br><br>" +
                "<b>Features:</b><br>" +
                "- Interaktive Benutzeroberfläche: Klicken Sie auf die Elemente im Periodensystem, um Details anzuzeigen.<br>" +
                "- Suchfunktion: Suchen Sie nach Elementen anhand von Symbol, Name oder Kategorie.<br>" +
                "- Mol-Rechner: Berechnen Sie die Masse oder die Menge von Stoffen basierend auf der molaren Masse.<br>" +
                "- Legende: Eine Legende zeigt die verschiedenen Kategorien von Elementen mit entsprechenden Farben an.<br><br>" +
                "<b>Benutzung:</b><br>" +
                "- Um ein Element zu finden, geben Sie den Namen, das Symbol oder die Kategorie in das Suchfeld ein " +
                "und drücken Sie die Eingabetaste.<br>" +
                "- Klicken Sie auf ein Element, um weitere Informationen anzuzeigen.<br>" +
                "- Klicken Sie auf den X-Button, um alle Markierungen verschwinden zu lassen.<br>" +
                "- Verwenden Sie den Mol-Rechner, um Berechnungen für die Menge oder Masse von Elementen durchzuführen." +
                "</body></html>");
        infoPane.setEditable(false);
        infoPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Link für E-Mail und GitHub
        JLabel linkLabel = new JLabel("<html><body style='font-family: Arial; font-size: 14px;'>" +
                "Name: Ilias Mayer<br>" +
                "E-Mail: <a href='mailto:ilias.mayer@outlook.com'>ilias.mayer@outlook.com</a><br>" +
                "GitHub: <a href='https://github.com/Ilias-Mayer/Periodensystem'>https://github.com/Ilias-Mayer/Periodensystem</a>" +
                "</body></html>");
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // E-Mail-Link
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().mail(new URI("mailto:ilias.mayer@outlook.com"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // GitHub-Link
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/Ilias-Mayer/Periodensystem"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Scrollbarer Bereich für den Text
        JScrollPane scrollPane = new JScrollPane(infoPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        aboutDialog.add(scrollPane, BorderLayout.CENTER);
        infoPane.setCaretPosition(0);

        // Schließen-Button
        JButton closeButton = new JButton("Schließen");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.addActionListener(e -> aboutDialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        // Komponenten hinzufügen
        aboutDialog.add(linkLabel, BorderLayout.NORTH);
        aboutDialog.add(buttonPanel, BorderLayout.SOUTH);
        aboutDialog.setVisible(true);
    }

    public void openMolCalculator() {
        new Rechner();
    }

    public void searchElement() {
        String query = searchField.getText().trim().toLowerCase();

        for (int i = 0; i < elementData.length; i++) {
            if (elementButtons[i] != null) {
                String symbol = elementData[i].symbol.toLowerCase();
                String name = elementData[i].name.toLowerCase();
                String category = elementData[i].category.toLowerCase();

                if (symbol.equals(query) || name.equals(query) || category.equals(query)) {
                    elementButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                    elementButtons[i].setSelected(true);
                } else {
                    elementButtons[i].setBorder(null);
                    elementButtons[i].setSelected(false);
                }
            }
        }
    }

    public void highlightLanthanoides(){
        clearSearch();
        for (int i = 56; i < 71; i++) {
            if (elementButtons[i] != null) {
                elementButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                elementButtons[i].setSelected(true);
            }
        }
    }

    public void highlightActinoides(){
        clearSearch();
        for (int i = 88; i < 103; i++) {
            if (elementButtons[i] != null) {
                elementButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                elementButtons[i].setSelected(true);
            }
        }
    }

    public void clearSearch() {
        searchField.setText("");
        for (JButton button : elementButtons) {
            if (button != null) {
                button.setBorder(null);
            }
        }
    }

    public void showElementDetails(Element element) {
        JDialog detailsDialog = new JDialog(mainFrame, "Details: " + element.getName(), true);
        detailsDialog.setSize(650, 400);
        detailsDialog.setLocationRelativeTo(null);

        ElementDetails elementDetailsPanel = new ElementDetails(element, this);
        detailsDialog.add(elementDetailsPanel);

        detailsDialog.setVisible(true);
    }

    public Color getCategoryColor(String category) {
        switch (category) {
            case "Nichtmetall": return new Color(161, 218, 247);
            case "Edelgas": return new Color(239,153,126);
            case "Alkalimetall": return new Color(86,176,62);
            case "Erdalkalimetall": return new Color(235,244,47);
            case "Übergangsmetall": return new Color(234,163,59);
            case "Metall": return new Color(255,9,15);
            case "Halbmetall": return new Color(243,114,227);
            case "Halogen": return new Color(167,157,207);
            case "Lanthanoid": return new Color(192,192,192);
            case "Actinoid": return new Color(110,110,110);
            default: return new Color(0, 0, 0);
        }
    }

    public void run() {
        setupLayout();
    }
}