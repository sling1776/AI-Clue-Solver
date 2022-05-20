package com.example.ai_clue_solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.ai_clue_solver.Cards.Card;
import com.example.ai_clue_solver.Drivers.MainProgram;
import com.example.ai_clue_solver.Rummor.Rummor;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private MainProgram mp;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private int numPlayers;
    private int[] playerCardCounts;
    private String[] playernames;
    private Card[] playerOneCards;


    private int playerMakingRummorID;
    private Rummor rummor;
    private Boolean[] playerResponses;


    private int playerSpoiledID;
    private Card cardSpoiled;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNumberOfPlayersDialog();

    }

    public void createNumberOfPlayersDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popup_numberOfPlayers = getLayoutInflater().inflate(R.layout.popup_number_of_players, null);
        Button two = (Button)popup_numberOfPlayers.findViewById(R.id.numPlayersButton_2);
        Button three = (Button)popup_numberOfPlayers.findViewById(R.id.numPlayersButton_3);
        Button four = (Button)popup_numberOfPlayers.findViewById(R.id.numPlayersButton_4);
        Button five = (Button)popup_numberOfPlayers.findViewById(R.id.numPlayersButton_5);
        Button six = (Button)popup_numberOfPlayers.findViewById(R.id.numPlayersButton_6);

        dialogBuilder.setView(popup_numberOfPlayers);
        dialog = dialogBuilder.create();
        dialog.show();


        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numPlayers_buttonClickHelper(2);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numPlayers_buttonClickHelper(3);

            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numPlayers_buttonClickHelper(4);

            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numPlayers_buttonClickHelper(5);

            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numPlayers_buttonClickHelper(6);
            }
        });


    }
    public void createPlayerCardCountDialog(){
        for(String s:playernames){
            System.out.println(s);
        }
    }
    public void createPlayerNamesDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popup_enterPlayerNames = getLayoutInflater().inflate(R.layout.popup_player_names, null);
        RecyclerView list = popup_enterPlayerNames.findViewById(R.id.enter_player_names_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new PlayerNameEntryAdapter(numPlayers));

        Button skip = popup_enterPlayerNames.findViewById(R.id.enter_player_names_skip_button);
        Button ok = popup_enterPlayerNames.findViewById(R.id.enter_player_names_ok_button);

        dialogBuilder.setView(popup_enterPlayerNames);
        dialog = dialogBuilder.create();
        dialog.show();

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playernames = new String[numPlayers];
                for(int i = 0 ; i < numPlayers; i ++){
                    playernames[i] = "Player " + (i+1);
                }

                dialog.dismiss();
                createPlayerCardCountDialog();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playernames = new String[numPlayers];
                for(int i = 0 ; i < numPlayers; i ++){
                    playernames[i] = ((PlayerNameEntryAdapter)list.getAdapter()).getName(i);
                }
                for(int i = 0 ; i < numPlayers; i ++){
                    if(playernames[i].equals("")){
                        playernames[i] = "Player " + (i+1);
                    }
                }


                dialog.dismiss();
                createPlayerCardCountDialog();
            }
        });
    }
    public void createAddCardsToPlayerOneDialog(){

    }
    private void numPlayers_buttonClickHelper(int buttonId){
        numPlayers = buttonId;
        dialog.dismiss();
        createPlayerNamesDialog();
    }



    public void createAskForWhichPlayerMadeRummorDialog(){

    }
    public void createAskForRummorDialog(){

    }
    public void createAskIfPlayerShowedCardDialog(int playerID){

    }


    public void createAskForWhichPlayerSpoiledDialog(){

    }
    public void createAskForWhichCardWasSpoiledDialog(){

    }

}