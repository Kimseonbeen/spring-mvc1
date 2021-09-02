package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

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
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    // key: 매핑 URL / value: 호출될 컨트롤러
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    // 서블릿이 생성이 될때 생성자를 통해 값을 넣어준다.
    // 매핑 하는것
    public FrontControllerServletV3() {
        // /front-controller/v1/members/new-form 요청이 들어오면 new MemberFormControllerV3()을 만들어준다.
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        // 요청 주소값을 다 받을수 있음 ex. 8080/front-controller/v1에서 front-controller/v1값 추출가능
        String requestURI = request.getRequestURI();

        // controller은 찾은 객체 인스턴스
        ControllerV3 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap

        Map<String, String> paramMap = createParamMap(request);
        // {age=20, username=test1}
        System.out.println("paramMap = " + paramMap);
        ModelView mv = controller.process(paramMap);
        System.out.println("mv = " + mv);

        String viewName = mv.getViewName(); // 논리이름 new-form
        MyView view = viewResolver(viewName);

        /**
         * 뷰 객체를 통해서 HTML 화면을 렌더링 한다.
         * 뷰 객체의 'render()'는 모델 정보도 함께 받는다.
         * JSP는 request.getAttribute()로 데이터를 조회하기 때문에, 모델의 데이터를 꺼내서
         * request.setAtrribute()로 담아둔다.
         * JSP로 포워드 해서 JSP를 렌더링 한다.
         */
        view.render(mv.getModel(), request, response);
    }

    /**
     * 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경한다. 그리고 실제 물리 경로가 있는 MyView 객체를 반환
     * 논리 뷰 이름 : 'members' List 조회 경우
     * 물리 뷰 이름 : 'WEB-INF/views/member.jsp'
     */
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        // 모든 파라미터 요청을 꺼냄
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
