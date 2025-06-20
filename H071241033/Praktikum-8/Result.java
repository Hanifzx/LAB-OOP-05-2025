import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Result {
    int fileId;
    String threadName;
    int duration;
    String status;

    // Constructor untuk kelas Result
    public Result(int fileId, String threadName, int duration, String status) {
        this.fileId = fileId;
        this.threadName = threadName;
        this.duration = duration;
        this.status = status;
    }

    // Variabel statis untuk melacak status download
    private static int successfulDownloads = 0;
    private static int completedDownloads = 0;
    private static int totalFiles = 0;
    private static boolean allDone = false;
    private static ArrayList<Result> results = new ArrayList<>();

    // TODO (1)
    // Setiap kali method dipanggil, total file yang selesai didownload akan bertambah
    // Penjelasan: Method ini mencatat hasil download setiap file, menentukan status
    // berdasarkan durasi (Success jika <= 2 detik, Timeout jika > 2 detik),
    // dan menambahkannya ke list results.
    public static synchronized void recordResult(int fileId, int duration, String threadName) {
        completedDownloads++;
        String status;
        if (duration <= 2) {
            status = "Success";
            successfulDownloads++;
        } else {
            status = "Timeout";
        }
        Result result = new Result(fileId, threadName, duration, status);
        results.add(result);
    }

    public static void main(String[] args) {
        // TODO (2)
        // Gunakan Scanner untuk meminta input jumlah file yang akan diunduh dari pengguna
        // Penjelasan: Membuat objek Scanner untuk membaca input jumlah file dari console
        // dan menyimpannya ke variabel totalFiles.
        Scanner x = new Scanner(System.in);
        System.out.print("Enter the number of files to download: ");
        totalFiles = x.nextInt();
        x.nextLine();

        // TODO (3)
        // Siapkan dua executor service:
        // Penjelasan: Membuat dua ExecutorService: downloadExecutor dengan 3 thread untuk
        // download paralel dan uiExecutor dengan 1 thread untuk menampilkan status UI.
        ExecutorService downloadExecutor = Executors.newFixedThreadPool(3); // 3 thread untuk download
        ExecutorService uiExecutor = Executors.newSingleThreadExecutor();   // 1 thread untuk UI

        // Mencatat waktu mulai
        long startTime = System.currentTimeMillis();

        // TODO (4)
        // Buat tugas yang dijalankan oleh uiExecutor untuk menampilkan proses download
        // Penjelasan: Menjalankan tugas di uiExecutor untuk mencetak status "Downloading files..."
        // setiap detik sampai semua download selesai (allDone = true).
        uiExecutor.submit(() -> {
            int time = 0;
            while (!allDone) {
                System.out.printf("Downloading files....(%ds)%n", time);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                time++;
            }
        });

        // TODO (5)
        // Buat dan jalankan tugas download untuk setiap file menggunakan downloadExecutor
        // Penjelasan: Membuat tugas download untuk setiap file (berdasarkan totalFiles)
        // dengan durasi acak 1-3 detik, lalu mencatat hasilnya menggunakan recordResult.
        for (int i = 1; i <= totalFiles; i++) {
            int fileId = i;
            downloadExecutor.submit(() -> {
                int duration = new Random().nextInt(3) + 1;
                try {
                    Thread.sleep(duration * 1000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                String threadName = Thread.currentThread().getName();
                recordResult(fileId, duration, threadName);
            });
        }

        // TODO (6)
        // Tutup downloadExecutor agar tidak menerima tugas baru
        // Penjelasan: Memanggil shutdown() pada downloadExecutor untuk mencegah
        // penerimaan tugas baru setelah semua tugas download disubmit.
        downloadExecutor.shutdown();

        // TODO (7)
        // Tunggu semua tugas download selesai sebelum lanjut ke proses berikutnya
        // Penjelasan: Menggunakan awaitTermination untuk menunggu semua tugas download
        // selesai dalam waktu maksimal 1 menit, jika tidak, memaksa shutdown.
        try {
            if (!downloadExecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                downloadExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            downloadExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // TODO (8)
        // Ketika semua proses download sudah selesai:
        // Penjelasan: Menandai allDone sebagai true untuk menghentikan UI thread,
        // lalu menutup uiExecutor dan menunggu hingga selesai (maksimal 5 detik).
        allDone = true;
        uiExecutor.shutdown();
        try {
            uiExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            uiExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Mencatat total waktu proses secara keseluruhan
        long endTime = System.currentTimeMillis();
        int totalTime = (int) ((endTime - startTime) / 1000);

        // Header tabel
        System.out.println("--------------------------------------------------");
        System.out.println("                  Detailed Report                 ");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-8s | %-18s | %-8s | %-8s%n",
                "File ID", "Thread", "Duration", "Status");
        System.out.println("--------------------------------------------------");

        // Urutkan hasil berdasarkan fileId
        results.sort(Comparator.comparingInt(r -> r.fileId));

        // Tampilan hasil setiap proses download dalam bentuk table
        for (Result r : results) {
            System.out.printf("%-8d | %-18s | %-8s | %-8s%n",
                    r.fileId, r.threadName, r.duration + "s", r.status);
        }

        // Ringkasan Proses
        System.out.println("--------------------------------------------------");
        System.out.println("                     Summary                      ");
        System.out.println("--------------------------------------------------");
        System.out.println("Successful downloads : " + successfulDownloads);
        System.out.println("Failed downloads     : " + (totalFiles - successfulDownloads));
        System.out.printf("Total time           : %ds%n", totalTime);
    }
}