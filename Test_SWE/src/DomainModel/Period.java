package DomainModel;

import java.util.Calendar;
public class Period {
    private Calendar Start;
    private Calendar End;

    public Period(Calendar S, Calendar E){
        Start = S;
        End = E;
    }

    public Calendar getStart() {
        return Start;
    }

    public Calendar getEnd() {
        return End;
    }
}
