package d1w3u5.d1w3u5.exceptions;
import d1w3u5.d1w3u5.controllers.DipendenteController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice(assignableTypes = DipendenteController.class)
public class DipendenteControllerAdvice {

    @ExceptionHandler(DipendenteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleDipendenteNotFoundException(DipendenteNotFoundException ex, WebRequest request) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleInvalidEmailException(InvalidEmailException ex, WebRequest request) {
        // Ensure that the message is not prefixed again with "Email non valida:"
        return new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handleGeneralException(Exception ex, WebRequest request) {
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Errore generico: " + ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleMissingServletRequestPartException(MissingServletRequestPartException ex, WebRequest request) {
        return new ApiError(HttpStatus.BAD_REQUEST.value(), "Parte della richiesta mancante: " + ex.getRequestPartName(), request.getDescription(false));
    }

    public static class ApiError {
        private int status;
        private String message;
        private String path;

        public ApiError(int status, String message, String path) {
            this.status = status;
            this.message = message;
            this.path = path;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
