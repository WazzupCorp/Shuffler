package wazzup.shuffler;


import android.support.v7.widget.RecyclerView;

public interface SwipeControllerItemTouchHelperListener {

    void onSwiped(RecyclerView.ViewHolder viewHolder,int direction , int position);
}
