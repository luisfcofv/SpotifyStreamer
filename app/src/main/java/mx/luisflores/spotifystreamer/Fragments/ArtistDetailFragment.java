package mx.luisflores.spotifystreamer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import mx.luisflores.spotifystreamer.R;


public class ArtistDetailFragment extends Fragment {

    private final String LOG_TAG = ArtistDetailFragment.class.getSimpleName();

    public static final String SPOTIFY_ARTIST = "mx.luisflores.spotifystreamer.SPOTIFY_ARTIST";

    private SpotifyService mSpotifyService;

    public ArtistDetailFragment() {
        SpotifyApi api = new SpotifyApi();
        mSpotifyService = api.getService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_detail, container, false);

        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra(SPOTIFY_ARTIST)) {
            String artist = intent.getStringExtra(SPOTIFY_ARTIST);
            fetchSongsFromArtist(artist);
        }

        return rootView;
    }

    private void fetchSongsFromArtist(String artist) {
        Log.d("Luis", "Artist: " + artist);
    }
}
