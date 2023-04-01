package com.goally.assignment.domain.retrofit

import com.goally.assignment.data.dataModels.ResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {


    // Get QuickMode Questions
    @GET(URLS.quickModeQuestionEndpoint)
    suspend fun getQuickModeQuestions(): ResponseData

    @GET(URLS.normalModeQuestionEndpoint)
    suspend fun getNormalModeQuestions(
        @Query(value = "category") category: Int,
        @Query(value = "difficulty") difficulty: String,
        @Query(value = "type") type: String
    ): ResponseData
//
//    // Get Languages
//    @GET(URLS.getLanguages)
//    suspend fun getLanguages(): MDLanguage
//
//    // Login User
//    @POST(URLS.login)
//    suspend fun loginUser(
//        @Body data: JsonObject
//    ): MDUser
//
//    // Get User Profile
//
//    // Register User
//    @POST(URLS.register)
//    suspend fun registerUser(
//        @Body data: JsonObject
//    ): MDUser
//
//
//    // Client Home Data
//    @POST(URLS.getClientHomeData)
//    suspend fun clientHomeData(
//        @Body data: JsonObject
//    ): MDHomeData
//
//
//    // Get Banners
//    @GET(URLS.getBanners)
//    suspend fun getBannersData(
//        @Header("Authorization") header: String,
//        @Query(value = "userRole", encoded = true) userRole: Int,
//    ): BannerModel
//
//
//    @GET(URLS.refreshToken)
//    suspend fun refreshToken(
//        @Query(value = "token", encoded = true) token: String,
//    ): MDRefreshToken
//
//    @POST(URLS.getInfluencers)
//    suspend fun getInfluencers(
//        @Body data: JsonObject
//    ): MDInfluencers
//
//    @GET(URLS.getCategories)
//    suspend fun getCategories(): CategoryMD
//
//
////    @Raw
////    @POST(URLS.getTutorial)
////    fun getCommentAction(
////        @Body data: JsonObject
////    ): Call<CommentActionModel>
//
//
////    @FormUrlEncoded
////    @POST(URLS.login)
////    suspend fun loginUser(
////        @Field("email") email: String,
////        @Field("password") password: String,
////        @Field("userType") userType: String,
////        @Field("firebaseToken") firebaseToken: String,
////    ): RegisterModel
////
////    @FormUrlEncoded
////    @POST(URLS.login)
////    suspend fun loginWasher(
////        @Field("phoneNo") phoneNo: String,
////        @Field("password") password: String,
////        @Field("userType") userType: Int,
////        @Field("firebaseToken") firebaseToken: String,
////    ): RegisterModel
////
////    @FormUrlEncoded
////    @POST(URLS.forgotPassword)
////    suspend fun forgotPassword(
////        @Field("userEmail") userEmail: String
////    ): forgotPassModel
////
////    @FormUrlEncoded
////    @POST(URLS.updatePassword)
////    suspend fun updatePassword(
////        @Field("oldPass") oldPass: String,
////        @Field("newPass") newPass: String
////    ): forgotPassModel
////
////
////    @POST(URLS.logout)
////    suspend fun logout(): forgotPassModel
////
//////    @GET(URLS.getUserProfile)
//////    suspend fun getUserProfile(): RegisterModel
////
////
////    @GET(URLS.getPackages)
////    suspend fun getPackages(): PackagesModel
////
////    @GET(URLS.getVehicle)
////    suspend fun getVehicle(): VehicleModel
////
////    @FormUrlEncoded
////    @POST(URLS.register)
////    suspend fun register(
////        @Field("firstName") firstName: String,
////        @Field("lastName") lastName: String,
////        @Field("email") email: String,
////        @Field("phoneNo") phoneNo: String,
////        @Field("password") password: String,
////        @Field("userType") userType: String,
////        @Field("firebaseToken") firebaseToken: String,
////        @Field("address") address: String,
////    ): RegisterModel
////
////    @Multipart
////    @POST(URLS.addCoverImage)
////    suspend fun addCoverImage(
////        @Part userCoverImage: MultipartBody.Part
////    ): ProfileModel
////
////    @Multipart
////    @POST(URLS.editProfile)
////    suspend fun editProfileWithImage(
////        @Part("firstName") firstName: RequestBody,
////        @Part("lastName") lastName: RequestBody,
////        @Part("address") address: RequestBody,
////        @Part("phoneNo") phoneNo: RequestBody,
////        @Part part: MultipartBody.Part
////    ): ProfileModel
////
////
////    @FormUrlEncoded
////    @POST(URLS.editProfile)
////    suspend fun editProfile(
////        @Field("firstName") firstName: String,
////        @Field("lastName") lastName: String,
////        @Field("address") address: String,
////        @Field("phoneNo") phoneNo: String,
////    ): ProfileModel
////
////
//////    @FormUrlEncoded
//////    @POST(URLS.addAddress)
//////    suspend fun addAddress(
//////        @Field("address") address: String,
//////        @Field("isDefault") isDefault: Boolean
//////    ): RegisterModel
////
//////    @FormUrlEncoded
//////    @POST(URLS.deleteAddress)
//////    suspend fun deleteAddress(
//////        @Field("addressId") addressId: String
//////    ): RegisterModel
////
//////    @FormUrlEncoded
//////    @POST(URLS.editAddress)
//////    suspend fun editAddress(
//////        @Field("addressId") addressId: String,
//////        @Field("address") address: String,
//////        @Field("isDefault") isDefault: Boolean
//////    ): RegisterModel
////
////    @FormUrlEncoded
////    @POST(URLS.deleteVehicle)
////    suspend fun deleteVehicle(
////        @Field("vehicleId") vehicleId: String
////    ): VehicleModel
////
////    @Multipart
////    @POST(URLS.addVehicle)
////    suspend fun addVehicle(
////        @Part("vehicleModel") vehicleModel: RequestBody,
////        @Part("vehicleYear") vehicleYear: RequestBody,
////        @Part("vehicleColor") vehicleColor: RequestBody,
////        @Part("vehicleNumber") vehicleNumber: RequestBody,
////        @Part part: MultipartBody.Part
////    ): VehicleModel
////
////
////    @Multipart
////    @POST(URLS.jobDone)
////    suspend fun jobDone(
////        @Part("status") status: RequestBody,
////        @Part("orderId") orderId: RequestBody,
////        @Part("orderCompletedDateTime") orderCompletedDateTime:RequestBody,
////        @Part vehicleImage: MultipartBody.Part
////    ): UserCartModel
////
////
////    @POST(URLS.getAssignedVehicle)
////    suspend fun getAssignedVehicle(): UserOrderHistoryModel
////
////
//////    @FormUrlEncoded
//////    @POST(URLS.addService)
//////    suspend fun addService(
//////        @Field("service_name") service_name: String
//////    ): RegisterModel
////
//////    @FormUrlEncoded
//////    @POST(URLS.addPackage)
//////    suspend fun addPackage(
//////        @Field("addressId") addressId: String,
//////        @Field("address") address: String,
//////        @Field("isDefault") isDefault: Boolean
//////    ): RegisterModel
////
////    @FormUrlEncoded
////    @POST(URLS.otpGenerate)
////    suspend fun otpGenerate(
////        @Field("phoneNo") phoneNo: String
////    ): OTPModel
////
////
////    @FormUrlEncoded
////    @POST(URLS.otpVerification)
////    suspend fun otpVerification(
////        @Field("phoneNo") phoneNo: String,
////        @Field("code") code: String
////    ): OTPModel
////
////
////    @FormUrlEncoded
////    @POST(URLS.checkOut)
////    suspend fun checkOut(
////        @Field("vehicleId") vehicleId: String,
////        @Field("packageId") packageId: String,
////        @Field("additionalServicesId") additionalServicesId: String,
////        @Field("totalPrice") totalPrice: String,
////        @Field("orderDateTime") orderDateTime: String,
////    ): UserCartModel
////
////    @FormUrlEncoded
////    @POST(URLS.getVehicleHistory)
////    suspend fun getVehicleHistory(
////        @Field("userType") userType:String
////    ): UserOrderHistoryModel
////
}