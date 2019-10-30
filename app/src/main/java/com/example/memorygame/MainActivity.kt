package com.example.memorygame

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private var clickedPair = mutableListOf<TextView>()

    private fun setListeners() {
        val cards= getCardsWithStyle()

        for (card in cards) {
            card.first.first.setOnClickListener { makeStyle(it as TextView, card.second, card.third) }
            card.first.second.setOnClickListener { makeStyle(it as TextView, card.second, card.third) }
        }
    }

    private fun makeStyle(view: TextView, color: Int, text: String) {
        clickedPair.add(view)

        view.setBackgroundColor(color)
        view.text = text

        if (clickedPair.size == 2)
            checkIfPair()
    }

    private val changeBackToWhite =
        { clickedPair.forEach { view -> view.setBackgroundResource(android.R.color.white) } }

//    private fun changeBackToWhite() {
//        clickedPair.forEach { view -> view.setBackgroundResource(android.R.color.white) }
//    }

    private fun checkIfPair() {
        if (clickedPair[0].text != clickedPair[1].text)
            changeBackToWhite()
        clickedPair.clear()
    }

    private fun getCardsWithStyle(): List<Triple<Pair<TextView, TextView>, Int, String>> {

        val pairs = createListOfPairs()

        val colors = List(pairs.size) { createRandomColor() }

        //TODO: use values from string.xml
        val textOnCards = List(pairs.size) { i -> "Pair ${i + 1}" }

        return List(pairs.size) {i -> Triple(pairs[i], colors[i], textOnCards[i]) }

    }

    private fun createListOfPairs(): List<Pair<TextView, TextView>> {
        val shuffledCards = getCards().shuffled()

        val sizeOfList = shuffledCards.size / 2

        return List(sizeOfList) { i -> shuffledCards[i] to shuffledCards[i + sizeOfList] }
    }

    private fun createRandomColor(): Int {
        return Color.argb(255,
            Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }

    private fun getCards(): List<TextView> {
        val parent: ViewGroup = background

        val clickableViews = mutableListOf<TextView>()
        for (i in 0..parent.childCount) {
            if (parent.getChildAt(i) is TextView)
                clickableViews.add(parent.getChildAt(i) as TextView)
        }

        return clickableViews
    }
}

