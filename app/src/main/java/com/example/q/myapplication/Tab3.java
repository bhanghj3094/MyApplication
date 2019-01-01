package com.example.q.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Tab3  extends Fragment implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    MediaPlayer mediaPlayer;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView turn1;
    private TextView turn2;

    @Override
    public void onClick(View v)
    {
        if(!((Button)v).getText().toString().equals(""))
        {
            return;
        }
        if(player1Turn)
        {
            ((Button)v).setText("X");
            turn1.setVisibility(View.INVISIBLE);
            turn2.setVisibility(View.VISIBLE);
        }
        else
        {
            ((Button)v).setText("O");
            turn1.setVisibility(View.VISIBLE);
            turn2.setVisibility(View.INVISIBLE);
        }
        roundCount++;
        if(checkForWin())
        {
            if(player1Turn)
            {
                player1Wins();
            } else
            {
                player2Wins();
            }
        } else if(roundCount==9)
        {
            draw();
        } else
        {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];
        for(int i = 0 ; i < 3 ; i++)
        {
            for(int j = 0 ; j <3;j++)
            {
                field[i][j]=buttons[i][j].getText().toString();
            }
        }
        for(int i = 0 ; i < 3 ; i++)
        {
           if(field[i][0].equals(field[i][1])&&field[i][0].equals(field[i][2])&&!field[i][0].equals(""))
           {
               return true;
           }
        }
        for(int i = 0 ; i < 3 ; i++)
        {
            if(field[0][i].equals(field[1][i])&&field[0][i].equals(field[2][i])&&!field[0][i].equals(""))
            {
                return true;
            }
        }
        if(field[0][0].equals(field[1][1])&&field[0][0].equals(field[2][2])&&!field[0][0].equals(""))
        {
            return true;
        }
        if(field[0][2].equals(field[1][1])&&field[1][1].equals(field[2][0])&&!field[0][2].equals(""))
        {
            return true;
        }
        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(getContext(),"Player 1 Wins!",Toast.LENGTH_SHORT).show();
        clap();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins(){
        player2Points++;
        Toast.makeText(getContext(),"Player 2 Wins!",Toast.LENGTH_SHORT).show();
        clap();
        updatePointsText();
        resetBoard();
    }
    private void draw(){
        Toast.makeText(getContext(),"Draw!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePointsText()
    {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }
    private void resetBoard()
    {
        for(int i = 0 ; i < 3 ; i++)
        {
            for(int j = 0 ; j < 3 ; j++)
            {
               buttons[i][j].setText("");
            }
        }
        roundCount=0;
        player1Turn = true;
        turn1.setVisibility(View.VISIBLE);
        turn2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount",roundCount);
        outState.putInt("player1points",player1Points);
        outState.putInt("player2points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if(savedInstanceState!=null) {
                roundCount = savedInstanceState.getInt("roundCount");
                player1Points = savedInstanceState.getInt("player1points");
                player2Points = savedInstanceState.getInt("player2points");
                player1Turn = savedInstanceState.getBoolean("player1Turn");
            }
    }
    public void clap()
    {
        mediaPlayer = MediaPlayer.create(getContext(),R.raw.clap);
        mediaPlayer.start();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        textViewPlayer1 = rootView.findViewById(R.id.text_view_p1);
        textViewPlayer2 = rootView.findViewById(R.id.text_view_p2);
        turn1 = rootView.findViewById(R.id.p1turn);
        turn2 = rootView.findViewById(R.id.p2turn);

        for(int i = 0 ; i < 3 ; i++)
        {
            for(int j = 0 ; j < 3 ; j++)
            {
                String buttonID = "button_" + i+ (j+1);
                int resID = getResources().getIdentifier(buttonID,"id",getActivity().getPackageName());
                buttons[i][j]=rootView.findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        return rootView;
    }
}
