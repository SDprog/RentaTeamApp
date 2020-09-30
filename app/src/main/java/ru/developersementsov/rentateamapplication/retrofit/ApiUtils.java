package ru.developersementsov.rentateamapplication.retrofit;

public class ApiUtils {
    public static final String BASE_URL = "https://reqres.in/";

    public static UsersApi getUsersApi() {
        return RetrofitBuilder.getClient(BASE_URL).create(UsersApi.class);
    }
}
