package ru.developersementsov.rentateamapplication.retrofit;

import retrofit2.http.GET;
import ru.developersementsov.rentateamapplication.model.AnswersResponse;
import rx.Observable;

public interface UsersApi {
    @GET("api/users")
    Observable<AnswersResponse> getAnswers();
}
