package com.sameer.antsmasher

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity(), Contract.GameView {


    private var gameLayout: FrameLayout? = null
    //    private var playButton: View? = null
    private var introText: View? = null
    private var gameOverText: View? = null
    private var score: TextView? = null

    private val gameEngine: GameEngine = GameEngine()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(Toolbar)
        gameLayout = game_layout
//        Play_button = play_button
        introText = intro_text
        gameOverText = game_over
        score = findViewById<TextView>(R.id.score)

        play_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                gameEngine.onPlayButtonClicked()
            }
        })

//        val d = Drawable.createFromStream(getAssets().open("bg.jpeg"), null);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            gameLayout?.background = d
//        }

        gameEngine.onViewAttached(this)
    }

    override fun showAnt(ant: Ant) {
        val antView = ImageView(this)
        antView.setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.ic_ant
        ))
        antView.scaleType = ImageView.ScaleType.FIT_CENTER
        antView.tag = ant
        antView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val ant = v!!.tag as Ant
                gameEngine.onAntClicked(ant)
                gameEngine.hideBlood(ant)
            }
        })
        val antSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65f, resources.displayMetrics)

        val layoutParams = FrameLayout.LayoutParams(antSize.toInt(), antSize.toInt())
        val screenWidth = gameLayout!!.width - antSize
        val screenHeight = gameLayout!!.height - antSize
        layoutParams.leftMargin = (ant.x * screenWidth).toInt()
        layoutParams.topMargin = (ant.y * screenHeight).toInt()
        gameLayout?.addView(antView, layoutParams)
    }

    override fun showblood(blood: Ant) {
        val antView = ImageView(this)
        antView.setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.ants
        ))
        antView.scaleType = ImageView.ScaleType.FIT_CENTER
        antView.tag = blood

        val antSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65f, resources.displayMetrics)

        val layoutParams = FrameLayout.LayoutParams(antSize.toInt(), antSize.toInt())
        val screenWidth = gameLayout!!.width - antSize
        val screenHeight = gameLayout!!.height - antSize
        layoutParams.leftMargin = (blood.x * screenWidth).toInt()
        layoutParams.topMargin = (blood.y * screenHeight).toInt()
        gameLayout?.addView(antView, layoutParams)
    }

    override fun hideAnt(antToHide: Ant) {
        val antViewsCount: Int? = gameLayout?.childCount

        antViewsCount?.let {

            for (i: Int in 0..antViewsCount) {
                val view = gameLayout?.getChildAt(i)
                val tempAnt = view?.tag
                if (antToHide == (tempAnt)) {
                    gameLayout?.removeView(view)
                    break
                }
            }

        }
    }

    override fun hideBlood(blhi: Ant) {
        val antViewsCount: Int? = gameLayout?.childCount

        antViewsCount?.let {

            for (i: Int in 0..antViewsCount) {
                val view = gameLayout?.getChildAt(i)
                val tempAnt = view?.tag
                if (blhi == (tempAnt)) {
                    gameLayout?.removeView(view)
                    break
                }
            }

        }
    }


    @SuppressLint("RestrictedApi")
    override fun setPlayButtonVisibility(visible: Boolean) {
        play_button.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun clearView() {
        gameLayout?.removeAllViews()
        introText?.visibility = View.GONE
        gameOverText?.visibility = View.GONE

    }

    override fun setGameOverVisibility(visible: Boolean) {
        gameOverText?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun setIntroTextVisibility(visible: Boolean) {
        introText?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showScore(score: Int) {
        this.score?.text = "Points: " + score
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_Source -> {
                val intent = Intent(android.content.Intent.ACTION_VIEW)
                intent.data = Uri.parse("https:www.evenidontknow.com")
                startActivity(intent)
            }
            R.id.Rate -> {
                Toast.makeText(this, "SOON YOU CAN RATE", Toast.LENGTH_SHORT).show()

            }

        }
        return super.onOptionsItemSelected(item)
    }
}
