package com.chami.newsapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.chami.newsapplication.databinding.FragmentInfoBinding
import com.chami.newsapplication.presentation.viewmodel.NewsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar


class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding :FragmentInfoBinding get() =  _binding!!

    private lateinit var viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args : InfoFragmentArgs by navArgs()
        val article = args.selectedArticle
        viewModel = (activity as MainActivity).viewModel

        binding.webView.apply {
            webViewClient =  WebViewClient()
            article?.url?.let {
                loadUrl(it)
            }
        }

        binding.faButton.setOnClickListener{
            if (article != null) {
                viewModel.saveArticles(article = article)
                Snackbar.make(view,"Save News Successfully",Snackbar.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}