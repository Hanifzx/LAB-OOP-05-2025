import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryLogger logger = new LibraryLogger();
        Library library = new Library(logger);

        while (true) {
            System.out.println("\n=== Sistem Manajemen Perpustakaan ===");
            System.out.println("1. Tambah Item");
            System.out.println("2. Tambah Anggota");
            System.out.println("3. Pinjam Item");
            System.out.println("4. Kembalikan Item");
            System.out.println("5. Lihat Status Perpustakaan");
            System.out.println("6. Lihat Log Aktivitas");
            System.out.println("7. Lihat Item yang Dipinjam Anggota");
            System.out.println("8. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (pilihan) {
                case 1:
                    System.out.println("Pilih jenis item:");
                    System.out.println("1. Buku");
                    System.out.println("2. DVD");
                    System.out.print("Pilihan: ");
                    int jenis = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    if (jenis == 1) {
                        System.out.print("Masukkan ID item: ");
                        int idb = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Masukkan judul item: ");
                        String judulb = scanner.nextLine();

                        System.out.print("Masukkan nama Author: ");
                        String author = scanner.nextLine();

                        LibraryItem itemb = new Books(judulb, idb, author);
                        library.addItem(itemb);
                        System.out.println("Buku berhasil ditambahkan.");
                    } else if (jenis == 2) {
                        System.out.print("Masukkan ID item: ");
                        int idd = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Masukkan judul item: ");
                        String juduld = scanner.nextLine();

                        System.out.print("Masukkan durasi DVD (menit): ");
                        int durasi = scanner.nextInt();
                        scanner.nextLine();

                        LibraryItem itemd = new Dvd(juduld, idd, durasi);
                        library.addItem(itemd);
                        System.out.println("DVD berhasil ditambahkan.");
                    } else {
                        System.out.println("Jenis item tidak valid.");
                    }
                    break;

                case 2:
                    System.out.print("Masukkan nama anggota: ");
                    String nama = scanner.nextLine();
                    System.out.print("Masukkan Id anggota: ");
                    int id = scanner.nextInt();
                    Member member = new Member(nama, id);
                    library.addMember(member);
                    System.out.println("Anggota " + nama + " berhasil ditambahkan.");
                    break;

                case 3:
                    System.out.print("ID Anggota: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("ID Item: ");
                    int itemId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Jumlah hari peminjaman: ");
                    int days = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        Member foundMember = library.findMemberById(memberId);
                        LibraryItem foundItem = library.findItemById(itemId);

                        String message = foundMember.borrow(foundItem, days);
                        System.out.println(message);
                        library.getLogger().logActivity(foundItem.getClass().getSimpleName() + " \"" + foundItem.title + "\" dipinjam oleh " + foundMember.name);
                    } catch (Exception e) {
                        System.out.println("Gagal meminjam: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("ID Anggota: ");
                    int memberIdReturn = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("ID Item yang ingin dikembalikan: ");
                    int itemIdReturn = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Jumlah hari keterlambatan: ");
                    int daysLate = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        Member foundMember = library.findMemberById(memberIdReturn);
                        LibraryItem foundItem = library.findItemById(itemIdReturn);

                        String result = foundMember.returnItem(foundItem, daysLate);
                        System.out.println(result);
                        library.getLogger().logActivity(foundItem.getClass().getSimpleName() + " \"" + foundItem.title + "\" dikembalikan oleh " + foundMember.name);
                    } catch (Exception e) {
                        System.out.println("Gagal mengembalikan item: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println(library.getLibraryStatus());
                    break;

                case 6:
                    String logs = library.getLogger().getLogs();
                    if (logs.isEmpty()) {
                        System.out.println("Belum ada aktivitas yang dicatat.");
                    } else {
                        System.out.println("\n=== Log Aktivitas Perpustakaan ===");
                        System.out.println(logs);
                    }
                    break;
                case 7:
                    System.out.print("Masukkan ID anggota: ");
                    int idAnggota = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        Member anggota = library.findMemberById(idAnggota);
                        anggota.getBorrowwedItem(); // Akan menampilkan daftar item yang dipinjam
                    } catch (Exception e) {
                        System.out.println("Gagal menampilkan item yang dipinjam: " + e.getMessage());
                    }
                    break;

                case 8:
                    System.out.println("Keluar dari sistem. Terima kasih!");
                    return;

                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
