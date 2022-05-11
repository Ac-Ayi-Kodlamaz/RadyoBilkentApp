package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import model.Blog;

public class BlogFragmentAdapter extends FragmentStateAdapter {

    private int itemCount;

    public BlogFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int itemCount) {
        super(fragmentManager, lifecycle);
        this.itemCount = itemCount;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Blog blog = BlogActivity.blogs.get(position);
        blog.consume();
        return BlogFragment.newInstance(blog.getCoverImageURL(),
                blog.getTitle(),
                blog.getCreator(),
                blog.getDate(),
                blog.getDuration(),
                blog.getTimesConsumed(),
                blog.getContent()
        );
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

}
