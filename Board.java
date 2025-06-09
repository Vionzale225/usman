public class Board {
    int[][] board;

    public Board() {
        board = new int[3][3];
        // Inisialisasi papan kosong (0)
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++){
                board[i][j] = 0;
            }
        }
    }

    // Cek pemenang: return 1 jika X menang, -1 jika O menang, 0 jika belum ada
    public int winner() {
        // Periksa baris
        for(int i = 0; i < 3; i++){
            int sum = board[i][0] + board[i][1] + board[i][2];
            if (sum == 3) return 1;
            if (sum == -3) return -1;
        }
        // Periksa kolom
        for(int j = 0; j < 3; j++){
            int sum = board[0][j] + board[1][j] + board[2][j];
            if (sum == 3) return 1;
            if (sum == -3) return -1;
        }
        // Periksa diagonal
        int sum = board[0][0] + board[1][1] + board[2][2];
        if (sum == 3) return 1;
        if (sum == -3) return -1;
        sum = board[0][2] + board[1][1] + board[2][0];
        if (sum == 3) return 1;
        if (sum == -3) return -1;
        return 0; // Belum ada pemenang
    }

    // Cek apakah permainan selesai (ada pemenang atau papan penuh)
    public boolean gameOver() {
        if (winner() != 0) return true;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if (board[i][j] == 0) {
                    return false; // masih ada kotak kosong
                }
            }
        }
        return true; // seri karena penuh
    }

    // Cetak papan ke terminal
    public void printBoard() {
        System.out.println("Papan saat ini:");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                char c;
                if (board[i][j] == 1) c = 'X';
                else if (board[i][j] == -1) c = 'O';
                else c = '-';
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    // Algoritma Minimax untuk mengevaluasi posisi, player = 1 (X) atau -1 (O)
    public int minimax(int player) {
        int win = winner();
        if (win != 0) {
            // Jika X menang -> +10, O menang -> -10:contentReference[oaicite:7]{index=7}
            return (win == 1) ? 10 : -10;
        }
        // Cek seri (papan penuh)
        boolean empty = false;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if (board[i][j] == 0) {
                    empty = true;
                    break;
                }
            }
            if(empty) break;
        }
        if (!empty) {
            return 0; // seri, nilai 0
        }

        // Rekursi Minimax
        int bestVal;
        if (player == 1) { // giliran X (maksimizer)
            bestVal = -1000;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if (board[i][j] == 0) {
                        board[i][j] = 1;
                        int val = minimax(-1);
                        board[i][j] = 0;
                        bestVal = Math.max(bestVal, val);
                    }
                }
            }
        } else { // giliran O (minimizasi)
            bestVal = 1000;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if (board[i][j] == 0) {
                        board[i][j] = -1;
                        int val = minimax(1);
                        board[i][j] = 0;
                        bestVal = Math.min(bestVal, val);
                    }
                }
            }
        }
        return bestVal;
    }
}
