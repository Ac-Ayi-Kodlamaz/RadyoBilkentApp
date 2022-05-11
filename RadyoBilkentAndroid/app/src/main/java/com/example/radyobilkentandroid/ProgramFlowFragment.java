package com.example.radyobilkentandroid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class ProgramFlowFragment extends Fragment {

    private ViewPager2 pager;
    private Object[][] programData;
    private CollectionReference mProgramReference;

    public ProgramFlowFragment() {
        // Required empty public constructor
    }


    public static ProgramFlowFragment newInstance() {
        ProgramFlowFragment fragment = new ProgramFlowFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_program_flow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgramReference = MainActivity.mDB.collection("programms");
        mProgramReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful()) {
                    programData = new Object[task.getResult().size()][4];
                    for (int i = 0; i < task.getResult().size(); i++) {
                        Map<String, Object> map = task.getResult().getDocuments().get(i).getData();
                        programData[i][0] = (String) map.get("title");
                        programData[i][1] = (String) map.get("time");
                        programData[i][2] = (String) map.get("explanation");
                        programData[i][3] = (String) map.get("image");
                    }
                }
                else {
                    programData = new Object[0][0];
                    Log.d("PROGRAM_FLOW: ", "Could not receive program flow info");
                    Toast.makeText(getContext(), "Could not receive program flow info", Toast.LENGTH_SHORT).show();
                }
                updatePager(view);
            }
        });

    }

    private void updatePager(View view){
        pager = view.findViewById(R.id.program_flow_pager);
// ulas here. I had to convert this piece of code from kotlin to java

        // MyRecyclerViewAdapter is an standard RecyclerView.Adapter :)
        pager.setAdapter(new ProgramFlowRecyclerViewAdapter(programData));
        // You need to retain one page on each side so that the next and previous items are visible
//        pager.setOffscreenPageLimit(1);

        // Add a PageTransformer that translates the next and previous items horizontally
        // towards the center of the screen, which makes them visible

//        float nextItemVisibleDp = getResources().getDimension(R.dimen.program_flow_next_item_visible);
//        float currentItemHorizontalMarginDp = getResources().getDimension(R.dimen.program_flow_current_item_horizontal_margin);

//        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
//        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)

//        float pageTranslationX = nextItemVisibleDp + currentItemHorizontalMarginDp;

//        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx

//        ViewPager2.PageTransformer pageTransformer = new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                page.setTranslationX(-pageTranslationX * position);
//                page.setScaleY(1 - (0.25f * Math.abs(position)));
//                // page.setAlpha(0.25f + (1 - Math.abs(position)));
//            }
//        };
//        pager.setPageTransformer(pageTransformer);

//        val pageTransformer = pager.PageTransformer { page: View, position: Float ->
//                page.translationX = -pageTranslationX * position
        // Next line scales the item's height. You can remove it if you don't want this effect
//            page.scaleY = 1 - (0.25f * abs(position))
        // If you want a fading effect uncomment the next line:
        // page.alpha = 0.25f + (1 - abs(position))
//        }
//        pager.setPageTransformer(pageTransformer)



//        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
//        itemDecoration.setDrawable(getContext().getDrawable(R.drawable.pager_divider));
//        pager.addItemDecoration(itemDecoration);


        // The ItemDecoration gives the current (centered) item horizontal margin so that
        // it doesn't occupy the whole screen width. Without it the items overlap
//        val itemDecoration = HorizontalMarginItemDecoration(
//                context,
//                R.dimen.viewpager_current_item_horizontal_margin
//        )
//        pager.addItemDecoration(itemDecoration)
    }


}