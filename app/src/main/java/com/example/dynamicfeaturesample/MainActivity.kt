package com.example.dynamicfeaturesample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            startModule()
        }
    }

    fun startModule(){
        // Creates an instance of SplitInstallManager.
        val splitInstallManager = SplitInstallManagerFactory.create(this)

        // Creates a request to install a module.
        val request =
            SplitInstallRequest
                .newBuilder()
                .addModule("module")
                .build()

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener { sessionId ->
                if (splitInstallManager.installedModules.contains("module")) {
                    val i = Intent()
                    i.setClassName(BuildConfig.APPLICATION_ID, "com.example.module.ModuleActivity")
                    i.putExtra("ExtraInt", 3) // Test intent for Dynamic feature
                    startActivity(i)
                } else {
                    Log.e("tag", "module is not installed")
                }
            }
            .addOnFailureListener { exception ->  }
    }
}