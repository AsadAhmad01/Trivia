package com.goally.assignment.data.dataModels

import androidx.core.text.HtmlCompat

class CategoryModel(
    var id: Int,
    var name: String
) {

    companion object {

        fun setValues(): ArrayList<CategoryModel> {
            val list: ArrayList<CategoryModel> = ArrayList()
            list.add(0, CategoryModel(0, "Any Category"))
            list.add(1, CategoryModel(9, "General Knowledge"))
            list.add(2, CategoryModel(10, "Entertainment: Books"))
            list.add(3, CategoryModel(11, "Entertainment: Film"))
            list.add(3, CategoryModel(12, "Entertainment: Music"))
            list.add(4, CategoryModel(13, decoder("Entertainment: Musicals &amp; Theatres")))
            list.add(5, CategoryModel(14, "Entertainment: Television"))
            list.add(6, CategoryModel(15, "Entertainment: Video Games"))
            list.add(7, CategoryModel(16, "Entertainment: Board Games"))
            list.add(8, CategoryModel(17, decoder("Science &amp; Nature")))
            list.add(9, CategoryModel(18, "Science: Computers"))
            list.add(10, CategoryModel(19, "Science: Mathematics"))
            list.add(11, CategoryModel(20, "Mythology"))
            list.add(12, CategoryModel(21, "Sports"))
            list.add(13, CategoryModel(22, "Geography"))
            list.add(14, CategoryModel(23, "History"))
            list.add(15, CategoryModel(24, "Politics"))
            list.add(16, CategoryModel(25, "Art"))
            list.add(17, CategoryModel(26, "Celebrities"))
            list.add(18, CategoryModel(27, "Animals"))
            list.add(19, CategoryModel(28, "Vehicles"))
            list.add(20, CategoryModel(29, "Entertainment: Comics"))
            list.add(21, CategoryModel(30, "Science: Gadgets"))
            list.add(22, CategoryModel(31, "Entertainment: Japanese Anime &amp; Manga"))
            list.add(23, CategoryModel(32, decoder("Entertainment: Cartoon &amp; Animations")))

            return list
        }


        fun decoder(value: String): String {

            return HtmlCompat
                .fromHtml(value, HtmlCompat.FROM_HTML_MODE_COMPACT)
                .toString()
        }
    }
}