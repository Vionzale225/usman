import java.util.Scanner;

public class SticksGame{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Pemain memilih jumlah batang awal (min 10, max 30)
        System.out.print("Masukkan jumlah batang (10-30): ");
        int sticks = scanner.nextInt();
        // Validasi jumlah batang
        while (sticks < 10 || sticks > 30) {
            System.out.print("Jumlah tidak valid! Masukkan antara 10 dan 30: ");
            sticks = scanner.nextInt();
        }

        // Pemain memilih siapa yang mulai (1=Anda, 2=Bot)
        System.out.print("Siapa yang mulai? (1=Anda, 2=Bot): ");
        int turn = scanner.nextInt();
        boolean playerTurn = (turn == 1);

        System.out.println("Permainan dimulai dengan " + sticks + " batang.");

        // Loop utama permainan
        while (sticks > 0) {
            if (playerTurn) {
                // Giliran pemain
                System.out.print("Giliran Anda. Ambil 1-3 batang: ");
                int taken = scanner.nextInt();
                // Validasi input pemain
                while (taken < 1 || taken > 3) {
                    System.out.print("Ambil minimal 1 dan maksimal 3 batang: ");
                    taken = scanner.nextInt();
                }
                // Jika pemain mencoba mengambil lebih banyak batang dari sisa, ambil yang tersisa saja
                if (taken > sticks) {
                    taken = sticks;
                }

                sticks -= taken;
                System.out.println("Anda mengambil " + taken + " batang. Sisa: " + sticks + " batang.");

                // Cek akhir permainan: jika tak ada batang tersisa, yang mengambil terakhir menang
                if (sticks == 0) {
                    System.out.println("Anda mengambil batang terakhir. Anda menang!");
                    break;
                }
            } else {
                // Giliran bot
                // Strategi bot: meninggalkan kelipatan 4 batang untuk pemain (strategi menang)
                int taken;
                int mod = sticks % 4;
                if (mod != 0) {
                    taken = mod;  // ambil batang supaya sisa menjadi kelipatan 4
                } else {
                    taken = 1;    // jika sudah kelipatan 4, ambil 1 batang (langkah valid minimum)
                }
                // Jika sisa batang kurang dari yang diinginkan, ambil semua sisanya
                if (taken > sticks) {
                    taken = sticks;
                }

                sticks -= taken;
                System.out.println("Bot mengambil " + taken + " batang. Sisa: " + sticks + " batang.");

                // Cek akhir permainan
                if (sticks == 0) {
                    System.out.println("Bot mengambil batang terakhir. Anda kalah!");
                    break;
                }
            }
            // Ganti giliran: pemain <-> bot
            playerTurn = !playerTurn;
        }

        scanner.close();
    }
}
