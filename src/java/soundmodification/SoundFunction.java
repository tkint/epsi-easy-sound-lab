/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundmodification;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Eric-PC
 */
public class SoundFunction {

    // Durée d'enregistrement en millisecondes
    //static final long RECORD_TIME = 120000;  // 1 minute
    // Chemin du fichier audio à enregistrer
    //File wavFile = new File("C:/Users/Eric-PC/Desktop/testSound/voiceAwsomeGood.wav");
    //File wavFile = new File(AbsolutePath);
    public static Clip clip;

    // format du fichier audio
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    // la ligne à partir de laquelle l'audio est capturé
    TargetDataLine line;

    public void recordAudio(String AbsolutePath, long RECORD_TIME) {

        final SoundFunction recorder = new SoundFunction();

        Thread stopper = new Thread(() -> {
            try {
                Thread.sleep(RECORD_TIME);
            } catch (InterruptedException ex) {

                Logger.getLogger(SoundFunction.class.getName()).log(Level.SEVERE, null, ex);
            }
            recorder.finish();
        });

        stopper.start();

        recorder.start(AbsolutePath);
    }

    /**
     * Capture le son et l'enregistre en format wav
     */
    void start(String AbsolutePath) {

        File wavFile = new File(AbsolutePath);

        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            //vérifie si le système supporte la data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Format Audio non supporté");
                System.exit(0);
            }

            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("Début enregistrement...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Enregistrement en cours...");

            // start recording
            AudioSystem.write(ais, fileType, wavFile);

        } catch (LineUnavailableException | IOException ex) {

            //ex.printStackTrace();
            Logger.getLogger(SoundFunction.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    /**
     * Ferme la data line ciblée pour finir l'enregistrement.
     */
    void finish() {
        line.stop();
        line.close();
        System.out.println("Terminé");
    }

    public static void copyCutAudio(String sourceFileName, String destinationFileName, int startSecond, int secondsToCopy) {

        AudioInputStream inputStream = null;
        AudioInputStream shortenedStream = null;

        try {
            File file = new File(sourceFileName); // Fichier d'origine

            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file); // Format du Ficher

            AudioFormat format = fileFormat.getFormat(); // Format audio 

            inputStream = AudioSystem.getAudioInputStream(file);

            int bytesPerSecond = format.getFrameSize() * (int) format.getFrameRate();

            inputStream.skip(startSecond * bytesPerSecond);

            long framesOfAudioToCopy = secondsToCopy * (int) format.getFrameRate();

            shortenedStream = new AudioInputStream(inputStream, format, framesOfAudioToCopy);

            File destinationFile = new File(destinationFileName);

            AudioSystem.write(shortenedStream, fileFormat.getType(), destinationFile);

        } catch (IOException | UnsupportedAudioFileException e) {

            Logger.getLogger(SoundFunction.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Logger.getLogger(SoundFunction.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if (shortenedStream != null) {
                try {
                    shortenedStream.close();
                } catch (IOException e) {

                    Logger.getLogger(SoundFunction.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    public void changeSampleSize(String FilePath, Integer sampleSize) {

        try {

            final File file1 = new File(FilePath);

            //Fichier de destination
            final File file2 = new File(FilePath + ""); //FilePath + ""

            AudioInputStream in1 = null;
            AudioFormat inFormat = null;

            try {
                in1 = getAudioInputStream(file1);
                inFormat = getOutFormat(in1.getFormat(), sampleSize);

            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(SoundFunction.class.getName()).log(Level.SEVERE, null, ex);
            }
            //get audio format for targetted sound

            final AudioInputStream in2 = getAudioInputStream(inFormat, in1);
            AudioSystem.write(in2, AudioFileFormat.Type.WAVE, file2);

        } catch (IOException ex) {
            Logger.getLogger(SoundFunction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public AudioFormat getOutFormat(AudioFormat inFormat, Integer sampleSize) {
        int ch = inFormat.getChannels();
        float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, sampleSize, 16, ch, ch * 2, rate,
                inFormat.isBigEndian());
    }

    /**
     * Définit le format audio
     */
    AudioFormat getAudioFormat() {

        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;

        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

}
