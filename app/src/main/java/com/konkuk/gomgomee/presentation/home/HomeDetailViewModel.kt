package com.konkuk.gomgomee.presentation.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.HomeDetailCardData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class HomeDetailViewModel : ViewModel() {

    private val _detailCards = mutableStateListOf<HomeDetailCardData>()
    val detailCards: List<HomeDetailCardData> = _detailCards

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    fun fetchLearningDisorderInfo() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val list = getLearningDisorderInfo()
                _detailCards.clear()
                _detailCards.addAll(list)
            } catch (e: Exception) {
                Log.e("SNUH Crawling", "크롤링 실패", e)
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
}