package com.pedrogomes.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.pedrogomes.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks")) {
            // Get authentication
            var auth = request.getHeader("Authorization");
            var encodedToken = auth.substring("Basic".length()).trim();

            byte[] decodedToken = Base64.getDecoder().decode(encodedToken);
            var tokenString = new String(decodedToken);
            String[] credentials = tokenString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // Validate data
            var user = this.userRepository.findByUsername(username);

            if (user != null) {
                var pwdVerification = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (pwdVerification.verified) {
                    request.setAttribute("userId", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            } else {
                response.sendError(401);
            }
        }
    }
}
