package wazzup.shuffler;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.TextView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

import wazzup.shuffler.R;

import static activities.GruppenActivity.ShuffledGruppe;


public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private ArrayList<String> preferencesArray ;
    private onItemClickListener mListener;

    GroupAdapter(Context context, ArrayList p)
    {
        Context mContext = context;
        this.preferencesArray=p;
    }

    void updateList(ArrayList<String> preferencesArray)
    {
        this.preferencesArray= preferencesArray;
        notifyDataSetChanged();
    }

    public interface onItemClickListener
    {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    void setOnItemClickListener(onItemClickListener listener)
    {
        mListener = listener;
    }


    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_groupitem,parent, false);
        return new ViewHolder(view , mListener);
    }


    @Override
    public void onBindViewHolder(GroupAdapter.ViewHolder holder, int position) {
        holder.groupItems.setText(preferencesArray.get(position));
        holder.groupNumber.setText(String.valueOf(position+1));
        //String currentName = preferencesArray.get(position);

    }

    @Override
    public int getItemCount() {
        return preferencesArray.size();
    }


    //ViewHolder Class
    public static class ViewHolder extends  RecyclerView.ViewHolder {

        protected TextView groupNumber;
        protected FrameLayout groupNumberLayout;
        protected TextView groupItems;
        protected FrameLayout groupItemsLayout;

        ViewHolder(final View itemView, final onItemClickListener listener)
        {
            super(itemView);
            groupNumber = itemView.findViewById(R.id.groupItemNumber);
            groupItems = itemView.findViewById(R.id.groupItem);
            groupNumberLayout = itemView.findViewById(R.id.groupNumberFrame);
            groupItemsLayout = itemView.findViewById(R.id.groupItemFrame);
            Random randomColor = new Random();
            int color = Color.argb(255,randomColor.nextInt(256),randomColor.nextInt(256),randomColor.nextInt(256));
            groupItemsLayout.setBackgroundColor(color);

            //Dunklere Farbe für Gruppennummer Layout
            float[] hsv = new float[3];
            Color.colorToHSV(color,hsv);
            hsv[2] *= 0.8f;
            color = Color.HSVToColor(hsv);
            groupNumberLayout.setBackgroundColor(color);


            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(listener !=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });





        }


    }
}
