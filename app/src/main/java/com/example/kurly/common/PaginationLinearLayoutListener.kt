package com.example.kurly.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationLinearLayoutListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var isLoading = false
    private var isLastPage = false
    private var nextPage = 1

    abstract fun loadMoreItems(currentPage: Int)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount - 1
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems(nextPage)
            }
        }
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    fun setLastPage(isLastPage: Boolean) {
        this.isLastPage = isLastPage
    }

    fun setNextPage(nextPage: Int) {
        this.nextPage = nextPage
    }
}