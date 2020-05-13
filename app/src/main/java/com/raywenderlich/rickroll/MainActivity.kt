package com.raywenderlich.rickroll


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {

    var position = 0
    var mcontroller:MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (mcontroller == null) {
            mcontroller = MediaController(this@MainActivity)
        }
        try {
            video_view.setMediaController(mcontroller)
            video_view.setVideoURI(Uri.parse("android.resource://"+packageName+"/"+R.raw.test_video))
        }
        catch (e:Exception) {
            Log.e("Error:", e.message)
        }
        video_view.requestFocus()
        video_view.setOnCompletionListener {
            video_view.seekTo(position)
            if (position == 0) {
                video_view.start()
            } else {
               video_view.pause()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (outState!=null) {
            outState.putInt("position", video_view.currentPosition)
        }
        video_view.pause()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState!=null) {
           position = savedInstanceState.getInt("position")
        }
        video_view.seekTo(position)
    }

}
