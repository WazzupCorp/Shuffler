package wazzup.shuffler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import wazzup.shuffler.R;


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
        holder.text.setText(preferencesArray.get(position));
        //String currentName = preferencesArray.get(position);

    }

    @Override
    public int getItemCount() {
        return preferencesArray.size();
    }


    //ViewHolder Class
    public static class ViewHolder extends  RecyclerView.ViewHolder {

        protected TextView text;



        ViewHolder(final View itemView, final onItemClickListener listener)
        {
            super(itemView);
            text = itemView.findViewById(R.id.groupItem);



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

           ;



        }


    }
}
