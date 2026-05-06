package com.example.moviematch.presentation.UI.components

import com.example.moviematch.R

fun getPosterResId(posterName: String): Int {
    return when (posterName) {
        "interstellar" -> R.drawable.interstellar
        "the_devil_wears_prada" -> R.drawable.the_devil_wears_prada
        "wuthering_heights" -> R.drawable.wuthering_heights
        "the_gentlemen" -> R.drawable.the_gentlemen
        "the_green_mile" -> R.drawable.the_green_mile
        "intouchables" -> R.drawable.intouchables
        "sen_to_chihiro_no_kamikakushi" -> R.drawable.sen_to_chihiro_no_kamikakushi
        "the_godfather" -> R.drawable.the_godfather
        "coco" -> R.drawable.coco
        "shrek" -> R.drawable.shrek
        "five_element" -> R.drawable.five_element
        "matrix" -> R.drawable.matrix
        "avatar" -> R.drawable.avatar
        "now_you_see_me" -> R.drawable.now_you_see_me
        "knives_out" -> R.drawable.knives_out
        "requiem_for_a_dream" -> R.drawable.requiem_for_a_dream
        "hachiko" -> R.drawable.hachiko
        "parasite" -> R.drawable.parasite
        "koukaku_kidoutai" -> R.drawable.koukaku_kidoutai
        "ne_zha_zhi_mo_tong_nao_hai" -> R.drawable.ne_zha_zhi_mo_tong_nao_hai
        "taxi" -> R.drawable.taxi
        "how_to_train_your_dragon" -> R.drawable.how_to_train_your_dragon
        "zootopia" -> R.drawable.zootopia
        "la_la_land" -> R.drawable.la_la_land
        "the_notebook" -> R.drawable.the_notebook

        else -> R.drawable.ic_launcher_background
    }
}