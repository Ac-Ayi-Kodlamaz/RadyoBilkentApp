package com.example.radyobilkentandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;

public class ProgramFlowRecyclerViewAdapter extends RecyclerView.Adapter<ProgramFlowRecyclerViewAdapter.ViewHolder> {

    // each array holds {String programName, String programTime, Bitmap programImage}
    private Object[][] localDataSet;

    public ProgramFlowRecyclerViewAdapter () {
    }

    public ProgramFlowRecyclerViewAdapter(Object[][] dataSet) {
        localDataSet = dataSet;
    }


    /**
     * RecyclerView calls this method whenever it needs to create a new ViewHolder.
     * The method creates and initializes the ViewHolder and its associated View,
     * but does not fill in the view's contents—the ViewHolder has not yet been
     * bound to specific data.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program_item, parent, false);

        return new ViewHolder(view);
    }

    /**
     * RecyclerView calls this method to associate a ViewHolder with data. The method
     * fetches the appropriate data and uses the data to fill in the view holder's
     * layout. For example, if the RecyclerView displays a list of names, the method
     * might find the appropriate name in the list and fill in the view holder's
     * TextView widget.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getProgramName().setText((String) localDataSet[position][0]);
        holder.getProgramTime().setText((String) localDataSet[position][1]);
        holder.getProgramExplanation().setText((String) localDataSet[position][2]);
        new DownloadImageTask(holder.getProgramImage()).execute((String) localDataSet[position][3]);
    }

    /**
     * RecyclerView calls this method to get the size of the data set. For example,
     * in an address book app, this might be the total number of addresses.
     * RecyclerView uses this to determine when there are no more items that can
     * be displayed.
     * @return the number of items
     */
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView programName;
        private TextView programTime;
        private TextView programExplanation;
        private ImageView programImage;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            programName = view.findViewById(R.id.program_name_text);
            programTime = view.findViewById(R.id.program_time_text);
            programExplanation = view.findViewById(R.id.program_explanation_text);
            programImage = view.findViewById(R.id.program_image);
            programImage.setImageResource(R.color.teal_700);
        }

        public TextView getProgramName() {
            return programName;
        }

        public TextView getProgramTime() {
            return programTime;
        }

        public TextView getProgramExplanation() {
            return programExplanation;
        }

        public ImageView getProgramImage() {
            return programImage;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView bmImage) {
            this.imageView = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
