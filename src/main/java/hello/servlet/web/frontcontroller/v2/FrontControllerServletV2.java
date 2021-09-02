package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * urlPatterns = v1/* : /front-controller/v1을 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.
 */
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    // key: 매핑 URL / value: 호출될 컨트롤러
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    // 서블릿이 생성이 될때 생성자를 통해 값을 넣어준다.
    // 매핑 하는것
    public FrontControllerServletV2() {
        // /front-controller/v1/members/new-form 요청이 들어오면 new MemberFormControllerV1()을 만들어준다.
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        // 요청 주소값을 다 받을수 있음 ex. 8080/front-controller/v1에서 front-controller/v1값 추출가능
        String requestURI = request.getRequestURI();

        // controller은 찾은 객체 인스턴스
        ControllerV2 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
