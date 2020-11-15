package com.example.practicapmdm.apiRest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import com.example.practicapmdm.constants.Constants;
import com.example.practicapmdm.domain.JsonResponse;

public interface ApiLocationMadridData {
    @Headers({"Accept: application/json' 'https://datos.madrid.es/egob/catalogo/210227-0-piscinas-publicas.json"})
    @GET(Constants.END_POINT )
    Call <JsonResponse> getPools(@Query("latitude") Double latitude, @Query("longitude") Double longitude, @Query("distance") int distance);
}
