package com.example.Spring_AI_Workshop.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
	private final OpenAiImageModel imageModel;

	public ImageService(OpenAiImageModel imageModel) {
		this.imageModel = imageModel;
	}

	public String generateImage(String prompt) {
		OpenAiImageOptions imageOptions = OpenAiImageOptions.builder().quality("auto").N(1).height(1024).width(1024)
				.build();

		ImageResponse response = imageModel.call(new ImagePrompt(prompt, imageOptions));
		String imageString= response.getResult().getOutput().getB64Json();
		byte[] imageByte = Base64.getDecoder().decode(imageString);
		
		try (FileOutputStream f = new FileOutputStream("June08Image.png")) {
			f.write(imageByte);
			System.out.println("Image file saved as output.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Image generation successful. Check png file.";
		
				}
}
