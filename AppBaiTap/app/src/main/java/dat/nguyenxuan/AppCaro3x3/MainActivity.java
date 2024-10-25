package com.example.carogame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import dat.nguyenxuan.viduhello.R;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Khởi tạo các button
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + (i * 3 + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new ButtonClickListener(i, j));
            }
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            if (button.getText().toString().equals("")) {
                button.setText(player1Turn ? "X" : "O");
                if (checkForWin()) {
                    // Thông báo người thắng
                    String winner = player1Turn ? "Player 1 (X)" : "Player 2 (O)";
                    Toast.makeText(MainActivity.this, winner + " wins!", Toast.LENGTH_SHORT).show();
                    resetGame();
                } else if (isBoardFull()) {
                    // Thông báo hòa
                    Toast.makeText(MainActivity.this, "It's a draw!", Toast.LENGTH_SHORT).show();
                    resetGame();
                }
                player1Turn = !player1Turn;
            }
        }
    }

    private boolean checkForWin() {
        // Kiểm tra hàng
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(buttons[i][1].getText().toString()) &&
                    buttons[i][0].getText().toString().equals(buttons[i][2].getText().toString()) &&
                    !buttons[i][0].getText().toString().equals("")) {
                return true;
            }
        }

        // Kiểm tra cột
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().toString().equals(buttons[1][j].getText().toString()) &&
                    buttons[0][j].getText().toString().equals(buttons[2][j].getText().toString()) &&
                    !buttons[0][j].getText().toString().equals("")) {
                return true;
            }
        }

        // Kiểm tra đường chéo
        if (buttons[0][0].getText().toString().equals(buttons[1][1].getText().toString()) &&
                buttons[0][0].getText().toString().equals(buttons[2][2].getText().toString()) &&
                !buttons[0][0].getText().toString().equals("")) {
            return true;
        }
        if (buttons[0][2].getText().toString().equals(buttons[1][1].getText().toString()) &&
                buttons[0][2].getText().toString().equals(buttons[2][0].getText().toString()) &&
                !buttons[0][2].getText().toString().equals("")) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        player1Turn = true;
    }
}