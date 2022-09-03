package com.lion.tank;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio extends Thread {
	private AudioFormat audioFormat;

	private SourceDataLine sourceDataLine;
	private DataLine.Info dataLine_info;
	private AudioInputStream audioInputStream;
	
	public Audio(String filePath)  {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
			audioFormat = audioInputStream.getFormat();

			dataLine_info = new DataLine.Info(SourceDataLine.class, audioFormat);

			sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLine_info);
		
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		
	}

	@Override
	public void run() {
		try {
			byte[] b=new byte[1024];
			int len=0;
			sourceDataLine.open(audioFormat,1024);
			sourceDataLine.start();
			while((len=audioInputStream.read(b))>0) {
				sourceDataLine.write(b,0,len);
			}
			audioInputStream.close();
			sourceDataLine.drain();
			sourceDataLine.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
