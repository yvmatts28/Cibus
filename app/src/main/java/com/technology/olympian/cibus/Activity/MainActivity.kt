package com.technology.olympian.cibus.Activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock.sleep
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.awesome.dialog.AwesomeCustomDialog
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.*
import com.technology.olympian.cibus.Data.Upload
import com.technology.olympian.cibus.R
import dmax.dialog.SpotsDialog
import es.dmoral.toasty.Toasty

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.food_info_popup.*
import kotlinx.android.synthetic.main.food_info_popup.view.*
import java.io.File
import java.util.*
import java.util.concurrent.Delayed


class MainActivity : AppCompatActivity() {

    private val CAMERA = 100
    private val GALLERY = 200
    private var mStorage: StorageReference? = null
    private var mDatabase: DatabaseReference? = null
    lateinit var cameraUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mStorage = FirebaseStorage.getInstance().reference
        mDatabase = FirebaseDatabase.getInstance().reference


        fab_menu.photo.setOnClickListener {
            ImagePicker.create(this)
                    .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                    .folderMode(true) // folder mode (false by default)
                    .toolbarFolderTitle("Folder") // folder selection title
                    .toolbarImageTitle("Tap to select") // image selection title
                    .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                    .single() // single mode

                    .limit(1) // max images can be selected (99 by default)
                    .showCamera(true) // show camera or not (true by default)
                    .imageDirectory("Cibus") // directory name for captured image  ("Camera" folder by default)

                    .start()

        }


        collageButton.setOnClickListener {
            val intent = Intent(this, CollageActivity::class.java)
            startActivity(intent)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        ImageCaption.visibility = View.INVISIBLE
        var image = ImagePicker.getFirstImageOrNull(data)
        var path = image.path
        var imageUri = Uri.fromFile(File(path))
        pictureImageView.setImageURI(Uri.fromFile(File(path)))
        pictureImageView.visibility = View.VISIBLE

        var filePath: StorageReference = mStorage!!.child("Photos").child(imageUri.lastPathSegment)
    //    fabUploadButton.setOnClickListener {

        fab_menu.upload.setOnClickListener {
            var dialog = AwesomeCustomDialog(this)
            //AwesomeCustomDialog(this)

            var uploadProgress = SpotsDialog(applicationContext,"Uploading....")
            dialog.setTopColor(Color.parseColor("#00BCD4"))
                    .setIcon(R.drawable.ic_register)
                    .setIconTintColor(Color.WHITE)
                    .setView(R.layout.food_info_popup)
                    .configureView(object : AwesomeCustomDialog.ViewConfigurator {
                        override fun configureView(v: View) {


                            var foodName = v.foodNameText.text.toString()
                            var quantity = v.foodQuantity.text.toString()
                            v.saveBtn.setOnClickListener {

                                v.progressBar3.visibility = View.VISIBLE
                                //dialog.dismiss()


                                filePath.putFile(imageUri)
                                        .addOnCompleteListener { task: Task<UploadTask.TaskSnapshot> ->

                                            if (task.isSuccessful) {

                                                v.progressBar3.visibility = View.INVISIBLE
                                                v.imageView3.visibility = View.VISIBLE

                                                Toasty.success(applicationContext, "Uploaded",  Toast.LENGTH_SHORT).show()
                                                pictureImageView.visibility = View.INVISIBLE

                                                ImageCaption.visibility = View.VISIBLE
                                                dialog.dismiss()
                                            } else {
//                                                if(this@MainActivity.isFinishing){
//
//                                                    uploadProgress.dismiss()
//                                                }
//                                                //dialog.dismiss()
                                                Toasty.error(applicationContext, "Unable to Upload", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                                            var up = Upload(foodName, taskSnapshot!!.downloadUrl.toString(), quantity)
                                            var upId = mDatabase!!.push().key
                                            mDatabase!!.child("Photos").child(upId).setValue(up)
                                        }

                            }
                        }
                    })
                    .show()

        }



        //      }

    }


}