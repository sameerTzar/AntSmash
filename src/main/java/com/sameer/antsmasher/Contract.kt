package com.sameer.antsmasher

interface Contract {

    interface GameView {
        fun setIntroTextVisibility(visible: Boolean)
        fun setPlayButtonVisibility(visible: Boolean)
        fun setGameOverVisibility(visible: Boolean)
        fun showAnt(ant: Ant)
        fun hideAnt(antToHide: Ant)
        fun hideBlood(antToHide: Ant)
    fun showblood(blood: Ant)
        fun showScore(score: Int)
        fun clearView()
    }

    interface GameEngine {
        fun onViewAttached(view: GameView)
        fun onViewDetached()
        fun onPlayButtonClicked()
        fun onAntClicked(ant: Ant)
        fun hideBlood(antToHide: Ant)
    }
}