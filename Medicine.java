public class Medicine {
    private int id;
    private String name;
    private String type;
    private double price;

    // Constructor without ID (for insertion)
    public Medicine(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    // Constructor with ID (for fetching from DB)
    public Medicine(int id, String name, String type, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getPrice() { return price; }
}
