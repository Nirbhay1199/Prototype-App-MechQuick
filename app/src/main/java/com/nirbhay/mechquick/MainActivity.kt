package com.nirbhay.mechquick

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayoutMediator
import com.mappls.sdk.maps.Mappls
import com.mappls.sdk.services.account.MapplsAccountManager
import com.nirbhay.mechquick.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        MapplsAccountManager.getInstance().restAPIKey = "7e9a95677c5233fb94daffec1cf5c90f"
        MapplsAccountManager.getInstance().mapSDKKey = "7e9a95677c5233fb94daffec1cf5c90f"
        MapplsAccountManager.getInstance().atlasClientId = "96dHZVzsAuuSrX7rUNX-sDyOY1xeJGyQ3j9iApqru2vxmht4kpWKRcTl3H5BJ9jILWxgw2iOjr68botRIGL_3-gWrsFXwq-T"
        MapplsAccountManager.getInstance().atlasClientSecret = "lrFxI-iSEg-49ra8pVg050Q3ivQCeATjN5ebY-VSH2AMSJdG70hZvVVtqArArf09_fuAZOq7xvM8Hh0XebCV5jHvksA8sGewBdz5k6480d0="
        Mappls.getInstance(applicationContext)

        PreferenceManager.init(this)

        val isLogin = PreferenceManager.getBoolValue("isLogin")
        if (!isLogin) {
            val intent = Intent(this, UserAuthentication::class.java)
            startActivity(intent)
            finish()
        }



        binding.logoutBtn.setOnClickListener {
            PreferenceManager.setBoolValue("isLogin", false)
            val intent = Intent(this, UserAuthentication::class.java)
            startActivity(intent)
            finish()
        }


        val viewPager = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPager
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 2

        try {
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Search"
                        tab.icon = ContextCompat.getDrawable(this, R.drawable.search)
                    }

                    1 -> {
                        tab.text = "Map"
                        tab.icon = ContextCompat.getDrawable(this, R.drawable.map)
                    }
                }
            }.attach()

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}