package akshay.kapase.tvsautomobile.api;

import com.google.gson.JsonObject;

import akshay.kapase.tvsautomobile.models.Admin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TvsApi {

    @POST("gettabledata.php")
    Call<JsonObject> loginUser(@Body Admin admin);

}
