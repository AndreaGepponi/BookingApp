package DomainModel;

import java.util.Calendar;

public class Booking {
    private String H;
    private Customer U;
    private Period P;
    private int nRoom;

    public Booking(Customer u_name, Calendar s, Calendar e, String h, int id){
        U = u_name;
        P = new Period(s, e);
        H = h;
        nRoom = id;
    }

    public Booking(){}

    public Customer getU() {
        return U;
    }
    public Period getP() {
        return P;
    }
    public String getH() {
        return H;
    }
    public int getRoom() {
        return nRoom;
    }

    public void setU(Customer u) {
        U = u;
    }
    public void setP(Calendar S, Calendar E) {
        P = new Period(S, E);
    }
    public void setH(String h) {
        H = h;
    }
    public void setnRoom(int nRoom) {
        this.nRoom = nRoom;
    }
}
