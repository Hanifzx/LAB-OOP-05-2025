public abstract class Kendaraan {
    protected String id;
    protected String merek;
    protected String model;
    protected int tahunProduksi;
    protected String warna;
    protected double kecepatan;

    public Kendaraan(String merek, String model) {
        this.merek = merek;
        this.model = model;
    }

    public String getID() {return this.id;}

    public String getMerek() {return this.merek;}

    public String getModel() {return this.model;}

    public int getTahunProduksi() {return this.tahunProduksi;}

    public void setTahunProduksi(int tahunProduksi) {this.tahunProduksi = tahunProduksi;}

    public String getWarna() {return this.warna;}

    public void setWarna(String warna) {this.warna = warna;}

    public abstract double hitungPajak();

    public abstract String getTipeKendaraan();

    public void setKecepatan(double kecepatan) {this.kecepatan = kecepatan;}

    public double getKecepatan() {return this.kecepatan;}
}