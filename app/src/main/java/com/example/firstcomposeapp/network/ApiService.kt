package com.example.firstcomposeapp.network

import com.example.firstcomposeapp.model.BaseModel
import com.example.firstcomposeapp.model.CategoryItem
import com.example.firstcomposeapp.model.MenuResponseModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("api/v5/whitelabel/getMenuItemListingTagFilter")
    suspend fun getMenuItemListing(
        @Field("merchant_id") merchant_id: String,
        @Field("outlet_id") outlet_id: String,
        @Field("section_id") section_id: String,
        @Field("supercategory_id") supercategory_id: String,
        @Field("category_id") category_id: String,
        @Field("subcategory_id") subcategory_id: String,
        @Field("microcategory_id") microcategory_id: String,
        @Field("current_page") current_page: Int,
        @Field("page_size") page_size: Int,
        @Field("start_price") start_price: String,
        @Field("end_price") end_price: String,
        @Field("name") name: String,
        @Field("sorting_value") sorting_value: String,
        @Field("sorting_action") sorting_action: String,
        @Field("tag_category_id") tag_name_id: String,
        @Field("item_type") item_type: String
    ): Response<BaseModel<MenuResponseModel>>

    @FormUrlEncoded
    @POST("api/v5/whitelabel/getMenuCategoryListing")
    suspend fun getMenuCategoryListing(
        @Field("merchant_id") merchant_id: String?,
        @Field("outlet_id") outlet_id: Int,
        @Field("section_id") section_id: Int
    ): Response<BaseModel<CategoryItem>>
}