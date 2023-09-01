package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.modcom.medilabmemberapp.constants.Constants
import com.modcom.medilabmemberapp.helpers.ApiHelper
import com.modcom.medilabmemberapp.helpers.DialogHelper
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // find SurnameEditText and passwordEditText
        val surname: EditText = findViewById(R.id.editSurname)
        val password : EditText = findViewById(R.id.editPassword)

        // get Login Button and setListener
        val buttonLogin : Button = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {

            // specify endpoint
            val api: String = Constants.BASE_URL + "/member_signin"
            // body payload -> JSONObject
            val body = JSONObject()
            body.put("surname",surname.text.toString())
            body.put("password", password.text.toString())

            val helper = ApiHelper(applicationContext)
            helper.post(api, body, object : ApiHelper.CallBack{
                override fun onSuccess(result: JSONArray?) {
                    TODO("Not yet implemented")
                }

                override fun onSuccess(result: JSONObject?) {
                    // logged in successfully or invalid credential
                    if(result!!.has("refresh_token")){
                        // save access_token, refresh_token, member to the Prefs

                        val refresh_token = result.getString("refresh_token")
                        val access_token = result.getString("access_token")
                        val message = result.getString("member")

                       PrefsHelper.savePrefs(applicationContext, "refresh_token", refresh_token)
                        PrefsHelper.savePrefs(applicationContext, "access_token", access_token)

                        // get member data from the message jsonObject
                        val member = JSONObject(message)
                        val member_id = member.getString("member_id")
                        val email = member.getString("email")
                        val surname = member.getString("surname")
                        val phone = member.getString("phone")

                        PrefsHelper.savePrefs(applicationContext, "member_id", member_id)
                        PrefsHelper.savePrefs(applicationContext, "email", email)
                        PrefsHelper.savePrefs(applicationContext, "surname", surname)
                        PrefsHelper.savePrefs(applicationContext, "phone", phone)


                        val intentHome = Intent(applicationContext, HomeActivity::class.java)
                        startActivity(intentHome)

                    }  // Login is Success

                        else{
                        Toast.makeText(applicationContext, "${result.toString()}", Toast.LENGTH_SHORT).show()
                        }
                }

                override fun onFailure(result: String?) {
                    Toast.makeText(applicationContext, "${result.toString()}", Toast.LENGTH_SHORT).show()
                }
            })


        }
    }
}