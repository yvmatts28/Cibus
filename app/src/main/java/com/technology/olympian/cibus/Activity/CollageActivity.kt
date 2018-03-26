package com.technology.olympian.cibus.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Window
import android.view.WindowAnimationFrameStats
import android.widget.Toast
import com.google.firebase.database.*
import com.technology.olympian.cibus.Data.ImageAdapter
import com.technology.olympian.cibus.Data.Upload
import com.technology.olympian.cibus.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_collage.*

class CollageActivity : AppCompatActivity() {

    private var mDatabase: DatabaseReference?= null
    private var list: ArrayList<Upload>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_collage)
      //  var mRecyclerView = findViewById<RecyclerView>(R.id.imageRecyclerView)
        imageRecyclerView.layoutManager = LinearLayoutManager(this)
        list = ArrayList()
        mDatabase = FirebaseDatabase.getInstance().getReference("photos")

        mDatabase?.addValueEventListener(object:ValueEventListener{

            override fun onCancelled(p0: DatabaseError?) {
                Toasty.error(applicationContext, p0!!.message,Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot : DataSnapshot?) {

                  for(postSnapshot in  dataSnapshot!!.children){

                     var upload:Upload? = postSnapshot.getValue(Upload::class.java)
                     Toasty.info(applicationContext,"${upload!!.mName}",Toast.LENGTH_SHORT).show()
                    Log.d("Name","${upload.mName}")
                     list!!.add(upload!!)

                }
                var mAdapter = ImageAdapter(list!!,applicationContext)
                imageRecyclerView.adapter = mAdapter
                imageRecyclerView.adapter.notifyDataSetChanged()
            }
        })


    }
}
