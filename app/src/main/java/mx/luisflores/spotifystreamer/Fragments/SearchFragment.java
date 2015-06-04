package mx.luisflores.spotifystreamer.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import mx.luisflores.spotifystreamer.R;
import mx.luisflores.spotifystreamer.activities.ArtistDetailActivity;
import mx.luisflores.spotifystreamer.adapter.ArtistAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SearchFragment extends Fragment {

    private final String LOG_TAG = SearchFragment.class.getSimpleName();

    private SpotifyService mSpotifyService;
    private ArtistAdapter mArtistAdapter;
    private ArrayList<Artist> mArtistList = new ArrayList<>();
    private String mCurrentArtist;

    public SearchFragment() {
        SpotifyApi api = new SpotifyApi();
        mSpotifyService = api.getService();
    }

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
                String searchText = charSequence.toString();

                if (mCurrentArtist == null || !mCurrentArtist.equals(searchText)) {
                    mCurrentArtist = searchText;
                    searchArtists(mCurrentArtist);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        ListView listView = (ListView) rootView.findViewById(R.id.artist_listview);
        mArtistAdapter = new ArtistAdapter(getActivity(), mArtistList);
        listView.setAdapter(mArtistAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String artist = (String) adapterView.getItemAtPosition(index);
                Intent intent = new Intent(getActivity(), ArtistDetailActivity.class)
                        .putExtra(ArtistDetailFragment.SPOTIFY_ARTIST, artist);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void searchArtists(String artistName) {
        mSpotifyService.searchArtists(artistName, new Callback<ArtistsPager>() {
            @Override
            public void success(ArtistsPager artistsPager, Response response) {
                mArtistList.clear();
                mArtistList.addAll(artistsPager.artists.items);
                onArtistListFetch();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, error.getMessage());
            }
        });
    }

    private void onArtistListFetch() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mArtistAdapter.notifyDataSetChanged();

                if (mArtistList.size() == 0) {
                    int duration = Toast.LENGTH_SHORT;
                    String message = getString(R.string.empty_artist_result);
                    Toast toast = Toast.makeText(getActivity(), message, duration);
                    toast.show();
                }
            }
        });
    }
}