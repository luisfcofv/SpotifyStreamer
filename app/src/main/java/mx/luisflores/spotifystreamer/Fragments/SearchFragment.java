package mx.luisflores.spotifystreamer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import mx.luisflores.spotifystreamer.R;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        EditText searchEditText = (EditText) rootView.findViewById(R.id.search_edittext);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("Luis", "Here:" + charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        ListView listView = (ListView) rootView.findViewById(R.id.artist_listview);

        return rootView;
    }


}