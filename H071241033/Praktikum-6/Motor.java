import java.util.Calendar;
import java.util.Date;

public class Motor extends Kendaraan implements I_Bergerak, I_Serviceable {
    private String jenisMotor;
    private double kapasitasTangki;
    private String tipeSuspensi;
    private Date waktuServisBerikutnya;

    public Motor(String merek, String model) {
        super(merek, model);
    }

    public String getJenisMotor() {
        return this.jenisMotor;
    }

    public void setJenisMotor(String jenisMotor) {
        this.jenisMotor = jenisMotor;
    }

    public double getKapasitasTangki() {
        return this.kapasitasTangki;
    }

    public void setKapasitasTangki(double kapasitasTangki) {
        this.kapasitasTangki = kapasitasTangki;
    }

    public String getTipeSuspensi() {
        return this.tipeSuspensi;
    }

    public void setTipeSuspensi(String tipeSuspensi) {
        this.tipeSuspensi = tipeSuspensi;
    }

    @Override
    public double hitungPajak() {
        return 0.02 * this.kapasitasTangki * 10000;
    }

    @Override
    public String getTipeKendaraan() {
        return "Motor Sport"; 
    }

    @Override
    public boolean mulai() {
        System.out.println("Motor sedang bergerak");
        return true;
    }

    @Override
    public boolean berhenti() {
        System.out.println("Motor telah berhenti");
        return false;
    }

    @Override
    public double getKecepatan() {
        return this.kecepatan;
    }

    @Override
    public void setKecepatan(double kecepatan) {
        this.kecepatan = kecepatan;
    }

    @Override
    public boolean periksaKondisi() {
        Date now = new Date();
        if (getWaktuServisBerikutnya() != null) {
            return now.after(getWaktuServisBerikutnya());
        }
        return false;
    }

    @Override
    public void lakukanServis() {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MONTH, 2);
        this.waktuServisBerikutnya = cal.getTime();
        System.out.println("Motor telah diservis. Waktu servis berikutnya: " + this.waktuServisBerikutnya);
    }

    @Override
    public Date getWaktuServisBerikutnya() {
        return this.waktuServisBerikutnya;
    }

    @Override
    public double hitungBiayaServis() {
        return 100_000;
    }
}