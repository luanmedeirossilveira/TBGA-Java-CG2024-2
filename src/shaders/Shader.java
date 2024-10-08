package shaders;

import javax.vecmath.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int programId;

    public Shader(String vertexPath, String fragmentPath) {
        programId = glCreateProgram();
        int vertexShader = carregarShader(vertexPath, GL_VERTEX_SHADER);
        int fragmentShader = carregarShader(fragmentPath, GL_FRAGMENT_SHADER);

        glAttachShader(programId, vertexShader);
        glAttachShader(programId, fragmentShader);
        glLinkProgram(programId);
        glValidateProgram(programId);
    }

    private int carregarShader(String caminho, int tipo) {
        String codigoShader = lerArquivo(caminho);
        System.out.println("Compilando shader: " + caminho);

        int idShader = glCreateShader(tipo);

        glShaderSource(idShader, codigoShader);
        glCompileShader(idShader);

        if (glGetShaderi(idShader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Erro ao compilar shader: " + glGetShaderInfoLog(idShader));
        }

        return idShader;
    }

    private String lerArquivo(String caminho) {
        try {
            String codigo = new String(Files.readAllBytes(Paths.get(caminho)));
            System.out.println("Shader lido com sucesso: " + caminho);
            return codigo;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao ler arquivo de shader: " + caminho);
        }
    }

    public void usar() {
        glUseProgram(programId);
    }

    public void definirUniformeMatrix4f(String nome, Matrix4f matriz) {
        int local = glGetUniformLocation(programId, nome);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);

            fb.put(matriz.m00); fb.put(matriz.m01); fb.put(matriz.m02); fb.put(matriz.m03);
            fb.put(matriz.m10); fb.put(matriz.m11); fb.put(matriz.m12); fb.put(matriz.m13);
            fb.put(matriz.m20); fb.put(matriz.m21); fb.put(matriz.m22); fb.put(matriz.m23);
            fb.put(matriz.m30); fb.put(matriz.m31); fb.put(matriz.m32); fb.put(matriz.m33);

            fb.flip();

            glUniformMatrix4fv(local, false, fb);
        }
    }
}
