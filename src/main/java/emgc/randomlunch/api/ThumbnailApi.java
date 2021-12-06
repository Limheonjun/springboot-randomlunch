package emgc.randomlunch.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emgc.randomlunch.service.function.ThumbnailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("thumbnails")
public class ThumbnailApi {

	private ThumbnailService thumbnailService;

}
