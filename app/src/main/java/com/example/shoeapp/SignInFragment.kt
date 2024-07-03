package com.example.shoeapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.shoeapp.Extensions.toast
import com.example.shoeapp.databinding.FragmentSigninBinding
import com.example.shoeapp.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragmentFragment : Fragment(R.layout.fragment_signin) {

    private lateinit var binding: FragmentSigninBinding
    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSigninBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) { // Jeżeli użytkownik już był zalogowany w aplikacji to
                                        // po kolejnemu wejściu do program od razu kieruje go
                                        // użytkownika do menu głównego
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_signInFragmentFragment_to_mainFragment)
        }
        // Jeżeli użytkownik pierwszy raz wchodzi do programu to muszi się zalogować lub w fragmencie
        // SignUpFragment utworzyć nowe konto
        binding.btnSignIn.setOnClickListener {

            if (
                binding.etEmailSignIn.text.isNotEmpty() && // sprawdza, czy oba pola (etEmailSignIn
                binding.etPasswordSignIn.text.isNotEmpty() // i etPasswordSignIn) zawierają tekst.
            ) {
                signinUser(binding.etEmailSignIn.text.toString(),
                    binding.etPasswordSignIn.text.toString()) //przekazywanie tekstu z pól adresu e-mail i hasła.

            } else {
                requireActivity().toast("Some Fields are Empty") // bo musi wprowadzić
            }                                                       // wszystki dostępne pola
        }

        binding.tvNavigateToSignUp.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_signInFragmentFragment_to_signUpFragment)
        }

    }

    private fun signinUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    requireActivity().toast("Sign In Successful") // udane logowanie

                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_signInFragmentFragment_to_mainFragment)
                } else {
                    requireActivity().toast(task.exception!!.localizedMessage!!)

                }


            }

    }


}