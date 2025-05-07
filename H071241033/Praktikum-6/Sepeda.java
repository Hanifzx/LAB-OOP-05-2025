import java.util.Calendar;
import java.util.Date;

public class Sepeda extends Kendaraan implements I_Bergerak, I_Serviceable {
    private String jenisSepeda;
    private int jumlahGear;
    private int ukuranRoda;
    private Date waktuServisBerikutnya;

    public Sepeda(String merek, String model) {
        super(merek, model);
    }

    public String getJenisSepeda() {
        return this.jenisSepeda;
    }

    public void setJenisSepeda(String jenisSepeda) {
        this.jenisSepeda = jenisSepeda;
    }

    public int getJumlahGear() {
        return this.jumlahGear;
    }

    public void setJumlahGear(int jumlahGear) {
        this.jumlahGear = jumlahGear;
    }

    public int getUkuranRoda() {
        return this.ukuranRoda;
    }

    public void setUkuranRoda(int ukuranRoda) {
        this.ukuranRoda = ukuranRoda;
    }

    @Override
    public double hitungPajak() {
        return 0;
    }

    @Override
    public String getTipeKendaraan() {
        return "Sepeda Fixie";
    }

    @Override
    public boolean mulai() {
        System.out.println("Sepeda sedang bergerak");
        return true;
    }

    @Override
    public boolean berhenti() {
        System.out.println("Sepeda telah berhenti");
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
        cal.add(Calendar.MONTH, 12);
        this.waktuServisBerikutnya = cal.getTime();
        System.out.println("Sepeda telah diservis. Waktu servis berikutnya: " + this.waktuServisBerikutnya);
    }

    @Override
    public Date getWaktuServisBerikutnya() {
        return this.waktuServisBerikutnya;
    }

    @Override
    public double hitungBiayaServis() {
        return 50_000;
    }
} 