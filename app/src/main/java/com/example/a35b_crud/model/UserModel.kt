package com.example.a35b_crud.model

import android.os.Parcel
import android.os.Parcelable

data class UserModel(
    var userId : String = "",
    var email : String = "",
    var firstName : String = "",
    var lastName : String = "",
    var address : String = "",
    var phoneNumber : String = "",
    var imageName : String = "",
    var imageUrl : String = "",
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(email)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(address)
        parcel.writeString(phoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}