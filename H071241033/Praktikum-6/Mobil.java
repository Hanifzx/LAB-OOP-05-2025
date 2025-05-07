import java.util.Calendar;
import java.util.Date;

public class Mobil extends Kendaraan implements I_Bergerak, I_Serviceable {
    private int jumlahPintu;
    private double kapasitasMesin;
    private int jumlahKursi;
    private String bahanBakar;
    private Date waktuServisBerikutnya;

    public Mobil (String model, String merek) {
        super(merek, model);
    }

    public int getJumlahPintu() {
        return this.jumlahPintu;
    }

    public void setJumlahPintu(int jumlahPintu) {
        this.jumlahPintu = jumlahPintu;
    }

    public double getKapasitasMesin() {
        return this.kapasitasMesin;
    }

    public void setKapasitasMesin(double kapasitasMesin) {
        this.kapasitasMesin = kapasitasMesin;
    }

    public int getJumlahKursi() {
        return this.jumlahKursi;
    }

    public void setJumlahKursi(int jumlahKursi) {
        this.jumlahKursi = jumlahKursi;
    }

    public String getBahanBakar() {
        return this.bahanBakar;
    }

    public void setBahanBakar(String bahanBakar) {
        this.bahanBakar = bahanBakar;
    }

    @Override
    public double hitungPajak() {
        return 0.02 * this.kapasitasMesin * 50000;
    }

    @Override
    public String getTipeKendaraan() {
        return "Mobil Listrik";
    }

    @Override
    public boolean mulai() {
        System.out.println("Mobil sedang bergerak");
        return true;
    }

    @Override
    public boolean berhenti() {
        System.out.println("Mobil telah berhenti");
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
        Date now = new Date(); // ambil tanggal hari ini
        if (getWaktuServisBerikutnya() != null) {
            return now.after(getWaktuServisBerikutnya()); // true kalau sudah lewat waktunya
        }
        return false; // kalau belum pernah servis atau belum waktunya
    }

    @Override
    public void lakukanServis() {
        Date now = new Date(); // ambil tanggal hari ini
        Calendar cal = Calendar.getInstance(); // bikin kalender
        cal.setTime(now); // set tanggal ke hari ini
        cal.add(Calendar.MONTH, 6);  // tambahkan waktu bulan
        this.waktuServisBerikutnya = cal.getTime(); // simpan tanggal servis berikutnya
        System.out.println("Mobil telah dilakukan servis. Waktu servis berikutnya: " + this.waktuServisBerikutnya);
    }

    @Override

    public Date getWaktuServisBerikutnya() {
        return this.waktuServisBerikutnya;
    }

    @Override
    public double hitungBiayaServis() {
        return 300_000;
    }
}