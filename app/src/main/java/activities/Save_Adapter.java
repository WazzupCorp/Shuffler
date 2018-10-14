package activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wazzup.shuffler.R;


public class Save_Adapter extends RecyclerView.Adapter<Save_Adapter.ViewHolder> {
    private ArrayList<String> preferencesArray ;
    private Context mContext;
    private String currentName;
    private onItemClickListener mListener;
    public Save_Adapter(Context context ,ArrayList p)
    {
        this.mContext=context;
        this.preferencesArray=p;
    }

    public void updateList(ArrayList<String> preferencesArray)
    {
        this.preferencesArray= preferencesArray;
        notifyDataSetChanged();
    }

    public interface onItemClickListener
    {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener)
    {
    mListener = listener;
    }


    @Override
    public Save_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent, false);
        ViewHolder holder = new ViewHolder(view , mListener);
        return holder;
    }


    @Override
    public void onBindViewHolder(Save_Adapter.ViewHolder holder, int position) {
        holder.text.setText(preferencesArray.get(position));
        currentName = preferencesArray.get(position).toString();

    }

    @Override
    public int getItemCount() {
        return preferencesArray.size();
    }


    //ViewHolder Class
    public static class ViewHolder extends  RecyclerView.ViewHolder {

        protected TextView text;
        protected ImageView delete;


        public ViewHolder(final View itemView , final onItemClickListener listener)
        {
            super(itemView);
            text = itemView.findViewById(R.id.recycleritem);
            delete = itemView.findViewById(R.id.image_delete);


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

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener !=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });



        }


    }
}