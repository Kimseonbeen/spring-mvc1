package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 파라미터 전송 기능
 * http://localhost:8080/request-param?username=hello&age=20
 *
 */

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[전체 파라미터조회] - start");

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName)));

        System.out.println("[전체 파라미터조회] - end");
        System.out.println();

        /**
         * request.getParameter()는 GET URL 쿼리 파리미터 형식도 지원하고, POST HTML Form 형식도 둘 다 지원한다.
         *
         * 참고 !
         * content-type은 HTTP 메시지 바디의 데이터 형식을 지정한다.
         * GET URL 쿼리 파라미터 형식으로 클라이언트에서 서버로 데이터를 전달할 때는 HTTP 메시지 바디를 사욯하지 않기 때문에
         * content-type이 없다
         *
         * POST HTML Form 형식으로 데이터를 전달하면 HTTP 메시지 바디에 해당 데이터를 포함해서 보내기 때문에 바디에
         * 포함된 데이터가 어떤 형식인지 content-type을 꼭 지정해야한다. 이렇게 폼으로 데이터를 전송하는 형식을
         * 'application/x-www-form-urlencoded'라 한다.
         */
        System.out.println("[단일 파리미터 조회]");
        String username = request.getParameter("username");
        String age = request.getParameter("age");

        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println();

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        // http://localhost:8080/request-param?username=hello&age=20&username=hello2 조회 시
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("name = " + name);
        }

        response.getWriter().write("OK !");


    }
}
