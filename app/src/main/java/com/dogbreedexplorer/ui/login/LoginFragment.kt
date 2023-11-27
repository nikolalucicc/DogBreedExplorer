package com.dogbreedexplorer.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dogbreedexplorer.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val emailEditText = view.findViewById<EditText>(R.id.emailLogin)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordLogin)
        val buttonLogin = view.findViewById<Button>(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Email and password are required", Toast.LENGTH_SHORT).show()
            }
        }

        val textViewRegister = view.findViewById<TextView>(R.id.textViewRegister)
        textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return view
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Login successful, obtain auth token
                    val user: FirebaseUser? = auth.currentUser
                    user?.getIdToken(true)
                        ?.addOnCompleteListener { tokenTask ->
                            if (tokenTask.isSuccessful) {
                                // Auth token retrieved, you can use tokenTask.result.token
                                val authToken = tokenTask.result?.token
                                Log.d("AuthToken", "Authentication Token: $authToken")
                                // Handle the auth token as needed (e.g., store it, pass it to another component)
                                // Navigate to the next screen or perform other actions
                                Toast.makeText(requireContext(), "Successful login!", Toast.LENGTH_SHORT).show()
                            } else {
                                // Failed to retrieve auth token
                                Toast.makeText(requireContext(), "Unsuccessful registration!", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    // If login fails, display a message to the user.
                    // You can also log the exception by using task.exception
                    showErrorMessage("Login failed. Please check your credentials.")
                }
            }
    }

    private fun showErrorMessage(message: String) {
        // Display a Snackbar with the provided error message
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}
