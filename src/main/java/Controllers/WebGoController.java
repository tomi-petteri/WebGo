package Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebGoController {
	@RequestMapping("/webgo")
    public String webgo(Model model) {
        return "webgoview";
    }
}
