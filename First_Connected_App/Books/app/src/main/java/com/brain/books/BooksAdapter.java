package com.brain.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder>{

    ArrayList<Book> books;
    public BooksAdapter(ArrayList<Book> books){
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itmeView = LayoutInflater.from(context)
                .inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(itmeView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvAuthors;
        TextView tvDate;
        TextView tvPublisher;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvAuthors = (TextView) itemView.findViewById(R.id.tv_authors);
            tvDate = (TextView) itemView.findViewById(R.id.tv_published_date);
            tvPublisher = (TextView) itemView.findViewById(R.id.tv_publisher);

        }

        public void bind(Book book){
            tvTitle.setText(book.title);
            String authors = "";
            int i = 0;
            for(String author: book.authors){
                authors += author;
                i++;
                if(i < book.authors.length){
                    authors += ", ";
                }
            }
            tvDate.setText(book.publishedDate);
            tvPublisher.setText(book.publisher);
        }
    }
}
