package com.example.myapplication.t6_1;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.Buffer;

public class t6_1MainActivity extends AppCompatActivity {
    private ListView ListView;
    private ProductAdapter adapter;
    private List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_t61_main);
        ListView=findViewById(R.id.t6_1listview);
        adapter=new ProductAdapter(this,productList);
        ListView.setAdapter(adapter);
        new FetchProductsTask.execute();
    }
    private class FetchProductsTask extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder response=new StringBuilder();
            try{
                URL url=new URL("http://hungnttg.github.io/shopgiay.json");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                Buffer reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line="";
                while ((line=reader.readLine())!=null){
                    response.append(line);
                }
                reader.close();
            } catch (MalformedURLException e){
                throw new RuntimeException(e);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s!=null && !s.isEmpty()){
                try {
                    JSONObject json=new JSONObject(s);
                    JSONArray productsArray=json.getJSONArray("products");
                    for(int i=0;i<productsArray.length();i++){
                        JSONObject productObject=productsArray.getJSONObject(i);
                        String styleID=productObject.getString("styleid");
                        String brand =productObject.getString("brand_filter_facet");
                        String price=productObject.getString("price");
                        String additionalInfo=productObject.getString("product_additional_info");
                        String searchImage=productObject.getString("search_image");
                        Product product=new Product(styleID,brand,price,additionalInfo,searchImage);
                        product.add(product);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }else {
                Toast.makeText((t6_1MainActivity.this,"Failed to fetfch prooducts!",Toast.LENGTH_LONG).show())
            }
        }
    }
}