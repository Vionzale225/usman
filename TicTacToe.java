import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();
        System.out.println("Permainan Tic Tac Toe");
        System.out.print("Masukkan 1 jika ingin jalan duluan, 2 jika bot jalan duluan: ");
        int choice = scanner.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.print("Pilihan tidak valid. Masukkan 1 atau 2: ");
            choice = scanner.nextInt();
        }
        // Tentukan simbol pemain/bot
        int userSymbol = (choice == 1) ? 1 : -1; 
        int botSymbol = -userSymbol; 
        int turn = 1; // 1 = X, -1 = O. X selalu mulai.
        if (choice == 2) {
            turn = 1; // Bot (X) mulai
        }

        board.printBoard();
        // Loop permainan
        while (!board.gameOver()) {
            if (turn == userSymbol) {
                // Giliran pemain
                char sym = (userSymbol == 1) ? 'X' : 'O';
                System.out.println("Giliran Anda (" + sym + ")");
                int row, col;
                do {
                    System.out.print("Masukkan baris (1-3): ");
                    row = scanner.nextInt() - 1;
                    System.out.print("Masukkan kolom (1-3): ");
                    col = scanner.nextInt() - 1;
                } while (row < 0 || row > 2 || col < 0 || col > 2 || board.board[row][col] != 0);
                board.board[row][col] = userSymbol;
            } else {
                // Giliran bot
                char sym = (botSymbol == 1) ? 'X' : 'O';
                System.out.println("Giliran bot (" + sym + ")");
                // Cari langkah terbaik dengan Minimax
                int bestVal = (botSymbol == 1) ? -1000 : 1000;
                int bestRow = -1, bestCol = -1;
                int bestRank = 100; // prioritas (tengah=1, sudut=2, tepi=3)
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        if (board.board[i][j] == 0) {
                            board.board[i][j] = botSymbol;
                            int val = board.minimax(-botSymbol);
                            board.board[i][j] = 0;
                            // Tetapkan peringkat posisi
                            int rank;
                            if (i == 1 && j == 1) rank = 1;           // tengah
                            else if (i % 2 == 0 && j % 2 == 0) rank = 2; // sudut
                            else rank = 3;                            // tepi
                            // Pilih langkah berdasarkan nilai minimax dan prioritas:contentReference[oaicite:8]{index=8}
                            if ((botSymbol == 1 && val > bestVal) ||
                                (botSymbol == -1 && val < bestVal) ||
                                (val == bestVal && rank < bestRank)) {
                                bestVal = val;
                                bestRank = rank;
                                bestRow = i;
                                bestCol = j;
                            }
                        }
                    }
                }
                board.board[bestRow][bestCol] = botSymbol;
                System.out.println("Bot meletakkan pada (" + (bestRow+1) + "," + (bestCol+1) + ")");
            }
            board.printBoard();
            turn = -turn; // ganti giliran
        }
        // Tampilkan hasil akhir
        int result = board.winner();
        if (result == userSymbol) {
            System.out.println("Anda menang!");
        } else if (result == botSymbol) {
            System.out.println("Bot menang!");
        } else {
            System.out.println("Hasil seri.");
        }
    }
}
