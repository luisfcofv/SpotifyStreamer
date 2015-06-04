package mx.luisflores.spotifystreamer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import mx.luisflores.spotifystreamer.R;
import mx.luisflores.spotifystreamer.adapter.TrackAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TopTracksFragment extends Fragment {

    private final String LOG_TAG = TopTracksFragment.class.getSimpleName();

    public static final String SPOTIFY_ARTIST = "mx.luisflores.spotifystreamer.SPOTIFY_ARTIST";
    public static final String SPOTIFY_ARTIST_ID = "mx.luisflores.spotifystreamer.SPOTIFY_ARTIST_ID";

    private SpotifyService mSpotifyService;
    private ListView mTracksListView;

    public TopTracksFragment() {
        SpotifyApi api = new SpotifyApi();
        mSpotifyService = api.getService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(SPOTIFY_ARTIST_ID)) {
                String artistId = intent.getStringExtra(SPOTIFY_ARTIST_ID);
                fetchSongsFromArtistId(artistId);
            }

            if (intent.hasExtra(SPOTIFY_ARTIST) && getActivity() instanceof ActionBarActivity) {
                String artist = intent.getStringExtra(SPOTIFY_ARTIST);
                ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle(artist);
            }
        }

        mTracksListView = (ListView) rootView.findViewById(R.id.tracks_listview);
        return rootView;
    }

    private void fetchSongsFromArtistId(String artistId) {
        HashMap<String, Object> options = new HashMap<>();
        options.put("country", "MX");

        mSpotifyService.getArtistTopTrack(artistId, options, new Callback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {
                ArrayList<Track> tracksList = new ArrayList<>();
                tracksList.addAll(tracks.tracks);
                final TrackAdapter trackAdapter = new TrackAdapter(getActivity(), tracksList);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTracksListView.setAdapter(trackAdapter);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LOG_TAG, error.getMessage());
            }
        });
    }
}
