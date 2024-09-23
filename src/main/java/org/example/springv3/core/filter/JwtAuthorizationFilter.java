package org.example.springv3.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.springv3.core.util.JwtUtil;
import org.example.springv3.core.util.Resp;
import org.example.springv3.user.User;

import java.io.IOException;
import java.io.PrintWriter;

// 책임 : 인가
// 인증된 사람만 들어오게 하는게 인가
// 토큰이 있는지 없는지 인가해주는 이 필터만 있으면 된다.( 인가필터 )
public class JwtAuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 매서드를 더 많이 가진 자식 인터페이스로 다운 캐스팅을 해준다. ServletRequest (부모) -> HttpServletRequest (자식)
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String accessToken = req.getHeader("Authorization"); // 요청헤더에서 token을 꺼낸다.

        if (accessToken == null || accessToken.isBlank()) { // 토큰이 있으면 ( isEmpty 말고 isBlank 써주기 )
            // resp 해줘야 함
            System.out.println("토큰이 없어요");
            //resp.setHeader("Content-Type", "application/json; charset=utf-8");
            resp.setContentType("application/json; charset=utf-8"); // setHeader랑 같은거
            PrintWriter out = resp.getWriter(); // 버퍼에 쏜다
            Resp fail = Resp.fail(401, "토큰이 없어요"); // 인가가 안되는 거지만 근본적인 원인은 인증이 안된거니까 401 로 터트린다.
            // 여기서는 응답하고 끝내야하니까 doFilter를 사용하지 않는다.
            String responseBody = new ObjectMapper().writeValueAsString(fail); // 객체를 문자열로 바꿔준다. (Onject -> Json)

            out.println(responseBody);
            out.flush();
            return; // 여기서 return 안하면 코드가 밑으로 내려간다. else가 있으면 return이 안필요한데 else안 쓰고 쓰려니까 reutrn 해줘야함

        }

        // verify할 때 토큰이 위조되었거나 만료되었을 때 예외가 발생함
        // try catch로 제어해야 한다.
        // 항상 동일한 응답구조를 만들기 위해 try catch로 잡아준다.
        try {
            User sessionUser = JwtUtil.verify(accessToken); // 검증 로직으로 토큰 검증
            HttpSession session = req.getSession();
            session.setAttribute("sessionUser", sessionUser); // session에 id랑 username만 들어간다.
            filterChain.doFilter(req, resp); // 다음체인으로 넘어가라. 없으면 dispatcher servlet으로 간다.
        } catch (Exception e) {
            //resp.setHeader("Content-Type", "application/json; charset=utf-8");
            resp.setContentType("application/json; charset=utf-8"); // setHeader랑 같은거
            PrintWriter out = resp.getWriter();
            Resp fail = Resp.fail(401, e.getMessage()); // 근본적인 원인은 인증이 안된거니까 401 로 터트린다.

            String responseBody = new ObjectMapper().writeValueAsString(fail); // 객체를 문자열로 바꿔준다.

            out.println(responseBody);
            out.flush();
        }


    }


}
