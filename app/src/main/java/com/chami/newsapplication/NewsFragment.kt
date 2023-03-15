package com.chami.newsapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chami.newsapplication.data.model.Article
import com.chami.newsapplication.data.util.Resource
import com.chami.newsapplication.databinding.FragmentNewsBinding
import com.chami.newsapplication.presentation.adapter.NewsAdapter
import com.chami.newsapplication.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding: FragmentNewsBinding get() = _binding!!
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**onViewCreated will be call imminently after all the view crated.
    It's much safe to use avoid unexpected errors in partially created views.
     **/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        val emptyList = listOf<Article>()
        newsAdapter.differ.submitList(emptyList)
        iniRecyclerView()
        getNewsList()

        newsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
        }
    }

    private fun getNewsList() {

        viewModel.getNewsHeadLines(country = "us", page = 1)
        viewModel.newsHeadlineLiveData.observe(viewLifecycleOwner) { response ->

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

    private fun iniRecyclerView() {

        /**this is kind of manual dependency injection( newsAdapter = NewsAdapter()) so instead of doing that we can create a hilt @Module for that and can pass it as
         * field @Inject
         */
//        newsAdapter = NewsAdapter()
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = linearLayoutManager
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