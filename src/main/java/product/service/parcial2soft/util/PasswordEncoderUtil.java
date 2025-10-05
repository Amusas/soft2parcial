package product.service.parcial2soft.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderUtil() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Encripta una contraseña en texto plano
     * @param plainPassword contraseña en texto plano
     * @return contraseña encriptada
     */
    public String encryptPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    /**
     * Verifica si una contraseña en texto plano coincide con la contraseña encriptada
     * @param plainPassword contraseña en texto plano
     * @param encryptedPassword contraseña encriptada
     * @return true si coinciden, false en caso contrario
     */
    public boolean matches(String plainPassword, String encryptedPassword) {
        return passwordEncoder.matches(plainPassword, encryptedPassword);
    }
}