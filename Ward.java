public class Ward {
    private int id;
    private String wardName;
    private String roomNumber;
    private int capacity;

    // Constructor without ID (for insertion)
    public Ward(String wardName, String roomNumber, int capacity) {
        this.wardName = wardName;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    // Constructor with ID (for fetching from DB)
    public Ward(int id, String wardName, String roomNumber, int capacity) {
        this.id = id;
        this.wardName = wardName;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    // Getters
    public int getId() { return id; }
    public String getWardName() { return wardName; }
    public String getRoomNumber() { return roomNumber; }
    public int getCapacity() { return capacity; }
}
