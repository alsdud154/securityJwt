package kr.co.velnova.securityjwt.filter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import kr.co.velnova.securityjwt.jwt.CustomAuthentication;
import kr.co.velnova.securityjwt.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String jwt = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        String type = ((HttpServletRequest) request).getHeader("type");
        String instanceName = null;

        if ("user".equals(type)) {
            instanceName = "userFirebaseApp";
        } else {
            instanceName = "adminFirebaseApp";
        }

        FirebaseToken decodedToken = FirebaseAuth.getInstance(FirebaseApp.getInstance(instanceName)).verifyIdToken(jwt);

        Map<String, Object> claims = decodedToken.getClaims();

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add((GrantedAuthority) () -> "ROLE_USER");
        grantedAuthorities.add((GrantedAuthority) () -> "ROLE_ADMIN");
//        grantedAuthorities.add((GrantedAuthority) () -> "ROLE_SUPER");

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(claims, "", grantedAuthorities));

        chain.doFilter(request, response);
    }

}