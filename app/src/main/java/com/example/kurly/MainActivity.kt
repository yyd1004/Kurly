package com.example.kurly

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kurly.common.PaginationLinearLayoutListener
import com.example.kurly.databinding.ActivityMainBinding
import com.example.kurly.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: ProductViewModel by viewModels()

    lateinit var binding: ActivityMainBinding

    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = KurlyAdapter()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvKurly.adapter = adapter
        binding.rvKurly.layoutManager = layoutManager

        val paginationLinearLayoutListener = object : PaginationLinearLayoutListener(layoutManager) {
            override fun loadMoreItems(currentPage: Int) {
                if (!isLoading) {
                    isLoading = true
                    setIsLoading(true)

                    viewModel.getSections(currentPage)
                }
            }
        }

        binding.rvKurly.addOnScrollListener(paginationLinearLayoutListener)

        binding.sflKurly.setOnRefreshListener {
            adapter.itemList.clear()
            viewModel.getSections(1)
        }

        viewModel.getSections(1)

        viewModel.sectionsLiveData.observe(this) {
            it.paging?.let {
                paginationLinearLayoutListener.setNextPage(it.next_page)
            }

            it.data?.let {
                it.forEachIndexed { index, sections ->
                    val returnPosition = (adapter.itemCount + index)
                    viewModel.getProducts(sections.id, returnPosition)
                }

                adapter.submitList(it)
            }

            paginationLinearLayoutListener.setIsLoading(false)
            isLoading = false
            binding.sflKurly.isRefreshing = false
        }

        viewModel.productsLiveData.observe(this) { productData ->
            productData.returnPosition?.let {
                adapter.itemList.get(it).products = productData.data
                adapter.notifyItemChanged(it)
            }
        }
    }
}