import java.util.*;

public class KnightsTour {
    static final int N = 8;
    static int[][] board = new int[N][N];
    static int[] rowMoves = {2, 1, -1, -2, -2, -1, 1, 2};
    static int[] colMoves = {1, 2, 2, 1, -1, -2, -2, -1};
    static int[] pathRow = new int[N*N];  // Simpan baris setiap langkah
    static int[] pathCol = new int[N*N];  // Simpan kolom setiap langkah

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Masukkan baris awal (1-8): ");
        int r = sc.nextInt();
        System.out.print("Masukkan kolom awal (1-8): ");
        int c = sc.nextInt();
        if (r < 1 || r > 8 || c < 1 || c > 8) {
            System.out.println("Input tidak valid. Baris/kolom harus 1 hingga 8.");
            return;
        }
        int startX = r - 1;
        int startY = c - 1;
        // Tandai posisi awal
        board[startX][startY] = 1;
        pathRow[0] = startX;
        pathCol[0] = startY;

        // Mulai pencarian jalur
        if (solve(1, startX, startY)) {
            // Tampilkan hasil langkah-langkah kuda
            for (int i = 0; i < N*N; i++) {
                // Output menggunakan indeks 1–8
                System.out.printf("Langkah ke-%d: (%d, %d)\n", i+1, pathRow[i]+1, pathCol[i]+1);
            }
        } else {
            System.out.println("Tidak ditemukan jalur Knight's Tour.");
        }
        sc.close();
    }

    // Fungsi rekursif untuk mencoba penyelesaian Knight's Tour
    static boolean solve(int moveCount, int x, int y) {
        if (moveCount == N*N) {
            // Seluruh 64 langkah telah ditempuh
            return true;
        }
        // Buat daftar calon langkah (degree, baris, kolom)
        List<int[]> moves = new ArrayList<>();
        for (int k = 0; k < 8; k++) {
            int nx = x + rowMoves[k];
            int ny = y + colMoves[k];
            if (nx >= 0 && nx < N && ny >= 0 && ny < N && board[nx][ny] == 0) {
                int deg = countAccess(nx, ny);
                moves.add(new int[]{deg, nx, ny});
            }
        }
        // Urutkan berdasarkan degree (aksesibilitas) naik (Warnsdorff's heuristic)
        Collections.sort(moves, Comparator.comparingInt(a -> a[0]));

        // Coba setiap langkah dalam urutan
        for (int[] m : moves) {
            int nx = m[1], ny = m[2];
            board[nx][ny] = moveCount + 1;  // tandai sebagai dikunjungi
            pathRow[moveCount] = nx;
            pathCol[moveCount] = ny;
            if (solve(moveCount + 1, nx, ny)) {
                return true;
            }
            // Backtracking: batalkan tanda kunjungan
            board[nx][ny] = 0;
        }
        return false;
    }

    // Hitung jumlah langkah lanjut yang mungkin dari (x, y)
    static int countAccess(int x, int y) {
        int count = 0;
        for (int k = 0; k < 8; k++) {
            int nx = x + rowMoves[k];
            int ny = y + colMoves[k];
            if (nx >= 0 && nx < N && ny >= 0 && ny < N && board[nx][ny] == 0) {
                count++;
            }
        }
        return count;
    }
}