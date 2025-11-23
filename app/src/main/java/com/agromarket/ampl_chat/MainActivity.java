package com.agromarket.ampl_chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agromarket.ampl_chat.adapters.ChatAdapter;
import com.agromarket.ampl_chat.models.ChatItem;
import com.agromarket.ampl_chat.models.Customer;
import com.agromarket.ampl_chat.models.api.CustomerListResponse;
import com.agromarket.ampl_chat.utils.ApiClient;
import com.agromarket.ampl_chat.utils.ApiService;
import com.agromarket.ampl_chat.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ChatAdapter adapter;
    ArrayList<ChatItem> list;
    EditText searchEdit;
    TextView txtEmpty, txtNotFound;
    ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    v.getPaddingLeft(),
                    systemBars.top,
                    v.getPaddingRight(),
                    systemBars.bottom
            );
            return insets;
        });

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> performLogout());

        recyclerView = findViewById(R.id.recyclerChats);
        searchEdit = findViewById(R.id.searchEdit);
        txtEmpty = findViewById(R.id.txtEmpty);
        txtNotFound = findViewById(R.id.txtNotFound);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new ChatAdapter(this, list);
        recyclerView.setAdapter(adapter);

        // Listen to search results from adapter
        adapter.setOnSearchResultListener(count -> {
            String query = searchEdit.getText().toString();

            if (query.isEmpty()) {
                txtNotFound.setVisibility(View.GONE);

                if (list.isEmpty()) {
                    txtEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    txtEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

            } else {
                if (count == 0) {
                    recyclerView.setVisibility(View.GONE);
                    txtNotFound.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    txtNotFound.setVisibility(View.GONE);
                }
            }
        });

        loadCustomers();
        setupSearch();
    }

    private void loadCustomers() {
        SessionManager session = new SessionManager(this);
        String token = session.getToken();

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.getAssignedCustomers("Bearer " + token)
                .enqueue(new Callback<CustomerListResponse>() {
                    @Override
                    public void onResponse(Call<CustomerListResponse> call, Response<CustomerListResponse> response) {

                        if (!response.isSuccessful() || response.body() == null) {
                            Toast.makeText(MainActivity.this, "Failed to load customers", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ArrayList<ChatItem> result = new ArrayList<>();

                        for (Customer c : response.body().customers) {
                            result.add(new ChatItem(c.id, c.name, c.email));
                        }

                        if (result.isEmpty()) {
                            txtEmpty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            txtEmpty.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        list.clear();
                        list.addAll(result);
                        adapter.updateList(result);
                    }

                    @Override
                    public void onFailure(Call<CustomerListResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupSearch() {
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                adapter.filter(s.toString());
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void performLogout() {
        SessionManager session = new SessionManager(this);
        String token = session.getToken();

        if (token == null) {
            goToLogin();
            return;
        }

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.logout("Bearer " + token)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        // Always log out locally
                        session.clear();
                        Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                        goToLogin();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        session.clear();
                        Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                        goToLogin();
                    }
                });
    }

    private void goToLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish(); // prevent return with back button
    }
}