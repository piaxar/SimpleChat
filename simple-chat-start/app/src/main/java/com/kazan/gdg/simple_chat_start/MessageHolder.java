package com.kazan.gdg.simple_chat_start;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * @author ravil
 */

public class MessageHolder extends RecyclerView.ViewHolder {

    TextView mName;

    TextView mText;

    public MessageHolder(View itemView) {
        super(itemView);

        mName = (TextView) itemView.findViewById(R.id.sender_name);
        mText = (TextView) itemView.findViewById(R.id.message_text);
    }
}
