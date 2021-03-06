package com.groupdocs.metadata.examples;

import com.groupdocs.metadata.*;
import com.groupdocs.metadata.examples.Utilities.Common;
import org.apache.commons.io.FileUtils;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import static com.groupdocs.metadata.MetadataKey.*;

public class AudioFormats {
	public static class Mp3 {
		private static String filepath = "\\Audio\\Mp3\\sample.mp3";
		private static String directory = "\\Audio\\Mp3\\";
		private static String outputPath = "\\Document\\Xls\\result.xlsx";

		public static void exportMetadataToExcel() throws IOException {
			// get all files
			File folder = new File(Common.mapSourceFilePath(directory));
			File[] files = folder.listFiles();
			for (File path : files) {// export to excel
				byte[] content = ExportFacade.exportToExcel(path.getAbsolutePath());
				// write data to the file
				FileUtils.writeByteArrayToFile(new File(Common.mapDestinationFilePath(outputPath)), content);
			}
			System.out.printf("File has been exported");
		}

		public static void detectMp3Format() {
			// get all files using Directory.GetFiles approach
			File folder = new File(Common.mapSourceFilePath(directory));
			File[] files = folder.listFiles();

			for (File path : files) {
				// detect format
				FormatBase format = FormatFactory.recognizeFormat(path.getAbsolutePath());
				if (format == null) {
					// skip unsupported format
					continue;
				}

				if (format.getType() == DocumentType.Mp3) {
					System.out.printf("File %s has MP3 format", path.getName());
				}
			}
		}

		public static void readMPEGAudioInfo() {
			// get MPEG audio info
			MpegAudio audioInfo = (MpegAudio) MetadataUtility
					.extractSpecificMetadata(Common.mapSourceFilePath(filepath), MetadataType.MpegAudio);
			// another approach is to use Mp3Format directly:

			// init Mp3Format class
			// Mp3Format mp3Format = new
			// Mp3Format((Common.mapSourceFilePath(filepath)));

			// get MPEG audio info
			// MpegAudio audioInfo = mp3Format.getAudioDetails();

			// display MPEG audio version
			System.out.printf("MPEG audio version: %s", audioInfo.getMpegAudioVersion());
			// display layer version
			System.out.printf("Layer version: %s", audioInfo.getLayerVersion());
			// display header offset
			System.out.printf("Header offset: %s", audioInfo.getHeaderPosition());
			// display bitrate
			System.out.printf("Bitrate: %s", audioInfo.getBitrate());
			// display frequency
			System.out.printf("Frequency: %s", audioInfo.getFrequency());
			// display channel mode
			System.out.printf("Channel mode: %s", audioInfo.getChannelMode());
			// display original bit
			System.out.printf("Is original: %s", audioInfo.isOriginal());
			// display protected bit
			System.out.printf("Is protected: %s", audioInfo.isProtected());
		}

		public static void readID3v1Tags() {
			// initialize Mp3Format class
			Mp3Format mp3Format = new Mp3Format((Common.mapSourceFilePath(filepath)));

			// get ID3v1 tag
			Id3v1Tag id3V1 = mp3Format.getId3v1();

			// NOTE: please remember you may use different approaches to getting
			// metadata

			// second approach
			// id3V1 =
			// (Id3v1Tag)MetadataUtility.extractSpecificMetadata(Common.mapSourceFilePath(filepath),
			// MetadataType.Id3v1);

			// check if ID3v1 is presented. It could be absent in Mpeg file.
			if (id3V1 != null) {
				// Display version
				System.out.printf("ID3v1 version: %s", id3V1.getVersion());

				// Display tag properties
				System.out.printf("Album: %s", id3V1.getAlbum());
				System.out.printf("Artist: %s", id3V1.getArtist());
				System.out.printf("Comment: %s", id3V1.getComment());
				System.out.printf("Genre: %s", id3V1.getGenre());
				System.out.printf("Title: %s", id3V1.getTitle());
				System.out.printf("Year: %s", id3V1.getYear());

				if (id3V1.getVersion() == "ID3v1.1") {
					// Track number is presented only in ID3 v1.1
					System.out.printf("Track number: %s", id3V1.getTrackNumber());
				}
			}
		}

		public static void updateID3v1Tags() {
			// initialize Mp3Format class
			Mp3Format mp3Format = new Mp3Format((Common.mapSourceFilePath(filepath)));

			// create id3v1 tag
			Id3v1Tag id3Tag = new Id3v1Tag();

			// set artist
			id3Tag.setArtist("A-ha");

			// set title
			id3Tag.setTitle("Take on me");

			// update ID3v1 tag
			mp3Format.updateId3v1(id3Tag);

			// and commit changes

			mp3Format.save();
		}

		public static void readID3v2Tags() {
			// initialize Mp3Format class
			Mp3Format mp3Format = new Mp3Format((Common.mapSourceFilePath(filepath)));

			// get ID3 v2 tag
			Id3v2Tag id3v2 = mp3Format.getId3v2();
			if (id3v2 != null) {
				// write ID3v2 version
				System.out.printf("Version: %s", id3v2.getVersion());

				// write known frames' values
				System.out.printf("Title: %s", id3v2.getTitle());
				System.out.printf("Artist: %s", id3v2.getArtist());
				System.out.printf("Album: %s", id3v2.getAlbum());
				System.out.printf("Comment: %s", id3v2.getComment());
				System.out.printf("Composers: %s", id3v2.getComposers());
				System.out.printf("Band: %s", id3v2.getBand());
				System.out.printf("Track Number: %s", id3v2.getTrackNumber());
				System.out.printf("Year: %s", id3v2.getYear());

				// in trial mode only first 5 frames are available
				TagFrame[] idFrames = id3v2.getFrames();

				for (TagFrame tagFrame : idFrames) {
					System.out.printf("Frame: %s, value: %s", tagFrame.getName(), tagFrame.getFormattedValue());
				}
			}
		}

		public static void updateID3v2Tags() {
			// initialize Mp3Format class
			Mp3Format mp3Format = new Mp3Format((Common.mapSourceFilePath(filepath)));

			// get id3v2 tag
			Id3v2Tag id3Tag = mp3Format.getId3v2();

			// set artist
			id3Tag.setArtist("A-ha");

			// set title
			id3Tag.setTitle("Take on me");

			// set band
			id3Tag.setBand("A-ha");

			// set comment
			id3Tag.setComment("GroupDocs.Metadata creator");

			// set track number
			id3Tag.setTrackNumber("5");

			// set year
			id3Tag.setYear("1986");

			// update ID3v2 tag
			mp3Format.updateId3v2(id3Tag);

			// and commit changes
			mp3Format.save();
		}

		public static void removeID3v2Tags() {
			// init Mp3Format class
			Mp3Format mp3Format = new Mp3Format((Common.mapSourceFilePath(filepath)));

			// remove ID3v2 tag
			mp3Format.removeId3v2();

			// and commit changes
			mp3Format.save();
		}

		public static void readLayrics3Tags() {
			// initialize Mp3Format class
			Mp3Format mp3Format = new Mp3Format((Common.mapSourceFilePath(filepath)));

			// get Lyrics3 v2.00 tag
			Lyrics3Tag lyrics3Tag = mp3Format.getLyrics3v2();

			// check if Lyrics3 is presented. It could be absent.
			if (lyrics3Tag != null) {
				// Display defined tag values
				System.out.printf("Album: %s", lyrics3Tag.getAlbum());
				System.out.printf("Artist: %s", lyrics3Tag.getArtist());
				System.out.printf("Track: %s", lyrics3Tag.getTrack());

				// get all fields presented in Lyrics3Tag
				Lyrics3Field[] allFields = lyrics3Tag.getFields();

				for (Lyrics3Field lyrics3Field : allFields) {
					System.out.printf("Name: %s, value: %s", lyrics3Field.getName(), lyrics3Field.getValue());

				}
			}
		}

		public static void removeLayrics3Tags() {
			// initialize Mp3Format (If file is not Mp3 then appropriate
			// exception will be thrown)
			Mp3Format mp3Format = new Mp3Format(Common.mapSourceFilePath(filepath));

			// and remove LYRICS3 tag
			mp3Format.removeLyrics3v2();

			// save file
			mp3Format.save();
		}

		public static void readId3MetadataDirectly() {

			// init Mp3Format class
			Mp3Format mp3Format = new Mp3Format(Common.mapSourceFilePath(filepath));

			// read album in ID3 v1
			System.out.printf(mp3Format.getId3v1().getAlbum());

			// read title in ID3 v2
			System.out.printf("Title: %", mp3Format.getId3v2().getTitle());

		}

		public static void readApev2Tags() {
			// initialize Mp3Format. If file is not Mp3 then appropriate
			// exception will throw.
			Mp3Format mp3Format = new Mp3Format(Common.mapSourceFilePath(filepath));

			// get APEv2 tag
			Apev2Metadata apev2 = mp3Format.getAPEv2();

			// NOTE: please remember you may use different approaches to getting
			// metadata

			// second approach
			apev2 = (Apev2Metadata) MetadataUtility.extractSpecificMetadata(Common.mapSourceFilePath(filepath),
					MetadataType.APEv2);

			// check if APEv2 tag is presented
			if (apev2 != null) {
				// Display tag properties
				System.out.printf("Album: %s", apev2.getAlbum());
				System.out.printf("Artist: %s", apev2.getArtist());
				System.out.printf("Comment: %s", apev2.getComment());
				System.out.printf("Genre: %s", apev2.getGenre());
				System.out.printf("Title: %s", apev2.getTitle());
				System.out.printf("Track: %s", apev2.getTrack());
			}
		}

		public static void removeApev2Tags() {
			// initialize Mp3Format (If file is not Mp3 then appropriate
			// exception will throw)
			Mp3Format mp3Format = new Mp3Format(Common.mapSourceFilePath(filepath));

			// remove LYRICS3 tag
			mp3Format.removeAPEv2();

			// and commit changes
			mp3Format.save();
		}

		public static void cleanMetadata() {
			// init Mp3Format class
			Mp3Format mp3Format = new Mp3Format(Common.mapSourceFilePath(filepath));

			// clean metadata
			mp3Format.cleanMetadata();

			// and commit changes
			mp3Format.save();
		}
	}

	public static class Wav {
		private static String filepath = "\\Audio\\Wav\\sample.wav";
		private static String directory = "\\Audio\\Wav\\";
		private static String outputPath = "\\Document\\Xls\\result.xlsx";

		public static void detectWavFormat() {
			// Get files from directory
			File folder = new File(Common.mapSourceFilePath(directory));
			File[] files = folder.listFiles();
			for (File path : files) {
				// detect format
				FormatBase format = FormatFactory.recognizeFormat(path.getAbsolutePath());
				if (format == null) {
					// skip unsupported format
					continue;
				}

				if (format.getType() == DocumentType.Wav) {
					System.out.printf("File %s has WAV format", path.getName());
				}
			}
		}

		public static void readAudioDetails() {
			// initialize WavFormat class
			WavFormat wavFormat = new WavFormat((Common.mapSourceFilePath(filepath)));

			// get audio info
			WavAudioInfo audioInfo = wavFormat.getAudioInfo();

			// display bits per sample
			System.out.printf("Bits per sample: %s", audioInfo.getBitsPerSample());
			// display audio format version
			System.out.printf("Audio format: %s", audioInfo.getAudioFormat());
			// display number of channels
			System.out.printf("Number of channels: %s", audioInfo.getNumberOfChannels());
			// display sample rate
			System.out.printf("Sample rate: %s", audioInfo.getSampleRate());
		}
	}
}
