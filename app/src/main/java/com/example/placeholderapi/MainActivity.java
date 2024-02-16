package com.example.placeholderapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PostAdapter adapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();
        adapter = new PostAdapter(postList);
        recyclerView.setAdapter(adapter);

        fetchPosts();
    }

    private void fetchPosts() {
        String url = "https://jsonplaceholder.typicode.com/posts";

        // Create a JSON array request using Volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parse JSON response and populate postList
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                String body = jsonObject.getString("body");
                                Post post = new Post(id, title, body);
                                postList.add(post);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged(); // Notify adapter of data change
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the JSON array request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}

//    private void fetchPosts() {
//        String url = "https://jsonplaceholder.typicode.com/posts";
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // Parse JSON response and populate postList
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject jsonObject = response.getJSONObject(i);
//                                int id = jsonObject.getInt("id");
//                                String title = jsonObject.getString("title");
//                                String body = jsonObject.getString("body");
//                                Post post = new Post(id, title, body);
//                                postList.add(post);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Volley.newRequestQueue(this).add(jsonArrayRequest);
//    }

