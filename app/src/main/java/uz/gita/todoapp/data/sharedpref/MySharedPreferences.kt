package uz.gita.todoapp.data.sharedpref


interface MySharedPreferences {

    fun getRegister(): Boolean

    fun setRegister(register: Boolean)

    fun getName(): String

    fun setName(name: String)

    fun getImageUri():String

    fun setImageUri(uri:String)

}