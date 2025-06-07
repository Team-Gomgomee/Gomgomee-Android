package com.konkuk.gomgomee.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.HomeDetailCardData
import com.konkuk.gomgomee.type.DisorderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class HomeDetailViewModel : ViewModel() {

    private val _detailCards = mutableStateListOf<HomeDetailCardData>()
    val detailCards: List<HomeDetailCardData> = _detailCards

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    private val _disorderType = mutableStateOf(DisorderType.LEARNING)
    val disorderType: State<DisorderType> = _disorderType

    private val _selectedCardIndex = mutableIntStateOf(-1)
    val selectedCardIndex: State<Int> = _selectedCardIndex

    fun setDisorderType(type: DisorderType) {
        _disorderType.value = type
    }

    fun setInitialDataIndex(index: Int) {
        _selectedCardIndex.value = index
    }

    fun fetchLearningDisorderInfo() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val list = getLearningDisorderInfo()
                _detailCards.clear()
                _detailCards.addAll(list)
            } catch (e: Exception) {
                Log.e("Learning Disorder Crawling", "크롤링 실패", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchAdhdInfo() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val list = getAdhdInfo()
                _detailCards.clear()
                _detailCards.addAll(list)
            } catch (e: Exception) {
                Log.e("ADHD Crawling", "크롤링 실패", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchCommunicationDisorderInfo() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val list = getCommunicationDisorderInfo()
                _detailCards.clear()
                _detailCards.addAll(list)
            } catch (e: Exception) {
                Log.e("Communication Disorder Crawling", "크롤링 실패", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchDyslexiaInfo() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val list = getDyslexiaInfo()
                _detailCards.clear()
                _detailCards.addAll(list)
            } catch (e: Exception) {
                Log.e("Dyslexia Crawling", "크롤링 실패", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun getLearningDisorderInfo(): List<HomeDetailCardData> = withContext(Dispatchers.IO) {
        val url = "https://snuh.org/health/nMedInfo/nView.do?category=DIS&medid=AA000616"

        val doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36")
            .referrer("https://www.google.com")
            .timeout(10_000)
            .get()

        val result = mutableListOf<HomeDetailCardData>()

        val headingElements = doc.select("h5")

        for (heading in headingElements) {
            val cardTitle = heading.text().trim()

            val contentBuilder = StringBuilder()
            var sibling = heading.nextElementSibling()

            while (sibling != null && sibling.tagName() != "h5") {
                val text = sibling.wholeText().trim()
                if (text.isNotEmpty()) {
                    contentBuilder.append(text).append("\n\n")
                }
                sibling = sibling.nextElementSibling()
            }

            val cardContent = contentBuilder.toString().trim()
            if (cardContent.isNotEmpty()) {
                result.add(HomeDetailCardData(title = cardTitle, content = cardContent))
            }
        }

        result
    }

    private suspend fun getAdhdInfo(): List<HomeDetailCardData> = withContext(Dispatchers.IO) {
        val url = "https://www.snuh.org/health/nMedInfo/nView.do?category=DIS&medid=AA000358"

        val doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36")
            .referrer("https://www.google.com")
            .timeout(10_000)
            .get()

        val result = mutableListOf<HomeDetailCardData>()

        val headingElements = doc.select("h5")
        for (heading in headingElements) {
            val cardTitle = heading.text().trim()

            val contentBuilder = StringBuilder()
            var sibling = heading.nextElementSibling()

            while (sibling != null && sibling.tagName() != "h5") {
                val text = sibling.wholeText().trim()
                if (text.isNotEmpty()) {
                    contentBuilder.append(text).append("\n\n")
                }
                sibling = sibling.nextElementSibling()
            }

            val cardContent = contentBuilder.toString().trim()
            if (cardContent.isNotEmpty()) {
                result.add(HomeDetailCardData(title = cardTitle, content = cardContent))
            }
        }

        result
    }

    private suspend fun getCommunicationDisorderInfo(): List<HomeDetailCardData> = withContext(Dispatchers.IO) {
        val url = "https://snuh.org/health/nMedInfo/nView.do?category=DIS&medid=AA001092"

        val doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36")
            .referrer("https://www.google.com")
            .timeout(10_000)
            .get()

        val result = mutableListOf<HomeDetailCardData>()

        val headingElements = doc.select("h5")
        for (heading in headingElements) {
            val cardTitle = heading.text().trim()

            val contentBuilder = StringBuilder()
            var sibling = heading.nextElementSibling()

            while (sibling != null && sibling.tagName() != "h5") {
                val text = sibling.wholeText().trim()
                if (text.isNotEmpty()) {
                    contentBuilder.append(text).append("\n\n")
                }
                sibling = sibling.nextElementSibling()
            }

            val cardContent = contentBuilder.toString().trim()
            if (cardContent.isNotEmpty()) {
                result.add(HomeDetailCardData(title = cardTitle, content = cardContent))
            }
        }

        result
    }

    private suspend fun getDyslexiaInfo(): List<HomeDetailCardData> = withContext(Dispatchers.IO) {
        val url = "https://www.snuh.org/health/nMedInfo/nView.do?category=DIS&medid=AA000594"

        val doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36")
            .referrer("https://www.google.com")
            .timeout(10_000)
            .get()

        val result = mutableListOf<HomeDetailCardData>()

        val headingElements = doc.select("h5")
        for (heading in headingElements) {
            val cardTitle = heading.text().trim()

            val contentBuilder = StringBuilder()
            var sibling = heading.nextElementSibling()

            while (sibling != null && sibling.tagName() != "h5") {
                val text = sibling.wholeText().trim()
                if (text.isNotEmpty()) {
                    contentBuilder.append(text).append("\n\n")
                }
                sibling = sibling.nextElementSibling()
            }

            val cardContent = contentBuilder.toString().trim()
            if (cardContent.isNotEmpty()) {
                result.add(HomeDetailCardData(title = cardTitle, content = cardContent))
            }
        }

        result
    }
}