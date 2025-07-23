package ir.gh.traveling2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<Trip> tripList;

    public TripAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.tvDestination.setText(trip.destination);
        holder.tvDates.setText(trip.startDate + " - " + trip.endDate);
        holder.tvBudget.setText("Budget: " + trip.budget);
        holder.tvNotes.setText(trip.notes);
        holder.tvReminder.setText(trip.reminder ? "Reminder: Yes" : "Reminder: No");
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {

        TextView tvDestination, tvDates, tvBudget, tvNotes, tvReminder;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvDates = itemView.findViewById(R.id.tvDates);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            tvReminder = itemView.findViewById(R.id.tvReminder);
        }
    }
}
