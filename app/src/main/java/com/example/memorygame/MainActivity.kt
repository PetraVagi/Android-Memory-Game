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

            cardPair.first.first.setOnClickListener {
                makeStyle(it as TextView, cardPair.second, cardPair.third, Handler()) }
             cardPair.first.second.setOnClickListener {
                    makeStyle(it as TextView, cardPair.second, cardPair.third, Handler()) }
        }
    }

    private fun makeStyle( view: TextView, color: Int, text: String, handler: Handler ) {
        clickedPair.add(view)

        view.setBackgroundColor(color)
        view.text = text

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

    /**This function returns a List of Triples which contains the following elements:
     *      - first (Pair of TextViews): that will be pairs in the game too
     *      - second (Int): a randomly calculated number to set same the background color for the pairs
     *      - third (String): text to be displayed on the TextViews
     * */
    private fun getCardsWithStyle(): List<Triple<Pair<TextView, TextView>, Int, String>> {

        val pairs = createListOfPairs()

        val colors = List(pairs.size) { createRandomColor() }

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

class Card(var view: TextView, var color: Int, var text: String)

