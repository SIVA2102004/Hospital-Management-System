public class Patient {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String disease;
    private String contact;

    // Constructor without ID (for insertion)
    public Patient(String name, int age, String gender, String disease, String contact) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.contact = contact;
    }

    // Constructor with ID (for fetching from DB)
    public Patient(int id, String name, int age, String gender, String disease, String contact) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.contact = contact;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getDisease() { return disease; }
    public String getContact() { return contact; }
}
