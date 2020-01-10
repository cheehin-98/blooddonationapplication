package com.example.volunteerassignment

import android.content.Context
import android.content.Intent
import android.graphics.Picture
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.multidex.MultiDex
import androidx.viewpager.widget.ViewPager
import com.example.volunteerassignment.ui.home.HomeFragment
//import com.example.volunteerassignment.ui.home.ViewPagerAdapter
import com.example.volunteerassignment.ui.login.LoginActivity
import com.example.volunteerassignment.ui.login.LoginFragment
import com.example.volunteerassignment.ui.signup.SignUpActivity
import com.example.volunteerassignment.ui.signup.SignUpFragment
import com.example.volunteerassignment.ui.signup.SignUpViewModel
import com.example.volunteerassignment.ui.user_account.UserAccountFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_home.*


class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var btnSignUp: Button

    private lateinit var auth: FirebaseAuth


   // var Slide:MutableList<Slide> = ArrayList()
   // var sliderPage: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ///sliderPage = this.findViewById(R.id.viewEvents)

        //Slide.add(Slide(R.drawable.blood_donation_event,title = String()))

        //var adapter: SlidePagerAdapter = SlidePagerAdapter(this,Slide)
        //sliderPage!!.adapter = adapter

        //viewEvent = findViewById<View>(R.id.viewEvents) as  ViewPager
        //viewEvent.adapter = adapter
        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_user_account, R.id.nav_user_activity,
                R.id.nav_user_notification, R.id.nav_user_point_and_redeem, R.id.nav_organizer_account, R.id.nav_organizer_activity, R.id.nav_organizer_notification
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        /*nav_view.setNavigationItemSelectedListener(this)*/

    }

    fun signUp(view: View) {

        val intent: Intent = Intent(applicationContext, SignUpActivity::class.java)
        startActivity(intent)

    }
    fun Login(view: View){
        val intent: Intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)

    }
fun LinktoSignup(view: View){
    val intent = Intent(this, SignUpActivity::class.java)

}

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}

    private fun updateNavHeader(){
        val navView: NavigationView = findViewById(R.id.nav_view)

       val headerView:View  = navView.getHeaderView(0)

        val navEmail: TextView = headerView.findViewById(R.id.nav_email)

        val btnSignup: Button = headerView.findViewById(R.id.btnSignUp)

        val btnLogin: Button = headerView.findViewById(R.id.btnLogin)

        val picture: ImageView = headerView.findViewById(R.id.imageView)




       // nav
      //  val user = FirebaseAuth.getInstance().currentUser
       // val email = keyinSignupEmail.text.toString()
       // user?.let {
         //   for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
          //      val providerId = profile.providerId

                // UID specific to the provider
             //   val uid = profile.uid

                // Name, email address, and profile photo Url
              //  val name = profile.displayName
              //  val email = profile.email
               // val photoUrl = profile.photoUrl
            }
        }


        // val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        //val buttonLogin: Button = findViewById(R.id.)

       // val buttonSignUp: Button = findViewById(R.id.btnSignUp)

        //buttonLogin.setOnClickListener()
