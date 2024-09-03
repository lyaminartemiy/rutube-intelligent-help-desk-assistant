//package org.example.auth;
//
//import com.invexion.auth.model.exception.AuthServiceException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ProblemDetail;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestControllerAdvice
//public class AuthControllerAdvice {
//
//    @ExceptionHandler(AuthServiceException.class)
//    public ResponseEntity<ProblemDetail> handleAuthServiceException(AuthServiceException e) {
//
//        ProblemDetail problemDetail;
//        if (e.getCause() instanceof AuthenticationException) {
//            problemDetail = ProblemDetail.forStatusAndDetail(
//                    HttpStatus.UNAUTHORIZED,
//                    String.join(" | ", getExceptionMessageChain(e))
//            );
//            problemDetail.setTitle(e.getMessage());
//        }
//        else if (e.getCause() instanceof UsernameNotFoundException) {
//            problemDetail = ProblemDetail.forStatusAndDetail(
//                    HttpStatus.NOT_FOUND,
//                    String.join(" | ", getExceptionMessageChain(e))
//            );
//            problemDetail.setTitle(e.getMessage());
//        }
//        else {
//            problemDetail = ProblemDetail.forStatusAndDetail(
//                    HttpStatus.BAD_REQUEST,
//                    String.join(" | ", getExceptionMessageChain(e))
//            );
//            problemDetail.setTitle(e.getMessage());
//        }
//        return ResponseEntity.of(problemDetail).build();
//    }
//
//    public static List<String> getExceptionMessageChain(Throwable throwable) {
//        List<String> result = new ArrayList<>();
//        while (throwable != null) {
//            result.add(throwable.getMessage());
//            throwable = throwable.getCause();
//        }
//        return result;
//    }
//}
