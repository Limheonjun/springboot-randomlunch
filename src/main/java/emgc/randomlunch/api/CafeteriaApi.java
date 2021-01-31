package emgc.randomlunch.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cafeteria")
public class CafeteriaApi {

    @GetMapping("/test")
    public String test() {
        return "안녕하세요";
    }
}
