package com.h.myapplication.test;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.h.myapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class CusRecyclerAdapter extends RecyclerView.Adapter<CusRecyclerAdapter.ViewHolder> {
    private static final String TAG = CusRecyclerAdapter.class.getSimpleName();

    List<Book> mBooks;

    public CusRecyclerAdapter(List<Book> books){
        mBooks = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_book,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Book book = mBooks.get(position);
        viewHolder.mBookImage.setImageResource(book.getIcon());
        viewHolder.mBookName.setText(book.getName());
        viewHolder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.book_image)
        ImageView mBookImage;
        @BindView(R.id.book_name)
        TextView mBookName;
        int mPosition = -1;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

            /*mBookImage = (ImageView) view.findViewById(R.id.book_image);
            mBookname = (TextView) view.findViewById(R.id.book_name);*/
        }

        public void setPosition(int position){
            mPosition = position;
        }

        @OnClick({R.id.book_image,R.id.book_name})
        public void clickView(){
            Log.d(TAG,"position = " +mPosition);

        }

        @OnLongClick({R.id.book_image,R.id.book_name})
        public boolean longClickView(){
            Log.d(TAG,"longClickView position = " +mPosition);
            return true;
        }

    }

}
