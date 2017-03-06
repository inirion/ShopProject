package com.example.grzegorzkokoszka.shopproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Grzegorz Kokoszka on 2016-12-28.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private int fragmentId;
    private List<Product> productList = Collections.emptyList();
    public ProductAdapter(Context ctx, List<Product> productList,int id){
        inflater=LayoutInflater.from(ctx);
        this.productList = productList;
        context = ctx;
        fragmentId = id;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_row_guest,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Product currentObj = productList.get(position);
        holder.name.setText(currentObj.getName());
        if(fragmentId == 1) {
            if (User.getId().equals(currentObj.getHighestBitterId()))
                holder.name.setTextColor(Color.GREEN);
            else
                holder.name.setTextColor(Color.RED);
        }
        if(fragmentId == 2) {
            if (currentObj.isFinished())
                holder.name.setTextColor(Color.GREEN);
        }
        holder.description.setText(currentObj.getDescription().substring(0, Math.min(currentObj.getDescription().length(), 150))+"...");
        holder.time.setText("Czas do końca : " + currentObj.getTime());
        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(holder.image);
        Picasso
                .with(context)
                .load("http://i.imgur.com/DvpvklR.png")
                .resize(300, 300) // resizes the image to these dimensions (in pixel). does not respect aspect ratio
                .into(holder.image);
        holder.price.setText("Cena : " + Float.toString(currentObj.getPrice())+ " zł");
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Item Clicked at position" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(inflater.getContext(),ProductDetails.class);
                intent.putExtra("name",currentObj.getName());
                intent.putExtra("description",currentObj.getDescription());
                intent.putExtra("image",currentObj.getPicture());
                intent.putExtra("price","Cena : " + Float.toString(currentObj.getPrice())+ " zł");
                intent.putExtra("author",currentObj.getAuthorId());
                intent.putExtra("auction",currentObj.getAuctionId());
                intent.putExtra("highestBitId",currentObj.getHighestBitterId());
                intent.putExtra("time",currentObj.getTime());
                inflater.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView description;
        TextView price;
        TextView time;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.DiscText);
            name = (TextView) itemView.findViewById(R.id.ProductName);
            price = (TextView) itemView.findViewById(R.id.Price);
            image = (ImageView) itemView.findViewById(R.id.ProductImage);
            time = (TextView)itemView.findViewById(R.id.CzasDoKonca);
        }
    }
}
