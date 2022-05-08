package com.example.radyobilkentandroid;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RegisterFragmentAdapter extends FragmentStateAdapter {

    public RegisterFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return LoginFragment.newInstance();
        }
        return SignInFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
