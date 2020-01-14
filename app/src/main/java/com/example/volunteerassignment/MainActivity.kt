package com.example.volunteerassignment

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.multidex.MultiDex
//import com.example.volunteerassignment.ui.home.ViewPagerAdapter
import com.example.volunteerassignment.ui.login.LoginActivity
import com.example.volunteerassignment.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text


class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mAuth: FirebaseAuth
    private lateinit var ref: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        ref = FirebaseFirestore.getInstance()


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)


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
    fun Logout(view: View){
        FirebaseAuth.getInstance().signOut()
        updateUI()
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
//        finish()
//        val intent = Intent(this,MainActivity::class.java)
//        startActivity(intent)
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

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    fun updateUI() {
        val currentuser = mAuth.currentUser
        val navView: NavigationView = findViewById(R.id.nav_view)

        val drawer: Menu = navView.menu
        val headerLayout = navView.getHeaderView(0)
        val login = headerLayout.findViewById<Button>(R.id.btnLogin)
        val logout = headerLayout.findViewById<Button>(R.id.btnLogout)
        val signUp = headerLayout.findViewById<Button>(R.id.btnSignUp)
        val txtEmail = headerLayout.findViewById<TextView>(R.id.nav_email)

        if (currentuser != null) {
            login.visibility = View.GONE
            logout.visibility = View.VISIBLE
            signUp.visibility = View.GONE

            val bundle: Bundle? = intent.extras
            if (bundle != null) {
                val email = bundle.getString("email")
                txtEmail.text = email.toString()
            }
                ref.collection("Users").document(currentuser?.uid)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            if (documentSnapshot.get("Type").toString() == "Volunteer") {
                                val userNavigate = drawer.setGroupVisible(R.id.user_navigate, true)
                                val organizerNavigate =
                                    drawer.setGroupVisible(R.id.organization_navigate, false)
                                //userNavigate.run {  }

                            } else if (documentSnapshot.get("Type").toString() == "Organizer") {
                                val organizerNavigate =
                                    drawer.setGroupVisible(R.id.organization_navigate, true)
                                val userNavigate = drawer.setGroupVisible(R.id.user_navigate, false)
                                //organizerNavigate.setVisible(true)
                            }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Unable to update UI!", Toast.LENGTH_SHORT).show()
                    }
            } else {
                login.visibility = View.VISIBLE
                logout.visibility = View.GONE
                signUp.visibility = View.VISIBLE
                val userNavigate = drawer.setGroupVisible(R.id.user_navigate, false)
                val organizerNavigate = drawer.setGroupVisible(R.id.organization_navigate, false)
                txtEmail.text = ""
            }
    }
}
