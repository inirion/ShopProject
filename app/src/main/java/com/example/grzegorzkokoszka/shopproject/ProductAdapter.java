package com.example.grzegorzkokoszka.shopproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Grzegorz Kokoszka on 2016-12-28.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<Product> productList = Collections.emptyList();
    public ProductAdapter(Context ctx, List<Product> productList){
        inflater=LayoutInflater.from(ctx);
        this.productList = productList;
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
        holder.description.setText(currentObj.getDescription());
        holder.image.setImageResource(currentObj.getPicture());
        holder.price.setText(Float.toString(currentObj.getPrice()));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Item Clicked at position" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(inflater.getContext(),ProductDetails.class);
                intent.putExtra("name",currentObj.getName());
                intent.putExtra("description",currentObj.getDescription());
                intent.putExtra("image",currentObj.getPicture());
                intent.putExtra("price",Float.toString(currentObj.getPrice()));
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
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.DiscText);
            name = (TextView) itemView.findViewById(R.id.ProductName);
            price = (TextView) itemView.findViewById(R.id.Price);
            image = (ImageView) itemView.findViewById(R.id.ProductImage);
        }
    }
}
