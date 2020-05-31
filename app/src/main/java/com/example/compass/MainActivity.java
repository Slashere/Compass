package com.example.compass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    // image view
    private ImageView image;
    // текущий угол повората компаса
    private float currentDegree = 0f;
    // device sensor manager
    private SensorManager mSensorManager;

    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ImageView с изображением компаса
        image = (ImageView) findViewById(R.id.imageViewCompass);
        // TextView со значением градусов
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        // проверка возможности устройства на наличие датчика
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // регистрация слушателей датчика орентации
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // для остановки слушателей
        mSensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        // получение градусов по z-оси из PI
        float degree = Math.round(event.values[2] * 57.2957795131) ;
        tvHeading.setText("Градусы по оси Z: " + Float.toString(degree));
        // создание анимацию врашения
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // скорость анимации
        ra.setDuration(210);
        // изменение анимации после ее завершения
        ra.setFillAfter(true);
        // начало анимации
        image.startAnimation(ra);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // не используется
    }
}