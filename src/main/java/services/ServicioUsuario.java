package services;

import entidades.Usuario;
import org.jasypt.util.text.AES256TextEncryptor;

import java.util.List;

public class ServicioUsuario extends GestionBD<Usuario> {


    private static ServicioUsuario instancia;

    private ServicioUsuario() {
        super(Usuario.class);
    }

    public static ServicioUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ServicioUsuario();
        }
        return instancia;
    }


    public List<Usuario> listaUsuarios() {
        return super.buscarTodos();
    }

    public Usuario getUsuario(String usuario) {
        return buscar(usuario);
    }

    public Usuario crearUsuario(Usuario aux) {
        Usuario usuario = encryptacionUsuario(aux);
        return super.crear(usuario);
    }

    public Usuario encryptacionUsuario(Usuario usuario) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        String myEncryptionPassword = "admin";
        textEncryptor.setPassword(myEncryptionPassword);
        String myEncryptedText = textEncryptor.encrypt(usuario.getPassword());
        usuario.setPassword(myEncryptedText);
        return usuario;
    }

    public Usuario autenticarUsuario(String usuario, String password) {

        if (this.getUsuario(usuario) != null) {
            if (desincryptar(this.getUsuario(usuario).getPassword()).equals(password)) {
                return this.getUsuario(usuario);
            }
        } else {
            return null;
        }

        return null;

    }

    public String desincryptar(String password) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        String myEncryptionPassword = "admin";
        textEncryptor.setPassword(myEncryptionPassword);
        String myDecryptedText = textEncryptor.decrypt(password);
        return myDecryptedText;
    }
}

