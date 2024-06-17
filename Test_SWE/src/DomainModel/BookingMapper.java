package DomainModel;

import java.util.Calendar;

public class BookingMapper {

    public Booking map(String u_name, Calendar s, Calendar e, String h, int id){
        Booking booking = new Booking();

        booking.setH(h);
        booking.setnRoom(id);
        booking.setP(s, e);
        booking.setU(u_name);

        return booking;
    }
}
