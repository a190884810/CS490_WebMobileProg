package com.themattburton.cs490.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchWordCardAdapter extends ArrayAdapter<SearchWordCardModel> {

    public SearchWordCardAdapter(@NonNull Context context) {
        super(context, R.layout.search_word_card_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.search_word_card_item, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SearchWordCardModel model = getItem(position);

        holder.searchWordTextView.setText(model.getSearchWord());
        if (model.isFound()) {
            holder.searchWordTextView.setBackgroundColor(getContext().getResources().getColor(R.color.colorCardFound));
            holder.searchWordTextView.setText(model.getSearchWord() + getContext().getResources().getString(R.string.card_found_phrase));
        }


        return convertView;
    }
    static class ViewHolder {
        TextView searchWordTextView;

        ViewHolder(View view) {
            searchWordTextView = view.findViewById(R.id.searchWordCardTextView);
        }
    }
}
