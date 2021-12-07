package emgc.randomlunch.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public static int getHeight(BufferedImage image) {
		return image.getHeight();
	}

	public static int getWidth(BufferedImage image) {
		return image.getWidth();
	}

	public static BufferedImage toBufferedImage(MultipartFile file) throws IOException {
		return ImageIO.read(file.getInputStream());
	}

	public static String getExtension(MultipartFile file) {
		String originalName = getOriginFullName(file);
		return originalName.substring(originalName.lastIndexOf(".") + 1);
	}

	public static String getOriginalName(MultipartFile file) {
		String originalName = getOriginFullName(file);
		return originalName.substring(0, originalName.lastIndexOf("."));
	}

	public static String getOriginFullName(MultipartFile file) {
		return file.getOriginalFilename();
	}

	public static String saveFile(MultipartFile file, String path) throws IOException {
		String extension = getExtension(file);
		UUID uuid = UUID.randomUUID();
		String saveName = uuid + "_" + LocalDate.now() + "." + extension;
		file.transferTo(new File(path, saveName));
		return saveName;
	}
}
