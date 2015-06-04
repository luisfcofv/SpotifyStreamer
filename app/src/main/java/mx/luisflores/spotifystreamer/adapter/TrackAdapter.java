package mx.luisflores.spotifystreamer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.AlbumSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import mx.luisflores.spotifystreamer.R;


public class TrackAdapter extends BasicAdapter {

    public TrackAdapter(Context context, ArrayList<?> list) {
        super(context, list);
    }

    @Override
    public View getView(int index, View convertView, ViewGroup viewGroup) {
        TrackViewHolder viewHolder;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_track, null);
            viewHolder = new TrackViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TrackViewHolder) convertView.getTag();
        }

        Track track = (Track) mList.get(index);
        AlbumSimple album = track.album;

        if (album.images.size() > 0) {
            Image image = album.images.get(0);
            Picasso.with(mContext).load(image.url).into(viewHolder.thumbnailImageView);
        }

        viewHolder.albumTextView.setText(album.name);
        viewHolder.trackTextView.setText(track.name);

        return convertView;
    }

    private class TrackViewHolder {

        public ImageView thumbnailImageView;
        public TextView albumTextView;
        public TextView trackTextView;

        public TrackViewHolder(View view) {
            thumbnailImageView = (ImageView) view.findViewById(R.id.list_item_track_imageview);
            albumTextView = (TextView) view.findViewById(R.id.list_item_album_textview);
            trackTextView = (TextView) view.findViewById(R.id.list_item_track_textview);
        }
    }

}
