package edu.rosehulman.MovieQuote

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.rosehulman.MovieQuote.databinding.ActivityMainBinding
import edu.rosehulman.MovieQuote.models.UserViewModel

class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding
        private lateinit var navController: NavController
        private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onStart() {
        super.onStart()
        Firebase.auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        Firebase.auth.removeAuthStateListener(authStateListener)
    }


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            initializaAuthListener()

            val navView: BottomNavigationView = binding.navView
            navController = findNavController(R.id.nav_host_fragment_activity_main)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.

            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home, R.id.navigation_quotes, R.id.navigation_notifications
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)//top bar name change

            navView.setupWithNavController(navController) //button menu is tied with controller
        }


    private fun initializaAuthListener() {
        authStateListener = FirebaseAuth.AuthStateListener { auth:FirebaseAuth->
            val user = auth.currentUser
            if(user == null){ //if the user is not currently log in
                setupAuthUI()
            }else{
                with(user){
                    Log.d(Constants.TAG,"User: $uid, $email,$displayName,$photoUrl ")
                }
                val userModel = ViewModelProvider(this).get(UserViewModel::class.java)

                userModel.getOrMakeUser {
                    if (userModel.hasCompletedSetup()) {
//                        val id =
//                            findNavController(R.id.nav_host_fragment_activity_main).currentDestination!!.id
//                        if (id == R.id.navigation_splash) {
//                            findNavController(R.id.nav_host_fragment_activity_main)
//                                .navigate(R.id.navigation_quotes)
//                        }
                        navController.navigate(R.id.navigation_quotes)
                    } else {
                        navController.navigate(R.id.navigation_user_edit)
                    }
                }
            }

        }
    }


    val signinLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { /* empty since the auth listener already responds .*/ }

    private fun setupAuthUI() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val signinIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .setLogo(R.drawable.ic_face_foreground)
            .build()
        signinLauncher.launch(signinIntent)
    }

}