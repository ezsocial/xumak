package net.ezmovil.xumak;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.ezmovil.xumak.info.infoXumakImage;

import java.util.List;

public class AlbumAdapter2 extends RecyclerView.Adapter<AlbumAdapter2.MyViewHolder> {

    private Context mContext;
    private List<net.ezmovil.xumak.Album2> albumList;

    public String global;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname, name, count, uri;
        public TextView occupation, status, portrayed;
        public ImageView image, btndetails;
        public ImageButton heart;

        public MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            nickname = (TextView) view.findViewById(R.id.nickname);
            count = (TextView) view.findViewById(R.id.count);
            image = (ImageView) view.findViewById(R.id.thumbnail);
            btndetails = (ImageView) view.findViewById(R.id.btndetails);
            uri = (TextView) view.findViewById(R.id.uri);

            occupation = (TextView) view.findViewById(R.id.occupation);
            status = (TextView) view.findViewById(R.id.status);
            portrayed = (TextView) view.findViewById(R.id.portrayed);

            heart = (ImageButton) view.findViewById(R.id.heart);
        }
    }


    public AlbumAdapter2(Context mContext, List<Album2> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_xumak, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album2 album2 = albumList.get(position);
        holder.name.setText(album2.getName());
        holder.nickname.setText(album2.getNickname());
        holder.count.setText(album2.getEpisode() + "");
        holder.uri.setText(album2.getUri());

        holder.occupation.setText(album2.getOccupation());
        holder.status.setText(album2.getStatus());
        holder.portrayed.setText(album2.getPortrayed());

        //loading album cover using Glide library
        Glide.with(mContext).load(album2.getUri()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                infoXumakImage _infoXumakrImage = new infoXumakImage();
                _infoXumakrImage.setTxt(holder.uri.getText().toString());
                _infoXumakrImage.setName(holder.name.getText().toString());
                _infoXumakrImage.setNickname(holder.nickname.getText().toString());
                _infoXumakrImage.setGeonameid(holder.count.getText().toString());

                _infoXumakrImage.setOccupation(holder.occupation.getText().toString());
                _infoXumakrImage.setStatus(holder.status.getText().toString());
                _infoXumakrImage.setPortrayed(holder.portrayed.getText().toString());

                showPopupMenu(holder.image, holder.uri.getText().toString());
            }
        });

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.heart.isActivated())
                {
                    holder.heart.setImageResource(R.drawable.icon_xumak_heart);
                }
                else
                {
                    holder.heart.setImageResource(R.drawable.icon_xumak_heart_on);

                }
            }
        });


    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, String uri) {

        global = uri;

        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_share:
                    Toast.makeText(mContext, "Compartir imagen", Toast.LENGTH_SHORT).show();


                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, global);
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, "Share Image from Bottle Rocket");
                    mContext.startActivity(shareIntent);


                    return true;
                case R.id.action_details:
                    Toast.makeText(mContext, "Detalles", Toast.LENGTH_SHORT).show();


                    Intent weatherIntent = new Intent(mContext, XumakActivity.class);
                    mContext.startActivity(weatherIntent);

                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}