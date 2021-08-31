package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// HttpServletRequest 역할
// HTTP 요청 메세지를 개발자가 직접 파싱해서 사용해도 되지만, 매우 불편
// 서블릿은 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에 HTTP 요청 메시지를 파싱
// 그리고 그 결과를 HTTPServletRequest 객체에 담아서 제공한다.

// 또한 임시 저장소 기능
// 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능
// 저장 : request.setAttribute(name, value)
// 조회 : request.getAttribute(name)

// 세션 관리 기능
// request.getSession(create: true);

// 중요 !!!
// HttpServletRequest, HttpServletResponse를 사용할 때 가장 중요한 점은 이 객체들이 HTTP 요청 메세지,
// HTTP 응답 메시지를 편리하게 사용하도록 도와주는 객체라는 점이다.
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    // ctrl + o 로 service 찾으면 자동코드 됌
    // HTTP 요청을 통해 매핑된 URL이 호출되면 서블릿 컨테이너는 밑의 메서드를 실행한다.
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + username);
    }
}
