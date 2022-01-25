package com.example.happyplaces.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happyplaces.R
import com.example.happyplaces.activities.MainActivity.Companion.EXTRA_PLACE_DETAILS
import com.example.happyplaces.models.HappyPlaceModel
import kotlinx.android.synthetic.main.activity_happy_place_detail.*
import kotlinx.android.synthetic.main.activity_happy_place_detail.iv_place_image
import kotlinx.android.synthetic.main.item_happy_place.*

class HappyPlaceDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_happy_place_detail)

        var happyPlaceDetailModel : HappyPlaceModel? = null
        if(intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)){
            happyPlaceDetailModel = intent.getParcelableExtra(
                MainActivity.EXTRA_PLACE_DETAILS)
        }

        if(happyPlaceDetailModel!=null){
            setSupportActionBar(toolbar_happy_place_detail)

            if(supportActionBar!=null){
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.title = happyPlaceDetailModel.title
            }
            toolbar_happy_place_detail.setNavigationOnClickListener {
                onBackPressed()
            }

            iv_place_image.setImageURI(Uri.parse(happyPlaceDetailModel.image))
            tv_description.text = happyPlaceDetailModel.description
            tv_location.text = happyPlaceDetailModel.location
        }

    }
}