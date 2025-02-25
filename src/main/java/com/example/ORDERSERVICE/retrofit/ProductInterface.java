package com.example.ORDERSERVICE.retrofit;

import com.example.ORDERSERVICE.dto.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.UUID;

public interface ProductInterface {

    @GET("/api/v1/product/{id}")
    Call<Product> getProduct(@Path("id")UUID id);
}
