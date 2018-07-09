package no.woact.morroo16.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.woact.morroo16.tictactoe.R;


public class InGameFragmentMidle extends Fragment {

    public InGameFragmentMidle() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_in_game_fragment_midle, container, false);


    }
}

