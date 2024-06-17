package DomainModel;

public class Room {
    private int Space, id, Cost;
    private String H;

    public Room(int P, int i, int C, String h){
        this.Space = P;
        this.id = i;
        this.Cost = C;
        this.H = h;
    }

    public int getSpace() {
        return Space;
    }

    public int getId() {
        return id;
    }

    public int getCost() {
        return Cost;
    }

    public String getH() {
        return H;
    }


}
