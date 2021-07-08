package com.appentus.shedistrict.view.activity

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.OnPermissionChange
import com.kotlinpermissions.KotlinPermissions
import kotlinx.android.synthetic.main.toolbar_logo.*

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toolbar_logo)
        setSupportActionBar(toolbar)

    }

    fun accessPermissions(permissionChange: OnPermissionChange) {

        KotlinPermissions.with(this)
                .permissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)

                .onAccepted {

                    permissionChange.havePermission(true)

                }
                .onDenied {
                    Toast.makeText(this, "Please accept all require permissions", Toast.LENGTH_SHORT)
                            .show()
                }
                .onForeverDenied {
                    Toast.makeText(
                            this,
                            "Please accept all require permissions from setting",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                .ask()
    }


}
