public class Main {
    public static void main(String[] args) {
        // Buat objek Mobil
        Mobil mobil = new Mobil("Model X", "Tesla");
        mobil.setJumlahPintu(4);
        mobil.setJumlahKursi(5);
        mobil.setKapasitasMesin(2.0);
        mobil.setBahanBakar("Listrik");
        mobil.setKecepatan(120);

        System.out.println("===== MOBIL =====");
        mobil.mulai();
        System.out.println("Tipe: " + mobil.getTipeKendaraan());
        System.out.println("Pajak: Rp" + mobil.hitungPajak());
        System.out.println("Kecepatan: " + mobil.getKecepatan() + " km/jam");
        mobil.lakukanServis();
        System.out.println("Biaya servis: Rp" + mobil.hitungBiayaServis());

        System.out.println();

        // Buat objek Motor
        Motor motor = new Motor("Yamaha", "R15");
        motor.setJenisMotor("Sport");
        motor.setKapasitasTangki(10);
        motor.setTipeSuspensi("Mono Shock");
        motor.setKecepatan(80);

        System.out.println("===== MOTOR =====");
        motor.mulai();
        System.out.println("Tipe: " + motor.getTipeKendaraan());
        System.out.println("Pajak: Rp" + motor.hitungPajak());
        System.out.println("Kecepatan: " + motor.getKecepatan() + " km/jam");
        motor.lakukanServis();
        System.out.println("Biaya servis: Rp" + motor.hitungBiayaServis());



        Truck truk = new Truck("Eropa", "BMW");
        truk.setJumlahSumbu(3);
        truk.setMuatanMaks(50);
        truk.setJenisTruk("Kontainer");
        truk.setKecepatan(100);
        truk.setBahanBakar("Solar");

        System.out.println("\n===== TRUK =====");
        truk.mulai();
        System.out.println("Tipe: " + truk.getTipeKendaraan());
        System.out.println("Pajak: Rp" + truk.hitungPajak());
        System.out.println("Kecepatan: " + truk.getKecepatan() + " km/jam");
        truk.mulai();
        truk.berhenti();
        truk.mulai();
        truk.berhenti();
        truk.lakukanServis();
        System.out.println("Biaya servis: Rp" + truk.hitungBiayaServis());


    }
}
