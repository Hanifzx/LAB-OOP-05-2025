
import java.util.Date;

public interface I_Serviceable {
    public boolean periksaKondisi();
    public void lakukanServis();
    public Date getWaktuServisBerikutnya();
    public double hitungBiayaServis();
}