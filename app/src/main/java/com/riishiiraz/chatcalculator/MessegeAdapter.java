package com.riishiiraz.chatcalculator;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessegeAdapter extends RecyclerView.Adapter {

    private ArrayList<Messege> Messeges = new ArrayList<Messege>();

    private static final int TYPE_SENDER=1;
    private static final int TYPE_RECIEVER=2;

    public MessegeAdapter(ArrayList<Messege> messeges) {
        this.Messeges = messeges;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if(viewType==TYPE_SENDER)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_layout,parent , false);
            return new SentViewHolder(view);
        }

        else if(viewType == TYPE_RECIEVER)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciever_layout,parent , false);
            return new RecievedViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType())
        {
            case TYPE_SENDER:{
                Messege msg = Messeges.get(position);
                ((SentViewHolder)holder).bind(msg.getText() , msg.getTime());
                //((SentViewHolder)holder).bind("Some Messege","20:54");
                break;
            }

            case TYPE_RECIEVER:{
                Messege msg = Messeges.get(position);
                ((RecievedViewHolder)holder).bind(msg.getText() ,msg.getTime());
                //((RecievedViewHolder)holder).bind("Some Messege","20:54");
            }

        }
    }

    @Override
    public int getItemCount() {
        return Messeges.size();
    }

    @Override
    public int getItemViewType(int position) {
        String sender = Messeges.get(position).getSender();

        if(sender.equals("Me")){return TYPE_SENDER;}
        return TYPE_RECIEVER;
    }



    public class SentViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_send,tv_send_time;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_send = itemView.findViewById(R.id.tv_send);
            tv_send_time = itemView.findViewById(R.id.tv_send_time);
        }

        void bind(String msg , String time)
        {
            tv_send.setText(msg);
            tv_send_time.setText(time);
        }
    }

    public class RecievedViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_receive,tv_receive_time;

        public RecievedViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_receive = itemView.findViewById(R.id.tv_recieve);
            tv_receive_time = itemView.findViewById(R.id.tv_recieve_time);
        }

        void bind(String msg , String time)
        {
            tv_receive.setText(msg);
            tv_receive_time.setText(time);
        }
    }
}
