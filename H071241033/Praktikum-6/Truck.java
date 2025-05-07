import java.util.Calendar;
import java.util.Date;

public class Truck extends Kendaraan implements I_Bergerak, I_Serviceable {
    private int jumlahSumbu;
    private double muatanMaks;
    private String jenisTruk;
    private String bahanBakar;
    private Date waktuServisBerikutnya;

    public Truck(String model, String merek) {
        super(merek, model);
    }

    public int getJumlahSumbu() {
        return this.jumlahSumbu;
    }

    public void setJumlahSumbu(int jumlahSumbu) {
        this.jumlahSumbu = jumlahSumbu;
    }

    public double getMuatanMaks() {
        return this.muatanMaks;
    }

    public void setMuatanMaks(double muatanMaks) {
        this.muatanMaks = muatanMaks;
    }

    public String getJenisTruk() {
        return this.jenisTruk;
    }

    public void setJenisTruk(String jenisTruk) {
        this.jenisTruk = jenisTruk;
    }

    public String getBahanBakar() {
        return this.bahanBakar;
    }

    public void setBahanBakar(String bahanBakar) {
        this.bahanBakar = bahanBakar;
    }

    @Override
    public double hitungPajak() {
        return 0.02 * this.muatanMaks * 50000;
    }

    @Override
    public String getTipeKendaraan() {
        return "Truck Kontainer";
    }

    @Override
    public boolean mulai() {
        System.out.println("Truck sedang bergerak");
        return true;
    }

    @Override
    public boolean berhenti() {
        System.out.println("Truck telah berhenti");
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
        cal.add(Calendar.MONTH, 6);
        this.waktuServisBerikutnya = cal.getTime();
        System.out.println("Truck telah dilakukan servis. Waktu servis berikutnya: " + this.waktuServisBerikutnya);
    }

    @Override
    public Date getWaktuServisBerikutnya() {
        return this.waktuServisBerikutnya;
    }

    @Override
    public double hitungBiayaServis() {
        return 750_000;
    }
}
