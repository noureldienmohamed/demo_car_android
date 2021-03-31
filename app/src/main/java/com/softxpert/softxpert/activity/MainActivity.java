package com.softxpert.softxpert.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.softxpert.softxpert.R;
import com.softxpert.softxpert.WebService.ApiCallbacks;
import com.softxpert.softxpert.WebService.ApiClient;
import com.softxpert.softxpert.adapter.CarsAdapter;
import com.softxpert.softxpert.databinding.ActivityMainBinding;
import com.softxpert.softxpert.model.CarsListModel;
import com.softxpert.softxpert.utils.CheckInternetConnection;
import com.softxpert.softxpert.utils.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softxpert.softxpert.utils.PaginationScrollListener.PAGE_START;

public class MainActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener {

    int pageNum = 0;
    LinearLayoutManager manager;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private CarsAdapter carsAdapter;
    List<CarsListModel.Datum> carsListModels;
    private boolean check = false ;

    ActivityMainBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        carsListModels = new ArrayList<CarsListModel.Datum>();
        binding.carsRecycler.setHasFixedSize(true);
        binding.carsRecycler.setItemViewCacheSize(20);
        manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.carsRecycler.setLayoutManager(manager);
        carsAdapter = new CarsAdapter(carsListModels, MainActivity.this);
        carsAdapter.setHasStableIds(true);
        binding.carsRecycler.setAdapter(carsAdapter);

        getCarsData();
        if (!check) {


            binding.carsRecycler.addOnScrollListener(new PaginationScrollListener(manager) {
                @Override
                protected void loadMoreItems() {
                    isLoading = true;
                    currentPage++;
                    if (!check) {
                        getCarsData();
                    }
                }
                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }
            });
        }

    }
    public void getCarsData() {
        CheckInternetConnection connectionCheck = new CheckInternetConnection(MainActivity.this);

        if (connectionCheck.haveNetworkConnection()) {
            ApiCallbacks apiService =
                    ApiClient.getClient(MainActivity.this).create(ApiCallbacks.class);
            if (check) {

                return;
            } else {
                if (pageNum < 1) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
                pageNum = pageNum + 1;
                Call<CarsListModel> call = apiService.getCarsData("cars?page=" + pageNum );
                call.enqueue(new Callback<CarsListModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<CarsListModel> call, Response<CarsListModel> response) {
                        Log.d("success", String.valueOf(response.body()));
                        binding.progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful()) {

                            assert response.body() != null;
                            if (response.body().getData() .isEmpty() && pageNum == 0) {
                                binding.carsRecycler.setVisibility(View.VISIBLE);

                            } else if (response.body().getData().isEmpty()) {
                                check = true;
                                isLoading = true;
                            } else {
                                isLoading = false;
                                binding.noCars.setVisibility(View.GONE);
                                binding.carsRecycler.getRecycledViewPool().setMaxRecycledViews(0, 5);
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.carsRecycler.getRecycledViewPool()
                                                .setMaxRecycledViews(0, 5);
                                    }
                                });

                                carsListModels.addAll(response.body().getData());
                                carsAdapter.notifyDataSetChanged();

                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CarsListModel> call, Throwable t) {
                        Toasty.warning(getApplicationContext(), getApplicationContext().getResources().getString(R.string.check_connection), Toast.LENGTH_LONG).show();
                        binding. noCars.setVisibility(View.VISIBLE);
                        binding. progressBar.setVisibility(View.GONE);

                    }
                });
            }
        }else{
            binding. noCars.setVisibility(View.VISIBLE);
            binding.noCarsImage.setImageResource(R.drawable.ic_internet);
            binding.carsText.setText(R.string.there_is_no_internet_connection);
            Toasty.warning(MainActivity.this, getResources().getString(R.string.check_connection), Toast.LENGTH_LONG).show();
            binding. progressBar.setVisibility(View.GONE);
            binding.carsRecycler.setVisibility(View.GONE);
            Toasty.warning(MainActivity.this, getResources().getString(R.string.check_connection), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        getCarsData();
    }
}