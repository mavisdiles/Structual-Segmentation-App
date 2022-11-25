package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;

    int position = 0; // 다시 시작 기능을 위한 현재 재생 위치 확인 변수
    int[] point = {12000, 45000, 57375, 105750, 152250, 209875, 226500, 228975};
    SeekBar seekBar1;

    String[] section_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String section = intent.getStringExtra("section");
        section_array = StringToArray(section);
        System.out.println(section_array);

        player = MediaPlayer.create(MainActivity.this, R.raw.music);
        seekBar1 = (SeekBar)findViewById(R.id.seekBar1);


        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio();
                playSeekBar();
            }
        });

        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseAudio();
            }
        });

        findViewById(R.id.resume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeAudio();
                playSeekBar();
            }
        });
        /*
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAudio();
            }
        }
        );*/


    }
    public void playSeekBar() {
        seekBar1.setMax(player.getDuration());  // 음악의 총 길이를 시크바 최대값에 적용
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)  // 사용자가 시크바를 움직이면
                    player.seekTo(progress);   // 재생위치를 바꿔준다(움직인 곳에서의 음악재생)
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //player.start();
        new Thread(new Runnable() {  // 쓰레드 생성
            @Override
            public void run() {
                while (player.isPlaying()) {  // 음악이 실행중일때 계속 돌아가게 함
                    try {
                        Thread.sleep(1000); // 1초마다 시크바 움직이게 함
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 현재 재생중인 위치를 가져와 시크바에 적용
                    seekBar1.setProgress(player.getCurrentPosition());
                }
            }
        }).start();
    }
    // 시작
    private void playAudio() {
        System.out.println(Integer.parseInt(section_array[2]));
        player.seekTo(Integer.parseInt(section_array[2]));
        player.start();
        Toast.makeText(this, "재생 시작됨.", Toast.LENGTH_SHORT).show();

    }

    // 현재 일시정지가 되었는지 중지가 되었는지 헷갈릴 수 있기 때문에 스위치 변수를 선언해 구분할 필요가 있다. (구현은 안했다.)
    private void pauseAudio() {
        if (player != null && player.isPlaying()) {
            position = player.getCurrentPosition();
            player.pause();

            Toast.makeText(this, "일시정지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    private void resumeAudio() {
        if (player != null && !player.isPlaying()) {
            System.out.println(position);
            player.seekTo(position);
            player.start();

            Toast.makeText(this, "재시작됨.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAudio() {
        if(player != null && player.isPlaying()){
            player.stop();

            Toast.makeText(this, "중지됨.", Toast.LENGTH_SHORT).show();
        }
    }


    public static String[] StringToArray(String args) {
        // 1. 배열로 변환할 문자열
        String str = args;

        // 2. 배열 생성 (문자열 길이)
        int[] arr = new int[8];

        str = str.replace("[", "");
        str = str.replace("]", "");
        str = str.replace(",", "");
        String[] result = str.split(" ");

        return result;
    }


}