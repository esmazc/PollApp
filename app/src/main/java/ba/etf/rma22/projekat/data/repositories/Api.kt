package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("/anketa/{id}/pitanja")
    suspend fun getPollQuestions(
        @Path("id") id: Int
    ): Response<List<Pitanje>>

    @POST("/student/{id}/anketa/{kid}")
    suspend fun startPoll(
        @Path("id") id: String,
        @Path("kid") kid: Int,
        //@Body anketaTaken: AnketaTaken
    ): Response<AnketaTaken?>

    @GET("/student/{id}/anketataken")
    suspend fun getActivePolls(
        @Path("id") id: String
    ): Response<List<AnketaTaken>?>

    @GET("/student/{id}/anketataken/{ktid}/odgovori")
    suspend fun getPollAnswers(
        @Path("id") id: String,
        @Path("ktid") ktid: Int
    ): Response<List<OdgovorResponse>?>

    @POST("/student/{id}/anketataken/{ktid}/odgovor")
    suspend fun setAnswer(
        @Path("id") id: String,
        @Path("ktid") ktid: Int,
        @Body odgovor: SetAnswerBody
    ): Response<OdgovorResponse>

    @GET("/anketa")
    suspend fun getPolls(
        @Query("offset") offset: Int
    ): Response<List<Anketa>?>

    @GET("/anketa/{id}")
    suspend fun getPollById(
        @Path("id") id: Int
    ): Response<Anketa?>

    @GET("/grupa/{id}/ankete")
    suspend fun getPollsByGroup(
        @Path("id") id: Int
    ): Response<List<Anketa>?>

    @GET("/istrazivanje")
    suspend fun getResearches(
        @Query("offset") offset: Int
    ): Response<List<Istrazivanje>?>

    @GET("/grupa")
    suspend fun getGroups(
    ): Response<List<Grupa>?>

    @GET("/istrazivanje/{id}")
    suspend fun getResearcheById(
        @Path("id") id: Int
    ): Response<Istrazivanje?>

    @POST("/grupa/{gid}/student/{id}")
    suspend fun enrollGroup(
        @Path("gid") gid: Int,
        @Path("id") id: String
    ): Response<EnrollGroupResponse>

    @GET("/student/{id}/grupa")
    suspend fun getEnrolledGroups(
        @Path("id") id: String
    ): Response<List<Grupa>?>

    @GET("/grupa/{gid}/istrazivanje")
    suspend fun getResearcheByGroup(
        @Path("gid") gid: Int
    ): Response<Istrazivanje>

    @GET("/anketa/{id}/grupa")
    suspend fun getGroupsForPoll(
        @Path("id") id: Int
    ): Response<List<Grupa>>

    @DELETE("/student/{id}/upisugrupeipokusaji")
    suspend fun delete(
        @Path("id") id: String
    ): Response<String>

    @GET("/student/{id}")
    suspend fun getAccount(
        @Path("id") id: String
    ): Response<Account>
}