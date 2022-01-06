package com.kikikoko.android.serverapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.btnTest)
        button.setOnClickListener { v ->
            val output = runCommand("adb shell")
            val output2 =
                runCommand(" CLASSPATH=/data/local/scrcpy/scrcpy-server.jar app_process / org.las2mile.scrcpy.Server ;")
            println(output + "\n" + output2)
        }

//        val runTime = Runtime.getRuntime()
//        runTime.exec("adb shell")
//        runTime.exec(" CLASSPATH=/data/local/scrcpy/scrcpy-server.jar app_process / org.las2mile.scrcpy.Server ;")
    }

    fun runCommand(cmd: String): String {
        val cmdOut = StringBuffer()
        try {
            val process = Runtime.getRuntime().exec(cmd)
            val reader = InputStreamReader(process.inputStream)
            val bufReader = BufferedReader(reader)

            val buf = CharArray(4096)
            var nRead = 0
            while (bufReader.read(buf).also { nRead = it } > 0) {
                cmdOut.append(buf, 0, nRead)
            }
            bufReader.close()
            try {
                process.waitFor()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return cmdOut.toString()
    }
}