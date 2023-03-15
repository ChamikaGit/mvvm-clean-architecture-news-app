package com.chami.newsapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chami.newsapplication.data.model.Article
import com.chami.newsapplication.data.util.Resource
import com.chami.newsapplication.databinding.FragmentSearchBinding
import com.chami.newsapplication.presentation.adapter.NewsAdapter
import com.chami.newsapplication.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        val emptyList = listOf<Article>()

        newsAdapter.differ.submitList(emptyList)
        binding.etSearch.text.clear()
        iniRecyclerView()
        binding.etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                /***
                MainScope().launch {
                    delay(2000)
                    getSearchedNews(binding.etSearch.text.toString())
                }
                 if you wanna to delay your UI thread function for a 2 seconds we can use this MainScope() Coroutines launcher
                **/
                getSearchedNews(binding.etSearch.text.toString())
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })

        newsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(R.id.action_searchFragment_to_infoFragment, bundle)
        }

    }

    private fun iniRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = linearLayoutManager
        }
    }

    private fun getSearchedNews(searchQuery: String) {
        viewModel.getSearchedNewsHeadlines(
            country = "us",
            page = 1,
            query = searchQuery
        )

        viewModel.searchedNewsUseCase.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    }

                }
                is Resource.Loading -> {
                    showProgressBar()
                }

            }

        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}