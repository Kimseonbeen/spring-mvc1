package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * WEB-INF : 이 경로안에 JSP가 있으면 외부에서 직접 JSP를 호출할 수 없다.
         * 우리가 기대하는 것은 항상 컨트롤러를 통해서 JSP를 호출하는것
         * 직접 경로 호출이 오류남
         */
        String viewPath = "/WEB-INF/views/new-form.jsp";
        /**
         *  getRequestDispatcher() : controller -> view 이동 시 사용
         */
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        /**
         *  forward()를 하면 이제 진짜 servlet에서 jsp 호출 !
         *  다른 서블릿이나 JSP로 이동할 수 있는 기능. 서버 내부에서 다시 호출이 발생한다.
         */
        dispatcher.forward(request, response);
    }
}
