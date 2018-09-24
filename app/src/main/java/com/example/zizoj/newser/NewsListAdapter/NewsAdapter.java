package com.example.zizoj.newser.NewsListAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zizoj.newser.R;
import com.example.zizoj.newser.Responses.Article;
import com.example.zizoj.newser.WebViewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.HolderView> {

    Context context1;
    ArrayList<Article> NewsList = new ArrayList<Article>();
    private String mAppend = "";

    public NewsAdapter(Context context, ArrayList<Article> NewsList1 ) {
        this.context1 = context;
        this.NewsList = NewsList1;
    }

    public void refreshList(List<Article> products){
        NewsList.clear();
        NewsList.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public HolderView onCreateViewHolder( ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item,parent,false);

        HolderView view = new HolderView(layout);

        return view;
    }

    @Override
    public void onBindViewHolder( HolderView holder, int position) {

        holder.source.setText(NewsList.get(position).getSource().getName());
        holder.Title.setText(NewsList.get(position).getTitle());
        holder.time.setText(NewsList.get(position).getPublishedAt());

        String imgURL = NewsList.get(position).getUrlToImage();

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage( mAppend + imgURL, holder.newsphoto, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    class HolderView extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView source ;
        TextView Title ;
        TextView time;
        ImageView newsphoto;

        public HolderView(View itemView) {
            super(itemView);

            source = (TextView)itemView.findViewById(R.id.Source);
            Title = (TextView)itemView.findViewById(R.id.News_Title);
            newsphoto = (ImageView)itemView.findViewById(R.id.News_Photo);
            time = (TextView)itemView.findViewById(R.id.NewsTime);

            newsphoto.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Intent intent = new Intent(context1, WebViewActivity.class);
            intent.putExtra("NewsUrl", NewsList.get(clickedPosition).getUrl());
            context1.startActivity(intent);
        }
    }
}
