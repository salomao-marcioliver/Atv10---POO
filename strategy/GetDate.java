package strategy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate implements CurrentDate{
    public String getDate(){
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy");
        Date data = new Date();
        return f.format(data);
    }
}
