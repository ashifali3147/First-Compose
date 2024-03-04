package com.example.firstcomposeapp

import androidx.compose.runtime.Stable

@Stable
class CategoryItem {
    var id = 0
    var name = ""
    var category_type = 0
    var image_url = ""
    var category_list: MutableList<CategoryItem> = mutableListOf()
    var parentCateGoryName = ""
    var supercatId = 0
    var categoryId = 0
    var subcategory = 0
    var isOpen = false
}
