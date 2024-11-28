import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.random;

// Kelas Barang
class Barang {
    protected String kodeBarang;
    protected String namaBarang;
    protected double hargaBarang;

    public Barang(String kodeBarang, String namaBarang, double hargaBarang) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
    }

    public double hitungTotal(int jumlahBeli) {
        return jumlahBeli * hargaBarang;
    }
}

// Subclass: Transaksi
class Transaksi extends Barang {
    private String noFaktur;

    public Transaksi(String noFaktur, String kodeBarang, String namaBarang, double hargaBarang) {
        super(kodeBarang, namaBarang, hargaBarang);
        this.noFaktur = noFaktur;
    }

    public void tampilkanTransaksi(int jumlahBeli, String namaKasir) {
        double total = hitungTotal(jumlahBeli);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String tanggalWaktu = sdf.format(new Date());

        System.out.println("\n=== Selamat Datang di Supermarket HappyMart ===");
        System.out.println("Tanggal dan Waktu: " + tanggalWaktu);
        System.out.println("+----------------------------------------------------+");
        System.out.println("No Faktur   : " + noFaktur);
        System.out.println("Kode Barang : " + kodeBarang);
        System.out.println("Nama Barang : " + namaBarang);
        System.out.println("Harga Barang: Rp " + hargaBarang);
        System.out.println("Jumlah Beli : " + jumlahBeli);
        System.out.println("TOTAL       : Rp " + total);
        System.out.println("+----------------------------------------------------+");
        System.out.println("Kasir       : " + namaKasir);
        System.out.println("+----------------------------------------------------+");
    }
}

// Custom Exception
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Login Section
        final String USERNAME = "jo";
        final String PASSWORD = "jo1234";
        String captchaGenerated = generateCaptcha();

        boolean loginSuccess = false;
        while (!loginSuccess) {
            System.out.println("\n+-----------------------------------------------------+");
            System.out.println("Log In");
            System.out.println("+-----------------------------------------------------+");
            System.out.print("Username : ");
            String username = scanner.nextLine().trim();

            System.out.print("Password : ");
            String password = scanner.nextLine().trim();

            System.out.println("Captcha  : " + captchaGenerated);
            System.out.print("Masukkan Captcha : ");
            String captchaInput = scanner.nextLine().trim();

            if (username.equals(USERNAME) && password.equals(PASSWORD) && captchaInput.equals(captchaGenerated)) {
                loginSuccess = true;
                System.out.println("Login berhasil!");
            } else {
                System.out.println("Login gagal. Silakan coba lagi.\n");
                captchaGenerated = generateCaptcha(); // Regenerate captcha for next attempt
            }
        }

        try {
            // Input Nama Kasir
            System.out.print("\nMasukkan Nama Kasir: ");
            String namaKasir = scanner.nextLine().trim();
            if (namaKasir.isEmpty()) {
                throw new InvalidInputException("Nama kasir tidak boleh kosong!");
            }
            namaKasir = namaKasir.toUpperCase(); // Contoh manipulasi String

            // Input No Faktur
            System.out.print("Masukkan No Faktur: ");
            String noFaktur = scanner.nextLine().trim();
            if (noFaktur.isEmpty()) {
                throw new InvalidInputException("No Faktur tidak boleh kosong!");
            }

            // Input Kode Barang
            System.out.print("Masukkan Kode Barang: ");
            String kodeBarang = scanner.nextLine().trim();
            if (kodeBarang.isEmpty()) {
                throw new InvalidInputException("Kode Barang tidak boleh kosong!");
            }

            // Input Nama Barang
            System.out.print("Masukkan Nama Barang: ");
            String namaBarang = scanner.nextLine().trim();
            if (namaBarang.isEmpty()) {
                throw new InvalidInputException("Nama Barang tidak boleh kosong!");
            }

            // Input Harga Barang
            System.out.print("Masukkan Harga Barang: ");
            if (!scanner.hasNextDouble()) {
                throw new InvalidInputException("Harga Barang harus berupa angka!");
            }
            double hargaBarang = scanner.nextDouble();
            if (hargaBarang <= 0) {
                throw new InvalidInputException("Harga Barang harus lebih dari 0!");
            }

            // Membersihkan buffer newline setelah nextDouble()
            scanner.nextLine();

            // Input Jumlah Beli
            System.out.print("Masukkan Jumlah Beli: ");
            if (!scanner.hasNextInt()) {
                throw new InvalidInputException("Jumlah Beli harus berupa angka!");
            }
            int jumlahBeli = scanner.nextInt();
            if (jumlahBeli <= 0) {
                throw new InvalidInputException("Jumlah Beli harus lebih dari 0!");
            }

            // Membuat objek transaksi
            Transaksi transaksi = new Transaksi(noFaktur, kodeBarang, namaBarang, hargaBarang);
            transaksi.tampilkanTransaksi(jumlahBeli, namaKasir);

        } catch (InvalidInputException e) {
            System.out.println("Input Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Method untuk generate captcha sederhana
    private static String generateCaptcha() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int randomIndex = (int) (Math.random() * chars.length());
            captcha.append(chars.charAt(randomIndex));
        }
        return captcha.toString();
    }
}
