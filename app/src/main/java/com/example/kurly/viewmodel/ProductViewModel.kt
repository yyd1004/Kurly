package com.example.kurly.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kurly.repository.ProductRepository
import com.example.kurly.response.ProductsData
import com.example.kurly.response.SectionsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel
@Inject
constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _sectionsLiveData: MutableLiveData<SectionsData> = MutableLiveData()

    val sectionsLiveData: LiveData<SectionsData>
        get() = _sectionsLiveData

    private val _productsLiveData: MutableLiveData<ProductsData> = MutableLiveData()

    val productsLiveData: LiveData<ProductsData>
        get() = _productsLiveData

    fun getSections(page: Int) {
        viewModelScope.launch {
            try {
                val result = productRepository.getSections(page)
                if (result.code() == 200) {
                    result.body()?.let {
                        _sectionsLiveData.value = it
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getProducts(sectionId: Int, returnPosition : Int) {
        viewModelScope.launch {
            try {
                val result = productRepository.getProducts(sectionId, returnPosition)
                result.body()?.let {
                    try {
                        it.returnPosition = Uri.parse(result.raw().request.url.toString())
                            .getQueryParameter("returnPosition")?.toInt()
                    } catch (e: Exception) {

                    }
                    _productsLiveData.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}