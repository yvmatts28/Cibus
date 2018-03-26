package com.technology.olympian.cibus.Data

/**
 * Created by Yash on 14-03-2018.
 */
class Upload(name: String,imageUrl:String,quantity:String){
    var mName:String? = name
    var mImageUrl:String? = imageUrl
    var mQuantity:String? = quantity

    fun getName():String?{
        return this.mName
    }
    fun setName(name:String){
        this.mName = name
    }

    fun getUrl():String?{
        return this.mImageUrl
    }
    fun setUrl(url:String){
        this.mImageUrl = url
    }
    fun getQuantity():String?{
        return this.mQuantity
    }
    fun setQuantity(quantity:String){
        this.mQuantity = quantity
    }
}