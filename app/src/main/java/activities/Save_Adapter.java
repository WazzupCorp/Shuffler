package activities;
import android.content.Context;

import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import wazzup.shuffler.R;

import static android.content.Context.MODE_PRIVATE;


public class Save_Adapter extends RecyclerView.Adapter<Save_Adapter.ViewHolder> {

    private ArrayList<String> preferencesArray ;
    private onItemClickListener mListener;
    public Context mContext;
    Save_Adapter(Context context, ArrayList p)
    {
         mContext = context;
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
    public Save_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent, false);
        return new ViewHolder(view , mListener, mContext);
    }


    @Override
    public void onBindViewHolder(Save_Adapter.ViewHolder holder, int position) {
        holder.text.setText(preferencesArray.get(position));
        //String currentName = preferencesArray.get(position);


    }




    @Override
    public int getItemCount() {
        return preferencesArray.size();
    }

    public void removeItem(int position)
    {
        preferencesArray.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(String item , int position)
    {
        preferencesArray.add(position, item);
        notifyItemInserted(position);
    }

    //ViewHolder Class
    public static class ViewHolder extends  RecyclerView.ViewHolder {
        public View viewForeground;
        public View viewBackground;
        public CardView nameCard;
        public TextView text;
        public Context context;
        private boolean THEME_MODE ;

        ViewHolder(final View itemView, final onItemClickListener listener, Context vContext)
        {
            super(itemView);
            nameCard = itemView.findViewById(R.id.saveCard);
            text = itemView.findViewById(R.id.recycleritem);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            context = vContext;

            loadThemeMode();
            if(THEME_MODE) {
                viewForeground.setBackgroundColor(context.getResources().getColor(R.color.darkergrey));
                text.setTextColor(context.getResources().getColor(R.color.white));
            }
            else
                {
                    viewForeground.setBackgroundColor(context.getResources().getColor(R.color.white));
                    text.setTextColor(context.getResources().getColor(R.color.black));
            }


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


        public void loadThemeMode()
        {
            String THEME = "themes";
            SharedPreferences sharedPref = context.getSharedPreferences(THEME,MODE_PRIVATE);
            String THEME_KEY = "THEME";
            THEME_MODE = sharedPref.getBoolean(THEME_KEY,true);
        }

    }





}
