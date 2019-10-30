package com.example.memorygame

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var clickedPair = mutableListOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        val cards= getCardsWithStyle()

        for (cardPair in cards) {
            cardPair.toList().forEach { card ->
                card.view.setOnClickListener { makeStyle(card, Handler()) }
            }
        }
    }

    private fun makeStyle(card: Card, handler: Handler) {
        clickedPair.add(card.view)

        card.view.setBackgroundColor(card.color)
        card.view.text = card.text

        if (clickedPair.size == 2)
            handler.postDelayed({ checkIfPair() }, 1200)
    }

    private fun checkIfPair() {
        if (clickedPair[0].text != clickedPair[1].text)
            changeBackToWhite()
        clickedPair.clear()
    }

    private fun changeBackToWhite() {
        clickedPair.forEach { view ->
            view.setBackgroundResource(android.R.color.white)
            view.text = ""
        }
    }

    private fun getCardsWithStyle(): List<Pair<Card, Card>> {

        val pairs = createListOfPairs()

        val colors = List(pairs.size) { createRandomColor() }

        val textOnCards = List(pairs.size) { i -> "Pair ${i + 1}" }

        return List(pairs.size) {i ->
            Card(pairs[i].first, colors[i], textOnCards[i]) to
            Card(pairs[i].second, colors[i], textOnCards[i])}

    }

    private fun createListOfPairs(): List<Pair<TextView, TextView>> {
        val shuffledCards = getClickableTextViews().shuffled()

        val sizeOfList = shuffledCards.size / 2

        return List(sizeOfList) { i -> shuffledCards[i] to shuffledCards[i + sizeOfList] }
    }

    private fun createRandomColor(): Int {
        return Color.argb(255,
            Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }

    private fun getClickableTextViews(): List<TextView> {
        val parent: ViewGroup = background

        val clickableViews = mutableListOf<TextView>()
        for (i in 0..parent.childCount) {
            if (parent.getChildAt(i) is TextView)
                clickableViews.add(parent.getChildAt(i) as TextView)
        }

        return clickableViews
    }
}

class Card(var view: TextView, var color: Int, var text: String)

