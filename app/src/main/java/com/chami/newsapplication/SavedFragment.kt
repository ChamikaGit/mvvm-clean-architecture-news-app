package com.chami.newsapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chami.newsapplication.data.model.Article
import com.chami.newsapplication.databinding.FragmentSavedBinding
import com.chami.newsapplication.presentation.adapter.NewsAdapter
import com.chami.newsapplication.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
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
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        val emptyList = listOf<Article>()
        newsAdapter.differ.submitList(emptyList)
        initRecyclerView()
        viewModel.getAllSavedArticles().observe(viewLifecycleOwner) { response ->

            if (response.isNotEmpty()) {
                newsAdapter.differ.submitList(response)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "No Saved Articles are available!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        newsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(R.id.action_savedFragment_to_infoFragment, bundle)
        }

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticles(article = article)
                Snackbar.make(view, "Delete Successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticles(article = article)
                    }
                    show()
                }

            }

        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.recyclerView)
        }

    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = linearLayoutManager
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}