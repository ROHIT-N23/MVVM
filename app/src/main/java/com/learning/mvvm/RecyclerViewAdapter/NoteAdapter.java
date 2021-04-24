package com.learning.mvvm.RecyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mvvm.R;
import com.learning.mvvm.Room.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> allNotes = new ArrayList<>();

    private OnNoteClickListener listener;

    public void setAllNotes(List<Note> allNotes) {
        this.allNotes = allNotes;
        notifyDataSetChanged();
    }


    // Passing Note Position //
    public Note getNoteAt(int position){
        return  allNotes.get(position);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_items, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = allNotes.get(position);

        holder.textview_title.setText(note.getTitle());
        holder.textview_description.setText(note.getDescription());
        holder.textview_priority.setText(note.getPriority() + "");

    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView textview_title, textview_description, textview_priority;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            textview_title = itemView.findViewById(R.id.text_view_title);
            textview_description = itemView.findViewById(R.id.text_view_description);
            textview_priority = itemView.findViewById(R.id.text_view_priority);

            // On Note Click passing Note Which was  Clicked //
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onNoteClick(allNotes.get(position));
                    }
                }
            });

        }

    }


    // Creating Own Listeners To Pass Clicked Note  //
    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public void setOnNoteClick(OnNoteClickListener listener) {
        this.listener = listener;
    }
}
