package com.example.firstcomposeapp.model

class MenuResponseModel {
    var item_list: MutableList<MenuItemModel> = mutableListOf()

//    var exceptions: MutableList<TimeSlotModel> = mutableListOf()
    var current_outlet_time = ""
    var total_page = 0
    //sectional menu

    var current_page = 0
}
