package DomainModel;

public class Structure {
    private String Name, Place, Type, Manager;
    private int Rooms, Reviews;
    
    public Structure(String N, String m, String P, int r, String type){
        int c = 45;
        Manager = m;
        Name = N;
        Place = P;
        Reviews = 0;
        Rooms = r;
        Type = type;
        int id = 0;
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
    public String getManager() {
        return Manager;
    }
    public String getType(){
        return Type;
    }
}
