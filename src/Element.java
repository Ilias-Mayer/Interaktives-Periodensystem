public class Element { // bildet ein chemisches Element mit seinen wichtigsten Eigenschaften ab
    public final String symbol;
    public final String name;
    public final String category;
    private final double molarMass;
    private final int atomicNumber;
    private final double meltingPoint;
    private final double boilingPoint;
    private final double density;
    private final String stateAtRoomTemp;
    private final double electronegativity;
    private final String oxidationStates;
    private final String solubility;
    private final String safetyInfo;

    /*
    Der Konstruktor initialisiert die Attribute des Elements mit den übergebenen Werten
     */
    public Element(String symbol, String name, String category, double molarMass, int atomicNumber, double meltingPoint, double boilingPoint, double density, String stateAtRoomTemp, double electronegativity, String oxidationStates, String solubility, String safetyInfo) {
        this.symbol = symbol;
        this.name = name;
        this.category = category;
        this.molarMass = molarMass;
        this.atomicNumber = atomicNumber;
        this.meltingPoint = meltingPoint;
        this.boilingPoint = boilingPoint;
        this.density = density;
        this.stateAtRoomTemp = stateAtRoomTemp;
        this.electronegativity = electronegativity;
        this.oxidationStates = oxidationStates;
        this.solubility = solubility;
        this.safetyInfo = safetyInfo;
    }

    /*
    Getter-Methoden, um auf die Attribut-Werte in anderen Klassen zugreifen zu können
     */
    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getMolarMass() {
        return molarMass;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public double getMeltingPoint() {
        return meltingPoint;
    }

    public double getBoilingPoint() {
        return boilingPoint;
    }

    public double getDensity() {
        return density;
    }

    public String getStateAtRoomTemp() {
        return stateAtRoomTemp;
    }

    public double getElectronegativity() {
        return electronegativity;
    }

    public String getOxidationStates() {
        return oxidationStates;
    }

    public String getSolubility() {
        return solubility;
    }

    public String getSafetyInfo() {
        return safetyInfo;
    }
}