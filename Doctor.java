public class Doctor {
    private int id;
    private String name;
    private String specialization;
    private String contact;

    // Constructor without ID (for insertion)
    public Doctor(String name, String specialization, String contact) {
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }

    // Constructor with ID (for fetching from DB)
    public Doctor(int id, String name, String specialization, String contact) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getContact() { return contact; }
}
