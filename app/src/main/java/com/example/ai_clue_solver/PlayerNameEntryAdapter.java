package com.example.ai_clue_solver;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlayerNameEntryAdapter extends RecyclerView.Adapter<PlayerNameEntryAdapter.ViewHolder> {

    private int numPlayers;
    private String[] playerNames;
    public PlayerNameEntryAdapter(int numberOfPlayers){
        numPlayers = numberOfPlayers;
        playerNames = new String[numPlayers];
        for(int i = 0 ; i < numPlayers; i ++){
            playerNames[i] = "";
        }
    }

    public String getName(int pos){
        return playerNames[pos];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_name_entry, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position == 0){
            holder.getTextView().setText("Enter your name");
            holder.getEditText().setHint("Your name");
        }else if(position == 1){
            holder.getTextView().setText("Enter the name of the player next to you");
            holder.getEditText().setHint("Player name");
        }else{
            holder.getTextView().setText("Enter the next player's name");
            holder.getEditText().setHint("Player name");
        }
        holder.setID(position);

    }

    @Override
    public int getItemCount() {
        return numPlayers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private EditText e;
        private TextView t;
        private int ID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            e = itemView.findViewById(R.id.player_name_entry_editText);
            t = itemView.findViewById(R.id.player_name_entry_textView);

            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    playerNames[ID] = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

        public TextView getTextView(){
            return t;
        }

        public EditText getEditText(){
            return e;
        }

        public void setID(int id){
            ID= id;
        }
    }
}
