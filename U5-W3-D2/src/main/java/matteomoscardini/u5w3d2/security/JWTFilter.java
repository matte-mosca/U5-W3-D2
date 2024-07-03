package matteomoscardini.u5w3d2.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import matteomoscardini.u5w3d2.entities.Employee;
import matteomoscardini.u5w3d2.exceptions.UnauthorizedException;
import matteomoscardini.u5w3d2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) throw new UnauthorizedException("Per favore inserisci il token nell'Authorization Header");
        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);

        String id = jwtTools.extractIdFromToken(accessToken);
        Employee currentEmployee = this.employeeService.findEmployeeById(Integer.parseInt(id));
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentEmployee, null, currentEmployee.getAuthorities());

        filterChain.doFilter(request, response);
    }

      @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
          return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
