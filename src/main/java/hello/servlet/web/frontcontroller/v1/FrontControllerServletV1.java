package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

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
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    // key: 매핑 URL / value: 호출될 컨트롤러
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    // 서블릿이 생성이 될때 생성자를 통해 값을 넣어준다.
    // 매핑 하는것
    public FrontControllerServletV1() {
        // /front-controller/v1/members/new-form 요청이 들어오면 new MemberFormControllerV1()을 만들어준다.
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        // 요청 주소값을 다 받을수 있음 ex. 8080/front-controller/v1에서 front-controller/v1값 추출가능
        String requestURI = request.getRequestURI();

        // controller은 찾은 객체 인스턴스
        ControllerV1 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 다형성에 의해 오버라이딩된 메서드가 호출된다 !!!!!
        // 만약 /front-controller/v1/members 객체 인스턴스라면 MemeberListControllerV1 process 메서드가 호출되겠지
        controller.process(request, response);
    }
}
