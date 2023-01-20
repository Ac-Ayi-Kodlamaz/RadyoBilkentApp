package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import model.Blog;
import model.Podcast;

public class PodcastFragmentAdapter extends FragmentStateAdapter {

    private int itemCount;

    public PodcastFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int itemCount) {
        super(fragmentManager, lifecycle);
        this.itemCount = itemCount;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Podcast podcast = PodcastActivity.podcasts.get(position);
        return PodcastFragment.newInstance(podcast.getCoverImageURL(),
                podcast.getTitle(),
                podcast.getCreator(),
                podcast.getDate(),
                podcast.getDuration(),
                podcast.getTimesConsumed(),
                podcast.getDescription(),
                podcast.getUrl(),
                position
        );
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

}
