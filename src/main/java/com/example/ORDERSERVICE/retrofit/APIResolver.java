package com.example.ORDERSERVICE.retrofit;

import com.example.ORDERSERVICE.dto.Product;
import com.example.ORDERSERVICE.dto.ResponseAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class APIResolver {

    private final ProductInterface productInterface;
    private final DiscoveryClient discoveryClient;
    private final AuthInterface authInterface;

    public APIResolver(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.authInterface = createRetrofit("AUTH-SERVICE").create(AuthInterface.class);
        productInterface = createRetrofit("PRODUCT-SERVICE").create(ProductInterface.class);
    }

    private Retrofit createRetrofit(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

        log.info(instances.get(0).toString());

        if (instances.isEmpty()) {
            throw new IllegalStateException("NO INSTANCE AVAILABLE FOR " + serviceName);
        }

        String baseUrl = instances.get(0).getUri().toString();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }




    public Product getProduct(UUID id) throws Exception {
        Call<Product> caller = productInterface.getProduct(id);
        Response<Product> product = caller.execute();

        if (product.isSuccessful()) {
            return product.body();
        }

        throw new Exception("PRODUCT DOES NOT EXIST");
    }


    public ResponseAuth verifyToken(String token) throws Exception {
        Call<ResponseAuth> caller = authInterface.verify(token);
        Response<ResponseAuth> response = caller.execute();

        if (response.isSuccessful()) {
            return response.body();
        }

        throw new Exception("INVALID TOKEN");
    }

}
