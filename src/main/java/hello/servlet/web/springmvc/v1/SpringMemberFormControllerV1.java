package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberFormControllerV1 {
    /**
     * 'RequestMappingHandlerMapping은 스프링 빈 중에서 @RequestMapping 또는 @Controller가 클래스
     * 레벨에 붙어 있는 경우에 매핑 정보로 인식한다.
     */
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView processs() {
        return new ModelAndView("new-form");
    }
}
