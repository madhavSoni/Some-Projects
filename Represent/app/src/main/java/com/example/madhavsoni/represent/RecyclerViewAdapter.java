package com.example.madhavsoni.represent;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import android.net.Uri;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 1/1/2018.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mWebsites = new ArrayList<>();
    private ArrayList<String> mParty = new ArrayList<>();
    private ArrayList<String> mIDs = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> imageNames, ArrayList<String> images, ArrayList<String> websites, ArrayList<String> party, ArrayList<String> ids) {
        mImageNames = imageNames;
        mImages = images;
        mContext = context;
        mWebsites = websites;
        mParty = party;
        mIDs = ids;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @TargetApi(16)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);
        if (mParty.get(position).equals("D")) {
            holder.image.setBackground(mContext.getResources().getDrawable(R.drawable.layout_sen_rep));
        } else {
            holder.image.setBackground(mContext.getResources().getDrawable(R.drawable.layout_red));
        }

        holder.imageName.setText(mImageNames.get(position));
        holder.contact.setText("Contact");
        holder.website.setText("Website");
        holder.moreInfo.setText("More Info");

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( mWebsites.get(position) + "contact/"));
                mContext.startActivity(intent);
            }
        });

        holder.website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( mWebsites.get(position)));
                mContext.startActivity(intent);
            }
        });

        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ID LLLL" +mIDs.get(position) );
                Intent intent = new Intent(mContext, More.class);
                intent.putExtra("id", mIDs.get(position));
                intent.putExtra("image_name", mImageNames.get(position));
                mContext.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mImageNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView imageName;
        ConstraintLayout parentLayout;
        TextView contact;
        TextView website;
        TextView moreInfo;



        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            contact = itemView.findViewById(R.id.contact);
            imageName = itemView.findViewById(R.id.image_name);
            website = itemView.findViewById(R.id.website);
            moreInfo = itemView.findViewById(R.id.moreinfo);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}



