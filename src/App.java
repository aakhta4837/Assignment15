import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    private Button[][] board = new Button[5][5];
    private boolean isXTurn = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();

        // Create a 5x5 grid of buttons
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Button button = new Button();
                button.setPrefSize(100, 100);
                int r = row, c = col;
                button.setOnAction(e -> handleMove(button, r, c));
                board[row][col] = button;
                gridPane.add(button, col, row);
            }
        }

        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setTitle("5x5 Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMove(Button button, int row, int col) {
        if (!button.getText().isEmpty()) return; 

        button.setText(isXTurn ? "X" : "O"); 
        if (checkWinner()) {
            showWinnerDialog(isXTurn ? "X" : "O");
            resetBoard();
        }
        isXTurn = !isXTurn; 
    }

    private boolean checkWinner() {
        String player = isXTurn ? "X" : "O";

        
        for (int i = 0; i < 5; i++) {
            if (allMatch(player, board[i][0], board[i][1], board[i][2], board[i][3], board[i][4]) ||
                allMatch(player, board[0][i], board[1][i], board[2][i], board[3][i], board[4][i])) {
                return true;
            }
        }

        // Check diagonals
        return allMatch(player, board[0][0], board[1][1], board[2][2], board[3][3], board[4][4]) ||
               allMatch(player, board[0][4], board[1][3], board[2][2], board[3][1], board[4][0]);
    }

    private boolean allMatch(String player, Button... buttons) {
        for (Button button : buttons) {
            if (!button.getText().equals(player)) return false;
        }
        return true;
    }


    private void resetBoard() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                board[row][col].setText("");
            }
        }
        isXTurn = true;
    }

    private void showWinnerDialog(String winner) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("We have a winner!");
        alert.setContentText("Winner is: " + winner);
        alert.showAndWait();
    }
}
