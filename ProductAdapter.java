package com.example.myapplication.t6_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class ProductAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> mProductList;

    public ProductAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderT6_1 holder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_view_t6_1,parent,false);
            holder=new ViewHolderT6_1();
            holder.imageView=convertView.findViewById(R.id.item_product_imageView);
            holder.styleTv=convertView.findViewById(R.id.item_styleid_textview);
            holder.brandTv=convertView.findViewById(R.id.item_brand_textview);
            holder.priceTv=convertView.findViewById(R.id.item_price_textview);
            holder.additionalInfoTv=convertView.findViewById(R.id.item_additionInfo_textview);

            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolderT6_1) convertView.getTag();
        }
        Product product=mProductList.get(position);
        if(product!=null){
            Picasso.get().load(product.getSearchImage()).into(holder.imageView);
            holder.additionalInfoTv.setText(product.getAdditionalInfo());
            holder.brandTv.setText(product.getBrand());
            holder.priceTv.setText(product.getPrice());
            holder.styleTv.setText(product.getStyleId());
        }
        return convertView;
    }
    static class ViewHolderT6_1{
        ImageView imageView;
        TextView styleTv,brandTv,priceTv, additionalInfoTv;
   }
}






