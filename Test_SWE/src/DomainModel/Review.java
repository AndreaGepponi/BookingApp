package DomainModel;

public class Review {
    private String Text, H_name, U_name;
    private  int Score;

    public Review(int s, String t, String Hn, String Un){
        Score = s;
        Text = t;
        H_name = Hn;
        U_name = Un;
    }

    public String getText() {
        return Text;
    }

    public int getScore() {
        return Score;
    }

    public String getH_name() {
        return H_name;
    }

    public String getU_name() {
        return U_name;
    }
}
