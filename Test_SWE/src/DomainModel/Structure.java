package DomainModel;

public class Structure {
    private String Name, Place, Type;
    private Manager manager;
    private int Rooms, Reviews;
    
    public Structure(String N, Manager m, String P, int r, String type){
        manager = m;
        Name = N;
        Place = P;
        Reviews = 0;
        Rooms = r;
        Type = type;
    }

    public String getName() {
        return Name;
    }
    public String getPlace() {
        return Place;
    }
    public int getRooms() {
        return Rooms;
    }
    public int getReviews() {
        return Reviews;
    }
    public Manager getManager() {
        return manager;
    }
    public String getType(){
        return Type;
    }
}
