package com.agromarket.ampl_chat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agromarket.ampl_chat.R;
import com.agromarket.ampl_chat.models.MessageItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<MessageItem> list;

    private static final int VIEW_SENT_TEXT = 1;
    private static final int VIEW_SENT_IMAGE = 2;
    private static final int VIEW_RECEIVED_TEXT = 3;
    private static final int VIEW_RECEIVED_IMAGE = 4;

    public ChatMessageAdapter(Context context, List<MessageItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        MessageItem item = list.get(position);
        boolean isSent = item.isSent;

        if (isSent) {
            return item.type == MessageItem.TYPE_TEXT ? VIEW_SENT_TEXT : VIEW_SENT_IMAGE;
        } else {
            return item.type == MessageItem.TYPE_TEXT ? VIEW_RECEIVED_TEXT : VIEW_RECEIVED_IMAGE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == VIEW_SENT_TEXT) {
            View v = inflater.inflate(R.layout.row_sent_text, parent, false);
            return new SentTextHolder(v);
        }

        if (viewType == VIEW_SENT_IMAGE) {
            View v = inflater.inflate(R.layout.row_sent_image, parent, false);
            return new SentImageHolder(v);
        }

        if (viewType == VIEW_RECEIVED_TEXT) {
            View v = inflater.inflate(R.layout.row_received_text, parent, false);
            return new ReceivedTextHolder(v);
        }

        View v = inflater.inflate(R.layout.row_received_image, parent, false);
        return new ReceivedImageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageItem item = list.get(position);

        if (holder instanceof SentTextHolder) {
            ((SentTextHolder) holder).msg.setText(item.text);
        } else if (holder instanceof SentImageHolder) {
            SentImageHolder h = (SentImageHolder) holder;
            Glide.with(context)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.ic_product_placeholder)
                    .into(h.img);
        } else if (holder instanceof ReceivedTextHolder) {
            ((ReceivedTextHolder) holder).msg.setText(item.text);
        } else if (holder instanceof ReceivedImageHolder) {
            ReceivedImageHolder h = (ReceivedImageHolder) holder;
            Glide.with(context)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.ic_product_placeholder)
                    .into(h.img);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // ---------- VIEW HOLDERS ----------

    static class SentTextHolder extends RecyclerView.ViewHolder {
        TextView msg;
        SentTextHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.textMessage);
        }
    }

    static class SentImageHolder extends RecyclerView.ViewHolder {
        ImageView img;
        SentImageHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageMessage);
        }
    }

    static class ReceivedTextHolder extends RecyclerView.ViewHolder {
        TextView msg;
        ReceivedTextHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.textMessage);
        }
    }

    static class ReceivedImageHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ReceivedImageHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageMessage);
        }
    }
}