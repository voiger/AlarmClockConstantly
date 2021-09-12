package com.example.alarmclockconstantly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private SortedList<ItemAlarmArrayList> sortedList;
    InterfaceMainActivityAdapter interfaceMainActivityAdapter;
    public AlarmAdapter(InterfaceMainActivityAdapter interfaceMainActivityAdapter) {
        this.interfaceMainActivityAdapter = interfaceMainActivityAdapter;
        sortedList = new SortedList<ItemAlarmArrayList>(ItemAlarmArrayList.class, new SortedList.Callback<ItemAlarmArrayList>() {
            @Override
            public int compare(ItemAlarmArrayList o1, ItemAlarmArrayList o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(ItemAlarmArrayList oldItem, ItemAlarmArrayList newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(ItemAlarmArrayList item1, ItemAlarmArrayList item2) {
                return false;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemRangeRemoved(fromPosition, toPosition);
            }
        });
    }

    public void setAlarm(List<ItemAlarmArrayList> item){
        sortedList.replaceAll(item);
    }


    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent , false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        ItemAlarmArrayList iaal = (ItemAlarmArrayList) sortedList.get(position);
        holder.timeEnd.setText(iaal.getEndTime());

    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder{

        TextView timeEnd;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            timeEnd = itemView.findViewById(R.id.textTimeAlarmEnd);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    sortedList.removeItemAt(getAdapterPosition());
                    interfaceMainActivityAdapter.saveList(sortedList);
                    return true;
                }
            });


        }

    }

}
