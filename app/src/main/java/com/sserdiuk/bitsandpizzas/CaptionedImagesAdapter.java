package com.sserdiuk.bitsandpizzas;


import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter class for card_captioned_image in material design
 * Adaptation classes for material design
 *
 * Created by sserdiuk on 3/7/18.
 */

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    private String[] captions;
    private int[] imageIds;

    public CaptionedImagesAdapter(String[] captions, int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        /**
         * In our component RecyclerView should be displaed cards
         * thats why we set vewHoler contain layout cardView.
         * If you want display in RecyclerView data of another type,
         * identify it here
         * */
        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);

        return new ViewHolder(cv);
    }

    /**
     * Метод onBindViewHolder() вызывается каждый раз,
     * когда компоненту RecyclerView потребуется вывести данные в ViewHolder.
     * Метод получает два параметра: объект ViewHolder, с которым должны быть связаны данные,
     * и позиция связываемых данных в наборе. Итак, карточку требуется заполнить данными.
     * Карточка содержит два представления:
     * графическое представление с идентификатором info_image
     * и надпись с идентификатором info_text.
     * Мы заполним их данными из массивов captions и imageIds.
     * */
    @Override
    public void onBindViewHolder(CaptionedImagesAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
        Drawable drawable  = cardView.getResources().getDrawable(imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);
    }

    @Override
    public int getItemCount() {
        return captions.length;
    }
}
